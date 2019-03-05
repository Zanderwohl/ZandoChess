package gameLogic;

import java.util.ArrayList;

import movement.Move;
import movement.MoveHistory;
import movement.Touple;
import piece.Piece;
import triggers.CaptureTrigger;
import triggers.SpaceTrigger;

/**
 * A model which controls behavior of the game and board, including piece behavior and teams.
 * @author Alexander
 *
 */
public class Model {

	private Board board;
	//private Piece selectedPiece;
	private Teams teams;
	
	ArrayList<CaptureTrigger> captureTriggers = new ArrayList<CaptureTrigger>();
	ArrayList<SpaceTrigger> spaceTriggers = new ArrayList<SpaceTrigger>();
	
	/**
	 * Initializes the Model to begin game setup.
	 * @param b The board to be used.
	 * @param t The set of teams to be used in this game.
	 */
	public void initialize(Board b, Teams t) {
		board = b;
		teams = t;
	}
	
	/**
	 * Lets other components access the board.
	 * @return The board in question.
	 */
	public Board getBoard() {
		return board;
	}
	
	public Teams getTeams() {
		return teams;
	}
	
	public boolean spaceEmpty(Touple t) {
		return board.spaceEmpty(t);
	}

	/*
	public boolean isMoveLegal(Touple from, Touple to) {
		boolean moveLegal = false;
		Touple[] legalSpaces = getLegalMoves(from);
		for(int i = 0; i < legalSpaces.length; i++) {
			if(legalSpaces[i].equals(to)) {
				moveLegal = true;
			}
		}
		return moveLegal;
	}*/
	
	public Move isMoveLegal(Touple from, Touple to) {
		Piece p = board.getPiece(from);
		Move confirmedMove = null;
		Move proposedMove = new Move(to.getXNumeral() - from.getXNumeral(), to.getYNumeral() - from.getYNumeral());
		ArrayList<Move> list = p.getMovesAndCaptures();
		for(int i = 0; i < list.size() ; i++) {
			if(list.get(i).equals(proposedMove)) {
				confirmedMove = list.get(i);
			}
		}
		return confirmedMove;
	}
	
	public Touple[] getLegalMoves(Touple t){
		Piece p = board.getPiece(t);
		ArrayList<Move> list = p.getMovesAndCaptures();
		Touple[] legalSpaces = new Touple[list.size()];
		for(int i = 0; i < legalSpaces.length; i++) {
			legalSpaces[i] = Touple.add(p.getPosition(), list.get(i));
		}
		
		for(int i = 0; i < legalSpaces.length; i++) {
			System.out.println("\t" + legalSpaces[i].getX() + legalSpaces[i].getY());
		}
		
		return legalSpaces;
	}

	public void movePiece(Touple from, Touple move) {
		Piece p = board.getPiece(from);
		Touple to = Touple.add(p.getPosition(), move);
		p.incrementMoves();
		System.out.println("Moving from " + from.getX() + from.getY() + " to " + to.getX() + to.getY());
		board.removePiece(from);
		if(move instanceof Move && ((Move) move).hasEffect()) {
			//System.out.println("Moved into effect branch.");
			MoveHistory effect = ((Move) move).getEffect();
			//System.out.println("\tMove effect is " + effect.getOldPosition().getX() + effect.getOldPosition().getY());
			//System.out.println("\tto " + effect.getNewPosition().getX() + effect.getNewPosition().getY());
			movePiece(effect.getOldPosition(), effect.getPositive());
		}
		if(!board.spaceEmpty(to)) {
			capture(p, to);
			//add to undo list
		}
		board.setPiece(p, to);
		moveListeners(p);
		//teams.incrementTurn();
	}

	public void capture(Piece attacker, Touple captureSpace) {
		Piece deadPiece = board.getPiece(captureSpace);
		board.removePiece(captureSpace);
		System.out.println("Captured " + deadPiece.getColor() + " " + deadPiece.getName() + " from " + captureSpace.getX() + captureSpace.getY() + "!\n");
		captureListeners(attacker, deadPiece);
	}
	
	private void captureListeners(Piece attacker, Piece victim) {
		//spaceListeners(deadPiece.getPosition());
		System.out.println("Running all capture triggers.");
		for(CaptureTrigger cl: captureTriggers) {
			cl.signal(attacker, victim);
		}
		
	}
	
	private void moveListeners(Piece p) {
		spaceListeners(p.getPosition());
		//System.out.println("Running all move triggers.");
		// TODO Auto-generated method stub
		
	}

	private void spaceListeners(Touple t) {
		//System.out.println("Running all space triggers.");
	}

	public void removePiece(Touple oldSpace) {
		board.removePiece(oldSpace);
		
	}

	public void setPiece(Piece selectedPiece, Touple t) {
		board.setPiece(selectedPiece, t);
		
	}

	public Piece getPiece(Touple t) {
		return board.getPiece(t);
	}

	/**
	 * The controller calls this method when it wants to tell the model that a piece is being selected.
	 * The model can reject that (wrong team, for example) or accept it.
	 * @param clickedSquare the coordinate selected.
	 */
	public boolean selectPiece(Touple clickedSquare) {
		//System.out.println("Piece color: " + board.getPiece(clickedSquare).getColor() + " (" + teams.getTurn() + "'s turn)");
		if(board.getPiece(clickedSquare).getColor().equals(teams.getTurn().teamName())) {
			//selectedPiece = board.getPiece(clickedSquare);
			return true;
		}
		return false;
	}
	
	public void deselectPiece() {
		//selectedPiece = null;
	}
	
	public void incrementTurn() {
		teams.incrementTurn();
		//if(teams.getTurn())
	}
	
	public void addCaptureTrigger(CaptureTrigger cl) {
		captureTriggers.add(cl);
	}
	
	public void addSpaceTrigger(SpaceTrigger sl) {
		spaceTriggers.add(sl);
	}
}

