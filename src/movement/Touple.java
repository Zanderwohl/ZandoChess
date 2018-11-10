package movement;

import java.util.Random;

import tools.ArgsParser;
import tools.Base;
import tools.Randomizer;

/**
 * <p>An immutable set of coordinates on a chess board.</p>
 * <p>Lowest coordinate possible is A1.</p>
 * @author Alexander Lowry
 *
 */
public class Touple {

	/**
	 * X-coordinate, 0-indexed.
	 */
	private final int x;
	/**
	 * Y-coordinate, 0-indexed.
	 */
	private final int y;
	
	/**
	 * Constructor for Touple that accepts 0-indexed numerical coordinates.
	 * @param x The x-position of this Touple.
	 * @param y The y-position of this Touple.
	 */
	public Touple(int x, int y) {
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor for Touple that takes two chess coordinate pieces.
	 * @param x The 1-indexed x-position as a letter or letters.
	 * @param y The 1-indexed y-position as a number.
	 */
	public Touple(String x, String y) {
		this.x = Base.convertFromLetters(x);	//convert the letter(s) into an index and store to x
		this.y = Integer.parseInt(y) - 1;		//zero-index the number and store to y
	}
	
	/**
	 * <p>Constructor for Touple that takes String parameters</p>
	 * <p>Commands take the form of: command &lt;param1&gt; &lt;param2&gt; &lt;param3&gt; </p>
	 * <p>random (max) &lt;min&gt;					Chooses random coords on square space.</p>
	 * <p>random (maxX) (minX) (maxY) (minY)		Chooses random coords on square space.</p>
	 * 
	 * <p>This is a bad function. Distribute functionality other places at some point, perhaps to a Touple factory of some kind.</p>
	 * @param parameters The string parameters.
	 */
	public Touple(String parameters) {
		//System.out.println(parameters.length() + " " + parameters.substring(0,6).equals("random") + " : " + parameters.substring(0,6));
		if(parameters.length() > 5 && parameters.substring(0,6).equals("random")) {	//if it begins with "random"
			String[] args = parameters.split(" ");									//split by spaces
			Random r = new Random();
			int[] parsedArgs = ArgsParser.parse(args);								//turn args from strings into ints
			if(args.length == 3) {
				this.x = Randomizer.randomRange(parsedArgs[1], parsedArgs[0]);		//set x a random number
				this.y = Randomizer.randomRange(parsedArgs[1], parsedArgs[0]);		//set y a random number
			}else {
				if(args.length == 5) {
					this.x = Randomizer.randomRange(parsedArgs[1], parsedArgs[0]);	//set x a random number
					this.y = Randomizer.randomRange(parsedArgs[3], parsedArgs[2]);	//set y a ranom number
				}else {
					this.x = r.nextInt(8);											//set random if no parameters given
					this.y = r.nextInt(8);
				}
			}
		}
		else {
			System.err.println("Touple instantiated with argument constructor, with wrong arguments!\nArgument was: " + parameters);
			this.x = 0;
			this.y = 0;
		}
	}
	
	/**
	 * Gets the x from the touple, a letter.
	 * @return x coordinate as letter.
	 */
	public String getX() {
		return Base.convertToLetters(x,26);
	}
	
	/**
	 * Gets the y from the touple, a number (1-indexed).
	 * @return y coordinate as number.
	 */
	public String getY() {
		return "" + (y + 1); //add 1 to return for 1-index
	}
	
	/**
	 * Converts the coordinate into a zero-indexed number coordinate.
	 * @return The index of the x coordinate.
	 */
	public int getXNumeral() {
		return x;
	}
	
	/**
	 * Converts the coordinate into a zero-indexed number coordinate.
	 * @return The index of the y coordinate.
	 */
	public int getYNumeral() {
		return y;
	}
	
	/**
	 * Static addition functionality, to find a new Touple that sums the values of each.
	 * @param a The first value, usually the absolute or larger one.
	 * @param b The second value, usually the smaller or relative one.
	 * @return A Touple that represents the sum of the x and y values of each.
	 */
	public static Touple add(Touple a, Touple b) {
		int xVal = a.getXNumeral() + b.getXNumeral();
		int yVal = a.getYNumeral() + b.getYNumeral();
		/*if(xVal < 0) {
			xVal = 0;
		}
		if(yVal < 0) {
			yVal = 0;
		}*/
		return new Touple(xVal, yVal);
	}
	
	/**
	 * Make a new touple based on this touple plus a change.
	 * @param dx Number to be added to x.
	 * @param dy Number to be added to y.
	 * @return A new Touple with coordinates (this.x + dx, this.y + dy).
	 */
	public Touple add(int dx, int dy) {
		int xVal = x + dx;
		int yVal = y + dy;
		return new Touple(xVal, yVal);
	}
	
	/**
	 * Checks equality of values between two touples.
	 * @param t The touple to be compared to this one.
	 * @return Equality of both x and y.
	 */
	public boolean equals(Touple t) {
		return t.getXNumeral() == this.getXNumeral() && t.getYNumeral() == this.getYNumeral();
	}
	
}
