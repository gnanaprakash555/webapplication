package webapplication;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/update")
public class UpdateAccount extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String dob = request.getParameter("dob");
		long aadhar_no = Long.parseLong(request.getParameter("aadhar_no"));
		long phone_no = Long.parseLong(request.getParameter("phone_no"));
		String email = request.getParameter("email");
		String ifsc = request.getParameter("ifsc_code");
		String password = request.getParameter("password");
		long amount = Long.parseLong(request.getParameter("amount"));

		EmployeeDetails e = new EmployeeDetails();
		e.setId(id);
		e.setName(name);
		e.setAge(age);
		e.setDob(dob);
		e.setAadhar_no(aadhar_no);
		e.setPhone_no(phone_no);
		e.setEmail(email);
		e.setIfsc_code(ifsc);
		e.setPassword(password);
		e.setAmount(amount);
		int status = EmployeeDataAccess.update(e);
		if (status > 0) {
			out.println("DATA UPDATED SUCCESSFULLY!!");
			RequestDispatcher rd = request.getRequestDispatcher("/index.html");
			rd.include(request, response);
		} else {
			out.println("Sorry! unable to update record");
		}

	}
}
