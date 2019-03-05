package gameLogic;

import java.util.ArrayList;
import java.util.Random;

import movement.Touple;
import piece.Piece;
import piece.PieceFactory;

/**
 * Makes boards filled with pieces, which also have moves.
 * @author Alexander
 *
 */
public class BoardFactory {

	PieceFactory spf;
	Teams teams;
	
	/**
	 * Constructs a piecefactory for this boardfactory by default.
	 */
	public BoardFactory(Teams teams) {
		spf = new PieceFactory();
		this.teams = teams;
	}
	
	/**
	 * A piecefactory must be supplied in the constructor.
	 * @param pieceFactory The piecefactory to be used.
	 */
	public BoardFactory(Teams teams, PieceFactory pieceFactory) {
		spf = pieceFactory;
		this.teams = teams;
	}
	
	/**
	 * Creates a board with no pieces of a specified size.
	 * @param x The width of the board.
	 * @param y The height of the board.
	 * @return The completely blank board.
	 */
	public Board blankBoard(int x, int y) {
		return new Board(x,y);
	}
	
	/**
	 * Returns a standard board. Should be deprecated when the proper factory gets implemented
	 * @param x The width of the board.
	 * @param y The height of the board.
	 * @return A board using standard chess setup.
	 */
	public Board standardBoard(int x, int y) {
		Board b = new Board(x,y);
		
		for(int i = 0; i < x; i++) {
			addOpposing(b, "pawn", new Touple(i,1));					//add pawns
		}
		for(int i = 0; i < x; i += 7) {
			addOpposing(b,"rook", new Touple(i,0));					//add rooks
		}
		for(int i = 1; i <= x; i += 7) {
			addOpposing(b,"knight", new Touple(i,0));				//add knights
			addOpposing(b,"knight", new Touple(i+5,0));
		}
		for(int i = 2; i <= x; i += 7) {
			addOpposing(b,"bishop", new Touple(i,0));				//add bishops
			addOpposing(b,"bishop", new Touple(i+3,0));
		}
		for(int i = 3; i <= x; i += 7) {
			addOpposing(b,"queen", new Touple(i,0));						//add queens
			addOpposing(b,"king", new Touple(i+1,0));						//add kings
		}
		//addOpposing(b,"king", new Touple(4,0));						//add kings
		
		//addStandardMoves(b);
		
		return b;
	}
	
	/**
	 * Returns a castle debug board. Should be deprecated when the proper factory gets implemented
	 * @param x The width of the board.
	 * @param y The height of the board.
	 * @return A board using castle debug setup.
	 */
	public Board castleBoard(int x, int y) {
		Board b = new Board(x,y);
		

		for(int i = 0; i < x; i += 7) {
			addOpposing(b,"rook", new Touple(i,0));					//add rooks
		}

		for(int i = 3; i <= x; i += 7) {
			addSymmetrical(b,"king", new Touple(i+1,0));						//add kings
		}
		
		addSymmetrical(b,"amazon", new Touple(3,4));
		addSymmetrical(b,"assasin", new Touple(3,3));
		
		addSymmetrical(b,"pawn", new Touple(1,1));
		addSymmetrical(b,"pawn", new Touple(6,1));
		
		return b;
	}
	
	/**
	 * Returns a randomized shuffle chess board. Should be deprecated when the proper factory gets implemented
	 * @return A board using standard chess setup.
	 */
	public Board shuffleBoard() {
		Board b = new Board(8,8);
		
		for(int i = 0; i < 8; i++) {
			addOpposing(b, "pawn", new Touple(i,1));					//add pawns
		}
		
		ArrayList<String> pieces = new ArrayList<String>();
		
		for(int i = 0; i < 2; i ++) {
			pieces.add("rook");
			pieces.add("bishop");
			pieces.add("knight");
		}
		pieces.add("king");
		pieces.add("queen");
		
		Random r = new Random();
		int piecesLength = pieces.size();
		for(int i = 0; i < piecesLength; i++) {
			String thisPiece = pieces.get(r.nextInt(pieces.size()));
			addOpposing(b,thisPiece, new Touple(i,0));
			pieces.remove(thisPiece);
		}
		
		//addStandardMoves(b);
		
		return b;
	}
	
	/**
	 * Returns a standard board. Should be deprecated when the proper factory gets implemented
	 * @return A board using standard chess setup.
	 */
	public Board chaturangaBoard() {
		Board b = new Board(8,8);
		
		for(int i = 0; i < 8; i++) {
			addOpposing(b, "padati", new Touple(i,1));					//add pawns
		}
		for(int i = 0; i <= 7; i += 7) {
			addOpposing(b,"ratha", new Touple(i,0));					//add rooks
		}
		for(int i = 1; i <= 6; i += 5) {
			addOpposing(b,"ashva", new Touple(i,0));				//add knights
		}
		for(int i = 2; i <= 5; i += 3) {
			addOpposing(b,"gaja", new Touple(i,0));				//add bishops
		}
		
		addSymmetrical(b,"mantri", new Touple(3,0));						//add queens
		addSymmetrical(b,"raja", new Touple(4,0));						//add kings
		
		return b;
	}
	
	/**
	 * Adds two opposing pieces on the board.
	 * @param b The board to add the piece to
	 * @param piece The name of the piece you wish to add
	 * @param lesserCoord The coordinate of the piece on the white (lesser numbered) side. The other will be derived automatically from the size of the board.
	 */
	/*private void addOpposing(Board b, String piece, Touple lesserCoord) {
		b.setPiece(spf.make("white", piece), lesserCoord);
		b.setPiece(spf.make("black", piece), new Touple(lesserCoord.getXNumeral(),b.getHeight()-lesserCoord.getYNumeral()-1));
	}*/
	
	private void addOpposing(Board b, String piece, Touple lesserCoord) {
		spf.startPiece(teams.getTeams()[0], piece, "standard");
		spf.addMoves(true);
		Piece firstPiece = spf.getPiece();
		spf.startPiece(teams.getTeams()[1], piece, "standard");
		spf.addMoves(true);
		Piece secondPiece = spf.getPiece();
		b.setPiece(firstPiece, lesserCoord);
		b.setPiece(secondPiece, new Touple(lesserCoord.getXNumeral(),b.getHeight()-lesserCoord.getYNumeral()-1));
	}
	
	/**
	 * Adds two pieces on the board, symmetrically.
	 * @param b The board to add the piece to
	 * @param piece The name of the piece you wish to add
	 * @param lesserCoord The coordinate of the piece on the white (lesser numbered) side. The other will be derived automatically from the size of the board.
	 */
	private void addSymmetrical(Board b, String piece, Touple lesserCoord) {
		spf.startPiece(teams.getTeams()[0], piece, "standard");
		spf.addMoves(true);
		Piece firstPiece = spf.getPiece();
		spf.startPiece(teams.getTeams()[1], piece, "standard");
		spf.addMoves(true);
		Piece secondPiece = spf.getPiece();
		b.setPiece(firstPiece, lesserCoord);
		b.setPiece(secondPiece, new Touple(b.getWidth()-lesserCoord.getXNumeral()-1,b.getHeight()-lesserCoord.getYNumeral()-1));
	}
}
