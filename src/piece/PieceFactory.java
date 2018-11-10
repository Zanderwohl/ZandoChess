package piece;

import movement.Castle;
import movement.MoveClass;
import movement.MoveSet;
import team.Team;

/**
 * PieceFactory is a class which can start "building" a piece, add attributes, moves, and an icon, then getPiece() will return that new piece.
 * @author Alexander
 *
 */
public class PieceFactory {

	private static final int UP = -1;
	private static final int DOWN = 1;
	private static final int LEFT = -1;
	private static final int RIGHT = 1;
	private static final boolean BLOCKABLE = true;
	private static final boolean UNBLOCKABLE = false;
	private static final boolean MOVE = true;
	private static final boolean NO_MOVE = false;
	private static final boolean CAPTURE = true;
	private static final boolean NO_CAPTURE = false;
	
	
	/**
	 * This piece is the one currently being "made".
	 */
	private Piece p;
	
	@SuppressWarnings("unused")
	private String set;
	
	boolean ready = false;
	
	/**
	 * Makes pieces, finds their picture, assigns a team, and adds moves.
	 * To add more later.
	 */
	public PieceFactory() {
		
	}
	
	/**
	 * Starts a piece's construction.
	 * @param team The team of the piece.
	 * @param piece The name/type of this piece.
	 * @param set The set/variant this is from. "standard" is the normal set.
	 */	
	public void startPiece(Team team, String piece, String set) {
		String symbol;
		ready = true;
		this.set = set;
		if(team.teamName().equalsIgnoreCase("white")) {
			symbol = piece.substring(0,1).toUpperCase();
		} else {
			symbol = piece.substring(0,1).toLowerCase();
		}
		
		p = new Piece(symbol,piece,team);
		
		//p.setImage(".\\src\\resources\\"+ set + "\\" + color + "\\" + piece + ".png");
		setImage(set, team.teamName(), piece);
	}
	
	/**
	 * Finds and sets the image of a piece, based on its set, team color, and piece name. All elements lowercase.
	 * @param set The set. For example, standard, chaturanga, etc.
	 * @param color The team color.
	 * @param piece The piece name.
	 */
	public void setImage(String set, String color, String piece	) {
		String URL = "/" + set + "/" + color + "/" + piece + ".png";
		System.out.println(URL + "\n");
		try {
			p.setImage(URL);
		} catch (IllegalArgumentException e) {
			System.err.println("No image found for " + set + " " + color + " " + piece + "!");
		}
	}
	
	/**
	 * Gets the piece from the piece factory. You cannot get the same piece twice. You must start a new one.
	 * @return The piece which has been worked on.
	 */
	public Piece getPiece() {
		if(ready) {
			ready = false;
			return p;
		}else {
			return null;
		}
	}
	
	/**
	 * Add a MoveSet to the current piece.
	 * @param ms The MoveSet to be added.
	 */
	public void addMoveSet(MoveSet ms) {
		p.addMoveSet(ms);
	}
	
	/**
	 * Add a MoveClass to the current piece.
	 * @param mc The MoveClass to be added.
	 */
	public void addMoveClass(MoveClass mc) {
		p.addMoveClass(mc);
	}
	
	//shorthand for addMoveClass(new MoveClass(String name, int limit, int dx, int dy, boolean blockable, boolean move, boolean capture, int minMove, int maxMove))
	private void addMoveClass(String name, int limit, int dx, int dy, boolean blockable, boolean move, boolean capture, int minMove, int maxMove) {
		addMoveClass(new MoveClass(name, limit, dx, dy, blockable, move, capture, minMove, maxMove));
	}
	
	//shorthand for addMoveClass(new MoveClass(String name, int limit, int dx, int dy, boolean blockable, boolean move, boolean capture))
	private void addMoveClass(String name, int limit, int dx, int dy, boolean blockable, boolean move, boolean capture) {
		addMoveClass(new MoveClass(name, limit, dx, dy, blockable, move, capture));
	}
	
	//shorthand for addMoveClass(new MoveClass(name, limit, dx, dy, blockable))
	private void addMoveClass(String name, int limit, int dx, int dy, boolean blockable) {
		addMoveClass(new MoveClass(name, limit, dx, dy, blockable));
	}

