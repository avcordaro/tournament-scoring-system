package tss.java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Tournaments {
	
	private Connection conn;
	private ResultSet rs;
	
	public Tournaments(Connection c) throws SQLException {
		conn = c;
		Statement stmt = conn.createStatement();
		String query = "SELECT * FROM Tournament;";
		rs = stmt.executeQuery(query);
	}
	
	public ResultSet getAllRecords() {
		return rs;
	}
	
	public ResultSet getSpecificRecord(int recordID) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "SELECT * FROM Tournament WHERE TournamentID = " + recordID + ";";
		ResultSet rsTemp = stmt.executeQuery(query);
		return rsTemp;
	}
}
