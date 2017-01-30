import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.User;

@WebServlet(name = "Login", urlPatterns = { "/Login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String errorMsg = null;
		System.out.println("query1");
		if (email == null || email.equals("")) {
			errorMsg = "User Email can't be null or empty";
		}
		if (password == null || password.equals("")) {
			errorMsg = "Password can't be null or empty";
		}

		if (errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" + errorMsg + "</font>");
			rd.include(request, response);
		} else {
			DBConnectionManager dbcm = (DBConnectionManager) getServletContext().getAttribute("DBManager");
			Connection con = dbcm.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(
						"select id, first_name, email from Customers where email=? and password=? limit 1");
				ps.setString(1, email);
				ps.setString(2, password);
				rs = ps.executeQuery();
				System.out.println("query");
				if (rs != null && rs.next()) {

					User user = new User(rs.getString("first_name"), rs.getString("email"), rs.getInt("id"));

					HttpSession session = request.getSession();
					session.setAttribute("User", user);
					response.sendRedirect("home.jsp");
					;
				} else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
					PrintWriter out = response.getWriter();

					out.println("<font color=red>No user found with given email id, please register first.</font>");
					rd.include(request, response);
				}
			} catch (SQLException e) {
				e.printStackTrace();

				throw new ServletException("DB Connection problem.");
			} finally {
				try {
					rs.close();
					ps.close();
				} catch (SQLException e) {

				}

			}
		}
	}

}