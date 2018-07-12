package tss;

public class TargetEntry {
	
	private int ID;
	private String archer;
	private String club;
	private String bowType;
	private String round;
	private String target;
	
	public TargetEntry(int id, String a, String c, String b, String r, String t) {
		ID = id;
		archer = a;
		club = c;
		bowType = b;
		round = r;
		target = t;
	}
	
	public int getID() {
		return ID;
	}
	public String getArcher() {
		return archer;
	}
	public String getClub() {
		return club;
	}
	public String getBowType() {
		return bowType;
	}
	public String getRound() {
		return round;
	}
	public String getTarget() {
		return target;
	}
}
