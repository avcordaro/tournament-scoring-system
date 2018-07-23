package tss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tournaments {
	
	private Connection conn;
	
	public Tournaments(Connection c) {
		conn = c;
	}
	
	public ResultSet getAllRecords() {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Tournament;");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getRecord(int recordID) {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Tournament WHERE TournamentID = " + recordID + ";");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public int newRecord(String title, String date, int apt, String metric, String teams, String couples) {
		String sql = "INSERT INTO Tournament (Title, Date, ArchersPerTarget, Metric, Teams, MarriedCouples)"
				+ " VALUES (?, ?, ?, ?, ?, ?);";
		int newID = 0;
		try {
			PreparedStatement prepStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			prepStmt.setString(1, title);
			prepStmt.setString(2, date);
			prepStmt.setInt(3, apt);
			prepStmt.setString(4, metric);
			prepStmt.setString(5, teams);
			prepStmt.setString(6, couples);
			prepStmt.execute();
			newID = prepStmt.getGeneratedKeys().getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return newID;
	}
	
	public void deleteRecord(int recordID, Archers archers, Scores scores, MarriedCouples marriedCouples) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM Tournament WHERE TournamentID =" + recordID + ";");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ArcherID FROM Archer WHERE TournamentID = " + recordID + ";");
			while(rs.next()) {
				archers.deleteRecord(rs.getInt("ArcherID"));
				scores.deletedRecord(rs.getInt("ArcherID"));
				marriedCouples.deleteRecord(rs.getInt("ArcherID"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateRecord(int recordID, String title, String date, int apt, String metric, String teams, String couples) {
		String update = "UPDATE Tournament SET Title=?, Date=?, ArchersPerTarget=?, Metric=?, Teams=?, "
				+ "MarriedCouples=? WHERE TournamentID = " + recordID + ";";
		try {
			PreparedStatement prepStmt = conn.prepareStatement(update);
			prepStmt.setString(1, title);
			prepStmt.setString(2, date);
			prepStmt.setInt(3, apt);
			prepStmt.setString(4, metric);
			prepStmt.setString(5, teams);
			prepStmt.setString(6, couples);
			prepStmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
