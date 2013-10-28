package com.github.safrain.evaluatic.groovy

import org.apache.velocity.VelocityContext

import org.apache.velocity.runtime.RuntimeSingleton
import org.apache.velocity.tools.generic.DateTool
import org.apache.velocity.tools.generic.EscapeTool
import org.apache.velocity.tools.generic.MathTool
import org.apache.velocity.tools.generic.NumberTool

import static com.github.safrain.evaluatic.support.RuntimeSupport.*


class Velocity {
    static toolbox = [
            date: new DateTool(),
            math: new MathTool(),
            escape: new EscapeTool(),
            number: new NumberTool()
    ]

    static void evaluate(VelocityContext context, StringWriter sw, String template) {
        RuntimeSingleton.getRuntimeServices().evaluate(context, sw, 'velocity', template)
    }

    static String render(String template, Map<String, Object> model) {
        def ctx = new org.apache.velocity.VelocityContext();
        toolbox.each { k, v ->
            ctx.put(k, v)
        }
        model.each { k, v ->
            ctx.put(k, v)
        }
        def sw = new java.io.StringWriter()
        evaluate(ctx, sw, template)
        String layout = ctx.get('layout')
        if (layout == null) {
            return sw.buffer.toString()
        }
        def ctxLayout = new org.apache.velocity.VelocityContext();
        toolbox.each { k, v ->
            ctxLayout.put(k, v)
        }
        def layoutTemplate = loadString(layout)
        def swLayout = new java.io.StringWriter()
        ctxLayout.put('screen_content', sw.buffer.toString())
        evaluate(ctxLayout, swLayout, layoutTemplate)
        swLayout.buffer.toString()
    }
}