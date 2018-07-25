package tss;

public final class Validation {
	
	public static boolean validatePresence(String input) {
		if(input.length() == 0) {
			return false;
		}
		return true;
	}
	
	public static boolean validateAsInteger(String input) {
		try {
			Integer.parseInt(input);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static boolean validateAsName(String input) {
		if(input.matches("^[\\p{L} .'-]+$")) {
			return true;
		}
		return false;
	}
	
	public static boolean validateAsTitle(String input) {
		if(input.matches("^[\\p{L}0-9 .'-]+$")) {
			return true;
		}
		return false;
	}
	
	public static boolean validateAsTime(String input) {
		if(input.matches("^[0-9][0-9.:apm ]+$")) {
			return true;
		}
		return false;
	}
	
	public static boolean validateAsTargetDetail(String input) {
		if(input.matches("^[0-9]+[A-Z]$")) {
			return true;
		}
		return false;
	}
}