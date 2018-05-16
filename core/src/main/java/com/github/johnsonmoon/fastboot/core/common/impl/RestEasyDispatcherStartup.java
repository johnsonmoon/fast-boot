package com.github.johnsonmoon.fastboot.core.common.impl;

import com.github.johnsonmoon.fastboot.core.common.DispatcherStartup;
import com.github.johnsonmoon.fastboot.core.entity.ApplicationConfiguration;
import com.github.johnsonmoon.fastboot.core.entity.ServletConfiguration;
import org.springframework.context.ApplicationContext;

import java.util.Collections;

/**
 * Create by johnsonmoon at 2018/5/13 13:02.
 */
public class RestEasyDispatcherStartup implements DispatcherStartup {
	@Override
	public ApplicationConfiguration dispatcherInit(ApplicationConfiguration configuration,
			ApplicationContext applicationContext) {
		ServletConfiguration servletConfiguration = new ServletConfiguration();
		servletConfiguration.setPath(configuration.getDispatcherPath() == null ? "/*" : configuration.getDispatcherPath());
		servletConfiguration.setName("rest-easy");
		servletConfiguration.setClassName("org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher");
		servletConfiguration.setInitParameters(Collections.singletonMap("javax.ws.rs.Application",
				"com.github.johnsonmoon.fastboot.core.common.impl.RestEasyInitApplication"));
		configuration.addServlet(servletConfiguration);
		RestEasyInitApplication.setApplicationContext(applicationContext);
		return configuration;
	}
}
