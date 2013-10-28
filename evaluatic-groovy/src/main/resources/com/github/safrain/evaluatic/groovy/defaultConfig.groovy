package com.github.safrain.evaluatic.groovy

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