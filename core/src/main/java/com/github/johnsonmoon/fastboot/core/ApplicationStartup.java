package com.github.johnsonmoon.fastboot.core;

import com.github.johnsonmoon.fastboot.core.common.ApplicationBootstrap;
import com.github.johnsonmoon.fastboot.core.common.ServerStartup;
import com.github.johnsonmoon.fastboot.core.entity.ApplicationConfiguration;
import com.github.johnsonmoon.fastboot.core.util.ApplicationConfigurationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

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
     * Set the application configuration.
     *
     * @param applicationConfiguration {@link ApplicationConfiguration}
     */
    public static void setApplicationConfiguration(ApplicationConfiguration applicationConfiguration) {
        ApplicationStartup.applicationConfiguration = applicationConfiguration;
    }

    /**
     * Startup application.
     *
     * @return {@link ApplicationContext}
     */
    public static ApplicationContext startup() {
        return startup(applicationConfiguration);
    }

    /**
     * Startup application.
     *
     * @param applicationConfiguration {@link ApplicationConfiguration}
     * @return {@link ApplicationContext}
     */
    public static ApplicationContext startup(ApplicationConfiguration applicationConfiguration) {
        if (!ApplicationConfigurationUtils.checkConfigurationAll(applicationConfiguration)) {
            System.exit(0);
        }
        applicationContext = ApplicationBootstrap.springStartup(applicationConfiguration);
        ApplicationBootstrap.dispatcherStartup(applicationConfiguration, applicationContext);
        serverStartup = ApplicationBootstrap.serverStartup(applicationConfiguration);
        return applicationContext;
    }

    /**
     * Startup application, with custom spring application context initialized.
     * <pre>
     *     This method calls when custom spring context has already been initialized.
     * </pre>
     *
     * @param applicationContext       {@link ApplicationContext}
     * @param applicationConfiguration {@link ApplicationConfiguration}
     */
    public static void startup(ApplicationContext applicationContext, ApplicationConfiguration applicationConfiguration) {
        if (!ApplicationConfigurationUtils.checkConfigurationMin(applicationConfiguration)) {
            System.exit(0);
        }
        ApplicationStartup.applicationContext = applicationContext;
        ApplicationBootstrap.dispatcherStartup(applicationConfiguration, applicationContext);
        ApplicationStartup.serverStartup = ApplicationBootstrap.serverStartup(applicationConfiguration);
    }

    private static AtomicBoolean shutdown = new AtomicBoolean(false);

    /**
     * Block the main thread.
     */
    public static void await() {
        if (!shutdown.get()) {
            try {
                TimeUnit.MILLISECONDS.sleep(10_000);
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
            if (!serverStartup.isStarted()) {
                logger.warn("Server not started!");
            }
        }
    }

    /**
     * Shutdown the application.
     */
    public static void shutdown() {
        if (serverStartup != null) {
            serverStartup.stopServer();
        }
        System.exit(0);
    }
}
