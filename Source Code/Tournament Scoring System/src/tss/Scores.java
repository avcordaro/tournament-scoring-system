package tss;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Utility class containing methods to do with creating, retrieving and updating score data.
 * @author Alex Cordaro
 */
public class Scores {
	
	Connection conn;
	
	public Scores(Connection c) {
		conn = c;
	}
	
	/**
	 * Retrieves an array list of ScoreEntry objects, used to fill the scores TableView.
	 * @param tournamentID id of the tournament to retrieve scores for
	 * @return the array list of ScoreEntry's
	 */
	public ArrayList<ScoreEntry> getDataForTable(int tournamentID) {
		ArrayList<ScoreEntry> data = new ArrayList<ScoreEntry>();
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Archer, Score WHERE Archer.TournamentID = " + tournamentID
					+ " AND Score.ArcherID = Archer.ArcherID ORDER BY Target, Detail";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				data.add(new ScoreEntry(rs.getInt("ArcherID"), rs.getString("FirstName"), 
						rs.getString("LastName"), rs.getString("Target") + rs.getString("Detail"), rs.getInt("Score"), 
						rs.getInt("Hits"), rs.getInt("Golds"), rs.getInt("Xs")));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * Creates a new score record in the database.
	 * @param archerID the id of the archer whom the score belongs to
	 */
	public void newRecord(int archerID) {
		String sql = "INSERT INTO Score (ArcherID, Score, Hits, Golds, Xs) VALUES (?, ?, ?, ?, ?);";
		try {
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			prepStmt.setInt(1, archerID);
			prepStmt.setInt(2, 0);
			prepStmt.setInt(3, 0);
			prepStmt.setInt(4, 0);
			prepStmt.setInt(5, 0);
			prepStmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates a given score record in the database, as an imperial score.
	 * @param archerID the id of the archer whom the score belongs to
	 * @param score the updated score
	 * @param hits the updated number of hits
	 * @param golds the updated number of golds
	 */
	public void updateRecord(int archerID, int score, int hits, int golds)  {
		String sql = "UPDATE Score SET Score=?, Hits=?, Golds=? WHERE ArcherID=?";
		try {
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, score);
		prepStmt.setInt(2, hits);
		prepStmt.setInt(3, golds);
		prepStmt.setInt(4, archerID);
		prepStmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates a given score record in the database, as metric score.
	 * @param archerID the id of the archer whom the score belongs to
	 * @param score the updated score
	 * @param hits the updated number of hits
	 * @param golds the updated number of golds
	 * @param Xs the updated number of Xs
	 */
	public void updateRecord(int archerID, int score, int hits, int golds, int Xs) {
		String sql = "UPDATE Score SET Score=?, Hits=?, Golds=?, Xs=? WHERE ArcherID=?";
		try {
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			prepStmt.setInt(1, score);
			prepStmt.setInt(2, hits);
			prepStmt.setInt(3, golds);
			prepStmt.setInt(4, Xs);
			prepStmt.setInt(5, archerID);
			prepStmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes a given score record from the database.
	 * @param archerID the id of the archer whom the score belongs to
	 */
	public void deletedRecord(int archerID) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM Score WHERE ArcherID = " + archerID + ";");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}