package com.github.johnsonmoon.fastboot.core;

import com.github.johnsonmoon.fastboot.core.common.JettyStartup;
import com.github.johnsonmoon.fastboot.core.common.RestEasyInit;
import com.github.johnsonmoon.fastboot.core.entity.ApplicationConfiguration;
import com.github.johnsonmoon.fastboot.core.util.StringUtils;
import org.eclipse.jetty.servlet.ServletHolder;
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
	 * Jetty server.
	 * {@link JettyStartup}
	 */
	private static JettyStartup jettyStartup;

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
	 * Get jetty server.
	 *
	 * @return {@link JettyStartup}
	 */
	public static JettyStartup getJettyStartup() {
		return jettyStartup;
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
		restEasyInit(configuration, applicationContext);
		jettyStartup(configuration);
		return applicationContext;
	}

	private static ApplicationContext springStartup(ApplicationConfiguration configuration) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
				configuration.getSpringConfigLocation());
		classPathXmlApplicationContext.registerShutdownHook();
		return classPathXmlApplicationContext;
	}

	private static void jettyStartup(ApplicationConfiguration configuration) {
		JettyStartup jettyStartup = new JettyStartup();
		jettyStartup.setHostPort(configuration.getHost(), configuration.getPort());
		jettyStartup.setContextPath(configuration.getContextPath());
		jettyStartup.setContextParams(configuration.getContextParams());
		jettyStartup.setServlets(configuration.getServlets());
		jettyStartup.setFilters(configuration.getFilters());
		if (jettyStartup.startServer()) {
			ApplicationStartup.jettyStartup = jettyStartup;
		}
	}

	private static void restEasyInit(ApplicationConfiguration configuration, ApplicationContext applicationContext) {
		ServletHolder servletHolder = new ServletHolder();
		servletHolder.setName("rest-easy");
		servletHolder.setClassName("org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher");
		servletHolder.setInitParameter("javax.ws.rs.Application",
				"com.github.johnsonmoon.fastboot.core.common.RestEasyInit");
		configuration.addServlet("/*", servletHolder);
		RestEasyInit.setApplicationContext(applicationContext);
	}
}
