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

@SuppressWarnings("serial")
@WebServlet("/delete")
public class DeleteAccount extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		HttpSession session1 = request.getSession();
		String username = (String) session1.getAttribute("name");
		if (username != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			out.print(id);
			int status = EmployeeDataAccess.deleteUser(id);
			out.print(status);
			out.println("ACCOUNT DELETED SUCCESSFULLY!!");
			RequestDispatcher rd = request.getRequestDispatcher("/ManagerIndex.html");
			rd.include(request, response);
		} else {
			out.print("Please login first");
			RequestDispatcher rd = request.getRequestDispatcher("/ManagerLogin.html");
			rd.include(request, response);

		}
	}
}
