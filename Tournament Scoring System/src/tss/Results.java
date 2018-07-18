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
