package tools;
/**
 * Piece of the chess library that converts chess letter coordinates to a number and back again.
 * @author Alexander
 *
 */
public class Base {
	
	private static int unicodeOffset = 64; //How far up the unicode list A is. Magic number.
	
	/**
	 * Converts an index n into a spreadsheet-style letter column index like AB or CD.
	 * Recursive, can take any size number.
	 * @param n The decimal number to be converted.
	 * @param base The base to convert into. 26 uses the whole alphabet.
	 * @return The converted index as a String.
	 */
	public static String convertToLetters(int n, int base){
		String answer = "" + (char)((n % base) + unicodeOffset + 1);//get value of least digit
		if(n >= base) {												//if there are greater digits
			answer = convertToLetters((n/base) - 1, base) + answer; //chop off least digit and recurse, then add to least digit's value
		}
		return answer;												//return the total
	}
	
	/**
	 * Converts an index n into a spreadsheet-style letter column index like AB or CD.
	 * Recursive, can take any size number.
	 * @param n The decimal number to be converted.
	 * @return The converted index as a String.
	 */
	public static String convertToLetters(int n) {
		return convertToLetters(n,26);
	}
	
	/**
	 * Converts from a spreadsheet-style index to an index number.
	 * @param n The String to be converted into a number.
	 * @return The number, equivalent to the spreadsheet index, 0-indexed.
	 */
	public static int convertFromLetters(String n) {
		int total = 0;										//beginning of accumulated total
		for(int i = 0; i < n.length(); i++ ) {				//for each digit in the string
			int addition = (int) Math.round((n.charAt(i) - unicodeOffset) * Math.pow(26,(n.length() - i - 1))); //add that much based on decimal place
			total += addition;								//add the addition
		}
		return total - 1;									//return one less, since a chess board is 1-indexed instead of 0-indexed.
	}
}
