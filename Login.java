package webapplication;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/servlet1")
public class Login extends HttpServlet {
	static String password;
	static int id;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		int id = 0;

		getServletContext().setAttribute("id", id);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("userName"));
		String pass = request.getParameter("userPass");

		password = EmployeeDataAccess.getPassword(id);
		out.print(password);
		if (pass.equals(password)) {

			HttpSession oldSession = request.getSession(false);
			if (oldSession != null) {
				oldSession.invalidate();
			}

			HttpSession newSession = request.getSession(true);
			newSession.setMaxInactiveInterval(5 * 60);

			ServletContext servletContext = getServletContext();
			servletContext.setAttribute("id", id);

			RequestDispatcher rd = request.getRequestDispatcher("/index.html");
			rd.forward(request, response);

		} else {
			out.print("Sorry UserName or Password Error!");
			RequestDispatcher rd = request.getRequestDispatcher("/login.html");
			rd.include(request, response);

		}

	}
}
