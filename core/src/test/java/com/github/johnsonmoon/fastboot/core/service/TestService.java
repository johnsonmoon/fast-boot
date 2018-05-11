package com.github.johnsonmoon.fastboot.core.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 * Create by johnsonmoon at 2018/5/11 22:10.
 */
@Path("/service")
@Service
public class TestService {
	@GET
	@Path("/test")
	public String test(@Context HttpServletRequest request, @HeaderParam("user_id") String user_id) {
		String test_url = "";
		try {
			test_url = request.getAttribute("test_url").toString();
		} catch (Exception e) {
			test_url = e.getMessage();
		}
		return "Hello Johnson!" + test_url + "," + user_id;
	}

	@GET
	@Path("/exep")
	public String testExcep() {
		throw new RuntimeException("Yes");
	}
}
