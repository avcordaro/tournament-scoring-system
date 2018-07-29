package tss;

/**
 * Utility class containing methods to do with creating, retrieving and updating target list data.
 */
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
	
	/**
	 * Retrieves archer records from the database, ordered by their target detail.
	 * @param tournamentID the id of the tournament to retrieve archers for
	 * @return ResultSet of the query result
	 */
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
	
	/**
	 * Retrieves an array list of TargetEntry objects, used to fill the target list TableView.
	 * @param tournamentID the id of the tournament to retrieve archers for
	 * @return the array list of TargetEntry's
	 */
	public ArrayList<TargetEntry> getDataForTable(int tournamentID) {
		ResultSet rs = getOrderedArcherRecords(tournamentID);
		ArrayList<TargetEntry> data = new ArrayList<TargetEntry>();
		try {
			while(rs.next()) {
				String target = rs.getString("Target") == null ? "" : rs.getString("Target");
				String detail = rs.getString("Detail") == null ? "" : rs.getString("Detail");
	    		data.add(new TargetEntry(rs.getInt("ArcherID"), rs.getString("FirstName"), 
	    				rs.getString("LastName"), rs.getString("Club"), rs.getString("Category"), 
	    				rs.getString("BowType"),rs.getString("Round"), target, detail));
	    	}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * Updates a given archer record's target detail.
	 * @param archerID the id of the archer
	 * @param target the updated target number
	 * @param detail the updated detail letter
	 */
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
	
	/**
	 * Automatic allocation algorithm, for assigning all archers their target detail for a given tournament.
	 * @param tournamentID the id of the tournament
	 * @param apt the max number of archers per target
	 */
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
	
	/**
	 * Fills the ComboBox used for selecting which target detail to swap a given archer with.
	 * @param tournamentID the id of the tournament
	 * @param entry the selected entry in the target list TableView
	 * @param apt the max number of archers per target
	 * @param cmb the ComboBox object
	 */
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
	
	/**
	 * Swaps two given archer's target details in the database.
	 * @param tournamentID the id of the tournament
	 * @param apt the max number of archer's per target
	 * @param target1 the target detail for the first archer
	 * @param target2 the target detail for the second archer
	 * @param target2ArcherID the id of the second archer
	 */
	public void swapTargetDetails(int tournamentID, int apt, String target1, String target2, int target2ArcherID) {
		try {
			char resetLetter = (char)('A' + apt);
			String[] target1Detail = target1.split("(?=[A-" + resetLetter + "])");
			String[] target2Detail = target2.split("(?=[A-" + resetLetter + "])");
			Statement stmt1 = conn.createStatement();
			ResultSet target1Archer = stmt1.executeQuery("SELECT * FROM Archer WHERE TournamentID = " + tournamentID
					+ " AND Target = \"" + target1Detail[0] + "\" AND Detail = \"" + target1Detail[1] + "\";");
			Statement stmt2 = conn.createStatement();
			ResultSet target2Archer = stmt2.executeQuery("SELECT * FROM Archer WHERE TournamentID = " + tournamentID
					+ " AND ArcherID = " + target2ArcherID + " AND Target = \"" + target2Detail[0] + "\" AND Detail = "
					+ "\"" + target2Detail[1] + "\";");
			if(!target1Archer.isAfterLast()) {
				updateDetail(target1Archer.getInt("ArcherID"), target2Detail[0], target2Detail[1]);
			}
			updateDetail(target2Archer.getInt("ArcherID"), target1Detail[0], target1Detail[1]);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generates and presents a preview of the target list report, using JasperReports.
	 * @param tournamentID the id of the tournament
	 * @param title the title of the tournament
	 * @param date the date of the tournament
	 * @param venue the optional venue of the tournament
	 * @param assembly the optional assembly time of the tournament
	 * @param sighters the optional sighters time of the tournament
	 */
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