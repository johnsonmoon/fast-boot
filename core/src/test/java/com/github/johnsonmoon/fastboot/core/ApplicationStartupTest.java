package com.github.johnsonmoon.fastboot.core;

import com.github.johnsonmoon.fastboot.core.common.TestFilter;
import com.github.johnsonmoon.fastboot.core.entity.ApplicationConfiguration;
import com.github.johnsonmoon.fastboot.core.entity.FilterConfiguration;
import com.github.johnsonmoon.fastboot.core.entity.ListenerConfiguration;
import com.github.johnsonmoon.fastboot.core.listener.TestListener;

/**
 * Create by johnsonmoon at 2018/5/11 21:33.
 */
public class ApplicationStartupTest {
	public static void main(String... args) {
		ApplicationConfiguration configuration = new ApplicationConfiguration();
		//--- spring configuration
		configuration.setSpringConfigLocation("classpath*:app-context.xml");
		//--- jetty server configuration
		configuration.setContextPath("/api");
		configuration.setHost("127.0.0.1");
		configuration.setPort("9110");
		//--- restEasy providers
		configuration.addProvider(com.github.johnsonmoon.fastboot.core.common.GlobalExceptionHandler.class);
		configuration.addProvider(com.github.johnsonmoon.fastboot.core.common.GlobalFilter.class);
		//--- filters
		FilterConfiguration filter = new FilterConfiguration();
		filter.setPath("/*");
		filter.setName("testFilter");
		filter.setClass(TestFilter.class);
		configuration.addFilter(filter);
		//--- servlets
		//--- default dispatcher servlet disabled, you can add custom servlet to dispatch "/*" url path
		//        ServletConfiguration servlet = new ServletConfiguration();
		//        configuration.setDispatcherDisable(true);
		//        servlet.setPath("/*");
		//        servlet.setName("testServlet");
		//        servlet.setClass(TestServlet.class);
		//        configuration.addServlet(servlet);
		//--- listeners
		ListenerConfiguration listeners = new ListenerConfiguration();
		listeners.addListener(new TestListener());
		configuration.setListeners(listeners);
		//--- startup
		ApplicationStartup.startup(configuration);
	}
}
