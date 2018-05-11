package com.github.johnsonmoon.fastboot.core.entity;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnsonmoon at 2018/5/11 17:01.
 */
public class ApplicationConfiguration {
	/**
	 * Spring context configuration resource location
	 */
	private String springConfigLocation;
	/**
	 * Jetty server host.
	 */
	private String host;
	/**
	 * Jetty server port.
	 */
	private String port;
	/**
	 * Application context path.
	 */
	private String contextPath;
	/**
	 * Application context parameters.
	 */
	private Map<String, String> contextParams;
	/**
	 * Servlet to be added to jetty.
	 * <pre>
	 *     [servlet context path,  servletHolder]
	 * </pre>
	 */
	private Map<String, ServletHolder> servlets;
	/**
	 * Filter to be added to jetty.
	 * <pre>
	 *     [filter context path,  filterHolder]
	 * </pre>
	 */
	private Map<String, FilterHolder> filters;

	public String getSpringConfigLocation() {
		return springConfigLocation;
	}

	public void setSpringConfigLocation(String springConfigLocation) {
		this.springConfigLocation = springConfigLocation;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public Map<String, String> getContextParams() {
		return contextParams;
	}

	public void setContextParams(Map<String, String> contextParams) {
		this.contextParams = contextParams;
	}

	public Map<String, ServletHolder> getServlets() {
		return servlets;
	}

	public void setServlets(Map<String, ServletHolder> servlets) {
		this.servlets = servlets;
	}

	public Map<String, FilterHolder> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, FilterHolder> filters) {
		this.filters = filters;
	}

	public void addContextParam(String name, String value) {
		if (this.contextParams == null) {
			this.contextParams = new HashMap<>();
		}
		this.contextParams.put(name, value);
	}

	public void addServlet(String path, ServletHolder servletHolder) {
		if (this.servlets == null) {
			this.servlets = new HashMap<>();
		}
		this.servlets.put(path, servletHolder);
	}

	public void addFilter(String path, FilterHolder filterHolder) {
		if (this.filters == null) {
			this.filters = new HashMap<>();
		}
		this.filters.put(path, filterHolder);
	}

	@Override
	public String toString() {
		return "ApplicationConfiguration{" +
				"springConfigLocation='" + springConfigLocation + '\'' +
				", host='" + host + '\'' +
				", port='" + port + '\'' +
				", contextPath='" + contextPath + '\'' +
				", contextParams=" + contextParams +
				", servlets=" + servlets +
				", filters=" + filters +
				'}';
	}
}
