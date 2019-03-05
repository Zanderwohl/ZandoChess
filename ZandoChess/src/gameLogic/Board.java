package gameLogic;

import movement.Touple;
import piece.Piece;

/**
 * A board which has pieces stored in a 2D space of discrete squares.
 * Pieces may be added, removed, and moved.
 * @author Alexander
 *
 */
public class Board {
	
	Piece[][] board;
	private int width, height;
	
	/**
	 * Creates a new board of specified dimensions.
	 * @param width Width for lettered side.
	 * @param height Height for numbered side.
	 */
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		board = new Piece[width][height];
	}
	
	/**
	 * Creates a new board of standard international 8x8 size.
	 */
	public Board() {
		this.width = 8;
		this.height = 8;
		board = new Piece[width][height];
	}
	
	/**
	 * Gets a piece based on its location on the board.
	 * @param t the location of the piece as a Touple.
	 * @return the piece at the specified location.
	 */
	public Piece getPiece(Touple t) {
		try {
			Piece p = board[t.getXNumeral()][t.getYNumeral()];
			return p;
		}catch(NullPointerException e) {	//If no piece is there, return null.
			return null;
		}catch(ArrayIndexOutOfBoundsException a) {
			return null;
		}
	}
	
	/**
	 * Says if the piece color at Touple t is the color specified.
	 * @param t The location to be checked.
	 * @param teamName The name of the team to be compared to.
	 * @return True if the team name of that piece is as specified.
	 */
	public boolean pieceColorIs(Touple t, String teamName) {
		return getPiece(t).getColor().equals(teamName);
	}
	
	/**
	 * Sets a piece on the board at that location.
	 * @param p The piece to be set.
	 * @param t The location it is to be set.
	 * @return True if it could be set, false if a piece is there, or the location is invalid.
	 */
	protected boolean setPiece(Piece p, Touple t) {
		if(isValidSquare(t) && spaceEmpty(t)) {
			board[t.getXNumeral()][t.getYNumeral()] = p;	//Set the location on the board.
			p.setPosition(t);								//Tell the piece where it is.
		}
		return true;
	}
	
	/**
	 * Removes a piece from the board, as long as the space is not empty.
	 * If it is empty, nothing happens.
	 * @param t The position of the piece to be removed.
	 */
	protected void removePiece(Touple t) {
		if(!spaceEmpty(t)) {
			board[t.getXNumeral()][t.getYNumeral()] = null;
		}
	}
	
	/**
	 * Checks whether a space is occupied by any piece, regardless of color
	 * @param t The location in question, as a Touple.
	 * @return True if the space is 
	 */
	public boolean spaceEmpty(Touple t) {
		//if the space doesn't exist, it's "full"
		if(t.getXNumeral() < 0 || t.getXNumeral() > width - 1 || t.getYNumeral() < 0 || t.getYNumeral() > height - 1) {
			return false;
		}
		return board[t.getXNumeral()][t.getYNumeral()] == null;	//Check if there is a null pointer at the location. If so, it's empty.
	}
	
	/**
	 * Gets a random space that has nothing in it.
	 * @return The space as a Touple.
	 */
	public Touple randomEmptySpace() {
		Touple t;
		do {
			t = new Touple("random " + width + " 0 " + height + " 0");  //get random Touple
		}while(getPiece(t) != null);									//if this space is not empty, try again.
		return t;
	}
	
	/**
	 * Gives the location of a piece on the board as known by the board, given any piece that is on the board.
	 * @param p The pieces to be located.
	 * @return A Touple that contains the location of that piece. If piece cannot be found, return null.
	 */
	public Touple locatePiece(Piece p) {
		for(int i = 0; i < board.length; i++) {			//for each row and column
			for(int j = 0; j < board[0].length; j++) {	
				if(board[i][j] == p) {					//check if that piece is in that space
					return p.getPosition();				//if so, return its location.
				}
			}
		}
		return null;									//if it cannot be found, return null
	}
	
	/**
	 * Gets the width of the board, in squares.
	 * @return The width as an int, of the board, in squares.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Gets the height of the board, in squares.
	 * @return The height as an int, of the board, in squares.
	 */
	public int getHeight() {
		return height;
	}
	
	
	/**
	 * Prints the board as a simple grid to the console, with the symbols for each piece.
	 * Block of arbitrary code. Don't touch.
	 */
	/*
	public void printBoard() {
		System.out.print("\t");
		for(int j = 0; j < board[0].length; j++) {
			System.out.print(Base.convertToLetters(j, 26) + " ");
		}
		System.out.println("");
		
		for(int i = board.length - 1 ; i >= 0; i--) {
			System.out.print((i+1) + "\t");
			for(int j = 0; j < board[i].length; j++) {
				if(board[i][j] != null) {
					System.out.print(board[i][j].getSymbol());
				}
				else {
					if(getSpaceColor(i,j).equals("Black")) {
						System.out.print("\u2022");
					}
					else {
						System.out.print("\u00B7");
					}
				}
				System.out.print(" ");
			}
			System.out.println("");
		}
	}*/
	
	/**
	 * Determines whether a given space is black or white, given where it is.
	 * @param x 0-indexed x-coordinate of the space.
	 * @param y 0-indexed y-coordinate of the space.
	 * @return "White" if it is white, "Black" if it is black.
	 */
	public static String getSpaceColor(int x, int y) {
		if(x % 2 == 0) {				//if x is even
			if(y % 2 == 0) {			//and y is even
				return "Black";				//it's black
			}else {						//and y is odd
				return "White";				//it's white
			}
		}else {							//if x is odd
			if(y % 2 == 0) {			//and y is even
				return "White";				//it's white
			}else {						//and y is odd
				return "Black";				//it's black
			}
		}
	}

	/**
	 * Determines whether a given space is black or white, given where it is.
	 * @param t The touple of the space specified.
	 * @return The color of the piece at the space (lowercase is standard).
	 */
	public static String getSpaceColor(Touple t) {
		return getSpaceColor(t.getXNumeral(),t.getYNumeral());
	}
	
	/**
	 * Determines if a given Touple is a valid space within the board.
	 * @param newPos The space to be checked.
	 * @return True if the space is a valid space, false if it is not (even if not empty).
	 */
	public boolean isValidSquare(Touple newPos) {
		//System.out.print(newPos.getX() + newPos.getY() + " is ");
		if(newPos.getXNumeral() < width && newPos.getYNumeral() < height && newPos.getXNumeral() >= 0 && newPos.getYNumeral() >= 0) {
			//System.out.println("valid.");
			return true;
		}
		//System.out.println("invalid.");
		return false;
	}
}
