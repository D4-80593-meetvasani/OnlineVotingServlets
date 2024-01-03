package com.sunbeam.servlets;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;
import com.sunbeam.pojos.Candidate;

@WebServlet("/candlist")
public class CandidateListServlet extends HttpServlet {
	protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException ,java.io.IOException {
		processRequest(req, resp);
	}
	protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException ,java.io.IOException {
		processRequest(req, resp);
	}
	protected void processRequest(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException ,java.io.IOException {
		// business logic
		List<Candidate> list = new ArrayList<>();
		try(CandidateDao dao = new CandidateDaoImpl()) {
			list = dao.findAll();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		// presentation logic
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Candidates</title>");
		out.println("</head>");
		out.println("<body>");
		String uname = "";
		Cookie[] arr = req.getCookies();
		if(arr != null) {
			for (Cookie c : arr) {
				if(c.getName().equals("username")) {
					uname = c.getValue();
					break;
				}
			}
		}
		
		out.printf("Hello, Voter - %s! <hr/>\n", uname);	
		
		// if any new announcement is available, then display it.
		ServletContext ctx = req.getServletContext();
		String message = (String) ctx.getAttribute("announcement");
		if(message != null)
			out.println("Annoucement: " + message + "<br/><br/>");
		
		out.printf("<form method='post' action='vote'>\n");
		
		for (Candidate c : list)
			out.printf("<input type='radio' name='candidate' value='%s'/> %s - %s <br/>\n", 
											c.getId(), c.getName(), c.getParty());
		out.println("<input type='submit' value='Vote'/>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");

	}
}










