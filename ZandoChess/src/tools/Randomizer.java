package tools;

import java.util.Random;

/**
 * A class which generates random chess-based items.
 * @author Alexander
 *
 */
public class Randomizer {
	
	/**
	 * Selects a random number in a range
	 * @param min Minimum integer, inclusive.
	 * @param max Maximum integer, inclusive.
	 * @return Random number in [min,max].
	 */
	public static int randomRange(int min, int max) {
		Random r = new Random();
		return min + (r.nextInt(max-min));
	}
}
