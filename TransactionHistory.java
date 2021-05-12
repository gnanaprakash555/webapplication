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

@WebServlet("/transactionhistory")

public class TransactionHistory extends HttpServlet {
	static long amount;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		response.setContentType("text/html");

		Connection c = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		ResultSet rs1 = null;
		PreparedStatement pt = null;

		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gnanaprakash", "postgres", "root");
			c.setAutoCommit(false);
			stmt1 = c.createStatement();
			stmt2 = c.createStatement();

			int opt = Integer.parseInt(request.getParameter("choice"));

			switch (opt) {
			case 1:
				pt = c.prepareStatement("SELECT *  FROM transaction_history  where type='deposit' and accounts_id=?;");
				pt.setInt(1, 1);
				rs1 = pt.executeQuery();
				while (rs1.next()) {

					amount = rs1.getLong("amount");
					String date = rs1.getString("date");

					out.println("<br>AMOUNT:" + amount);
					out.println("<br>DATE:" + date);
					out.println("<br>TYPE:" + "deposit");

				}
				break;
			case 2:
				pt = c.prepareStatement("SELECT *  FROM transaction_history  where type='withdraw' and accounts_id=?;");
				pt.setInt(1, 1);
				rs1 = pt.executeQuery();
				while (rs1.next()) {
					amount = rs1.getLong("amount");
					String date = rs1.getString("date");
					String type = rs1.getString("type");
					out.println("<br>AMOUNT:" + amount);
					out.println("<br>DATE:" + date);
					out.println("<br>TYPE:" + type);

				}
				break;
			case 3:
				pt = c.prepareStatement(
						"select accounts_id,amount,type,date,transfer_id,from_id,to_id,status FROM transaction_history RIGHT JOIN transfer_transactions \n"
								+ "ON transaction_history .transfer_id = transfer_transactions.fetch_id\n"
								+ "where transaction_history .accounts_id=?;");
				pt.setInt(1, 1);
				rs1 = pt.executeQuery();
				while (rs1.next()) {

					String type = rs1.getString("type");
					amount = rs1.getLong("amount");
					String date = rs1.getString("date");

					out.println("<br>AMOUNT:" + amount);
					out.println("<br>DATE:" + date);
					out.println("<br>TYPE:" + type);
					String status = rs1.getString("status");
					int to_id = rs1.getInt("to_id");
					int from_id = rs1.getInt("from_id");
					out.println("<br>FROM_ID:" + from_id);
					out.println("<br>TO_ID:" + to_id);
					out.println("<br>STATUS:" + status);

				}
				break;
			case 4:
				pt = c.prepareStatement("SELECT * FROM transaction_history FULL OUTER JOIN transfer_transactions\n"
						+ "ON transaction_history .transfer_id =transfer_transactions .fetch_id\n"
						+ "where transaction_history .accounts_id=?;");
				pt.setInt(1, 1);
				rs1 = pt.executeQuery();

				while (rs1.next()) {
					String type = rs1.getString("type");
					if (type.equals("deposit") || type.equals("withdraw")) {
						amount = rs1.getLong("amount");
						String date = rs1.getString("date");

						out.println("AMOUNT:" + amount);
						out.println("DATE:" + date);
						out.println("TYPE:" + type);

					} else {
						amount = rs1.getLong("amount");
						String date = rs1.getString("date");
						out.println("<br>AMOUNT:" + amount);
						out.println("<br>DATE:" + date);
						out.println("<br>TYPE:" + type);
						String status = rs1.getString("status");
						int to_id = rs1.getInt("to_id");
						int from_id = rs1.getInt("from_id");
						out.println("<br>FROM_ID:" + from_id);
						out.println("<br>TO_ID:" + to_id);
						out.println("<br>STATUS:" + status);

					}
				}

				break;
			case 0:
				out.println("THANK YOU");
				break;
			}

			c.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
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
				if (rs1 != null) {
					rs1.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
			try {

				if (stmt1 != null) {
					stmt1.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
			try {
				if (stmt2 != null) {
					stmt2.close();
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
