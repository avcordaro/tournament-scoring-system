package tss;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class Results {
	
	Connection conn;
	
	public Results(Connection c) {
		conn = c;
	}
	
	public void generateMarriedCoupleResults(int tournamentID, boolean metric) throws JRException, SQLException {
		JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("resources/MarriedCoupleResults.jrxml"));
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("METRIC", metric);
		Statement stmt = conn.createStatement();
		String query = "SELECT *, (ArcherScore + SpouseScore) AS TotalScore, (ArcherHits + SpouseHits) AS TotalHits, (ArcherGolds + SpouseGolds)"
				+ " AS TotalGolds, (ArcherXs + SpouseXs) AS TotalXs FROM (SELECT ArcherFirstName, ArcherLastName, ArcherScore, ArcherHits, ArcherGolds,"
				+ " ArcherXs, FirstName AS SpouseFirstName, LastName AS SpouseLastName, Score AS SpouseScore, Hits AS SpouseHits, Golds AS SpouseGolds,"
				+ " Xs AS SpouseXs FROM (SELECT Archer, Spouse, FirstName AS ArcherFirstName, LastName AS ArcherLastName, Score AS ArcherScore, Hits AS"
				+ " ArcherHits, Golds AS ArcherGolds, Xs AS ArcherXs FROM MarriedCouple, Archer, Score WHERE TournamentID = " + tournamentID + " AND"
				+ " MarriedCouple.Archer = Archer.ArcherID AND Archer.ArcherID = Score.ArcherID) AS FirstPartner, Archer, Score WHERE FirstPartner.Spouse"
				+ " = Archer.ArcherID AND Archer.ArcherID = Score.ArcherID) ORDER BY TotalScore DESC, TotalHits DESC, TotalGolds DESC, TotalXs DESC;";
		ResultSet rs = stmt.executeQuery(query);
		JRDataSource jrDataSource = new JRResultSetDataSource(rs);
		JasperPrint jPrint = JasperFillManager.fillReport(jr, params, jrDataSource);
		JasperViewer jViewer = new JasperViewer(jPrint, false);
		jViewer.setTitle("Married Couple Results Preview");
		ImageIcon img = new ImageIcon(getClass().getResource("resources/list.png"));
		jViewer.setIconImage(img.getImage());
		jViewer.setVisible(true);
	}
	
	public void generateIndividualResults(int tournamentID, String title, String date, boolean metric, String bestGold, String worstWhite) 
			throws JRException, SQLException {
		JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("resources/IndividualResults.jrxml"));
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("TITLE", title);
		params.put("DATE", date);
		params.put("METRIC", metric);
		params.put("BEST_GOLD", bestGold);
		params.put("WORST_WHITE", worstWhite);
		Statement stmt = conn.createStatement();
		String query = "SELECT * FROM Archer, Score, Round, Category WHERE TournamentID = " + tournamentID + " AND Archer.ArcherID = Score.ArcherID"
				+ " AND Archer.Round = Round.Name and Archer.Category = Category.Name ORDER BY Round.Precedence, Category.Precedence, Score DESC,"
				+ " Hits DESC, Golds DESC, Xs DESC;";
		ResultSet rs = stmt.executeQuery(query);
		JRDataSource jrDataSource = new JRResultSetDataSource(rs);
		JasperPrint jPrint = JasperFillManager.fillReport(jr, params, jrDataSource);
		JasperViewer jViewer = new JasperViewer(jPrint, false);
		jViewer.setTitle("Individual Results Preview");
		ImageIcon img = new ImageIcon(getClass().getResource("resources/list.png"));
		jViewer.setIconImage(img.getImage());
		jViewer.setVisible(true);
	}
}
