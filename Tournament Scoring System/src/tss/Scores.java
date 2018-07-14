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
		String sql = "INSERT INTO Score (ArcherID) VALUES (?);";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, archerID);
		prepStmt.execute();
	}

}
