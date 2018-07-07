package tss.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MarriedCouples {
	
	private Connection conn;
	
	public MarriedCouples(Connection c) {
		conn = c;
	}
	
	public void newRecord(int archerID, int spouseID) throws SQLException {
		String sql = "INSERT INTO MarriedCouple (Archer, Spouse) VALUES (?, ?);";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, archerID);
		prepStmt.setInt(2, spouseID);
		prepStmt.execute();
	}
}
