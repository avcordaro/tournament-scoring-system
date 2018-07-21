package tss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.ImageIcon;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
	
	public void generateTeamResults(int tournamentID, String round, int apt, ArrayList<String> bowTypes, boolean mixed, boolean metric) 
			throws SQLException, JRException {
		ArrayList<TeamMember> teamResultsData = new ArrayList<TeamMember>();
		ArrayList<String> genders = new ArrayList<String>();
		if(mixed) {
			genders.add("\"Men\", \"Women\"");
		} else {
			genders.add("\"Men\"");
			genders.add("\"Women\"");
		}
		for(String bow : bowTypes) {
			for(String gender : genders) {
				String query = "SELECT Club FROM Archer, Score, Category WHERE Archer.ArcherID = Score.ArcherID AND Archer.Category = Category.Name"
						+ " AND TournamentID=? AND Round=? AND BowType=? AND Gender IN (" + gender + ") GROUP BY Club HAVING COUNT(*) >= ?;";
				PreparedStatement prepStmt = conn.prepareStatement(query);
				prepStmt.setInt(1, tournamentID);
				prepStmt.setString(2, round);
				prepStmt.setString(3, bow);
				prepStmt.setInt(4, apt);
				ResultSet qualifyingClubs = prepStmt.executeQuery();
				ArrayList<Team> clubTeams = new ArrayList<Team>();
				while(qualifyingClubs.next()) {
					String sql = "SELECT Archer.ArcherID AS ArchID, Score, Hits, Golds, Xs FROM Archer, Score, Category WHERE Archer.ArcherID = Score.ArcherID AND Archer.Category = Category.Name AND TournamentID=?"
							+ " AND Club=? AND Gender IN (" + gender + ") ORDER BY Score DESC, Hits DESC, Golds DESC, Xs DESC LIMIT ?;";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, tournamentID);
					stmt.setString(2, qualifyingClubs.getString("Club"));
					stmt.setInt(3, apt);
					ResultSet clubTeam = stmt.executeQuery();
					Team team = new Team(qualifyingClubs.getString("Club"));
					while(clubTeam.next()) {
						team.addArcherID(clubTeam.getInt("ArchID"));
						team.incrementTotalHits(clubTeam.getInt("Score"));
						team.incrementTotalGolds(clubTeam.getInt("Golds"));
						team.incrementTotalXs(clubTeam.getInt("Xs"));
					}
					clubTeams.add(team);
				}
				Collections.sort(clubTeams);
				for(Team t : clubTeams) {
					String sql = "SELECT FirstName, LastName, Club, BowType, Gender, Score, Hits, Golds, Xs FROM Archer, Score, Category WHERE Archer.ArcherID = "
							+ "Score.ArcherID AND Archer.Category = Category.Name AND TournamentID=? AND Club=? AND Gender IN (" + gender + ") ORDER BY Score DESC, Hits DESC, Golds DESC, Xs DESC LIMIT ?;";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, tournamentID);
					stmt.setString(2, t.getClub());
					stmt.setInt(3, apt);
					ResultSet teamMembers = stmt.executeQuery();
					while(teamMembers.next()) {
						TeamMember member = new TeamMember();
						member.setTeamPosition(clubTeams.indexOf(t) + 1);
						member.setFirstName(teamMembers.getString("FirstName"));
						member.setLastName(teamMembers.getString("LastName"));
						member.setClub(teamMembers.getString("Club"));
						member.setBowType(teamMembers.getString("BowType"));
						if(!mixed) { member.setGender(teamMembers.getString("Gender")); }
						member.setScore(teamMembers.getInt("Score"));
						member.setHits(teamMembers.getInt("Hits"));
						member.setGolds(teamMembers.getInt("Golds"));
						member.setXs(teamMembers.getInt("Xs"));
						teamResultsData.add(member);
					}
				}
			}
		}
		JasperReport jr;
		if(mixed) {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("resources/TeamMixedResults.jrxml"));
		} else {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("resources/TeamSeparateResults.jrxml"));
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("METRIC", metric);
		params.put("ROUND", round);
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(teamResultsData);
		JasperPrint jPrint = JasperFillManager.fillReport(jr, params, jrDataSource);
		JasperViewer jViewer = new JasperViewer(jPrint, false);
		jViewer.setTitle("Married Couple Results Preview");
		ImageIcon img = new ImageIcon(getClass().getResource("resources/list.png"));
		jViewer.setIconImage(img.getImage());
		jViewer.setVisible(true);
	}
}
