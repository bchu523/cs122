import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

	static Connection con;
	static String url;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/moviedb";

	public static Connection getConnection() {

		try {

			Class.forName("com.mysql.jdbc.Driver");

			try {
				con = DriverManager.getConnection(DB_URL, "root", "mewmew");
				System.out.println("Connection established successfully!");
				// assuming your SQL Server's username is "username"
				// and password is "password"

			}

			catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("F!");
			}
		}

		catch (ClassNotFoundException e) {
			System.out.println(e);
			System.out.println("f2!");
		}

		return con;
	}

	public static void main(String[] args) {

		ConnectionManager cm = new ConnectionManager();
		Connection con = cm.getConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// execute select query
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("select * from Customers");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				System.out.print(rs.getInt(1) + " ");
				System.out.println(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}