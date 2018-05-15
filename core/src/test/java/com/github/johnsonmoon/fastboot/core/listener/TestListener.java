package com.github.johnsonmoon.fastboot.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by johnsonmoon at 2018/5/15 18:17.
 */
public class TestListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Test listener. -- initialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Test listener. -- destroyed");
	}
}
