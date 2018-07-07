
package tss.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Tournaments {
	
	private Connection conn;
	
	public Tournaments(Connection c) throws SQLException {
		conn = c;
	}
	
	public ResultSet getAllRecords() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Tournament;");
		return rs;
	}

	public ResultSet getRecord(int recordID) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Tournament WHERE TournamentID = " + recordID + ";");
		return rs;
	}
	
	public int newRecord(String title, String date, String apt, String metric, String teams, String couples)
			throws SQLException {
		String sql = "INSERT INTO Tournament (Title, Date, ArchersPerTarget, Metric, Teams, MarriedCouples)"
				+ " VALUES (?, ?, ?, ?, ?, ?);";
		PreparedStatement prepStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		prepStmt.setString(1, title);
		prepStmt.setString(2, date);
		prepStmt.setString(3, apt);
		prepStmt.setString(4, metric);
		prepStmt.setString(5, teams);
		prepStmt.setString(6, couples);
		prepStmt.execute();
		
		int newID = prepStmt.getGeneratedKeys().getInt(1);
		return newID;
	}
	
	public void deleteRecord(int recordID) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("DELETE FROM Tournament WHERE TournamentID =" + recordID + ";");
	}
	
	public void updateRecord(int recordID, String title, String date, int apt, String metric, String teams, 
			String couples) throws SQLException {
		String update = "UPDATE Tournament SET Title=?, Date=?, ArchersPerTarget=?, Metric=?, Teams=?, "
				+ "MarriedCouples=?WHERE TournamentID = " + recordID + ";";
		PreparedStatement prepStmt = conn.prepareStatement(update);
		prepStmt.setString(1, title);
		prepStmt.setString(2, date);
		prepStmt.setInt(3, apt);
		prepStmt.setString(4, metric);
		prepStmt.setString(5, teams);
		prepStmt.setString(6, couples);
		prepStmt.executeUpdate();
	}
}
