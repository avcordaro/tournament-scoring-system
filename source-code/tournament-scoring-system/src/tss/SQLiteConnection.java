package tss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for connecting to the SQLite database used by the system.
 * @author Alex Cordaro
 */
public class SQLiteConnection {
	
	/**
	 * Connects to the SQLite database and passes connection object.
	 * @return the connection object
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			String url = "jdbc:sqlite:TSSDatabase.db";
			conn = DriverManager.getConnection(url);
			System.out.println("Successfully connected to SQLite Database.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
}