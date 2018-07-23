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
		if(input.matches("^[a-zA-Z-'.\\s]+$")) {
			return true;
		}
		return false;
	}
	
	public static boolean validateAsTime(String input) {
		if(input.matches("^[0-9][0-9.:apm\\s]+$")) {
			return true;
		}
		return false;
	}
}