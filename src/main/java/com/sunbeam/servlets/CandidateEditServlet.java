package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;
import com.sunbeam.pojos.Candidate;

@WebServlet("/candedit")
public class CandidateEditServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// find candidate by id and display in html form
		String candId = req.getParameter("id");
		int id = Integer.parseInt(candId);

		Optional<Candidate> candOpt = null;
		try(CandidateDao dao = new CandidateDaoImpl()) {
			candOpt =  dao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}

		//candOpt.orElseThrow(() -> new ServletException("Candidate Not Found"));
		Candidate cand = candOpt.get();
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Edit Candidate</title>");
		out.println("</head>");
		out.println("<body>");
		

		ServletContext app = req.getServletContext();
		String title = app.getInitParameter("appTitle");
		out.printf("<h1>%s</h1>\n", title);
		
		out.println("<form method='post' action='candedit'>");
		out.printf("<input type='hidden' name='id' value='%d' readonly><br/><br/>\n", cand.getId());
		out.printf("Name: <input type='text' name='name' value='%s'><br/><br/>\n", cand.getName());
		out.printf("Party: <input type='text' name='party' value='%s'><br/><br/>\n", cand.getParty());
		out.printf("Votes: <input type='text' name='votes' value='%d' readonly><br/><br/>\n", cand.getVotes());
		out.println("<input type='submit' value='Update Candidate'/>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get candidate values from req param and update in db.
		String candId = req.getParameter("id"); // from hidden form field
		String name = req.getParameter("name"); // from input text
		String party = req.getParameter("party"); // from input text
		String votes = req.getParameter("votes"); // from input text (read-only)
		
		Candidate cand = new Candidate(Integer.parseInt(candId), name, party, Integer.parseInt(votes));
		int cnt = 0;
		try(CandidateDao candDao = new CandidateDaoImpl()) {
			cnt = candDao.update(cand);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		req.setAttribute("message", "Candidates Updated: " + cnt);

		// forward req to result servlet
		RequestDispatcher rd = req.getRequestDispatcher("result");
		rd.forward(req, resp);
	}
	
}


