package tss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Archers {
	
	private Connection conn;
	
	public Archers(Connection c) {
		conn = c;
	}
	
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
	
	public void deleteRecord(int recordID) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM Archer WHERE ArcherID =" + recordID + ";");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
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