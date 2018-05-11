package com.github.johnsonmoon.fastboot.core;

import com.github.johnsonmoon.fastboot.core.entity.ApplicationConfiguration;
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
     * Startup application.
     *
     * @return {@link ApplicationContext}
     */
    public static ApplicationContext startup() {
        //TODO
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext();//TODO);
        classPathXmlApplicationContext.registerShutdownHook();
        ApplicationStartup.applicationContext = classPathXmlApplicationContext;
        return classPathXmlApplicationContext;
    }
}
