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
import javax.servlet.http.HttpSession;

@WebServlet("/withdraw")
public class WithdrawAccount extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("name");
		if (username != null) {
			ServletContext servletContext = getServletContext();
			int id = (Integer) servletContext.getAttribute("id");
			long amount = Long.parseLong(request.getParameter("amount"));
			int status = EmployeeDataAccess.withdraw(id, amount);
			if (status > 0) {
				out.println("AMOUNT WITHDRAWN SUCCESSFULLY!!");
				RequestDispatcher rd = request.getRequestDispatcher("/index.html");
				rd.include(request, response);
			} else {
				out.println("Sorry! unable to withdraw amount");
				RequestDispatcher rd = request.getRequestDispatcher("/index.html");
				rd.include(request, response);
			}
		} else {
			out.print("Please login first");
			RequestDispatcher rd = request.getRequestDispatcher("/login.html");
			rd.include(request, response);

		}

	}
}
