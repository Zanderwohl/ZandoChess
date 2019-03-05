package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import events.WinEvent;
import gameLogic.Board;
import gameLogic.BoardFactory;
import gameLogic.Model;
import gameLogic.Teams;
import movement.Move;
import movement.Touple;
import piece.Piece;
import team.Team;
import team.TeamUser;
import triggers.CaptureTrigger;
import triggers.SpaceTrigger;
import view.UserView;

/**
 * Controller begins the game, and alerts the Model of user activity.
 * @author Alexander
 *
 */
public class Controller implements ActionListener{
	
	/**
	 * The view; the object which creates and renders a window for the user to interact with.
	 */
	public UserView v;
	/**
	 * The model; the object which holds the game state and controls the game logic.
	 */
	public Model m;
	
	private MouseMovement mouseMovement;
	
	/**
	 * Sets up the controller with any valid model and view.
	 * @param v The view component this controller will use.
	 * @param m The model component this controller will use.
	 */
	public Controller(UserView v, Model m) {
		this.v = v;
		this.m = m;
		mouseMovement = new MouseMovement();
	}
	
	/**
	 * Starts with a predetermined board layout.
	 * "standard" is normal game.
	 * "chaturanga" is chaturanga game.
	 * "shuffle" is normal with randomly-placed back row.
	 * "random" is standard pieces randomly placed all over board.
	 * @param name The name of the game rules you wish to initiate.
	 */
	public void hardcodedStart(String name) {
		Teams t = hardcodedTeams();
		
		BoardFactory bf = new BoardFactory(t);
		Board b = bf.blankBoard(8,8);
		if(name.equals("standard")) {
			//Random r = new Random();
			
			b = bf.standardBoard(8,8);
		}
		if(name.equals("shuffle")) {
			b = bf.shuffleBoard();
		}
		if(name.equals("chaturanga")) {
			b = bf.chaturangaBoard();
		}
		if(name.equals("castle")) {
			b = bf.castleBoard(8, 8);
		}
		m.initialize(b,t);
		hardcodedWin();
		hardcodedPromotion();
		v.initialize(b,70);
		v.setSquareSize(50);
		v.createWindow();
		v.getFlatBoard().addMouseListener(mouseMovement);
		v.gameLoop();
	}
	
	private Teams hardcodedTeams() {
		Teams t = new Teams();
		Team black = new TeamUser("black");
		Team white = new TeamUser("white");
		t.addTeam(white);
		t.addTeam(black);
		t.addTurnOrder(white);
		t.addTurnOrder(black);
		return t;
	}
	
	private void hardcodedWin() {
		String[] king = {"king"};
		CaptureTrigger kingCapture = new CaptureTrigger(null,king);
		m.addCaptureTrigger(kingCapture);
		kingCapture.addEvent(new WinEvent());
	}
	
	private void hardcodedPromotion() {
		m.addSpaceTrigger(new SpaceTrigger(1));
		m.addSpaceTrigger(new SpaceTrigger(8));
	}
	
	/**
	 * Class to listen to user interactions.
	 * @author Alexander
	 *
	 */
	private class MouseMovement extends MouseInputAdapter {

		Piece selectedPiece;
		
		/**
		 * Blank constructor needs no arguments.
		 */
		public MouseMovement() {
		}
		
		/**
		 * Doesn't work for some reason? I don't know.
		 * 
		 */
	    public void mouseMoved(MouseEvent e) {
	    	
	    }

	    /**
	     * Called automatically every time a piece is clicked.
	     * Will calculate all possible moves and captures only once.
	     * @param clickedSquare The place on the board which has been clicked.
	     */
	    public void pieceClicked(Touple clickedSquare) {
	    	selectedPiece = m.getPiece(clickedSquare);
			selectedPiece.generateMoves(m.getBoard());
			selectedPiece.generateCaptures(m.getBoard());
	    }
	    
	    /**
	     * When the mouse is released, the flatBoard is notified that it has been clicked and where.
	     */
	    public void mouseReleased(MouseEvent e) {
	    	if(v.getClickedSquare() != null && m.getTeams().currentTurnLocal()) {
		    	Touple clickedSquare = v.getClickedSquare();
		    	if(selectedPiece != null) {	//is a square already selected?
		    		Move proposedMove = m.isMoveLegal(selectedPiece.getPosition(), clickedSquare);
		    		//System.out.println("null move: " + (proposedMove == null));
		    		if(proposedMove != null) {
		    			m.movePiece(selectedPiece.getPosition(),proposedMove);
		    			m.incrementTurn();
		    			m.deselectPiece();
		    			v.deselectSquare();
		    			selectedPiece = null;
		    		}
		    		else {
		    			if(m.spaceEmpty(clickedSquare)) {
		    				
		    			}
		    			else {
		    				if(!m.selectPiece(clickedSquare)) {//and the selection has been rejected
			    				System.out.println("This piece cannot be selected (2).");
			    				v.deselectSquare();
			    			} else { 							//and the selection has been accepted.
			    				pieceClicked(clickedSquare);
			    			}
		    			}
		    		}
		    	}
		    	else {						//if a square isn't already selected
		    		if(m.spaceEmpty(clickedSquare)) {//and the space has nothing in it
		    			//Do nothing
		    		}
		    		else {							//and the space has something in it
		    			if(!m.selectPiece(clickedSquare)) {//and the selection has been rejected
		    				System.out.println("This piece cannot be selected (1).");
		    				v.deselectSquare();
		    			} else { 							//and the selection has been accepted.
		    				pieceClicked(clickedSquare);
		    			}
		    		}
		    	}
	    	}
	    }
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
