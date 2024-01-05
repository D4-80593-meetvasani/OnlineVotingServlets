package com.sunbeam.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/login")
public class MeasureFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		long time1 = System.currentTimeMillis();
		chain.doFilter(request, response);
		long time2 = System.currentTimeMillis();
		System.out.println("Time required for Login: " + (time2 - time1) + " ms");
	}
}