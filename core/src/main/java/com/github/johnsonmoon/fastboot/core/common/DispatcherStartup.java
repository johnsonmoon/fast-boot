package com.github.johnsonmoon.fastboot.core.common;

import com.github.johnsonmoon.fastboot.core.entity.ApplicationConfiguration;
import org.springframework.context.ApplicationContext;

/**
 * Create by johnsonmoon at 2018/5/13 12:56.
 */
public interface DispatcherStartup {
	/**
	 * Init dispatcher.
	 * <pre>
	 *     default dispatcher: {@link com.github.johnsonmoon.fastboot.core.common.impl.RestEasyDispatcherStartup}
	 * </pre>
	 *
	 * @param configuration      {@link ApplicationConfiguration}
	 * @param applicationContext {@link ApplicationContext}
	 * @return {@link ApplicationConfiguration}
	 */
	ApplicationConfiguration dispatcherInit(ApplicationConfiguration configuration,
			ApplicationContext applicationContext);
}
