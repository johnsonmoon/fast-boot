package com.github.johnsonmoon.fastboot.core;

import com.github.johnsonmoon.fastboot.core.dispacher.DispatcherInitialize;
import com.github.johnsonmoon.fastboot.core.server.impl.JettyServerStartup;
import com.github.johnsonmoon.fastboot.core.dispacher.resteasy.RestEasyDispatcherInitialize;
import com.github.johnsonmoon.fastboot.core.entity.ApplicationConfiguration;

import com.github.johnsonmoon.fastboot.core.server.ServerStartup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by johnsonmoon at 2018/5/15 17:47.
 */
class ApplicationBootstrap {
	private static Logger logger = LoggerFactory.getLogger(ApplicationBootstrap.class);

	static ApplicationContext springStartup(ApplicationConfiguration configuration) {
		logger.debug("Spring context initializing...");
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
				configuration.getSpringConfigLocation());
		classPathXmlApplicationContext.registerShutdownHook();
		return classPathXmlApplicationContext;
	}

	static void dispatcherStartup(ApplicationConfiguration configuration, ApplicationContext applicationContext) {
		if (configuration.isDispatcherDisable()) {//Disable dispatcher servlet addition.
			return;
		}
		logger.debug("Dispatcher servlet initializing...");
		DispatcherInitialize dispatcherStartup = configuration.getDispatcherStartup();
		if (dispatcherStartup == null) {
			dispatcherStartup = new RestEasyDispatcherInitialize();//default: restEasy
		}
		dispatcherStartup.dispatcherInit(configuration, applicationContext);
	}

	static ServerStartup serverStartup(ApplicationConfiguration configuration) {
		ServerStartup serverStartup = configuration.getServerStartup();
		if (serverStartup == null) {
			serverStartup = new JettyServerStartup();//default: jetty
		}
		logger.debug("Servlet container server starting...");
		serverStartup.setHostPort(configuration.getHost(), configuration.getPort());
		serverStartup.setContextPath(configuration.getContextPath());
		serverStartup.setContextParams(configuration.getContextParams());
		serverStartup.setServlets(configuration.getServlets());
		serverStartup.setFilters(configuration.getFilters());
		serverStartup.setListeners(configuration.getListeners());
		if (serverStartup.startServer()) {
			logger.debug(String.format("Servlet container server started at host:[%s], port:[%s]", configuration.getHost(),
					configuration.getPort()));
			return serverStartup;
		} else {
			logger.error("Servlet container server start failed.");
			return null;
		}
	}
}
