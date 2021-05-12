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

@WebServlet("/transfer")
public class TransferAccount extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("name");
		if (username != null) {

			ServletContext servletContext = getServletContext();
			int transfer_id = (Integer) servletContext.getAttribute("id");
			int receive_id = Integer.parseInt(request.getParameter("receive_id"));
			String ifsc = request.getParameter("ifsc_code");
			long amount = Long.parseLong(request.getParameter("amount"));
			EmployeeDetails e = EmployeeDataAccess.getIfscById(receive_id);
			String ifsc_code = e.getIfsc_code();

			if (ifsc_code.equals(ifsc)) {
				int status = EmployeeDataAccess.transferAccount(transfer_id, receive_id, amount);
				if (status > 0) {
					out.println("AMOUNT TRANSFERED SUCCESSFULLY!!");
					RequestDispatcher rd = request.getRequestDispatcher("/index.html");
					rd.include(request, response);
				} else {
					out.println("Sorry! unable to transfer amount");
				}

			} else {
				out.println("Id AND IFSC_CODE DOES NOT MATCHES!!");
				RequestDispatcher rd = request.getRequestDispatcher("/TransferAccount.html");
				rd.include(request, response);
			}
		} else {
			out.print("Please login first");
			RequestDispatcher rd = request.getRequestDispatcher("/login.html");
			rd.include(request, response);

		}
	}
}
