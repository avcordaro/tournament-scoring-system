package tss;

public class ScoreEntry {
	
	private int ID;
	private String firstName;
	private String lastName;
	private String target;
	private int score;
	private int hits;
	private int golds;
	private int Xs;
	
	public ScoreEntry(int id, String f, String l, String t, int s, int h, int g, int x) {
		ID = id;
		firstName = f;
		lastName = l;
		target = t;
		score = s;
		hits = h;
		golds = g;
		Xs = x;
	}
	
	public int getID() {
		return ID;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getTarget() {
		return target;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int s) {
		score = s;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int h) {
		hits = h;
	}
	public int getGolds() {
		return golds;
	}
	public void setGolds(int g) {
		golds = g;
	}
	public int getXs() {
		return Xs;
	}
	public void setXs(int x) {
		Xs = x;
	}
}