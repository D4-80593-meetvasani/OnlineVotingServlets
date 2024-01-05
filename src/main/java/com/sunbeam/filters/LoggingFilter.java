package com.sunbeam.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class LoggingFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("LoggingFilter.init() called.");
	}
	@Override
	public void destroy() {
		System.out.println("LoggingFilter.destroy() called.");
	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest rq = (HttpServletRequest) req;
		// pre-processing
		String method = rq.getMethod();
		String uri = rq.getRequestURI();
		System.out.println("BEFORE: " + method + " request to " + uri);
		chain.doFilter(req, resp);
		// post-processing
		System.out.println(" AFTER: " + method + " request to " + uri);
	}
}