package webapplication;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/delete")
public class DeleteAccount extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		int id = Integer.parseInt(request.getParameter("id"));
		out.print(id);
		int status = EmployeeDataAccess.deleteUser(id);
		out.print(status);
		out.println("ACCOUNT DELETED SUCCESSFULLY!!");
		RequestDispatcher rd = request.getRequestDispatcher("/ManagerIndex.html");
		rd.include(request, response);

	}
}
