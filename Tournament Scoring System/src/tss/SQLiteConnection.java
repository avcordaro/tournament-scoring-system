package tss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
	
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