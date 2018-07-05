package tss.java;

public class ArcherEntry {
	
	private int ID;
	private String firstName;
	private String lastName;
	private String club;;
	private String category;
	private String bowType;
	private String round;
	private String target;
	
	public ArcherEntry(int id, String f, String l, String c, String a, String b, String r, String t) {
		ID = id;
		firstName = f;
		lastName = l;
		club = c;
		category = a;
		bowType = b;
		round = r;
		target = t;
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
	public String getClub() {
		return club;
	}
	public String getCategory() {
		return category;
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
