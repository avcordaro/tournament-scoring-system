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
	
	public ResultSet getOrderedArcherRecords(int tournamentID) {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Archer LEFT JOIN MarriedCouple ON Archer.ArcherID ="
					+ " MarriedCouple.Archer OR Archer.ArcherID = MarriedCouple.Spouse WHERE TournamentID = "
					+ tournamentID + " ORDER BY Target, Detail;");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ArrayList<TargetEntry> getDataForTable(int tournamentID) {
		ResultSet rs = getOrderedArcherRecords(tournamentID);
		ArrayList<TargetEntry> data = new ArrayList<TargetEntry>();
		try {
			while(rs.next()) {
	    		data.add(new TargetEntry(rs.getInt("ArcherID"), rs.getString("FirstName"), 
	    				rs.getString("LastName"), rs.getString("Club"), rs.getString("Category"), 
	    				rs.getString("BowType"),rs.getString("Round"), rs.getString("Target"), rs.getString("Detail")));
	    	}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public void updateDetail(int archerID, String target, String detail) {
		String sql = "UPDATE Archer SET Target=?, Detail=? WHERE ArcherID=?;";
		try {
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			prepStmt.setInt(1, Integer.parseInt(target));
			prepStmt.setString(2, detail);
			prepStmt.setInt(3, archerID);
			prepStmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void assignAllDetails(int tournamentID, int apt) {
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT Archer.ArcherID AS ID, Archer.Round AS Round FROM Archer, BowType, Round WHERE"
					+ " TournamentID = " + tournamentID + " AND Archer.BowType = BowType.Name AND Archer.Round = "
					+ "Round.Name ORDER BY Round.Precedence, BowType.Precedence, random();";
			ResultSet rs = stmt.executeQuery(query);
			double detailNumber = 1.0;
			double detailIncrement = 1.0 / apt;
			char detailLetter = 'A';
			char resetLetter = (char)(detailLetter + apt);
			String sql = "UPDATE Archer SET Target=?, Detail=? WHERE ArcherID=?;";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			rs.next();
			while(!rs.isAfterLast()) {
				String round = rs.getString("Round");
				while(!rs.isAfterLast() && rs.getString("Round").equals(round)) {
					prepStmt.setInt(1, (int)detailNumber);
					prepStmt.setString(2, Character.toString(detailLetter));
					prepStmt.setInt(3, rs.getInt("ID"));
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
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void fillSwapDetailsComboBox(int tournamentID, TargetEntry entry, int apt, ComboBox<String> cmb) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Archer WHERE TournamentID = " + tournamentID 
					+ " AND Round = \"" + entry.getRound() + "\" ORDER BY Target, Detail;");
			if(rs.isAfterLast()) {
				return;
			}
			char resetLetter = (char)('A' + apt);
			int startingTarget = Integer.parseInt(rs.getString("Target").split("(?=[A-" + resetLetter + "])")[0]);
			HashMap<String, String> targetMap = new HashMap<String, String>();
			String archerDetail;
			while(rs.next()) {
				archerDetail = rs.getInt("Target") + rs.getString("Detail") + " - " + rs.getString("FirstName") + " " + rs.getString("LastName");
				targetMap.put(rs.getString("Target") + rs.getString("Detail"), archerDetail);
			}
			int i = startingTarget;
			String detail;
			cmb.getItems().add("None");
			while(!targetMap.isEmpty()) {
				for(char c = 'A'; c < resetLetter; c++) {
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
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void swapTargetDetails(int tournamentID, int apt, String target1, String target2) {
		try {
			char resetLetter = (char)('A' + apt);
			String[] target1Detail = target1.split("(?=[A-" + resetLetter + "])");
			String[] target2Detail = target2.split("(?=[A-" + resetLetter + "])");
			Statement stmt1 = conn.createStatement();
			ResultSet target1Archer = stmt1.executeQuery("SELECT * FROM Archer WHERE TournamentID = " + tournamentID
					+ " AND Target = \"" + target1Detail[0] + "\" AND Detail = \"" + target1Detail[1] + "\";");
			Statement stmt2 = conn.createStatement();
			ResultSet target2Archer = stmt2.executeQuery("SELECT * FROM Archer WHERE TournamentID = " + tournamentID
					+ " AND Target = \"" + target2Detail[0] + "\" AND Detail = \"" + target2Detail[1] + "\";");
			if(!target1Archer.isAfterLast()) {
				updateDetail(target1Archer.getInt("ArcherID"), target2Detail[0], target2Detail[1]);
			}
			updateDetail(target2Archer.getInt("ArcherID"), target1Detail[0], target1Detail[1]);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void previewTargetList(int tournamentID, String title, String date, String venue, String assembly, String sighters) {
		try {
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
		} catch(JRException e) {
			e.printStackTrace();
		}
	}
}