package com.github.johnsonmoon.fastboot.core;

import com.github.johnsonmoon.fastboot.core.entity.ApplicationConfiguration;
import com.github.johnsonmoon.fastboot.core.entity.FilterConfiguration;

/**
 * Create by johnsonmoon at 2018/5/11 21:33.
 */
public class ApplicationStartupTest {
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
		FilterConfiguration filter = new FilterConfiguration();
		filter.setPath("/*");
		filter.setName("testFilter");
		filter.setClassName("com.github.johnsonmoon.fastboot.core.common.TestFilter");
		configuration.addFilter(filter);
		ApplicationStartup.startup(configuration);
	}
}
