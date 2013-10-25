package com.github.safrain.evaluatic.servlet;

import com.github.safrain.evaluatic.ScriptRuntime;
import com.github.safrain.evaluatic.support.ServletSupport;
import com.github.safrain.evaluatic.support.SpringSupport;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EvaluaticServlet extends HttpServlet {

    private String beanName;

    private static final String DEFAULT_BEAN_NAME = "scriptRuntime";

    private static final String PARAM_BEAN_NAME = "beanName";

    @Override
    public void init() throws ServletException {
        beanName = getServletConfig().getInitParameter(PARAM_BEAN_NAME);
        if (beanName == null) {
            beanName = DEFAULT_BEAN_NAME;
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        ScriptRuntime scriptRuntime = (ScriptRuntime) context.getBean(beanName == null ? DEFAULT_BEAN_NAME : beanName);
        String scriptName = request.getPathInfo().substring(1);
        try {
            ServletSupport.requestThreadLocal.set(request);
            ServletSupport.responseThreadLocal.set(response);
            SpringSupport.applicationContextThreadLocal.set(context);
            scriptRuntime.run(scriptName);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
