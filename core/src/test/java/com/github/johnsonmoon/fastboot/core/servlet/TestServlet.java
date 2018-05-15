package com.github.johnsonmoon.fastboot.core.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by johnsonmoon at 2018/5/15 18:11.
 */
public class TestServlet implements Servlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String url = request.getRequestURL().toString();
		if (url.contains("/api/service2/test1")) {
			String method = request.getMethod();
			response.getOutputStream().write(("RequestMethod: " + method).getBytes());
			response.getOutputStream().flush();
		}
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void destroy() {

	}
}
