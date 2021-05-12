package webapplication;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/edit")
public class EditAccount extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<h1>Update Accounts</h1>");
		ServletContext servletContext = getServletContext();
		int id = (Integer) servletContext.getAttribute("id");
		EmployeeDetails e = EmployeeDataAccess.getEmployeeById(id);

		out.print("<form action='update' method='post'>");
		out.print("<table>");
		out.print("<tr><td></td><td><input type='hidden' name='id' value='" + e.getId() + "'/></td></tr>");
		out.print("<tr><td>Name:</td><td><input type='text' name='name' value='" + e.getName() + "'/></td></tr>");
		out.print("<tr><td>Age:</td><td><input type='text' name='age' value='" + e.getAge() + "'/></td></tr>");
		out.print("<tr><td>Dob:</td><td><input type='text' name='dob' value='" + e.getDob() + "'/></td></tr>");
		out.print("<tr><td>Aadhar_no:</td><td><input type='text' name='aadhar_no' value='" + e.getAadhar_no()
				+ "'/></td></tr>");
		out.print("<tr><td>Phone_no:</td><td><input type='text' name='phone_no' value='" + e.getPhone_no()
				+ "'/></td></tr>");
		out.print("<tr><td>Email:</td><td><input type='text' name='email' value='" + e.getEmail() + "'/></td></tr>");
		out.print("<tr><td>Ifsc_code:</td><td><input type='text' name='ifsc_code' value='" + e.getIfsc_code()
				+ "'/></td></tr>");
		out.print("<tr><td>Password:</td><td><input type='password' name='password' value='" + e.getPassword()
				+ "'/>  </td></tr>");
		out.print("<tr><td></td><td><input type='hidden' name='amount' value='" + e.getAmount() + "'/></td></tr>");
		out.print("<tr><td colspan='2'><input type='submit' value='Edit & Save '/></td></tr>");
		out.print("</table>");
		out.print("</form>");

	}
}
