package com.github.johnsonmoon.fastboot.core.common.impl;

import com.github.johnsonmoon.fastboot.core.common.ServerStartup;
import com.github.johnsonmoon.fastboot.core.entity.FilterConfiguration;
import com.github.johnsonmoon.fastboot.core.entity.ServletConfiguration;
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
import java.util.*;

/**
 * Create by johnsonmoon at 2018/5/13 11:00.
 */
public class JettyServerStartup implements ServerStartup {
	private static Logger logger = LoggerFactory.getLogger(JettyServerStartup.class);
	private boolean started = false;
	private Server server;
	private String host;
	private String port;
	private String contextPath;
	private Map<String, String> contextParams;
	private Map<ServletHolder, String> servlets;//<servlet, path>
	private Map<FilterHolder, String> filters;//<filter, path>
	private List<EventListener> listeners;//java.util.EventListener

	@Override
	public boolean isStarted() {
		return this.started;
	}

	@Override
	public void setHostPort(String host, String port) {
		this.host = host;
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
		if (servlets == null || servlets.isEmpty()) {
			return;
		}
		if (this.servlets == null) {
			this.servlets = new HashMap<>();
		}
		for (ServletConfiguration configuration : servlets) {
			ServletHolder servletHolder = new ServletHolder();
			servletHolder.setName(configuration.getName());
			servletHolder.setClassName(configuration.getClassName());
			if (configuration.getInitParameters() != null && !configuration.getInitParameters().isEmpty()) {
				servletHolder.setInitParameters(configuration.getInitParameters());
			}
			this.servlets.put(servletHolder, configuration.getPath());
		}
	}

	@Override
	public void setFilters(List<FilterConfiguration> filters) {
		if (filters == null || filters.isEmpty()) {
			return;
		}
		if (this.filters == null) {
			this.filters = new HashMap<>();
		}
		for (FilterConfiguration configuration : filters) {
			FilterHolder filterHolder = new FilterHolder();
			filterHolder.setName(configuration.getName());
			filterHolder.setClassName(configuration.getClassName());
			if (configuration.getInitParameters() != null && !configuration.getInitParameters().isEmpty()) {
				filterHolder.setInitParameters(configuration.getInitParameters());
			}
			this.filters.put(filterHolder, configuration.getPath());
		}
	}

	@Override
	public void setListeners(List<EventListener> listeners) {
		if (listeners == null || listeners.isEmpty()) {
			return;
		}
		if (this.listeners == null) {
			this.listeners = new ArrayList<>();
		}
		this.listeners.addAll(listeners);
	}

	@Override
	public boolean startServer() {
		if (StringUtils.containsEmpty(this.host, this.port, this.contextPath)) {
			logger.error("Jetty server host and port and contextPath must not be null!");
			return false;
		}
		InetSocketAddress address = new InetSocketAddress(this.host, Integer.parseInt(this.port));
		if (this.server == null) {
			this.server = new Server(address);
		}
		ServletContextHandler servletContextHandler = new ServletContextHandler();
		servletContextHandler.setContextPath(this.contextPath);
		if (!CollectionUtils.isMapEmpty(this.contextParams)) {
			for (Map.Entry<String, String> entry : this.contextParams.entrySet()) {
				servletContextHandler.setInitParameter(entry.getKey(), entry.getValue());
			}
		}
		if (!CollectionUtils.isCollectionEmpty(this.listeners)) {
			EventListener[] eventListeners = new EventListener[this.listeners.size()];
			for (int i = 0; i < this.listeners.size(); i++) {
				eventListeners[i] = this.listeners.get(i);
			}
			servletContextHandler.setEventListeners(eventListeners);
		}
		if (!CollectionUtils.isMapEmpty(this.servlets)) {
			for (Map.Entry<ServletHolder, String> entry : this.servlets.entrySet()) {
				servletContextHandler.addServlet(entry.getKey(), entry.getValue());
			}
		}
		if (!CollectionUtils.isMapEmpty(this.filters)) {
			for (Map.Entry<FilterHolder, String> entry : this.filters.entrySet()) {
				servletContextHandler.addFilter(entry.getKey(), entry.getValue(),
						EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
			}
		}
		this.server.setHandler(servletContextHandler);
		try {
			this.server.start();
		} catch (Exception e) {
			logger.warn(String.format("Jetty server start error: %s", e.getMessage()), e);
			return false;
		}
		this.server.setStopAtShutdown(true);
		this.started = true;
		return true;
	}

	@Override
	public boolean stopServer() {
		if (this.started && this.server != null) {
			try {
				this.server.stop();
			} catch (Exception e) {
				logger.warn(String.format("Jetty server stop error: %s", e.getMessage()), e);
				return false;
			}
		}
		this.started = false;
		return true;
	}
}
