package com.github.johnsonmoon.fastboot.core;

import com.github.johnsonmoon.fastboot.core.common.DispatcherStartup;
import com.github.johnsonmoon.fastboot.core.common.ServerStartup;
import com.github.johnsonmoon.fastboot.core.common.impl.JettyServerStartup;
import com.github.johnsonmoon.fastboot.core.common.impl.RestEasyDispatcherStartup;
import com.github.johnsonmoon.fastboot.core.entity.ApplicationConfiguration;
import com.github.johnsonmoon.fastboot.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by johnsonmoon at 2018/5/11 11:27.
 */
public class ApplicationStartup {
	private static Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
	/**
	 * Spring context. {@link ApplicationContext}
	 */
	private static ApplicationContext applicationContext;
	/**
	 * Application configuration to be set.
	 * {@link ApplicationConfiguration}
	 */
	private static ApplicationConfiguration applicationConfiguration;
	/**
	 * Server.
	 * {@link ServerStartup}
	 */
	private static ServerStartup serverStartup;

	/**
	 * Set application configuration.
	 *
	 * @param applicationConfiguration {@link ApplicationConfiguration}
	 */
	public static void setApplicationConfiguration(ApplicationConfiguration applicationConfiguration) {
		ApplicationStartup.applicationConfiguration = applicationConfiguration;
	}

	/**
	 * Get spring context.
	 *
	 * @return {@link ApplicationContext}
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Get server.
	 *
	 * @return {@link ServerStartup}
	 */
	public static ServerStartup getServerStartup() {
		return serverStartup;
	}

	/**
	 * Startup application.
	 *
	 * @return {@link ApplicationContext}
	 */
	public static ApplicationContext startup() {
		if (!checkConfiguration(applicationConfiguration)) {
			logger.error("ApplicationConfiguration setting error! Please check it.");
			System.exit(0);
		}
		ApplicationStartup.applicationContext = bootstrap(applicationConfiguration);
		return ApplicationStartup.applicationContext;
	}

	/**
	 * Startup application.
	 *
	 * @param applicationConfiguration {@link ApplicationConfiguration}
	 * @return {@link ApplicationContext}
	 */
	public static ApplicationContext startup(ApplicationConfiguration applicationConfiguration) {
		if (!checkConfiguration(applicationConfiguration)) {
			logger.error("ApplicationConfiguration setting error! Please check it.");
			System.exit(0);
		}
		ApplicationStartup.applicationConfiguration = applicationConfiguration;
		ApplicationStartup.applicationContext = bootstrap(applicationConfiguration);
		return ApplicationStartup.applicationContext;
	}

	private static boolean checkConfiguration(ApplicationConfiguration applicationConfiguration) {
		if (StringUtils.containsEmpty(applicationConfiguration.getSpringConfigLocation())) {
			logger.error("ApplicationConfiguration missing: springConfigLocation.");
			return false;
		}
		if (StringUtils.containsEmpty(applicationConfiguration.getHost(), applicationConfiguration.getPort())) {
			logger.error("ApplicationConfiguration missing: host or port.");
			return false;
		}
		if (StringUtils.containsEmpty(applicationConfiguration.getContextPath())) {
			logger.error("ApplicationConfiguration missing: contextPath.");
			return false;
		}
		return true;
	}

	private static ApplicationContext bootstrap(ApplicationConfiguration configuration) {
		ApplicationContext applicationContext = springStartup(configuration);
		dispatcherStartup(configuration, applicationContext);
		serverStartup(configuration);
		return applicationContext;
	}

	private static ApplicationContext springStartup(ApplicationConfiguration configuration) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
				configuration.getSpringConfigLocation());
		classPathXmlApplicationContext.registerShutdownHook();
		return classPathXmlApplicationContext;
	}

	private static void serverStartup(ApplicationConfiguration configuration) {
		ServerStartup serverStartup = configuration.getServerStartup();
		if (serverStartup == null) {
			serverStartup = new JettyServerStartup();//default: jetty
		}
		serverStartup.setHostPort(configuration.getHost(), configuration.getPort());
		serverStartup.setContextPath(configuration.getContextPath());
		serverStartup.setContextParams(configuration.getContextParams());
		serverStartup.setServlets(configuration.getServlets());
		serverStartup.setFilters(configuration.getFilters());
		if (serverStartup.startServer()) {
			ApplicationStartup.serverStartup = serverStartup;
		}
	}

	private static void dispatcherStartup(ApplicationConfiguration configuration, ApplicationContext applicationContext) {
		DispatcherStartup dispatcherStartup = configuration.getDispatcherStartup();
		if (dispatcherStartup == null) {
			dispatcherStartup = new RestEasyDispatcherStartup();//default: restEasy
		}
		dispatcherStartup.dispatcherInit(configuration, applicationContext);
	}
}
