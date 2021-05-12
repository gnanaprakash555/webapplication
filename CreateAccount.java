package webapplication;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/create")
public class CreateAccount extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String dob = request.getParameter("dob");
		long aadhar_no = Long.parseLong(request.getParameter("aadhar"));
		long phone_no = Long.parseLong(request.getParameter("phone"));
		String email = request.getParameter("email");
		String ifsc_code = request.getParameter("ifsc");
		String password = request.getParameter("password");
		long amount = 0;

		EmployeeDetails e = new EmployeeDetails();
		e.setId(id);
		e.setName(name);
		e.setAge(age);
		e.setDob(dob);
		e.setAadhar_no(aadhar_no);
		e.setPhone_no(phone_no);
		e.setEmail(email);
		e.setIfsc_code(ifsc_code);
		e.setPassword(password);

		e.setAmount(amount);

		int status = EmployeeDataAccess.save(e);
		if (status > 0) {
			out.print("<p>Record saved successfully!</p>");
			request.getRequestDispatcher("ManagerIndex.html").include(request, response);
		} else {
			out.println("Sorry! unable to save record");
		}

		out.close();

	}

}
