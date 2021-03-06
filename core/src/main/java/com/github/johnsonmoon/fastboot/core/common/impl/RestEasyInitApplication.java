package com.github.johnsonmoon.fastboot.core.common.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by johnsonmoon at 2018/5/11 11:30.
 */
public class RestEasyInitApplication extends Application {
	private static Logger logger = LoggerFactory.getLogger(RestEasyInitApplication.class);
	private static ApplicationContext applicationContext;
	private Set<Object> set = new HashSet<>();

	public static void setApplicationContext(ApplicationContext applicationContext) {
		RestEasyInitApplication.applicationContext = applicationContext;
	}

	public RestEasyInitApplication() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(
				"Service beans scan: \n-------------------------------------------------------------------------------------------------\n");
		Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Service.class);
		int i = 1;
		for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
			set.add(entry.getValue());
			stringBuilder.append(String.format("%3d. %-35s  %s\n", i, entry.getKey(), entry.getValue()));
			i++;
		}
		stringBuilder
				.append("-------------------------------------------------------------------------------------------------");
		logger.debug(stringBuilder.toString());
	}

	@Override
	public Set<Object> getSingletons() {
		return this.set;
	}
}
