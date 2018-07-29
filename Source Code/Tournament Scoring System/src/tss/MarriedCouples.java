package tss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class containing methods to do with creating, retrieving and updating married couple data.
 * @author Alex Cordaro
 */
public class MarriedCouples {
	
	private Connection conn;
	
	public MarriedCouples(Connection c) {
		conn = c;
	}
	
	/**
	 * Creates new married couple record in the database
	 * @param archerID id of the first archer
	 * @param spouseID id of their spouse
	 */
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
	
	/**
	 * Retrieves a given married couple record.
	 * @param recordID id of either of the archer or their spouse
	 * @return ResultSet of all the necessary data from the query result.
	 */
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
	
	/**
	 * Updates a given married couple record
	 * @param archerID the id of first archer
	 * @param selection the newly selected spouse
	 */
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
	
	/**
	 * Deletes a given married couple record
	 * @param archerID the id of either the archer or their spouse.
	 */
	public void deleteRecord(int archerID) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM MarriedCouple WHERE Archer =" + archerID + " or Spouse =" + archerID + ";");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
