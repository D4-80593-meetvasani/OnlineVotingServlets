package com.sunbeam.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunbeam.pojos.User;

//@WebFilter({"/candlist", "/vote", "/logout", "/result", "/candedit", "/canddel", "/announce"})
public class SecurityFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		HttpSession session = req.getSession(false);
		List<String> voterUris = new ArrayList<String>();
		Collections.addAll(voterUris, "/election_servlets/candlist", "/election_servlets/vote", "/election_servlets/logout");
		if(session != null) {
			User user = (User) session.getAttribute("curUser");
			if(user != null) {
				// current request is by the authenticated user
				if(user.getRole().equals("voter") && !voterUris.contains(uri)) {
					// voter user trying to access url that is not expected to access by voter
					resp.sendError(403, "You are voter. Behave like voter.");
					return;
				}
				chain.doFilter(request, response);
				return;
			}
		}
		// current request is by non-authenticated user
		resp.sendRedirect("index.html");
	}
}
