package webapplication;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deposit")
public class DepositAccount extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		ServletContext servletContext = getServletContext();
		int id = (Integer) servletContext.getAttribute("id");
		long amount = Long.parseLong(request.getParameter("amount"));
		int status = EmployeeDataAccess.deposit(id, amount);
		if (status > 0) {
			out.println("AMOUNT DEPOSITEDTED SUCCESSFULLY!!");
			RequestDispatcher rd = request.getRequestDispatcher("/index.html");
			rd.include(request, response);
		} else {
			out.println("Sorry! unable to deposit amount");
		}

	}
}