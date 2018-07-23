package tss;

public class TournamentMap {
	
	private String title;
	private int tournamentID;
	
	public TournamentMap(String name, int id) {
		title = name;
		tournamentID = id;
	}
	
	public int getID() {
		return tournamentID;
	}
	public String toString() {
		return title;
	}
}