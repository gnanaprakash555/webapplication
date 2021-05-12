package webapplication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/manager")
public class ManagerLogin extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("manager_id"));
		String username = request.getParameter("manager_id");
		String pass = request.getParameter("userPass");
		if (pass.equals("icici")) {

			RequestDispatcher rd = request.getRequestDispatcher("/ManagerIndex.html");
			rd.forward(request, response);
			HttpSession session1 = request.getSession();
			session1.setAttribute("name", username);
		} else {
			out.print("Sorry UserName or Password Error!");
			RequestDispatcher rd = request.getRequestDispatcher("/ManagerLogin.html");
			rd.include(request, response);

		}

	}
}