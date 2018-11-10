package movement;

import java.util.ArrayList;

import gameLogic.Board;
import piece.Piece;

/**
 * A set of moves which are dynamically generated based on the position of the piece and other pieces on the board.
 * @author Alexander
 *
 */
public class MoveClass extends MoveSet{

	
	
	protected String name;
	protected int limit;
	protected int dx = 0;
	protected int dy = 0;
	protected boolean move = true, capture = true;
	protected boolean active = true;
	/**
	 * These are inclusive.
	 * Range of moves is [minMove,maxMove] in other words.
	 */
	protected int minMove, maxMove;
	protected boolean blockable = true;
	
	/**
	 * Makes a class of sliding moves, along a vector in on direction with a certain limit to how far it can go.
	 * @param name The unlocalized name of this type of move. Typically describes direction. Use to allow activation/deactivation.
	 * @param limit The maximum number of iterations this move allows a piece to move.
	 * @param dx How much this move changes per iteration in the x direction. Typically between -1 through 1 to avoid skipping squares.
	 * @param dy How much this move changes per iteration in the y direction. Typically between -1 through 1 to avoid skipping squares.
	 * @param blockable If this move can be blocked by other pieces in its way. True if it can be blocked.
	 * @param move If this is a move, which can move a piece to an unoccupied space.
	 * @param capture If this is a captures, which can move a piece to an occupied space, capturing that piece in the process.
	 * @param minMove The minimum number of moves a piece can make before it is allowed to move this way (inclusive).
	 * @param maxMove The maximum number of moves a piece can make until it may no longer move this way (inclusive).
	 */
	public MoveClass(String name, int limit, int dx, int dy, boolean blockable, boolean move, boolean capture, int minMove, int maxMove) {
		this.name = name;
		this.limit = limit;
		this.blockable = blockable;
		this.move = move;
		this.capture = capture;
		this.dx = dx;
		this.dy = dy;
		this.minMove = minMove;
		this.maxMove = maxMove;
		if(limit == 0) {
			this.limit = Integer.MAX_VALUE;
		}
	}
	
	/**
	 * Makes a class of sliding moves, along a vector in on direction with a certain limit to how far it can go.
	 * By default, has no move number constraints.
	 * @param name The unlocalized name of this type of move. Typically describes direction. Use to allow activation/deactivation.
	 * @param limit The maximum number of iterations this move allows a piece to move.
	 * @param dx How much this move changes per iteration in the x direction. Typically between -1 through 1 to avoid skipping squares.
	 * @param dy How much this move changes per iteration in the y direction. Typically between -1 through 1 to avoid skipping squares.
	 * @param blockable If this move can be blocked by other pieces in its way. True if it can be blocked.
	 * @param move If this is a move, which can move a piece to an unoccupied space.
	 * @param capture If this is a captures, which can move a piece to an occupied space, capturing that piece in the process.
	 */
	public MoveClass(String name, int limit, int dx, int dy, boolean blockable, boolean move, boolean capture) {
		this.name = name;
		this.limit = limit;
		this.blockable = blockable;
		this.move = move;
		this.capture = capture;
		this.dx = dx;
		this.dy = dy;
		minMove = 0;
		maxMove = Integer.MAX_VALUE;
		if(limit == 0) {
			this.limit = Integer.MAX_VALUE;
		}
	}
	/**
	 * Makes a class of sliding moves, along a vector in on direction with a certain limit to how far it can go.
	 * By default, has no move number constraints and can both move to empty spaces and capture enemy pieces in occupied spaces.
	 * @param name The unlocalized name of this type of move. Typically describes direction. Use to allow activation/deactivation.
	 * @param limit The maximum number of iterations this move allows a piece to move.
	 * @param dx How much this move changes per iteration in the x direction. Typically between -1 through 1 to avoid skipping squares.
	 * @param dy How much this move changes per iteration in the y direction. Typically between -1 through 1 to avoid skipping squares.
	 * @param blockable If this move can be blocked by other pieces in its way. True if it can be blocked.
	 */
	public MoveClass(String name, int limit, int dx, int dy, boolean blockable) {
		this.name = name;
		this.limit = limit;
		this.blockable = blockable;
		move = true;
		capture = true;
		this.dx = dx;
		this.dy = dy;
		minMove = 0;
		maxMove = Integer.MAX_VALUE;
		if(limit == 0) {
			this.limit = Integer.MAX_VALUE;
		}
	}

