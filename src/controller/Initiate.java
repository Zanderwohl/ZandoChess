package controller;

import java.util.Locale;

import gameLogic.Model;
import view.UserView;

/**
 * Entry point for the program. Creates a model, view, and controller, finally linking them to each other.
 * @author Alexander
 * 
 *
 */
public class Initiate {

	private static Controller c;
	
	private static Locale currentLocale;
	
	/**
	 * Controller main. The program entry point.
	 * @param args No args input.
	 */
	public static void main(String[] args) {
		currentLocale = new Locale("en", "US");
		//currentLocale = new Locale("en","SF");
		
		UserView v = new UserView(currentLocale);
		Model m = new Model();
		c = new Controller(v,m);
		c.hardcodedStart("standard");
	}
	
}
