package webapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeDataAccess {

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gnanaprakash", "postgres",
					"root");
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

	public static int save(EmployeeDetails e) {
		int status = 0;
		PreparedStatement pt = null;
		Connection con = EmployeeDataAccess.getConnection();
		try {

			pt = con.prepareStatement(
					"INSERT INTO accounts(id,name,age,dob,aadhar_no,phone_no,email,ifsc,password,amount)VALUES (?,?,?,?,?,?,?,?,?,?)");
			pt.setInt(1, e.getId());
			pt.setString(2, e.getName());
			pt.setInt(3, e.getAge());
			pt.setString(4, e.getDob());
			pt.setLong(5, e.getAadhar_no());
			pt.setLong(6, e.getPhone_no());
			pt.setString(7, e.getEmail());
			pt.setString(8, e.getIfsc_code());
			pt.setString(9, e.getPassword());
			pt.setLong(10, 0);
			status = pt.executeUpdate();

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (pt != null) {
					pt.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
		}

		return status;
	}

	public static EmployeeDetails getEmployeeById(int id) {
		EmployeeDetails e = new EmployeeDetails();
		Connection con = EmployeeDataAccess.getConnection();
		PreparedStatement pt = null;
		try {

			pt = con.prepareStatement("select * from accounts where id=?");
			pt.setInt(1, id);
			ResultSet rs = pt.executeQuery();
			if (rs.next()) {
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setAge(rs.getInt(3));
				e.setDob(rs.getString(4));
				e.setAadhar_no(rs.getLong(5));
				e.setPhone_no(rs.getLong(6));
				e.setEmail(rs.getString(7));
				e.setIfsc_code(rs.getString(8));
				e.setPassword(rs.getString(9));

				e.setAmount(rs.getLong(10));
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (pt != null) {
					pt.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
		}

		return e;
	}

	public static int update(EmployeeDetails e) {
		int status = 0;
		Connection con = EmployeeDataAccess.getConnection();
		PreparedStatement pt = null;
		try {
			con = EmployeeDataAccess.getConnection();
			pt = con.prepareStatement(
					"update  accounts set name=?,age=?,dob=?,aadhar_no=?,phone_no=?,email=?,ifsc=?,password=?,amount=? where id=?");
			pt.setString(1, e.getName());
			pt.setInt(2, e.getAge());
			pt.setString(3, e.getDob());
			pt.setLong(4, e.getAadhar_no());
			pt.setLong(5, e.getPhone_no());
			pt.setString(6, e.getEmail());
			pt.setString(7, e.getIfsc_code());
			pt.setString(8, e.getPassword());
			pt.setLong(9, e.getAmount());
			pt.setInt(10, e.getId());
			status = pt.executeUpdate();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (pt != null) {
					pt.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
		}

		return status;
	}

	public static int deleteUser(int id) {
		int status = 0;
		Connection con = EmployeeDataAccess.getConnection();
		PreparedStatement pt = null;
		PreparedStatement s3 = null;
		ResultSet rs = null;
		PreparedStatement s = null;
		PreparedStatement s1 = null;
		PreparedStatement s2 = null;
		try {
			con = EmployeeDataAccess.getConnection();

			s3 = con.prepareStatement("SELECT * FROM transaction_history where accounts_id=?;");
			s3.setInt(1, id);

			rs = s3.executeQuery();
			while (rs.next()) {

				int t_id = rs.getInt("transfer_id");
				s = con.prepareStatement("delete from transfer_transactions where fetch_id = ?");
				s.setLong(1, t_id);
				s.executeUpdate();
			}

			s1 = con.prepareStatement("delete from accounts where id = ?");
			s1.setLong(1, id);
			s2 = con.prepareStatement("delete from transaction_history where accounts_id = ?");
			s2.setLong(1, id);

			status = s2.executeUpdate();
			status = s1.executeUpdate();

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (pt != null) {
					pt.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (s3 != null) {
					s3.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (s != null) {
					s.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (s1 != null) {
					s1.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (s2 != null) {
					s2.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
		}

		return status;
	}

	public static int deposit(int id, long amt) {
		int status = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String m = String.valueOf(formatter.format(date));
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		PreparedStatement s = null;
		PreparedStatement pt1 = null;
		try {
			con = EmployeeDataAccess.getConnection();
			pt = con.prepareStatement("select * from accounts where id=?");
			pt.setInt(1, id);
			rs = pt.executeQuery();
			if (rs.next()) {
				long amount = rs.getLong("amount") + amt;
				s = con.prepareStatement("update accounts set amount=? where id=?");
				s.setLong(1, amount);
				s.setInt(2, id);
				status = s.executeUpdate();

				pt1 = con.prepareStatement(
						"INSERT INTO transaction_history(accounts_id,amount,type,date)VALUES (?,?,?,?)");
				pt1.setInt(1, id);
				pt1.setLong(2, amt);
				pt1.setString(3, "deposit");
				pt1.setString(4, m);
				pt1.executeUpdate();
			}

		} catch (Exception e1) {
			e1.printStackTrace();

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (s != null) {
					s.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (pt != null) {
					pt.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (pt1 != null) {
					pt1.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
		}

		return status;

	}

	public static int withdraw(int id, long amt) {
		int status = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String m = String.valueOf(formatter.format(date));
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		PreparedStatement s = null;
		PreparedStatement pt1 = null;
		try {
			con = EmployeeDataAccess.getConnection();
			pt = con.prepareStatement("select * from accounts where id=?");
			pt.setInt(1, id);
			rs = pt.executeQuery();
			if (rs.next()) {

				long amount = rs.getLong("amount");
				if (amount >= amt) {
					amount = amount - amt;
					s = con.prepareStatement("update accounts set amount=? where id=?");
					s.setLong(1, amount);
					s.setInt(2, id);
					status = s.executeUpdate();
					pt1 = con.prepareStatement(
							"INSERT INTO transaction_history(accounts_id,amount,type,date)VALUES (?,?,?,?)");
					pt1.setInt(1, id);
					pt1.setLong(2, amt);
					pt1.setString(3, "withdraw");
					pt1.setString(4, m);
					pt1.executeUpdate();
				} else {
					status = 0;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (s != null) {
					s.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (pt != null) {
					pt.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (pt1 != null) {
					pt1.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
		}
		return status;

	}

	public static int transferAccount(int transfer_id, int receive_id, long amt) {
		int status = 0;
		long trnsfer_id = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String m = String.valueOf(formatter.format(date));
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		PreparedStatement pt = null;
		PreparedStatement pt1 = null;
		PreparedStatement pt2 = null;
		PreparedStatement pt3 = null;
		PreparedStatement pt4 = null;
		PreparedStatement p = null;
		PreparedStatement p1 = null;
		Connection con = null;
		PreparedStatement s = null;

		try {

			con = EmployeeDataAccess.getConnection();
			pt1 = con.prepareStatement("SELECT * FROM accounts where id=?;");
			pt1.setInt(1, transfer_id);
			rs1 = pt1.executeQuery();
			while (rs1.next()) {
				long amount = rs1.getLong("amount") - amt;

				s = con.prepareStatement("update accounts set amount=? where id=?");
				s.setLong(1, amount);
				s.setInt(2, transfer_id);
				status = s.executeUpdate();
				pt = con.prepareStatement(
						"INSERT INTO transaction_history(accounts_id,amount,type,date)VALUES (?,?,?,?)");
				pt.setInt(1, transfer_id);
				pt.setLong(2, amt);
				pt.setString(3, "transfer");
				pt.setString(4, m);
				pt.executeUpdate();
				pt3 = con.prepareStatement("SELECT * FROM transaction_history where accounts_id=?;");
				pt3.setInt(1, transfer_id);
				rs3 = pt3.executeQuery();

				while (rs3.next()) {

					trnsfer_id = rs3.getLong("transfer_id");

				}

				pt4 = con.prepareStatement(
						"INSERT INTO transfer_transactions(fetch_id,from_id,to_id,status)VALUES (?,?,?,?)");
				pt4.setLong(1, trnsfer_id);
				pt4.setInt(2, transfer_id);
				pt4.setInt(3, receive_id);
				pt4.setString(4, "debited");
				pt4.executeUpdate();
				break;
			}
			pt1.setInt(1, receive_id);
			rs2 = pt1.executeQuery();
			while (rs2.next()) {
				long amount = rs2.getLong("amount") + amt;

				pt2 = con.prepareStatement("update accounts set amount=? where id=?");
				pt2.setLong(1, amount);
				pt2.setInt(2, receive_id);
				status = pt2.executeUpdate();
				p = con.prepareStatement(
						"INSERT INTO transaction_history(accounts_id,amount,type,date)VALUES (?,?,?,?)");
				p.setInt(1, receive_id);
				p.setLong(2, amt);
				p.setString(3, "transfer");
				p.setString(4, m);
				p.executeUpdate();
				pt3.setInt(1, receive_id);
				rs4 = pt3.executeQuery();
				while (rs4.next()) {
					trnsfer_id = rs4.getLong("transfer_id");

				}

				p1 = con.prepareStatement(
						"INSERT INTO transfer_transactions(fetch_id,from_id,to_id,status)VALUES (?,?,?,?)");

				p1.setLong(1, trnsfer_id);
				p1.setInt(2, transfer_id);
				p1.setInt(3, receive_id);
				p1.setString(4, "credited");
				p1.executeUpdate();
				break;
			}

		} catch (Exception e1) {
			e1.printStackTrace();

		} finally {
			try {
				if (rs1 != null) {
					rs1.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (rs2 != null) {
					rs2.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (rs3 != null) {
					rs3.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (rs4 != null) {
					rs4.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (pt != null) {
					pt.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (pt3 != null) {
					pt3.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (pt4 != null) {
					pt4.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (s != null) {
					s.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (pt1 != null) {
					pt1.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (p != null) {
					p.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}try {
				if (p1 != null) {
					p1.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (pt2 != null) {
					pt2.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
		}

		return status;
	}

	public static EmployeeDetails getIfscById(int id) {
		EmployeeDetails e = new EmployeeDetails();
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		try {
			con = EmployeeDataAccess.getConnection();
			pt = con.prepareStatement("select * from accounts where id=?");
			pt.setInt(1, id);
			rs = pt.executeQuery();
			if (rs.next()) {

				e.setIfsc_code(rs.getString(8));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (pt != null) {
					pt.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
		}

		return e;
	}

	public static EmployeeDetails getEmployeeDetailsById(int id) {
		EmployeeDetails e = new EmployeeDetails();
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		try {
			con = EmployeeDataAccess.getConnection();
			pt = con.prepareStatement("select * from accounts where id=?");
			pt.setInt(1, id);
			rs = pt.executeQuery();
			if (rs.next()) {
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setAge(rs.getInt(3));
				e.setDob(rs.getString(4));
				e.setAadhar_no(rs.getLong(5));
				e.setPhone_no(rs.getLong(6));
				e.setEmail(rs.getString(7));
				e.setIfsc_code(rs.getString(8));
				e.setPassword(rs.getString(9));

				e.setAmount(rs.getLong(10));

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (pt != null) {
					pt.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
		}

		return e;
	}

	public static String getPassword(int id) {
		String password = null;
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		try {
			con = EmployeeDataAccess.getConnection();

			pt = con.prepareStatement("SELECT * FROM accounts where id=?;");
			pt.setInt(1, id);
			rs = pt.executeQuery();
			while (rs.next()) {
				password = rs.getString("password");
				break;
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (pt != null) {
					pt.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				System.exit(0);
			}
		}
		return password;
	}
}
