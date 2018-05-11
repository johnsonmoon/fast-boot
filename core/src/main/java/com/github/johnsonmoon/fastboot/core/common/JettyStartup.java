package com.github.johnsonmoon.fastboot.core.common;

import com.github.johnsonmoon.fastboot.core.util.CollectionUtils;
import com.github.johnsonmoon.fastboot.core.util.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import java.net.InetSocketAddress;
import java.util.EnumSet;
import java.util.Map;

/**
 * Created by johnsonmoon at 2018/5/11 11:30.
 */
public class JettyStartup {
    private static Logger logger = LoggerFactory.getLogger(JettyStartup.class);
    private static boolean started = false;
    private static Server server;
    private static String host;
    private static String port;
    private static String contextPath;
    private static Map<String, ServletHolder> servlets;//<path, servlet>
    private static Map<String, FilterHolder> filters;//<path, filter>

    public JettyStartup() {
    }

    public JettyStartup(String host, String port, String contextPath) {
        JettyStartup.host = host;
        JettyStartup.port = port;
    }

    public void setHostPort(String host, String port) {
        JettyStartup.host = host;
        JettyStartup.port = port;
    }

    public void setContextPath(String contextPath) {
        JettyStartup.contextPath = contextPath;
    }

    public void setServlets(Map<String, ServletHolder> servlets) {
        JettyStartup.servlets = servlets;
    }

    public void setFilters(Map<String, FilterHolder> filters) {
        JettyStartup.filters = filters;
    }

    public boolean startServer() {
        if (StringUtils.containsEmpty(host, port, contextPath)) {
            logger.error("Jetty server host and port and contextPath must not be null!");
            return false;
        }
        InetSocketAddress address = new InetSocketAddress(host, Integer.parseInt(port));
        if (server == null) {
            server = new Server(address);
        }
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath(contextPath);
        if (!CollectionUtils.isMapEmpty(servlets)) {
            for (Map.Entry<String, ServletHolder> entry : servlets.entrySet()) {
                servletContextHandler.addServlet(entry.getValue(), entry.getKey());
            }
        }
        if (!CollectionUtils.isMapEmpty(filters)) {
            for (Map.Entry<String, FilterHolder> entry : filters.entrySet()) {
                servletContextHandler.addFilter(entry.getValue(), entry.getKey(), EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
            }
        }
        servletContextHandler.setHandler(servletContextHandler);
        try {
            server.start();
        } catch (Exception e) {
            logger.warn(String.format("Jetty server start error: %s", e.getMessage()), e);
            return false;
        }
        server.setStopAtShutdown(true);
        started = true;
        return true;
    }

    public boolean stopServer() {
        if (started && server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                logger.warn(String.format("Jetty server stop error: %s", e.getMessage()), e);
                return false;
            }
        }
        started = false;
        return true;
    }
}
