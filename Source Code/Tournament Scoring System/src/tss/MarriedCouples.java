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
	
	public void newRecord(int archerID, int spouseID) {
		String sql = "INSERT INTO MarriedCouple (Archer, Spouse) VALUES (?, ?);";
		try {
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			prepStmt.setInt(1, archerID);
			prepStmt.setInt(2, spouseID);
			prepStmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getRecord(int recordID) {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT MarriedCouple.Archer, MarriedCouple.Spouse, Archer.FirstName, "
					+ "Archer.LastName FROM MarriedCouple, Archer WHERE (Archer = " + recordID + " AND "
					+ "MarriedCouple.Spouse = Archer.ArcherID) OR (Spouse = " + recordID + " AND "
					+ "MarriedCouple.Archer = Archer.ArcherID);");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public void updateRecord(int archerID, String selection) {
		ResultSet rs = getRecord(archerID);
		try {
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
		} catch(SQLException e) {
			e.printStackTrace();
		}
		if(!selection.equals("None")) {
			int spouseID = Integer.parseInt(selection.substring(0, selection.indexOf(" ")));
			newRecord(archerID, spouseID);
		}
	}
	
	public void deleteRecord(int archerID) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM MarriedCouple WHERE Archer =" + archerID + " or Spouse =" + archerID + ";");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
