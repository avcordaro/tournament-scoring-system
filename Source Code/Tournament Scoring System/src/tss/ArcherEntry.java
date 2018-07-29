package tss;

/**
 * ArcherEntry objects are used for filling the archers TableView element.  
 * @author Alex Cordaro
 *
 */
public class ArcherEntry {
	
	private int ID;
	private String firstName;
	private String lastName;
	private String club;;
	private String category;
	private String bowType;
	private String round;
	
	public ArcherEntry(int id, String f, String l, String c, String a, String b, String r) {
		ID = id;
		firstName = f;
		lastName = l;
		club = c;
		category = a;
		bowType = b;
		round = r;
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
}