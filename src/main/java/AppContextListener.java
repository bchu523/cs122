import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext ctx = servletContextEvent.getServletContext();

		String url = ctx.getInitParameter("DBURL");
		String u = ctx.getInitParameter("DBUSER");
		String p = ctx.getInitParameter("DBPWD");

		// create database connection from init parameters and set it to context
		DBConnectionManager dbManager;
		try {
			dbManager = new DBConnectionManager(url, u, p);
			ctx.setAttribute("DBManager", dbManager);
			System.out.println("Database connection initialized for Application.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext ctx = servletContextEvent.getServletContext();
		DBConnectionManager dbManager = (DBConnectionManager) ctx.getAttribute("DBManager");
		dbManager.closeConnection();
		System.out.println("Database connection closed for Application.");

	}

}