package tss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class containing methods to do with creating, retrieving and updating tournament data.
 * @author Alex Cordaro
 */
public class Tournaments {
	
	private Connection conn;
	
	public Tournaments(Connection c) {
		conn = c;
	}
	
	/**
	 * Retrieves all tournament records from the database.
	 * @return ResultSet of the query result
	 */
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
	
	/**
	 * Retrieves a given tournament record from the database.
	 * @param recordID id of the tournament
	 * @return ResultSet of the tournament's data
	 */
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
	
	/**
	 * Creates a new tournament record in the database.
	 * @param title the tournament's title
	 * @param date date of the tournament
	 * @param apt max number of archers per target 
	 * @param metric whether the tournament uses metric rounds or not
	 * @param teams whether the tournament requires team results
	 * @param couples whether the tournament will have a married couples award
	 * @return the id of the newly created record in the database
	 */
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
	
	/**
	 * Deletes the given tournament record, along with all other associated data in the database.
	 * @param recordID the id the tournament
	 * @param archers an Archers object to perform archer record deletions
	 * @param scores a Scores object to perform score record deletions
	 * @param marriedCouples a MarriedCouples object to perform married couple record deletions
	 */
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
	
	/**
	 * Updates a given record in the database.
	 * @param recordID the id of the tournament
	 * @param title the updated title
	 * @param date the updated date
	 * @param apt the updated max number of archers per target
	 * @param metric whether the tournament uses metric rounds
	 * @param teams whether the tournament requires team results
	 * @param couples whether the tournament will have a married couples award
	 */
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
