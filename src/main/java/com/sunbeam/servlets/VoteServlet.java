package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;
import com.sunbeam.daos.UserDao;
import com.sunbeam.daos.UserDaoImpl;
import com.sunbeam.pojos.User;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Voted</title>");
		out.println("</head>");
		out.println("<body>");

		String uname = "";
		Cookie[] arr = req.getCookies();
		if (arr != null) {
			for (Cookie c : arr) {
				if (c.getName().equals("username")) {
					uname = c.getValue();
					break;
				}
			}
		}

		out.printf("Hello, Voter - %s! <hr/>\n", uname);

		// get current user from the session
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("curUser");
		if (user.getStatus() != 0) { // user is already voted.
			out.println("You have already voted. <br/><br/>");
		} else {
			String candId = req.getParameter("candidate");
			int id = Integer.parseInt(candId);
			try (CandidateDao dao = new CandidateDaoImpl()) {
				dao.incrementVotes(id);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}

			// update the status of current user -- as voted
			user.setStatus(1);
			try (UserDao dao = new UserDaoImpl()) {
				dao.updateStatus(user.getId(), true);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}

			out.println("Your vote is registered successfully. <br/><br/>");
		}
		out.println("<a href='logout'>Sign Out</a>");
		out.println("</body>");
		out.println("</html>");
	}
}
