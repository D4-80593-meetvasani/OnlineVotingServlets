package com.sunbeam.listeners;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//@WebListener
public class ElectionUserSessionListener implements HttpSessionListener {
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("New user session is created.");
		// will execute when req.getSession() called first time for an user
		ServletContext ctx = se.getSession().getServletContext();
		Integer cnt = (Integer)ctx.getAttribute("userCnt");
		ctx.setAttribute("userCnt", cnt+1);
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("User session is destroyed.");
		// will execute when session.invalidate() or session timed-out.
		ServletContext ctx = se.getSession().getServletContext();
		Integer cnt = (Integer)ctx.getAttribute("userCnt");
		ctx.setAttribute("userCnt", cnt-1);
	}
}