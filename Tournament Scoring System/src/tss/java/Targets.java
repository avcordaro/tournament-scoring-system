package tss.java;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


public class Targets {
	
	Connection conn;
	
	public Targets(Connection c) {
		conn = c;
	}
	
	public ResultSet getOrderedArcherRecords(int tournamentID) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Archer WHERE TournamentID = " + tournamentID 
				+ " ORDER BY Target;");
		return rs;
	}
	
	public ArrayList<TargetEntry> getDataForTable(int tournamentID) throws SQLException {
		ResultSet rs = getOrderedArcherRecords(tournamentID);
		ArrayList<TargetEntry> data = new ArrayList<TargetEntry>();
		while(rs.next()) {
    		String archer = rs.getString("FirstName") + " " + rs.getString("LastName");
    		String category = rs.getString("Category");
    		switch(category.substring(0, 2)) {
	    		case "Ge": 	archer = "Mr " + archer; break;
	    		case "La": 	archer = "Mrs " + archer; break;
	    		case "JG": 	archer = "Ms " + archer; break;
	    		case "JL": 	archer = "Miss " + archer; break;
    		}
    		data.add(new TargetEntry(rs.getInt("ArcherID"), archer, rs.getString("Club"), 
    				rs.getString("Round"), rs.getString("BowType"), rs.getString("Target")));
    	}
		return data;
	}
	
	public void updateDetail(int archerID, String detail) throws SQLException {
		String sql = "UPDATE Archer SET Target=? WHERE ArcherID=?;";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setString(1, detail);
		prepStmt.setInt(2, archerID);
		prepStmt.execute();
	}
	
	public void assignAllDetails(int tournamentID, int apt) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "SELECT Archer.ArcherID AS ID, Archer.Round AS Round FROM Archer, BowType, Round WHERE"
				+ " TournamentID = " + tournamentID + " AND Archer.BowType = BowType.Name AND Archer.Round = "
				+ "Round.Name ORDER BY Round.Precedence, BowType.Precedence, random();";
		ResultSet rs = stmt.executeQuery(query);
		double detailNumber = 1.0;
		double detailIncrement = 1.0 / apt;
		char detailLetter = 'A';
		char resetLetter = (char)(detailLetter + apt);
		String sql = "UPDATE Archer SET Target=? WHERE ArcherID=?;";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		rs.next();
		while(!rs.isAfterLast()) {
			String round = rs.getString("Round");
			while(!rs.isAfterLast() && rs.getString("Round").equals(round)) {
				String detail = Integer.toString((int)detailNumber) + detailLetter;
				prepStmt.setString(1, detail);
				prepStmt.setInt(2, rs.getInt("ID"));
				prepStmt.addBatch();
				detailNumber += detailIncrement;
				detailLetter++;
				if(detailLetter == resetLetter) { 
					detailLetter = 'A'; 
				}
				rs.next();
			}
			detailNumber = Math.ceil(detailNumber);
			detailLetter = 'A';
		}
		prepStmt.executeBatch();
	}
	
	public void previewTargetList(int tournamentID, String title, String date) throws SQLException, JRException {
		JasperReport jr = JasperCompileManager.compileReport("src/tss/resources/TargetList.jrxml");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("TITLE", title);
		params.put("DATE", date);
		ResultSet rs = getOrderedArcherRecords(tournamentID);
		JRDataSource jrDataSource = new JRResultSetDataSource(rs);
		JasperPrint jPrint = JasperFillManager.fillReport(jr, params, jrDataSource);
		JasperViewer jViewer = new JasperViewer(jPrint, false);
		jViewer.setTitle("Target List Preview");
		ImageIcon img = new ImageIcon("src/tss/resources/list.png");
		jViewer.setIconImage(img.getImage());
		jViewer.setVisible(true);
	}
}

