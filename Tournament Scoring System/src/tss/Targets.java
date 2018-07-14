package tss;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import javafx.scene.control.ComboBox;
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
    		data.add(new TargetEntry(rs.getInt("ArcherID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Club"), rs.getString("Category"), rs.getString("BowType"),rs.getString("Round"), rs.getString("Target")));
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
	
	public void fillSwapDetailsComboBox(int tournamentID, TargetEntry entry, ComboBox<String> cmb) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Archer WHERE TournamentID = " + tournamentID 
				+ " AND Round = \"" + entry.getRound() + "\" ORDER BY Target;");
		if(rs.isAfterLast()) {
			return;
		}
		int startingTarget = Integer.parseInt(rs.getString("Target").split("A|B|C|D")[0]);
		HashMap<String, String> targetMap = new HashMap<String, String>();
		String archerDetail;
		while(rs.next()) {
			archerDetail = rs.getString("Target") + " - " + rs.getString("FirstName") + " " + rs.getString("LastName");
			targetMap.put(rs.getString("Target"), archerDetail);
		}
		int i = startingTarget;
		String detail;
		cmb.getItems().add("None");
		while(!targetMap.isEmpty()) {
			for(char c = 'A'; c < 'E'; c++) {
				detail = Integer.toString(i) + c;
				archerDetail = targetMap.get(detail);
				if(archerDetail != null) {
					if(!detail.equals(entry.getTarget())) {
						cmb.getItems().add(archerDetail);
					}
					targetMap.remove(detail);
				}
				else {
					cmb.getItems().add(detail + " - Empty");
				}
			}
			i++;
		}
		
	}
	
	public void swapTargetDetails(int tournamentID, String target1, String target2) throws SQLException {
		Statement stmt1 = conn.createStatement();
		ResultSet target1Archer = stmt1.executeQuery("SELECT * FROM Archer WHERE TournamentID = " + tournamentID
				+ " AND Target = \"" + target1 + "\";");
		Statement stmt2 = conn.createStatement();
		ResultSet target2Archer = stmt2.executeQuery("SELECT * FROM Archer WHERE TournamentID = " + tournamentID
				+ " AND Target = \"" + target2 + "\";");
		if(!target1Archer.isAfterLast()) {
			updateDetail(target1Archer.getInt("ArcherID"), target2);
		}
		updateDetail(target2Archer.getInt("ArcherID"), target1);
	}
	
	public void previewTargetList(int tournamentID, String title, String date, String venue, String assembly, String sighters)
			throws SQLException, JRException {
		JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("resources/TargetList.jrxml"));
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("TITLE", title);
		params.put("DATE", date);
		params.put("VENUE", venue); 
		params.put("ASSEMBLY", assembly); 
		params.put("SIGHTERS", sighters); 
		ResultSet rs = getOrderedArcherRecords(tournamentID);
		JRDataSource jrDataSource = new JRResultSetDataSource(rs);
		JasperPrint jPrint = JasperFillManager.fillReport(jr, params, jrDataSource);
		JasperViewer jViewer = new JasperViewer(jPrint, false);
		jViewer.setTitle("Target List Preview");
		ImageIcon img = new ImageIcon(getClass().getResource("resources/list.png"));
		jViewer.setIconImage(img.getImage());
		jViewer.setVisible(true);
	}
}