	/**
	 * Determines whether moves in this class are active based on "active" state and move number constraints.
	 * @param moveNumber The number of moves the piece has already made.
	 * @return Whether moves in this class are able to be made.
	 */
	public boolean active(int moveNumber) {
		return (active && (moveNumber >= minMove && moveNumber <= maxMove));
	}
	
	/**
	 * Get the unlocalized name of this type of move.
	 * @return The unlocalized name as a string.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the total list of possible moves based on where this piece is on the board.
	 * @param board The board this piece is on.
	 * @param piece The piece to check.
	 * @return The list of all possible moves on this board from this MoveClass.
	 */
	public ArrayList<Move> generateMoves(Board board, Piece piece){
		return generateMoves(board, piece, true, true);
	}
	
	/**
	 * Get the total list of possible moves based on where this piece is on the board.
	 * Please do not touch this method, it is scary and bad, and if you want to refactor it, make a copy please and overload it or something.
	 * @param board The board this piece is on.
	 * @param piece The piece to check.
	 * @param getMoves Whether or not to get moves onto blank squares.
	 * @param getCaptures Whether or not to get moves onto occupied squares which contain an enemy piece, which results in a capture.
	 * @return The list of all possible moves on this board from this MoveClass.
	 */
	public ArrayList<Move> generateMoves(Board board, Piece piece, boolean getMoves, boolean getCaptures){
		/*System.out.print("Getting ");
		if(getMoves) {
			System.out.print("moves ");
		}
		if(getCaptures) {
			System.out.print("captures");
		}
		System.out.println(" of " + name);*/
		ArrayList<Move> list = new ArrayList<Move>();
		int moveNumber = piece.getMoveNumber();
		if(moveNumber >= minMove && moveNumber <= maxMove) {
			int i = 0;
			int x = piece.getPosition().getXNumeral();
			int y = piece.getPosition().getYNumeral();
			//Touple current = new Touple(x,y);
			//System.out.println("Checking " + current.getX() + current.getY());
			while(x + dx >= 0 && y + dy >= 0 && x + dx < board.getWidth() && y + dy < board.getHeight() && i < limit) {
				x += dx;
				y += dy;
				i++;
				//current = new Touple(x,y);
				//System.out.println("\tDirect check on " + current.getX() + current.getY() + " dx: " + dx + " dy: " + dy);
				
				if(board.spaceEmpty(new Touple(x,y))) {
					//System.out.println("\t\tSpace " + current.getX() + current.getY() + " empty! ");
					if(move) {
						if(getMoves) {
							list.add(new Move(x-piece.getPosition().getXNumeral(),y-piece.getPosition().getYNumeral()));
							//System.out.println("\t\tmove added " + (x + piece.getPosition().getXNumeral()) + (y-piece.getPosition().getXNumeral()));
							//add a move
						}
					}
				} else {
					if(capture) {
						Touple proposedCapture = new Touple(x,y);
						if(getMoves && !board.getPiece(proposedCapture).getColor().equals(piece.getColor())) {
							list.add(new Move(x-piece.getPosition().getXNumeral(),y-piece.getPosition().getYNumeral()));
							//System.out.println("\t\tmove added " + (x + piece.getPosition().getXNumeral()) + (y-piece.getPosition().getXNumeral()));
							//add a move
						}
					}
					if(blockable) {	//if this move class is blockable
						//System.out.println("\t\tBlocked!");
						i = limit;  //allow no futher moves.
					}
				}
			}
		}
		/*
		System.out.println("\tReturning list of length " + list.size() + ":");
		for(Move m: list) {
			System.out.println("\t\t" + m.getX() + m.getY());
		}
		System.out.println("\t---END OF LIST---\n");*/
		return list;
	}
}
