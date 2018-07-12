package tss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public ResultSet getRecord(int recordID) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT MarriedCouple.Archer, MarriedCouple.Spouse, Archer.FirstName, "
				+ "Archer.LastName FROM MarriedCouple, Archer WHERE (Archer = " + recordID + " AND "
				+ "MarriedCouple.Spouse = Archer.ArcherID) OR (Spouse = " + recordID + " AND "
				+ "MarriedCouple.Archer = Archer.ArcherID);");
		return rs;
	}
	
	public void updateRecord(int archerID, String selection) throws SQLException {
		ResultSet rs = getRecord(archerID);
		if(rs.isBeforeFirst()) {
			if(selection.equals("None")) {
				deleteRecord(archerID);
				return;
			}
			int spouseID = Integer.parseInt(selection.substring(0, selection.indexOf(" ")));
			String sql = "UPDATE MarriedCouple SET Spouse=? WHERE Archer = " + archerID + ";";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			prepStmt.setInt(1, spouseID);
			prepStmt.execute();
			return;
		}
		if(!selection.equals("None")) {
			int spouseID = Integer.parseInt(selection.substring(0, selection.indexOf(" ")));
			newRecord(archerID, spouseID);
		}
	}
	
	public void deleteRecord(int archerID) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("DELETE FROM MarriedCouple WHERE Archer =" + archerID + " or Spouse =" + archerID + ";");
	}
}
