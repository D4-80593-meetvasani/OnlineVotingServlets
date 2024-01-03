package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;
import com.sunbeam.pojos.Candidate;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// business logic
		List<Candidate> list = new ArrayList<>();
		try(CandidateDao dao = new CandidateDaoImpl()) {
			list = dao.findAllOrderByVotesDesc();
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
		out.println("<title>Result</title>");
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
		}		out.println("<table border='1'>");
		
		
		out.printf("Hello, Admin - %s! <hr/>\n", uname);

		out.println("<thead>");
		out.println("<th>Id</th>");
		out.println("<th>Name</th>");
		out.println("<th>Party</th>");
		out.println("<th>Votes</th>");
		out.println("<th>Action</th>");
		out.println("</thead>");
		for (Candidate c : list)
			out.printf("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>"
					+ "<a href='candedit?id=%d'><img width='28' height='28' src='edit.png' alt='Edit'/></a>"
					+ "<a href='canddel?id=%d'><img width='28' height='28' src='delete.png' alt='Delete'/></a>"
					+ "</td></tr>\n", 
					c.getId(), c.getName(), c.getParty(), c.getVotes(), c.getId(), c.getId());
		out.println("</table>");

		String msg = (String) req.getAttribute("message");
		if(msg != null)
			out.println("<br/><br/>" + msg);
		out.println("<br/><br/><a href='logout'>Sign Out</a>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}
}