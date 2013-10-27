package com.github.safrain.evaluatic.groovy

import com.github.safrain.evaluatic.support.RuntimeSupport
import com.github.safrain.evaluatic.support.ServletSupport

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession


class Web {
    static def defaultTemplateEngine = 'velocity'
    static def engines = [:]
    static def runnableExtension = ['groovy']
    static def extensionHeaderMap = [
            'vm': 'text/html',
            'html': 'text/html',
            'htm': 'text/html',
            'json': 'application/json',
            'js': 'text/javascript',
            'txt': 'text/plain',
            'css': 'text/css'
    ]

    static void print(obj) {
        ServletSupport.response.characterEncoding = RuntimeSupport.scriptRuntime.charset
        ServletSupport.response.writer.print(obj)
    }

    static void println(obj) {
        ServletSupport.response.characterEncoding = RuntimeSupport.scriptRuntime.charset
        ServletSupport.response.writer.println(obj)
    }

    static void mav(String viewName, Map model, String engine = null) {
        def template = RuntimeSupport.loadString(viewName)
        def templateEngine = engines[engine == null ? defaultTemplateEngine : engine]
        web.print templateEngine(template, model)
    }

    static void redirect(String path) {
        ServletSupport.response.sendRedirect(path)
    }

    static processRequest() {
        if (runnableExtension.find { RuntimeSupport.sourceName.endsWith('.' + it) } != null) {
            RuntimeSupport.eval RuntimeSupport.sourceName
        } else {
            String header = extensionHeaderMap.find { k, v -> RuntimeSupport.sourceName.endsWith('.' + k) }?.value
            if (header != null) {
                response.setHeader('Content-Type', header)
            }
            ServletSupport.response.outputStream.print(RuntimeSupport.loadString(RuntimeSupport.sourceName))
            ServletSupport.response.outputStream.close()
        }
    }

    static HttpServletRequest request = ServletSupport.request
    static HttpServletResponse response = ServletSupport.response
    static HttpSession session = ServletSupport.request.getSession()
}