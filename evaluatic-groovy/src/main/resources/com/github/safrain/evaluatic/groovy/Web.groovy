package com.github.safrain.evaluatic.groovy

import com.github.safrain.evaluatic.support.RuntimeSupport
import com.github.safrain.evaluatic.support.ServletSupport

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

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
        def template = RuntimeSupport.loadString(viewName)
        def templateEngine = templateEngines.get()[engine == null ? defaultTemplateEngine : engine]
        Web.print templateEngine(template, model)
    }

    static void redirect(String path) {
        ServletSupport.response.sendRedirect(path)
    }

    static processRequest() {
        String name

        if (prefix.get() == null || !RuntimeSupport.sourceName.startsWith(prefix.get())) {
            name = RuntimeSupport.sourceName
        } else {
            name = RuntimeSupport.sourceName.substring(prefix.get().length())
        }

        if (runnableExtensions.get().find { name.endsWith('.' + it) } != null) {
            RuntimeSupport.eval name
        } else {
            String header = extensionMapping.find { k, v -> name.endsWith('.' + k) }?.value
            if (header != null) {
                response.setHeader('Content-Type', header)
            }
            ServletSupport.response.outputStream.print(RuntimeSupport.loadString(name))
            ServletSupport.response.outputStream.close()
        }
    }

    static HttpServletRequest request = ServletSupport.request
    static HttpServletResponse response = ServletSupport.response
    static HttpSession session = ServletSupport.request.getSession()
}