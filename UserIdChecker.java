package webapplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/idchecker")
public class UserIdChecker extends HttpServlet{
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int id=Integer.parseInt(request.getParameter("val"));  
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gnanaprakash", "postgres", "root");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			PreparedStatement ps=c.prepareStatement("select * from accounts where id=?");  
			ps.setInt(1,id);  
			 rs=ps.executeQuery();  
			if(rs.next()){  
			out.print("Unavailable!");  
			}else{  
			out.print("Available!");  
			out.print("<form action='create' method='post'>");
			out.print("<table>");
			
			out.print("<tr><td></td><td><input type='hidden' name='id' value='" + id + "'/></td></tr>");
			out.print("<tr><td>Name:</td><td><input type='text' name='name' /></td></tr>");
			out.print("<tr><td>Age:</td><td><input type='text' name='age' /></td></tr>");
			out.print("<tr><td>Dob:</td><td><input type='text' name='dob' /></td></tr>");
			out.print("<tr><td>Aadhar_no:</td><td><input type='text' name='aadhar_no' /></td></tr>");
			out.print("<tr><td>Phone_no:</td><td><input type='text' name='phone_no'/></td></tr>");
			out.print("<tr><td>Email:</td><td><input type='text' name='email' /></td></tr>");
			out.print("<tr><td>Ifsc_code:</td><td><input type='text' name='ifsc_code' /></td></tr>");
			out.print("<tr><td>Password:</td><td><input type='password' name='password' />  </td></tr>");
			out.print("<tr><td></td><td><input type='hidden' name='amount' /></td></tr>");
			out.print("<tr><td colspan='2'><input type='submit' value='create'/></td></tr>");
			out.print("</table>");
			out.print("</form>");
			}  
			c.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
			try {
				if (pt != null) {
					pt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		}

		
		
		
		

}
}