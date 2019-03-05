package piece;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import gameLogic.Board;
import movement.Move;
import movement.MoveClass;
import movement.MoveSet;
import movement.Touple;
import team.Team;

/**
 * A game piece which has a name, team color, and set of dynamically generated moves that it can do.
 * @author Alexander
 *
 */
public class Piece {

	private Touple position;
	private int move;
	
	private ArrayList<Move> perminantMoves = new ArrayList<Move>();
	private ArrayList<Move> perminantCaptures = new ArrayList<Move>();
	
	private ArrayList<MoveSet> moveSets = new ArrayList<MoveSet>();
	
	private ArrayList<Move> moveList = new ArrayList<Move>();
	private ArrayList<Move> captureList = new ArrayList<Move>();
	
	private String symbol;
	private String name;
	private Team team;
	private boolean moving;
	private Image image2D;
	
	/**
	 * Simple constructor for pieces.
	 * @param symbol The single-character symbol for this piece.
	 * @param name The unique name of this piece.
	 * @param team The team this piece is a member of.
	 */
	public Piece(String symbol, String name, Team team) {
		this.symbol = symbol;
		this.name = name;
		this.team = team;
		move = 0; //move always starts at 0; it has never moved.
	}
	
	/**
	 * Gets the symbol of the piece.
	 * @return the symbol of the chess piece in Unicode.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * Gets the full name of the piece.
	 * @return the name of the piece as a String.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the position of this piece
	 * @return position as a Touple.
	 */
	public Touple getPosition() {
		return position;
	}
	
	/**
	 * Gets this chess piece's X in chess coordinates.
	 * @return the X-coordinate of this piece.
	 */
	public String getX() {
		return position.getX();
	}
	
	/**
	 * Gets this chess piece's Y in chess coordinates.
	 * @return the Y-coordinate of this piece.
	 */
	public String getY() {
		return position.getY();
	}
	
	/**
	 * Sets both the X and Y coordinates of the chess piece.
	 * @param T The new coordinates of the piece.
	 * @return true if can be placed there and therefore has, false if it cannot be placed there.
	 */
	public boolean setPosition(Touple T) {
		position = T;
		return true;
	}
	
	/**
	 * <p>Sets a new position for the piece, as long as it's a legal move.</p>
	 * <p>Increments the move count by one.</p>
	 * @param T The new coordinates of the piece.
	 * @return true if the piece can be placed
	 */
	public boolean move(Touple T) {
		if(isValidMove(T)) {
			setPosition(T);
			move++;
		}
		return false;
	}
	
	/**
	 * Checks if a given Touple is a valid move for this piece.
	 * @param T The position this piece wishes to move to.
	 * @return True if the move is possible and legal, and false if the move is not.
	 */
	public boolean isValidMove(Touple T) {
		return true;
	}
	
	/**
	 * Gets how many times this piece has been moved.
	 * @return The move number as an int.
	 */
	public int getMoveNumber() {
		return move;
	}
	
	/**
	 * Sets the new X-position of the chess piece.
	 * @param newX The new x-position of the piece.
	 * @return true if can be placed there and therefore has, false if it cannot be placed there.
	 */
	public boolean setX(String newX) {
		Touple newPosition = new Touple(newX,position.getY());
		position = newPosition;
		return true;
	}
	
	/**
	 * Sets the new Y-position of the chess piece.
	 * @param newY The new y-position of the piece.
	 * @return true if can be placed there and therefore has, false if it cannot be placed there.
	 */
	public boolean setY(String newY) {
		Touple newPosition = new Touple(position.getX(),newY);
		position = newPosition;
		return true;
	}
	
	/**
	 * Gives all reachable spaces this piece can end up at in one move.
	 * @return a list of Touples of the coordinates this piece can go in one move.
	 */
	public ArrayList<Touple> possibleMoves(){
		return new ArrayList<Touple>();
	}
	
	/**
	 * Gets the team color of this piece.
	 * @return The color of the piece.
	 */
	public String getColor() {
		return team.teamName();
	}
	
	/**
	 * Sets the team color of this piece.
	 * @param newColor the name of the color that this piece is switching to.
	 */
	public void setColor(String newColor) {
		//this.team = newColor;
		//TODO: Re-add team changing functionality.
		System.err.println("Pieces cannot currently change team.");
	}
	
	/**
	 * <p>Gets whether or not this piece is in the middle of moving.</p>
	 * <p>Only the graphical display should touch this.</p>
	 * @return True if it is moving, false, if it is not.
	 */
	public boolean getMoving() {
		return moving;
	}
	
