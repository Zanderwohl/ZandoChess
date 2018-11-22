package movement;

import java.util.ArrayList;

import gameLogic.Board;
import piece.Piece;

/**
 * Implemented for the special case of en Passant.
 * Special type of capture that:
 * Takes into account the number of moves each piece has taken,
 * Moves the piece into a square it does not capture,
 * and captures a square that the piece does not touch.
 * @author Alexander
 *
 */
public class enPassant extends MoveClass{

	//protected String name;
	//protected int limit;
	//protected int dx = 0;
	//protected int dy = 0;
	protected int capDx;
	protected int capDy;
	protected boolean move = false, capture = true;
	protected boolean active = true;
	
	/**
	 * Please don't try to use this class.
	 * @param name
	 * @param limit
	 * @param dx
	 * @param dy
	 * @param capDx
	 * @param capDy
	 * @param minMove
	 * @param maxMove
	 */
	public enPassant(String name, int dx, int dy, int capDx, int capDy, int minMove, int maxMove) {
		super(name, 1, dx, dy, false);
		super.minMove = minMove;
		super.maxMove = maxMove;
		this.capDx = capDx;
		this.capDy = capDy;
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
		ArrayList<Move> list = new ArrayList<Move>();
		if(getCaptures) {
			int moveNumber = piece.getMoveNumber();
			
			Move proposedMove = new Move(capDx, capDy, new MoveHistory(new Touple(capDx, capDy), new Touple(dx, dy)));
			
			int currentX = piece.getPosition().getXNumeral();
			int currentY = piece.getPosition().getYNumeral();
			
			Touple otherPieceLocation = new Touple(currentX + capDx, currentY + capDy);
			
			if(!board.spaceEmpty(otherPieceLocation)) {
				Piece otherPiece = board.getPiece(otherPieceLocation);
				int otherMoveNumber = otherPiece.getMoveNumber();
				System.out.println("There's another piece.");
			}
			
			
			list.add(proposedMove);
			
			System.out.println("\tReturning list of length " + list.size() + ":");
			for(Move m: list) {
				System.out.println("\t\t" + m.getX() + m.getY());
			}
			System.out.println("\t---END OF LIST---\n");
		}
		
		
		return list;
	}

}
