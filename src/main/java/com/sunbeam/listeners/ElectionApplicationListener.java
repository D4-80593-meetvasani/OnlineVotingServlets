package com.sunbeam.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ElectionApplicationListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ElectionApplication started.");
		ServletContext ctx = sce.getServletContext();
		ctx.setAttribute("userCnt", 0);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ElectionApplication ended.");
	}
}