	/**
	 * <p>Sets whether or not this piece is moving</p>
	 * <p>Only the graphical display should touch this.</p>
	 * @param moving Sets the status of the piece moving or not.
	 */
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	/**
	 * Sets the image of the piece based on the path.
	 * @param path The path, relative to project folder, of the image's location.
	 */
	public void setImage(String path) {
		try {
			//ClassLoader classLoader = getClass().getClassLoader();
			//System.out.println(classLoader.getResource(path));
			//File file = new File(classLoader.getResource(path).getFile());
		    //File file = new File(path);
			
			URL url = getClass().getResource(path);
			//System.out.println(url);
			image2D = ImageIO.read(url);
			
			InputStream is = new BufferedInputStream(
	                new FileInputStream("resources/" + path));
	        image2D = ImageIO.read(is);
			
		    //image2D = ImageIO.read(file);
		}
		catch (IOException e) {
			System.err.println("Can't find the file specified for " + getName() + " at: " + path);
			System.err.println("Using default debug visualization instead.");
		    e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Gets the image of the piece, as an Image.
	 * @return The image, as previously set.
	 */
	public Image getImage() {
		return image2D;
	}
	
	protected void addMove(Move t) {
		perminantMoves.add(t);
	}
	
	protected void addCapture(Move t) {
		perminantCaptures.add(t);
	}
	
	protected void addMovePair(Move t) {
		addMove(t);
		addCapture(t);
	}
	
	protected void addMoveSet(MoveSet ms) {
		moveSets.add(ms);
	}
	
	protected void addMoveClass(MoveClass mc) {
		moveSets.add(mc);
	}
	
	protected void addMoveClass(String name, int limit, int dx, int dy, boolean blockable, boolean move, boolean capture) {
		moveSets.add(new MoveClass(name, limit, dx, dy, blockable, move, capture));
	}
	
	protected void addMoveClass(String name, int limit, int dx, int dy, boolean blockable) {
		moveSets.add(new MoveClass(name, limit, dx, dy, blockable));
	}
	
	/**
	 * Returns an ArrayList of all moves this piece can make.
	 * @return All moves that this piece may make.
	 */
	public ArrayList<Move> getMoves(){
		ArrayList<Move> moves = new ArrayList<Move>();
		for(Move m: moveList) {
			moves.add(m);
		}
		return moves;
	}
	
	/**
	 * Returns an ArrayList of all captures this piece can make.
	 * @return All moves that this piece can make which are captures.
	 */
	public ArrayList<Move> getCaptures(){
		ArrayList<Move> captures = new ArrayList<Move>();
		for(Move m: captureList) {
			captures.add(m);
		}
		return moveList;
	}
	
	/**
	 * Generates the list of all non-capture moves, at this place on this current board.
	 * @param b The board this piece is on.
	 */
	public void generateMoves(Board b) {
		moveList.clear();
		ArrayList<Move> list = new ArrayList<Move>();
		if(!perminantMoves.isEmpty())
			for(Move m: perminantMoves) {
				{
					Touple newPos = Touple.add(position, m);
					if(m.isActive(move) && b.isValidSquare(newPos)) {
						list.add(m);
					}
				}
			}
		if(!moveSets.isEmpty()) {
			for(MoveSet mc: moveSets) {
				ArrayList<Move> moves = mc.generateMoves(b, this, true, false);
				for(Move m: moves) {
					if(m.isActive(move)) {
						list.add(m);
					}
				}
			}
		}
		moveList = list;
	}
	
	/**
	 * Generates a list of all captures that this piece can make from this place, on this current board.
	 * @param b The current board this piece is on.
	 */
	public void generateCaptures(Board b){
		captureList.clear();
		ArrayList<Move> list = new ArrayList<Move>();
		if(!perminantCaptures.isEmpty()) {
			for(Move m: perminantCaptures) {
				if(m.isActive(move)) {
					list.add(m);
				}
			}
		}
		if(!moveSets.isEmpty()) {
			for(MoveSet mc: moveSets) {
				ArrayList<Move> moves = mc.generateMoves(b, this, false, true);
				for(Move m: moves) {
					if(m.isActive(move)) {
						list.add(m);
					}
				}
			}
		}
		captureList = list;
	}
	
	/**
	 * Returns a list of moves which are are capture and non-capture moves.
	 * @return All capture and non-capture moves.
	 */
	public ArrayList<Move> getMovesAndCaptures(){
		ArrayList<Move> list = getMoves();
		ArrayList<Move> captures = getCaptures();
		if(!captures.isEmpty()) {
			for(Move m: captures) {
				if(m.isActive(move)) {
					list.add(m);
				}
			}
		}
		return list;
	}
	
	/**
	 * Increments the move counter for this piece.
	 */
	public void incrementMoves() {
		move++;
		//System.out.println("I moved: " + move);
	}
	
}
