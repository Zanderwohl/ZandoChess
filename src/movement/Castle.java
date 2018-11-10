package movement;

import java.util.ArrayList;

import gameLogic.Board;
import piece.Piece;

/**
 * A type of moveclass which requires you to move to another piece, then put that piece on the other side of the first piece to protect it.
 * @author Alexander
 *
 */
public class Castle extends MoveClass{
	
	private int otherMinMove = 0, otherMaxMove = Integer.MAX_VALUE;
	/**
	 * Only able to castle with types listed in this array.
	 */
	private String[] validTypes;

	public Castle(String name, int limit, int dx, int dy, boolean blockable, String[] validTypes) {
		super(name, limit, dx, dy, blockable);
		otherMinMove = minMove;
		otherMaxMove = maxMove;
		this.validTypes = validTypes;
	}

	public Castle(String name, int limit, int dx, int dy, boolean blockable, boolean move, boolean capture, String[] validTypes) {
		super(name, limit, dx, dy, blockable, move, capture);
		otherMinMove = minMove;
		otherMaxMove = maxMove;
		this.validTypes = validTypes;
	}
	
	public Castle(String name, int limit, int dx, int dy, boolean blockable, boolean move, boolean capture, int minMove, int maxMove, String[] validTypes) {
		super(name, limit, dx, dy, blockable, move, capture, minMove, maxMove);
		otherMinMove = minMove;
		otherMaxMove = maxMove;
		this.validTypes = validTypes;
	}
	

	/**
	 * Get the total list of possible castle points that this castle set can generate.
	 * This code makes me want to vomit.
	 * It did when I wrote it.
	 * It does now.
	 * @param board The board this piece is on.
	 * @param piece The piece to check.
	 * @param getMoves Whether or not to get moves onto blank squares.
	 * @param getCaptures Whether or not to get moves onto occupied squares which contain an enemy piece, which results in a capture.
	 * @return The list of all possible moves on this board from this MoveClass.
	 */
	public ArrayList<Move> generateMoves(Board board, Piece piece, boolean getMoves, boolean getCaptures){
		ArrayList<Move> list = new ArrayList<Move>();
		int moveNumber = piece.getMoveNumber();
		if(moveNumber >= minMove && moveNumber <= maxMove) {
			int i = 0;
			int x = piece.getPosition().getXNumeral();
			int y = piece.getPosition().getYNumeral();
			while(x + dx >= 0 && y + dy >= 0 && x + dx < board.getWidth() && y + dy < board.getHeight() && i < limit) {
				x += dx;
				y += dy;
				i++;
				
				if(!board.spaceEmpty(new Touple(x + dx ,y + dy))) {
					if(move) {
						Touple proposedCapture = new Touple(x,y);
						if(getMoves && board.isValidSquare(proposedCapture.add(dx, dy))
								&& board.spaceEmpty(proposedCapture)
								&& board.getPiece(proposedCapture.add(dx, dy)).getColor().equals(piece.getColor())
								&& board.getPiece(proposedCapture.add(dx, dy)).getMoveNumber() >= otherMinMove
								&& board.getPiece(proposedCapture.add(dx, dy)).getMoveNumber() <= otherMaxMove
								) {
							Piece otherPiece = board.getPiece(proposedCapture.add(dx, dy));
							boolean correctType = false;
							for(int j = 0; j < validTypes.length; j++) {
								if(otherPiece.getName().equals(validTypes[j])) {
									//System.out.println(board.getPiece(proposedCapture.add(dx, dy)).getName() + " " + validTypes[j]);
									correctType = true;
								}
							}
							
							if(correctType && otherPiece.getMoveNumber() <= otherMaxMove && otherPiece.getMoveNumber() >= otherMinMove) {
								Touple start = proposedCapture.add(dx, dy);
								Touple end = proposedCapture.add(0 - dx, 0 - dy);
								//System.out.println(x + ":" + y + " " + proposedCapture.getX() + " " + proposedCapture.getY());
								//System.out.println("Effect is moving " + start.getX() + start.getY() + " to " + end.getX() + end.getY());
								MoveHistory effect = new MoveHistory(start, end);
								list.add(new Move(x-piece.getPosition().getXNumeral(),y-piece.getPosition().getYNumeral(),effect));
								//add a move
							}
						}
					}
					if(blockable) {	//if this move class is blockable
						i = limit;  //allow no futher moves.
					}
				}
			}
		}
		return list;
	}
}
