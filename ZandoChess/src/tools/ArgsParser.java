package tools;

/**
 * Splits a string command into a list of arguments.
 * @author Alexander
 *
 */
public class ArgsParser {
	
	/**
	 * Takes a split string of command and arguments, turns it into an int[] array of just arguments
	 * @param args The command, split by spaces, as a string in the format "[command] [arg0] <arg1>..."
	 * @return A list of all the arguments as integers.
	 */
	public static int[] parse(String[] args) {
		int[] parsedArgs = new int[args.length - 1];		//create new array for parsed arguments, ints
		for(int i = 0; i < parsedArgs.length; i++) {		//for each argument
			//System.out.println(i);
			parsedArgs[i] = Integer.parseInt(args[i+1]);	//convert to int and store
		}
		return parsedArgs;									//return
	}
}
