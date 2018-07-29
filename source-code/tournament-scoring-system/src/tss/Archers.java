package tss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class containing methods to do with creating, retrieving and updating archer data.
 * @author Alex Cordaro
 *
 */
public class Archers {
	
	private Connection conn;
	
	public Archers(Connection c) {
		conn = c;
	}
	
	/**
	 * Retrieves all archer records from the database for a given tournament.
	 * @param tournamentID id of the tournament to retrieve archers for
	 * @return ResultSet of the query result
	 */
	public ResultSet getAllRecords(int tournamentID) {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Archer WHERE TournamentID = " + tournamentID + ";");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Creates new archer record in the database.
	 * @param tID id of their associated tournament
	 * @param fname their first name
	 * @param lname their last name
	 * @param club their club name
	 * @param cat their category
	 * @param bow their bow type
	 * @param round the round they will be shooting
	 * @return id of the newly created record in the database
	 */
	public int newRecord(int tID, String fname, String lname, String club, String cat, String bow, String round) {
		String sql = "INSERT INTO Archer (TournamentID, FirstName, LastName, Club, Category, BowType, "
				+ "Round) VALUES (?, ?, ?, ?, ?, ?, ?);";
		int newID = 0;
		try {
			PreparedStatement prepStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			prepStmt.setInt(1, tID);
			prepStmt.setString(2, fname);
			prepStmt.setString(3, lname);
			prepStmt.setString(4, club);
			prepStmt.setString(5, cat);
			prepStmt.setString(6, bow);
			prepStmt.setString(7, round);
			prepStmt.execute();
			newID = prepStmt.getGeneratedKeys().getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return newID;
	}
	
	/**
	 * Deletes the given archer record.
	 * @param recordID id of the archer to delete
	 */
	public void deleteRecord(int recordID) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM Archer WHERE ArcherID =" + recordID + ";");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the information for a given archer record.
	 * @param recordID the archer's id
	 * @param fname their updated first name
	 * @param lname their updated last name
	 * @param club their updated club name
	 * @param cat their updated category
	 * @param bow their updated bow type
	 * @param round their updated round selection
	 */
	public void updateRecord(int recordID, String fname, String lname, String club, String cat, String bow, String round) {
		String sql = "UPDATE Archer SET FirstName=?, LastName=?, Club=?, Category=?, BowType=?, Round=? "
				+ "WHERE ArcherID = " + recordID + ";";
		try {
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			prepStmt.setString(1, fname);
			prepStmt.setString(2, lname);
			prepStmt.setString(3, club);
			prepStmt.setString(4, cat);
			prepStmt.setString(5, bow);
			prepStmt.setString(6, round);
			prepStmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieve all categories that an archer can belong to from the database.
	 * @return ResultSet of the query result
	 */
	public ResultSet getCategories() {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Name FROM Category ORDER BY Precedence;");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Retrieve all bow types that an archer can use from the database.
	 * @return ResultSet of the query result
	 */
	public ResultSet getBowTypes() {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Name FROM BowType ORDER BY Precedence;");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Retrieves all rounds that an archer can shoot from the database
	 * @return ResultSet of the query result
	 */
	public ResultSet getRounds() {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Name FROM Round ORDER BY Precedence;");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}