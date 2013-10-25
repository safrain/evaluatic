package com.github.safrain.evaluatic.support;

import org.springframework.context.ApplicationContext;

public class SpringSupport {
    public static final ThreadLocal<ApplicationContext> applicationContextThreadLocal = new ThreadLocal<ApplicationContext>();

    public static ApplicationContext getApplicationContext() {
        return applicationContextThreadLocal.get();
    }
}
