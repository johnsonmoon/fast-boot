package com.github.johnsonmoon.fastboot.core.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Create by johnsonmoon at 2018/5/11 21:44.
 */
public class TestFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String url = httpServletRequest.getRequestURL().toString();
		request.setAttribute("test_url", url);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
