package com.github.johnsonmoon.fastboot.core.common;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by johnsonmoon at 2018/5/11 21:34.
 */
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		Map<String, Object> map = new HashMap<>();
		map.put("message", exception.getMessage());
		return Response.status(Response.Status.BAD_REQUEST).entity(map)
				.type(MediaType.APPLICATION_JSON).build();
	}
}
