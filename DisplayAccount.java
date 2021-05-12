package webapplication;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/display")
public class DisplayAccount extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		response.setContentType("text/html");

		ServletContext servletContext = getServletContext();

		int id = (Integer) servletContext.getAttribute("id");

		EmployeeDetails e = EmployeeDataAccess.getEmployeeDetailsById(id);
		String name = e.getName();
		int age = e.getAge();
		String dob = e.getDob();
		long aadhar_no = e.getAadhar_no();
		long phone_no = e.getPhone_no();
		String email_id = e.getEmail();
		String ifsc_code = e.getIfsc_code();
		long amount = e.getAmount();

		out.println("<br>ID= " + id);
		out.println("<br>NAME= " + name);
		out.println("<br>AGE = " + age);
		out.println("<br>DOB = " + dob);
		out.println("<br>AADHAR_NO = " + aadhar_no);
		out.println("<br>PHONE_NO= " + phone_no);
		out.println("<br>EMAIL= " + email_id);
		out.println("<br>IFSC = " + ifsc_code);
		out.println("<br>AMOUNT = " + amount);

	}
}