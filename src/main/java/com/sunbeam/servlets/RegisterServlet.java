package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunbeam.daos.UserDao;
import com.sunbeam.daos.UserDaoImpl;
import com.sunbeam.pojos.User;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fname = req.getParameter("fname");
		String lname = req.getParameter("lname");
		String email = req.getParameter("email");
		String password = req.getParameter("pwd");
		String role = req.getParameter("role");
		String dob = req.getParameter("dob");
		
		String status = req.getParameter("status");
		
		int st = Integer.parseInt(status);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		
		Date uDate = null;
		try {
			uDate = sdf.parse(dob);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		java.sql.Date sqlDate = new java.sql.Date(uDate.getTime());
		
		try(UserDao dao = new UserDaoImpl()){
			Optional<User> userOpt = dao.findByEmail(email);
			if(userOpt.isPresent()) {
				throw new ServletException("user is already present");
			}else {
				User u = new User(0, fname, lname, email, password, sqlDate, st, role);
				dao.save(u);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		
		// presentation logic
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Registered</title>");
		out.println("</head>");
		out.println("<body>");
		

		ServletContext app = req.getServletContext();
		String title = app.getInitParameter("appTitle");
		out.printf("<h1>%s</h1>\n", title);
		
		
		out.println("<h1>Registered Sucessfully</h1><br/>");
		out.println("<a href=\"index.html\" >Login</a>");
		out.println("</body>");
		out.println("</html>");
	}
}
