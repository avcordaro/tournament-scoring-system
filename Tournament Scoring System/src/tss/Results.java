package tss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Results {
	
	Connection conn;
	
	public Results(Connection c) {
		conn = c;
	}
	
	public JasperPrint generateMarriedCoupleResults(int tournamentID, boolean metric) {
		JasperPrint jPrint = null;
		try {
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
			jPrint = JasperFillManager.fillReport(jr, params, jrDataSource);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(JRException e) {
			e.printStackTrace();
		}
		return jPrint;
	}
	
	public JasperPrint generateIndividualResults(int tournamentID, String title, String date, boolean metric, String bestGold, String worstWhite) {
		JasperPrint jPrint = null;
		try {
			JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("resources/IndividualResults.jrxml"));
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("TITLE", title);
			params.put("DATE", date);
			params.put("METRIC", metric);
			params.put("BEST_GOLD", bestGold);
			params.put("WORST_WHITE", worstWhite);
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Archer, Score, Round, Category, BowType WHERE TournamentID = " + tournamentID + " AND Archer.ArcherID ="
					+ " Score.ArcherID AND Archer.Round = Round.Name and Archer.Category = Category.Name AND Archer.BowType = BowType.Name ORDER BY Round.Precedence,"
					+ " Category.Precedence, BowType.Precedence, Score DESC, Hits DESC, Golds DESC, Xs DESC;";
			ResultSet rs = stmt.executeQuery(query);
			JRDataSource jrDataSource = new JRResultSetDataSource(rs);
			jPrint = JasperFillManager.fillReport(jr, params, jrDataSource);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(JRException e) {
			e.printStackTrace();
		}
		return jPrint;
	}
	
	public JasperPrint generateTeamResults(int tournamentID, String round, int apt, ArrayList<String> bowTypes, boolean mixedBow, boolean mixedGender, boolean metric) {
		JasperPrint jPrint = null;
		ArrayList<TeamMember> teamResultsData = new ArrayList<TeamMember>();
		ArrayList<String> genders = new ArrayList<String>();
		if(mixedGender) {
			genders.add("\"Men\", \"Women\"");
		} else {
			genders.add("\"Men\"");
			genders.add("\"Women\"");
		}
		try {
			for(String bow : bowTypes) {
				for(String gender : genders) {
					String query = "SELECT Club FROM Archer, Score, Category WHERE Archer.ArcherID = Score.ArcherID AND Archer.Category = Category.Name"
							+ " AND TournamentID=? AND Round=? AND BowType IN (" + bow + ") AND Gender IN (" + gender + ") GROUP BY Club HAVING COUNT(*) >= ?;";
					PreparedStatement prepStmt = conn.prepareStatement(query);
					prepStmt.setInt(1, tournamentID);
					prepStmt.setString(2, round);
					prepStmt.setInt(3, apt < 3 ? apt : apt -1);
					ResultSet qualifyingClubs = prepStmt.executeQuery();
					ArrayList<Team> clubTeams = new ArrayList<Team>();
					while(qualifyingClubs.next()) {
						String sql = "SELECT Archer.ArcherID, Score, Hits, Golds, Xs FROM Archer, Score, Category WHERE Archer.ArcherID ="
								+ " Score.ArcherID AND Archer.Category = Category.Name AND TournamentID=? AND Club=? AND Round=? AND BowType IN (" + bow + ") "
								+ "AND Gender IN (" + gender + ") ORDER BY Score DESC, Hits DESC, Golds DESC, Xs DESC LIMIT ?;";
						PreparedStatement stmt = conn.prepareStatement(sql);
						stmt.setInt(1, tournamentID);
						stmt.setString(2, qualifyingClubs.getString("Club"));
						stmt.setString(3, round);
						stmt.setInt(4, apt);
						ResultSet clubTeam = stmt.executeQuery();
						Team team = new Team(qualifyingClubs.getString("Club"));
						while(clubTeam.next()) {
							team.incrementTotalHits(clubTeam.getInt("Score"));
							team.incrementTotalGolds(clubTeam.getInt("Golds"));
							team.incrementTotalXs(clubTeam.getInt("Xs"));
						}
						clubTeams.add(team);
					}
					Collections.sort(clubTeams);
					for(Team t : clubTeams) {
						String sql = "SELECT FirstName, LastName, Club, BowType, Gender, Score, Hits, Golds, Xs FROM Archer, Score, Category WHERE"
								+ " Archer.ArcherID = Score.ArcherID AND Archer.Category = Category.Name AND TournamentID=? AND Club=? AND Round=? AND "
								+ "BowType IN (" + bow + ") AND Gender IN (" + gender + ") ORDER BY Score DESC, Hits DESC, Golds DESC, Xs DESC LIMIT ?;";
						PreparedStatement stmt = conn.prepareStatement(sql);
						stmt.setInt(1, tournamentID);
						stmt.setString(2, t.getClub());
						stmt.setString(3, round);
						stmt.setInt(4, apt);
						ResultSet teamMembers = stmt.executeQuery();
						while(teamMembers.next()) {
							TeamMember member = new TeamMember();
							member.setTeamPosition(clubTeams.indexOf(t) + 1);
							member.setFirstName(teamMembers.getString("FirstName"));
							member.setLastName(teamMembers.getString("LastName"));
							member.setClub(teamMembers.getString("Club"));
							member.setBowType(teamMembers.getString("BowType"));
							if(!mixedGender) { member.setGender(teamMembers.getString("Gender")); }
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
			if(mixedGender) {
				jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("resources/TeamMixedResults.jrxml"));
			} else {
				jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("resources/TeamSeparateResults.jrxml"));
			}
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("METRIC", metric);
			params.put("ROUND", round);
			params.put("MIXED_BOW", mixedBow);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(teamResultsData);
			jPrint = JasperFillManager.fillReport(jr, params, jrDataSource);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(JRException e) {
			e.printStackTrace();
		}
		return jPrint;
	}
}