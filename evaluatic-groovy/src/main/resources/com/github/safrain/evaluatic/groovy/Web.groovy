package com.github.safrain.evaluatic.groovy

import com.github.safrain.evaluatic.support.RuntimeSupport
import com.github.safrain.evaluatic.support.ServletSupport

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class Web {
    final static defaultTemplateEngine = new InheritableThreadLocal<String>()
    final static templateEngines = new InheritableThreadLocal<Map>()
    final static runnableExtensions = new InheritableThreadLocal<List>()
    final static extensionMapping = new InheritableThreadLocal<Map>()
    final static prefix = new InheritableThreadLocal<String>()

    static void print(obj) {
        ServletSupport.response.writer.print(obj)
    }

    static void println(obj) {
        ServletSupport.response.writer.println(obj)
    }

    static void mav(String viewName, Map model, String engine = null) {
        String name
        if (prefix.get() != null) {
            name = prefix.get() + '/' + viewName
        } else {
            name = viewName
        }
        def template = RuntimeSupport.loadString(name)
        def templateEngine = templateEngines.get()[engine == null ? defaultTemplateEngine.get() : engine]
        Web.print templateEngine(template, model)
    }

    static void redirect(String path) {
        ServletSupport.response.sendRedirect(path)
    }

    static processRequest() {
        String name

        if (prefix.get() != null) {
            name = prefix.get() + '/' + RuntimeSupport.sourceName
        } else {
            name = RuntimeSupport.sourceName
        }

        if (runnableExtensions.get().find { name.endsWith('.' + it) } != null) {
            RuntimeSupport.eval name
        } else {
            String header = extensionMapping.get().find { k, v -> name.endsWith('.' + k) }?.value
            if (header != null) {
                response.setHeader('Content-Type', header)
            }
            Web.print RuntimeSupport.loadString(name)
        }
    }

    static void configAsDefault() {
        Web.defaultTemplateEngine.set('velocity')
        Web.templateEngines.set(['velocity': { String template, Map<String, Object> model ->
            Velocity.render(template, model)
        }])
        Web.runnableExtensions.set(['groovy'])
        Web.extensionMapping.set([
                'vm': 'text/html',
                'html': 'text/html',
                'htm': 'text/html',
                'json': 'application/json',
                'js': 'text/javascript',
                'txt': 'text/plain',
                'css': 'text/css'
        ])
    }

    static HttpServletRequest getRequest() {
        return ServletSupport.request
    }

    static HttpServletResponse getResponse() {
        return ServletSupport.response
    }

}