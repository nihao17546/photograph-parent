package com.nihaov.photograph.web.listener;

import com.nihaov.photograph.pojo.constant.BaseConstant;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by nihao on 17/4/22.
 */
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute("contextPath",servletContextEvent.getServletContext().getContextPath());
        BaseConstant.contextPath = servletContextEvent.getServletContext().getContextPath();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
