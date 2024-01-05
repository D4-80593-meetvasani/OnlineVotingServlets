package com.sunbeam.listeners;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.sunbeam.util.DbUtil;

@WebListener
public class ElectionApplicationListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ElectionApplication started.");
		ServletContext ctx = sce.getServletContext();
		ctx.setAttribute("userCnt", 0);
		String driver = ctx.getInitParameter("DB_DRIVER");
		String url = ctx.getInitParameter("DB_URL");
		String user = ctx.getInitParameter("DB_USER");
		String password = ctx.getInitParameter("DB_PASSWORD");
		
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, password);
			ctx.setAttribute("dbconn", con);
			
			DbUtil.ctx = ctx;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ElectionApplication ended.");
	}
}