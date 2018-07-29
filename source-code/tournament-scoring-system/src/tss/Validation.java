package tss;

/**
 * Validation utility class that contains methods for various validation checks.
 * @author Alex Cordaro
 *
 */
public final class Validation {
	
	/**
	 * Validate that there is any input present.
	 * @param input the user's input
	 * @return true if it is present, false otherwise.
	 */
	public static boolean validatePresence(String input) {
		if(input.length() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Validate that the input is an integer.
	 * @param input the user's input
	 * @return true if it is an integer, false otherwise
	 */
	public static boolean validateAsInteger(String input) {
		try {
			Integer.parseInt(input);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Validate that the input string is a name.
	 * @param input the user's input
	 * @return true if it is a valid name, false otherwise
	 */
	public static boolean validateAsName(String input) {
		if(input.matches("^[\\p{L} .'-]+$")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Validate that the input is a title.
	 * @param input the user's input
	 * @return true if it is a valid title, false otherwise.
	 */
	public static boolean validateAsTitle(String input) {
		if(input.matches("^[\\p{L}0-9 .'-]+$")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Validate that the input is a time.
	 * @param input the user's input
	 * @return true if it is a valid time, false otherwise.
	 */
	public static boolean validateAsTime(String input) {
		if(input.matches("^[0-9][0-9.:apm ]+$")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Validate that the input is a target detail.
	 * @param input the user's input
	 * @return true if it is a valid target detail, false otherwise.
	 */
	public static boolean validateAsTargetDetail(String input) {
		if(input.matches("^[0-9]+[A-Z]$")) {
			return true;
		}
		return false;
	}
}