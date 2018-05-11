package com.github.johnsonmoon.fastboot.core.entity;

import com.github.johnsonmoon.fastboot.core.util.StringUtils;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnsonmoon at 2018/5/11 17:01.
 */
public class ApplicationConfiguration {
	private static final String REST_EASY_PROVIDERS = "resteasy.providers";
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
	 * Providers for restEasy.
	 */
	private List<String> providers;
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
		if (providers != null && !providers.isEmpty()) {
			addContextParam(REST_EASY_PROVIDERS, StringUtils.combineWithSymbol(providers, ","));
		}
		return contextParams;
	}

	public Map<String, ServletHolder> getServlets() {
		return servlets;
	}

	public Map<String, FilterHolder> getFilters() {
		return filters;
	}

	public void addContextParam(String name, String value) {
		if (this.contextParams == null) {
			this.contextParams = new HashMap<>();
		}
		this.contextParams.put(name, value);
	}

	public void addProvider(String provider) {
		if (provider == null || provider.isEmpty()) {
			return;
		}
		if (this.providers == null) {
			this.providers = new ArrayList<>();
		}
		this.providers.add(provider);
	}

	public void addProvider(Class<?> provider) {
		if (provider == null) {
			return;
		}
		if (this.providers == null) {
			this.providers = new ArrayList<>();
		}
		this.providers.add(provider.getName());
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
