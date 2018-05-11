package com.github.johnsonmoon.fastboot.core.common;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

/**
 * Create by johnsonmoon at 2018/5/11 21:36.
 */
public class GlobalFilter implements ContainerRequestFilter {
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		requestContext.getHeaders().add("user_id", "5974980e182787336b898502");
	}
}
