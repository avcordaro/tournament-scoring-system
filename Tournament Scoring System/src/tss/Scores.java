package tss;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Scores {
	
	Connection conn;
	
	public Scores(Connection c) {
		conn = c;
	}
	
	public ArrayList<ScoreEntry> getDataForTable(int tournamentID) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "SELECT * FROM Archer, Score WHERE Archer.TournamentID = " + tournamentID
				+ " AND Score.ArcherID = Archer.ArcherID ORDER BY Target";
		ResultSet rs = stmt.executeQuery(query);
		ArrayList<ScoreEntry> data = new ArrayList<ScoreEntry>();
		while(rs.next()) {
			data.add(new ScoreEntry(rs.getInt("ArcherID"), rs.getString("FirstName"), 
					rs.getString("LastName"), rs.getString("Target"), rs.getInt("Score"), 
					rs.getInt("Hits"), rs.getInt("Golds"), rs.getInt("Xs")));
		}
		return data;
	}
	
	public void newRecord(int archerID) throws SQLException {
		String sql = "INSERT INTO Score (ArcherID, Score, Hits, Golds, Xs) VALUES (?, ?, ?, ?, ?);";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, archerID);
		prepStmt.setInt(2, 0);
		prepStmt.setInt(3, 0);
		prepStmt.setInt(4, 0);
		prepStmt.setInt(5, 0);
		prepStmt.execute();
	}
	
	public void updateRecord(int archerID, int score, int hits, int golds) throws SQLException {
		String sql = "UPDATE Score SET Score=?, Hits=?, Golds=? WHERE ArcherID=?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, score);
		prepStmt.setInt(2, hits);
		prepStmt.setInt(3, golds);
		prepStmt.setInt(4, archerID);
		prepStmt.execute();
	}
	
	public void updateRecord(int archerID, int score, int hits, int golds, int Xs) throws SQLException {
		String sql = "UPDATE Score SET Score=?, Hits=?, Golds=?, Xs=? WHERE ArcherID=?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, score);
		prepStmt.setInt(2, hits);
		prepStmt.setInt(3, golds);
		prepStmt.setInt(4, Xs);
		prepStmt.setInt(5, archerID);
		prepStmt.execute();
	}
	
	public void deletedRecord(int archerID) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("DELETE FROM Score WHERE ArcherID = " + archerID + ";");
	}
}
