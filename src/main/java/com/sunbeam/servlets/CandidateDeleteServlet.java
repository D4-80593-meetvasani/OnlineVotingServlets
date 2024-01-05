package com.sunbeam.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;

@WebServlet("/canddel")
public class CandidateDeleteServlet extends HttpServlet {
	@Override
	protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException ,java.io.IOException {
		processRequest(req, resp);
	}	
	@Override
	protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException ,java.io.IOException {
		processRequest(req, resp);
	}	
	
	protected void processRequest(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException ,java.io.IOException {
		String candId = req.getParameter("id");
		int id = Integer.parseInt(candId);
		int cnt = 0;
		try(CandidateDao dao = new CandidateDaoImpl()) {
			cnt = dao.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		req.setAttribute("message", "Candidates Deleted: " + cnt);
		//resp.sendRedirect("result");
		
//		RequestDispatcher rd = req.getRequestDispatcher("result");
		
		ServletContext ctx = req.getServletContext();
		RequestDispatcher rd = ctx.getRequestDispatcher("/result");
		rd.forward(req, resp);
	}
}