	/**
	 * Add hardcoded standard moves.
	 */
	public void addMoves() {
		addMoves(true);
	}
	
	/**
	 * Add the moves to the piece.
	 * @param hardcoded if true, uses standard chess moves.
	 */
	public void addMoves(boolean hardcoded) {
		if(hardcoded) {
			hardcodedMoves(p.getName());
		} else {
			//parsing stuff goes here
		}
	}
	
	/**
	 * Adds standard chess moves to a piece.
	 * @param type The name of the piece.
	 */
	private void hardcodedMoves(String type) {
		
		MoveClass up = new MoveClass("up", 0, UP, 0, BLOCKABLE, MOVE, CAPTURE);
		MoveClass down = new MoveClass("down", 0, DOWN, 0, BLOCKABLE, MOVE, CAPTURE);
		MoveClass left = new MoveClass("left", 0, 0, LEFT, BLOCKABLE, MOVE, CAPTURE);
		MoveClass right = new MoveClass("right", 0, 0, RIGHT, BLOCKABLE, MOVE, CAPTURE);
		
		MoveClass upRight = new MoveClass("upRight", 0, -1, 1, BLOCKABLE, MOVE, CAPTURE);
		MoveClass upLeft = new MoveClass("upLeft", 0, -1, -1, BLOCKABLE, MOVE, CAPTURE);
		MoveClass downRight = new MoveClass("downRight", 0, 1, 1, BLOCKABLE, MOVE, CAPTURE);
		MoveClass downLeft = new MoveClass("downLeft", 0, 1, -1, BLOCKABLE, MOVE, CAPTURE);
		
		switch(type) {
			case "pawn":
				//TODO: add en passant capture
				//TODO: add ability to promote
				if(p.getColor().equals("white")){
					addMoveClass("forward-one", 1, 0, 1, BLOCKABLE, MOVE, NO_CAPTURE);
					addMoveClass("forward", 2, 0, 1, BLOCKABLE, MOVE, NO_CAPTURE, 0, 0);
					addMoveClass("cap1", 1, 1, 1, BLOCKABLE, NO_MOVE, CAPTURE);
					addMoveClass("cap2", 1, -1, 1, BLOCKABLE, NO_MOVE, CAPTURE);
				} else {
					addMoveClass("forward-one", 1, 0, -1, BLOCKABLE, MOVE, NO_CAPTURE);
					addMoveClass("forward", 2, 0, -1, BLOCKABLE, MOVE, NO_CAPTURE, 0, 0);
					addMoveClass("cap1", 1, 1, -1, BLOCKABLE, NO_MOVE, CAPTURE);
					addMoveClass("cap2", 1, -1, -1, BLOCKABLE, NO_MOVE, CAPTURE);
				}
				break;
			case "rook":
				addMoveClass(up);
				addMoveClass(down);
				addMoveClass(left);
				addMoveClass(right);
				break;
			case "bishop":
				addMoveClass(upRight);
				addMoveClass(upLeft);
				addMoveClass(downRight);
				addMoveClass(downLeft);
				break;
			case "queen":
				addMoveClass(up);
				addMoveClass(down);
				addMoveClass(left);
				addMoveClass(right);
				addMoveClass(upRight);
				addMoveClass(upLeft);
				addMoveClass(downRight);
				addMoveClass(downLeft);
				break;
			case "king":
				//TODO: disallow the king to move into check
				//TODO: force the king to move out of check if it is in check.
				addMoveClass("up", 1, -1, 0, BLOCKABLE, MOVE, CAPTURE);
				addMoveClass("down", 1, 1, 0, BLOCKABLE, MOVE, CAPTURE);
				addMoveClass("left", 1, 0, -1, BLOCKABLE, MOVE, CAPTURE);
				addMoveClass("right", 1, 0, 1, BLOCKABLE, MOVE, CAPTURE);
				addMoveClass("upRight", 1, -1, 1, BLOCKABLE, MOVE, CAPTURE);
				addMoveClass("upLeft", 1, -1, -1, BLOCKABLE, MOVE, CAPTURE);
				addMoveClass("downRight", 1, 1, 1, BLOCKABLE, MOVE, CAPTURE);
				addMoveClass("downLeft", 1, 1, -1, BLOCKABLE, MOVE, CAPTURE);
				
				String[] castles = {"rook"};
				Castle leftCastle = new Castle("leftCastle", 0, LEFT, 0, BLOCKABLE, MOVE, NO_CAPTURE, 0, 0, castles) ;
				Castle rightCastle = new Castle("rightCastle", 0, RIGHT, 0, BLOCKABLE, MOVE, NO_CAPTURE, 0, 0, castles);
				
				addMoveSet(leftCastle);
				addMoveSet(rightCastle);
				break;
			case "knight":
				int limit = 1;
				addMoveClass("one", limit, DOWN, RIGHT * 2, UNBLOCKABLE);
				addMoveClass("two", limit, DOWN * 2, RIGHT, UNBLOCKABLE);
				addMoveClass("thr", limit, DOWN * 2, LEFT, UNBLOCKABLE);
				addMoveClass("fou", limit, DOWN, LEFT * 2, UNBLOCKABLE);
				addMoveClass("fiv", limit, UP, RIGHT * 2, UNBLOCKABLE);
				addMoveClass("six", limit, UP * 2, RIGHT, UNBLOCKABLE);
				addMoveClass("sev", limit, UP * 2, LEFT, UNBLOCKABLE);
				addMoveClass("eig", limit, UP, LEFT * 2, UNBLOCKABLE);
				break;
			case "amazon":
				int amazonLimit = 1;
				addMoveClass("one", amazonLimit, DOWN, RIGHT * 2, UNBLOCKABLE);
				addMoveClass("two", amazonLimit, DOWN * 2, RIGHT, UNBLOCKABLE);
				addMoveClass("thr", amazonLimit, DOWN * 2, LEFT, UNBLOCKABLE);
				addMoveClass("fou", amazonLimit, DOWN, LEFT * 2, UNBLOCKABLE);
				addMoveClass("fiv", amazonLimit, UP, RIGHT * 2, UNBLOCKABLE);
				addMoveClass("six", amazonLimit, UP * 2, RIGHT, UNBLOCKABLE);
				addMoveClass("sev", amazonLimit, UP * 2, LEFT, UNBLOCKABLE);
				addMoveClass("eig", amazonLimit, UP, LEFT * 2, UNBLOCKABLE);
				addMoveClass(up);
				addMoveClass(down);
				addMoveClass(left);
				addMoveClass(right);
				addMoveClass(upRight);
				addMoveClass(upLeft);
				addMoveClass(downRight);
				addMoveClass(downLeft);
				break;
			case "assasin":
				String[] allPieces = {"assasin","amazon","rook","knight","bishop","pawn","queen","king"};
				Castle castle1 = new Castle("castle1", 0, LEFT, 0, BLOCKABLE, MOVE, NO_CAPTURE, allPieces) ;
				Castle castle2 = new Castle("castle2", 0, LEFT, UP, BLOCKABLE, MOVE, NO_CAPTURE, allPieces);
				Castle castle3 = new Castle("castle3", 0, 0, UP, BLOCKABLE, MOVE, NO_CAPTURE, allPieces);
				Castle castle4 = new Castle("castle4", 0, RIGHT, UP, BLOCKABLE, MOVE, NO_CAPTURE, allPieces);
				Castle castle5 = new Castle("castle5", 0, RIGHT, 0, BLOCKABLE, MOVE, NO_CAPTURE, allPieces);
				Castle castle6 = new Castle("castle6", 0, RIGHT, DOWN, BLOCKABLE, MOVE, NO_CAPTURE, allPieces);
				Castle castle7 = new Castle("castle7", 0, 0, DOWN, BLOCKABLE, MOVE, NO_CAPTURE, allPieces);
				Castle castle8 = new Castle("castle8", 0, LEFT, DOWN, BLOCKABLE, MOVE, NO_CAPTURE, allPieces);
				addMoveSet(castle1);
				addMoveSet(castle2);
				addMoveSet(castle3);
				addMoveSet(castle4);
				addMoveSet(castle5);
				addMoveSet(castle6);
				addMoveSet(castle7);
				addMoveSet(castle8);
				break;
			default:
				System.out.println("No hard-coded case found for piece type " + type + "!");
				break;
		}
	}
}
