package tss;

import java.io.Serializable;

public class TeamMember implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int teamPosition;
	private String firstName;
	private String lastName;
	private String club;
	private String bowType;
	private String gender;
	private int score;
	private int hits;
	private int golds;
	private int xs;
	
	public TeamMember() {
		super();
	}
	public int getTeamPosition() {
		return teamPosition;
	}
	public void setTeamPosition(int teamPosition) {
		this.teamPosition = teamPosition;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getClub() {
		return club;
	}
	public void setClub(String club) {
		this.club = club;
	}
	public String getBowType() {
		return bowType;
	}
	public void setBowType(String bowType) {
		this.bowType = bowType;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getGolds() {
		return golds;
	}
	public void setGolds(int golds) {
		this.golds = golds;
	}
	public int getXs() {
		return xs;
	}
	public void setXs(int xs) {
		this.xs = xs;
	}
}