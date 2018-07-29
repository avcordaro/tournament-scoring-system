package tss;

/**
 * Custom Map class used to map tournament names to their tournament ID's
 * @author Alex Cordaro
 *
 */
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