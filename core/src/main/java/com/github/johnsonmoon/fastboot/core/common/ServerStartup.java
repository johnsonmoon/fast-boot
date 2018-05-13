package com.github.johnsonmoon.fastboot.core.common;

import com.github.johnsonmoon.fastboot.core.entity.FilterConfiguration;
import com.github.johnsonmoon.fastboot.core.entity.ServletConfiguration;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

/**
 * Servlet container startup & initialise.
 * <p>
 * Create by johnsonmoon at 2018/5/13 10:52.
 */
public interface ServerStartup {
	/**
	 * Is servlet server started.
	 *
	 * @return true: started; false: stopped
	 */
	boolean isStarted();

	/**
	 * Set server host & port.
	 *
	 * @param host host
	 * @param port port
	 */
	void setHostPort(String host, String port);

	/**
	 * Set server context path.
	 *
	 * @param contextPath context path
	 */
	void setContextPath(String contextPath);

	/**
	 * Set server context initialise parameters.
	 *
	 * @param contextParams context init params.
	 */
	void setContextParams(Map<String, String> contextParams);

	/**
	 * Set servlets.
	 *
	 * @param servlets servlets {@link ServletConfiguration}
	 */
	void setServlets(List<ServletConfiguration> servlets);

	/**
	 * Set filters.
	 *
	 * @param filters filters {@link FilterConfiguration}
	 */
	void setFilters(List<FilterConfiguration> filters);

	/**
	 * Set listeners.
	 *
	 * @param listeners {@link EventListener}
	 */
	void setListeners(List<EventListener> listeners);

	/**
	 * Start server.
	 *
	 * @return true/false
	 */
	boolean startServer();

	/**
	 * Stop server.
	 *
	 * @return true/false
	 */
	boolean stopServer();
}
