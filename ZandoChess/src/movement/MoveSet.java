package movement;

import java.util.ArrayList;

import gameLogic.Board;
import piece.Piece;

/**
 * A MoveSet is a bundle of moves, in any datatype, which can behave in multiple ways.
 * The moves may be generated dynamically, or be a set predefined by some list.
 * An abstract class which defines the set of interactions all move sets must follow.
 * All moves must be able to be active or not, have a name, and be told when to generate moves when polled to do so.
 * @author Alexander
 *
 */
public abstract class MoveSet {
	
	/**
	 * Determines whether moves in this class are active based on "active" state and move number constraints.
	 * @param move The number of moves the piece has already made.
	 * @return Whether moves in this class are able to be made.
	 */
	public abstract boolean active(int move);
	
	/**
	 * Get the unlocalized name of this type of move.
	 * @return The unlocalized name as a string.
	 */
	public abstract String getName();
	
	/**
	 * Get the total list of possible moves based on where this piece is on the board.
	 * @param board The board this piece is on.
	 * @param piece The piece to check.
	 * @return The list of all possible moves on this board from this MoveClass.
	 */
	public abstract ArrayList<Move> generateMoves(Board board, Piece piece);
	
	/**
	 * Get the total list of possible moves based on where this piece is on the board.
	 * @param board The board this piece is on.
	 * @param piece The piece to check.
	 * @param getMoves Whether or not to get moves onto blank squares.
	 * @param getCaptures Whether or not to get moves onto occupied squares which contain an enemy piece, which results in a capture.
	 * @return The list of all possible moves on this board from this MoveClass.
	 */
	public abstract ArrayList<Move> generateMoves(Board board, Piece piece, boolean getMoves, boolean getCaptures);
	
}
