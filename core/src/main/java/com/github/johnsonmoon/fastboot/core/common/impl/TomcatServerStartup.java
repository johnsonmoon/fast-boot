package com.github.johnsonmoon.fastboot.core.common.impl;

import com.github.johnsonmoon.fastboot.core.common.ServerStartup;
import com.github.johnsonmoon.fastboot.core.entity.FilterConfiguration;
import com.github.johnsonmoon.fastboot.core.entity.ServletConfiguration;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Create by johnsonmoon at 2018/12/16 16:25.
 */
public class TomcatServerStartup implements ServerStartup {
    private static Logger logger = LoggerFactory.getLogger(TomcatServerStartup.class);
    private boolean started = false;
    private Tomcat tomcat;
    private String port;
    private String contextPath;
    private Map<String, String> contextParams;
    private List<ServletConfiguration> servlets;
    private List<FilterConfiguration> filters;
    private List<EventListener> listeners;//java.util.EventListener

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public void setHostPort(String host, String port) {
        this.port = port;
    }

    @Override
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public void setContextParams(Map<String, String> contextParams) {
        this.contextParams = contextParams;
    }

    @Override
    public void setServlets(List<ServletConfiguration> servlets) {
        this.servlets = servlets;
    }

    @Override
    public void setFilters(List<FilterConfiguration> filters) {
        this.filters = filters;
    }

    @Override
    public void setListeners(List<EventListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public boolean startServer() {
        try {
            Path tempBaseDir = Files.createTempDirectory("tomcat-temp-base-dir");
            Path tempDocDir = Files.createTempDirectory("tomcat-temp-doc-dir");

            this.tomcat = new Tomcat();
            Connector connector = new Connector();
            connector.setPort(Integer.parseInt(port));
            tomcat.getService().addConnector(connector);
            tomcat.setConnector(connector);
            tomcat.getHost().setAutoDeploy(false);
            tomcat.setBaseDir(tempBaseDir.toFile().getAbsolutePath());
            StandardContext context = (StandardContext) tomcat.addWebapp(contextPath, tempDocDir.toFile().getAbsolutePath());
            context.setParentClassLoader(this.getClass().getClassLoader());
            context.setUseRelativeRedirects(false);

            //--- Context Params
            if (contextParams != null && !contextParams.isEmpty()) {
                contextParams.forEach(context::addParameter);
            }


            //--- Servlets
            if (servlets != null && !servlets.isEmpty()) {
                servlets.forEach(servlet -> {
                    Wrapper wrapper = context.createWrapper();
                    wrapper.setName(servlet.getName());
                    wrapper.setServletClass(servlet.getClassName());
                    if (servlet.getInitParameters() != null && !servlet.getInitParameters().isEmpty()) {
                        servlet.getInitParameters().forEach(wrapper::addInitParameter);
                    }
                    context.addChild(wrapper);
                    context.addServletMappingDecoded(servlet.getPath(), servlet.getName());
                });
            }

            //--- Filters
            if (filters != null && !filters.isEmpty()) {
                filters.forEach(filter -> {
                    FilterDef filterDef = new FilterDef();
                    filterDef.setFilterName(filter.getName());
                    filterDef.setFilterClass(filter.getClassName());
                    if (filter.getInitParameters() != null && !filter.getInitParameters().isEmpty()) {
                        filter.getInitParameters().forEach(filterDef::addInitParameter);
                    }
                    context.addFilterDef(filterDef);
                    FilterMap filterMap = new FilterMap();
                    filterMap.setFilterName(filter.getName());
                    filterMap.addURLPattern(filter.getPath());
                    context.addFilterMap(filterMap);
                });
            }

            //--- Listeners
            if (listeners != null && !listeners.isEmpty()) {
                listeners.forEach(context::addApplicationEventListener);
            }
            tomcat.start();
        } catch (Exception e) {
            logger.error(String.format("Tomcat server start error: %s", e.getMessage()), e);
            return false;
        }
        this.started = true;
        return true;
    }

    @Override
    public boolean stopServer() {
        if (this.started && tomcat != null) {
            try {
                tomcat.stop();
            } catch (Exception e) {
                logger.error(String.format("Jetty server stop error: %s", e.getMessage()), e);
                return false;
            }
        }
        this.started = false;
        return true;
    }
}
