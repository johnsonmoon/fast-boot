package com.github.johnsonmoon.fastboot.core;

import com.github.johnsonmoon.fastboot.core.entity.ApplicationConfiguration;
import org.eclipse.jetty.servlet.FilterHolder;
import org.springframework.context.ApplicationContext;

/**
 * Create by johnsonmoon at 2018/5/11 21:33.
 */
public class ApplicationStartupTest {
	@SuppressWarnings("unused")
	private static ApplicationContext context;

	public static void main(String... args) {
		ApplicationConfiguration configuration = new ApplicationConfiguration();
		configuration.setSpringConfigLocation("classpath*:app-context.xml");
		configuration.setContextPath("/api");
		configuration.setHost("127.0.0.1");
		configuration.setPort("9110");
		//---
		configuration.addProvider(com.github.johnsonmoon.fastboot.core.common.GlobalExceptionHandler.class);
		configuration.addProvider(com.github.johnsonmoon.fastboot.core.common.GlobalFilter.class);
		//---
		FilterHolder filterHolder = new FilterHolder();
		filterHolder.setName("testFilter");
		filterHolder.setClassName("com.github.johnsonmoon.fastboot.core.common.TestFilter");
		configuration.addFilter("/*", filterHolder);
		context = ApplicationStartup.startup(configuration);
	}
}
