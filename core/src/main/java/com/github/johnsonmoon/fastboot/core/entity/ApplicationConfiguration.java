package com.github.johnsonmoon.fastboot.core.entity;

import com.github.johnsonmoon.fastboot.core.dispacher.DispatcherInitialize;
import com.github.johnsonmoon.fastboot.core.server.ServerStartup;
import com.github.johnsonmoon.fastboot.core.server.impl.JettyServerStartup;
import com.github.johnsonmoon.fastboot.core.util.StringUtils;

import java.util.*;

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
	 * Servlet to be added to server.
	 */
	private List<ServletConfiguration> servlets;
	/**
	 * Filter to be added to server.
	 */
	private List<FilterConfiguration> filters;
	/**
	 * Listener to be added to server.
	 */
	private List<EventListener> listeners;
	/**
	 * Server config.
	 * <pre>
	 *     default server config : {@link JettyServerStartup}
	 * </pre>
	 */
	private ServerStartup serverStartup;
	/**
	 * Dispatcher disabled. default is false.
	 * <pre>
	 *     If true, there will be no dispatcher servlet added into servlet container.
	 *     Default dispatcher config : {@link com.github.johnsonmoon.fastboot.core.dispacher.resteasy.RestEasyDispatcherInitialize}
	 * </pre>
	 */
	private boolean dispatcherDisable = false;
	/**
	 * Dispatcher servlet path.
	 * <pre>
	 *     default dispatcher path : "/*"
	 * </pre>
	 */
	private String dispatcherPath = "/*";
	/**
	 * Dispatcher config.
	 * <pre>
	 *     default dispatcher config : {@link com.github.johnsonmoon.fastboot.core.dispacher.resteasy.RestEasyDispatcherInitialize}
	 * </pre>
	 */
	private DispatcherInitialize dispatcherStartup;

	public ServerStartup getServerStartup() {
		return serverStartup;
	}

	public void setServerStartup(ServerStartup serverStartup) {
		this.serverStartup = serverStartup;
	}

	public boolean isDispatcherDisable() {
		return dispatcherDisable;
	}

	public void setDispatcherDisable(boolean dispatcherDisable) {
		this.dispatcherDisable = dispatcherDisable;
	}

	public String getDispatcherPath() {
		return dispatcherPath;
	}

	public void setDispatcherPath(String dispatcherPath) {
		this.dispatcherPath = dispatcherPath;
	}

	public DispatcherInitialize getDispatcherStartup() {
		return dispatcherStartup;
	}

	public void setDispatcherStartup(DispatcherInitialize dispatcherStartup) {
		this.dispatcherStartup = dispatcherStartup;
	}

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

	public List<String> getProviders() {
		return providers;
	}

	public List<ServletConfiguration> getServlets() {
		return servlets;
	}

	public List<FilterConfiguration> getFilters() {
		return filters;
	}

	public List<EventListener> getListeners() {
		return listeners;
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

	public void addServlet(ServletConfiguration servlet) {
		if (this.servlets == null) {
			this.servlets = new ArrayList<>();
		}
		this.servlets.add(servlet);
	}

	public void addFilter(FilterConfiguration filter) {
		if (this.filters == null) {
			this.filters = new ArrayList<>();
		}
		this.filters.add(filter);
	}

	public void addListener(EventListener listener) {
		if (this.listeners == null) {
			this.listeners = new ArrayList<>();
		}
		this.listeners.add(listener);
	}

	public void setListeners(ListenerConfiguration listenerConfiguration) {
		if (listenerConfiguration == null) {
			return;
		}
		this.listeners = listenerConfiguration.getListeners();
	}

	public void setListeners(List<EventListener> listeners) {
		this.listeners = listeners;
	}

	@Override
	public String toString() {
		return "ApplicationConfiguration{" +
				"serverStartup=" + serverStartup +
				", dispatcherDisable=" + dispatcherDisable +
				", dispatcherPath='" + dispatcherPath + '\'' +
				", dispatcherStartup=" + dispatcherStartup +
				", springConfigLocation='" + springConfigLocation + '\'' +
				", host='" + host + '\'' +
				", port='" + port + '\'' +
				", contextPath='" + contextPath + '\'' +
				", contextParams=" + contextParams +
				", providers=" + providers +
				", servlets=" + servlets +
				", filters=" + filters +
				", listeners=" + listeners +
				'}';
	}
}
