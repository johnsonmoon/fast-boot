package com.github.johnsonmoon.fastboot.core.util;

import com.github.johnsonmoon.fastboot.core.entity.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by johnsonmoon at 2018/5/15 17:45.
 */
public class ApplicationConfigurationUtils {
    private static Logger logger = LoggerFactory.getLogger(ApplicationConfigurationUtils.class);

    public static boolean checkConfiguration(ApplicationConfiguration applicationConfiguration) {
        if (applicationConfiguration == null) {
            logger.error("ApplicationConfiguration missing.");
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
}
