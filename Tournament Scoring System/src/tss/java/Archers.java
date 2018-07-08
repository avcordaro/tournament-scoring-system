package tss.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Archers {
	
	private Connection conn;
	
	public Archers(Connection c) throws SQLException {
		conn = c;
	}
	
	public ResultSet getAllRecords(int tournamentID) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Archer WHERE TournamentID = " + tournamentID + ";");
		return rs;
	}
	
	public int newRecord(int tID, String fname, String lname, String club, String cat, String bow,
			String round) throws SQLException {
		String sql = "INSERT INTO Archer (TournamentID, FirstName, LastName, Club, Category, BowType, "
				+ "Round) VALUES (?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement prepStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		prepStmt.setInt(1, tID);
		prepStmt.setString(2, fname);
		prepStmt.setString(3, lname);
		prepStmt.setString(4, club);
		prepStmt.setString(5, cat);
		prepStmt.setString(6, bow);
		prepStmt.setString(7, round);
		prepStmt.execute();
		
		int newID = prepStmt.getGeneratedKeys().getInt(1);
		return newID;
	}
	
	public void deleteRecord(int recordID) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("DELETE FROM Archer WHERE ArcherID =" + recordID + ";");
	}
	
	public void updateRecord(int recordID, String fname, String lname, String club, String cat, String bow,
			String round) throws SQLException {
		String sql = "UPDATE Archer SET FirstName=?, LastName=?, Club=?, Category=?, BowType=?, Round=? "
				+ "WHERE ArcherID = " + recordID + ";";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setString(1, fname);
		prepStmt.setString(2, lname);
		prepStmt.setString(3, club);
		prepStmt.setString(4, cat);
		prepStmt.setString(5, bow);
		prepStmt.setString(6, round);
		prepStmt.execute();
	}
	
	public ResultSet getCategories() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT Name FROM Category ORDER BY Precedence;");
		return rs;
	}
	
	public ResultSet getBowTypes() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT Name FROM BowType ORDER BY Precedence;");
		return rs;
	}
	
	public ResultSet getRounds() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT Name FROM Round ORDER BY Precedence;");
		return rs;
	}
}
