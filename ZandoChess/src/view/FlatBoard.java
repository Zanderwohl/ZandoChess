/**
 * @author Alexander
 */

package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComponent;

import gameLogic.Board;
import movement.Move;
import movement.Touple;
import piece.Piece;
import tools.Base;

/**
 * Please don't touch this class. It works, and isn't that good enough?
 * @author Alexander
 *
 */
public class FlatBoard extends JComponent implements ActionListener{

	/**
	 * I don't know why this is here, but Eclipse wants it.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The GUI can only have one board. It is bound to that board, with its width and height.
	 */
	private Board board;
	
	/**
	 * The square currently moused over.
	 */
	private Touple highlightedSquare;
	
	private Touple clickedSquare;
	
	private int squareSize;
	private int width, height;
	
	private boolean active = true;
	
	protected ColorPallette color = new ColorPallette();

	/**
	 * Construct a new FlatBoard with a given board and a default square size.
	 * @param b The board to be displayed by this FlatBoard.
	 */
	public FlatBoard(Board b) {
		board = b;
		setSquareSize(50);
		setBackground(color.edge());
	}
	
	/**
	 * Construct a new FlatBoard with a given board and square size.
	 * @param b Board to be displayed by this FlatBoard.
	 * @param squareSize The square size in pixels.
	 */
	public FlatBoard(Board b, int squareSize) {
		board = b;
		setSquareSize(squareSize);
		setBackground(color.edge());
	}
	
	/**
	 * Activates user interaction.
	 */
	public void activate() {
		active = true;
	}
	
	/**
	 * Deactivates user interaction.
	 */
	public void deactivate() {
		active = false;
	}
	
	/**
	 * Set if this board is active (able to be interacted with by the user.)
	 * @param active true if active, false if deactive.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * Allows access to the board if absolutely needed.
	 * @return The board.
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Sets the size of each square.
	 * @param squareSize The size of each square in pixels.
	 */
	protected void setSquareSize(int squareSize) {
		this.squareSize = squareSize;
		height = squareSize * (board.getHeight() + 2);
		width = squareSize * (board.getWidth() + 2);
		//System.out.println("W: "+ width + " h: " + height);
		setPreferredSize(new Dimension(width, height));
	}
	
	/**
	 * Gets the current size of each square in pixels.
	 * @return The size of each square in pixels.
	 */
	public int getSquareSize() {
		return squareSize;
	}
	
	/**
	 * Returns the height of the board + padding in pixels.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Returns the width of the board + padding in pixels.
	 */
	public int getWidth() {
		return width;
	}
	
	private int getX(int square) {
		return squareSize * (square + 1);
	}
	
	/**
	 * Returns the y-coordinate of the n-1 square you feed into it. Please don't ask.
	 * @param square The n-1 square you want to know the upper-left corner pixel of.
	 * @return The y-coordinate of the pixel of the upper-left corner of the square.
	 */
	private int getY(int square) {
		return height - ((square + 1) * squareSize);
	}
	
	/**
	 * Get the number of a square based on the y-coordinate.
	 * @param y The y-coordinate on the jcomponent.
	 * @return The number of that square.
	 */
	private int getSquareY(int y) {
		return (board.getHeight() + 1) - (y / squareSize);
	}
	
	/**
	 * Paints the background which will serve as a border for the board.
	 * @param g The graphics component.
	 */
	private void paintBorders(Graphics g) {
		g.setColor(color.edge());
		g.fillRect(0, 0, width, height);
	}
	
	/**
	 * Sets which square has been clicked by the mouse.
	 * @param x The file.
	 * @param y The rank.
	 */
	protected void setClickedSquare(int x, int y) {
		if(x > squareSize && x < width - squareSize && y > squareSize && y < height - squareSize) {
			String xSquare = Base.convertToLetters(((x / squareSize) - 1));
			String ySquare = "" + getSquareY(y);
			clickedSquare = new Touple(xSquare,ySquare);
		}
		else {
			clickedSquare = null;
		}
	}
	
	/**
	 * Gets which square has been clicked by the mouse.
	 * @return The square highlighted, as a Touple.
	 */
	public Touple getClickedSquare() {
		return clickedSquare;
	}
	
	/**
	 * Sets which square is hovered over by the mouse.
	 * @param x The file.
	 * @param y The rank.
	 */
	protected void setHighlightedSquare(int x, int y) {
		if(x > squareSize && x < width - squareSize && y > squareSize && y < height - squareSize) {
			String xSquare = Base.convertToLetters(((x / squareSize) - 1));
			String ySquare = "" + getSquareY(y);
			highlightedSquare = new Touple(xSquare,ySquare);
		}
		else {
			highlightedSquare = null;
		}
	}
	
	/**
	 * Gets which square is hovered over by the mouse.
	 * @return The square highlighted, as a Touple.
	 */
	public Touple getHighlightedSquare() {
		return highlightedSquare;
	}
	
	/**
	 * This function tells the FlatBoard component that it has been clicked, and selects the square that has been clicked.
	 * @param x The file.
	 * @param y The rank.
	 */
	protected void registerClick(int x, int y) {
		setClickedSquare(x,y);
	}
	
	/**
	 * Paints the squares onto the board. Correctly chooses a dark or light square.
	 * @param g The graphics component.
	 */
	private void paintGrid(Graphics g) {
		for(int i = 0; i < board.getWidth(); i++) {
			for(int j = 0; j < board.getHeight(); j++) {
				g.setColor(color.square(Board.getSpaceColor(i,j)));
				g.fillRect(getX(i), getY(j+1), squareSize, squareSize);
			}
		}
	}
	
	/**
	 * Paints the coordinates of rank and file across the sides of the board.
	 * @param g
	 */
	private void paintCoords(Graphics g) {
		g.setColor(color.text());
		for(int i = 0; i < board.getWidth(); i++) {
			g.drawString(Base.convertToLetters((i)),(squareSize / 3) + getX(i), getY(board.getHeight())  - (squareSize / 5));
			g.drawString(Base.convertToLetters((i)),(squareSize / 3) + getX(i), getY(-1)  - (squareSize / 2));
		}
		for(int i = 0; i < board.getHeight(); i++) {
			g.drawString("" + (i + 1), squareSize  - (squareSize / 2), (squareSize / 2) + getY(i+1));
			g.drawString("" + (i + 1), getX(board.getWidth())  + (squareSize / 4), (squareSize / 2) + getY(i+1));
		}
		
	}
	
	/**
	 * Systematically paints all pieces that exist on this board.
	 * @param g The graphics component.
	 */
	private void paintPieces(Graphics g){
		for(int i = 0; i < board.getWidth(); i++) {
			for(int j = 0; j < board.getHeight(); j++) {
				paintPiece(g, i, j);
			}
		}
	}
	
	/**
	 * Paints the piece on the board that sits at the coordinate specified.
	 * @param g The graphics component.
	 * @param i The file of the piece.
	 * @param j The rank of the piece.
	 */
	private void paintPiece(Graphics g, int i, int j) {
		if(board.getPiece(new Touple(i,j)) != null)
		{
			if(board.getPiece(new Touple(i,j)).getImage() != null) {
				imagePaint(g, i, j);
			}else {
				debugPaint(g, i, j);
			}
		}
	}
	
	/**
	 * Paints an image graphic for the piece, so that it is visible.
	 * @param g The graphics component.
	 * @param i The file of the piece.
	 * @param j The rank of the piece.
	 */
	private void imagePaint(Graphics g, int i, int j) {
		g.drawImage(board.getPiece(new Touple(i,j)).getImage(), getX(i), getY(j+1), squareSize, squareSize, null);
	}
	
	/**
	 * Paints a debug graphic for the piece, so that it is visible, even if the piece has no png image for its icon.
	 * @param g The graphics component
	 * @param i The file of the piece.
	 * @param j The rank of the piece.
	 */
	private void debugPaint(Graphics g, int i, int j) {
		g.setColor(color.warning());
		g.drawString(board.getPiece(new Touple(i,j)).getSymbol(), (squareSize / 8) + getX(i), (squareSize / 4) + getY(j + 1));
		g.drawString(board.getPiece(new Touple(i,j)).getName(), (squareSize / 8) + getX(i), (2 * squareSize / 4) + getY(j + 1));
		g.drawString(board.getPiece(new Touple(i,j)).getColor(), (squareSize / 8) + getX(i), (3 * squareSize / 4) + getY(j + 1));
		drawSquare(g, i, j);
	}
	
	/**
	 * Paints the square that's hovered over a different color, to indicate the mouse is over this square.
	 * @param g The graphics component.
	 */
	private void paintHighlightedSquare(Graphics g) {
		if(highlightedSquare != null) {
			g.setColor(color.selectedSquare(Board.getSpaceColor(highlightedSquare)));
			g.fillRect(getX(highlightedSquare.getXNumeral()), getY(highlightedSquare.getYNumeral()+1), squareSize, squareSize);
		}
		
	}
	
	/**
	 * Paints the square that has been clicked on with a red border.
	 * @param g The graphics component.
	 */
	private void paintClickedSquare(Graphics g) {
		if(clickedSquare != null) {
			g.setColor(color.selectedSquare(Board.getSpaceColor(clickedSquare)));
			
			int i = clickedSquare.getXNumeral();
			int j = clickedSquare.getYNumeral();
			
			fillSquare(g, i, j);
			g.setColor(color.clicked());
			drawSquare(g, i, j);
			
		}
	}
	
	/**
	 * Draws an outline of a square over the space specified by i and j.
	 * @param g The graphics component.
	 * @param i The number of the space.
	 * @param j The letter of the space.
	 */
	private void drawSquare(Graphics g, int i, int j) {
		j++;
		g.drawLine(getX(i), getY(j), getX(i + 1), getY(j));
		g.drawLine(getX(i), getY(j - 1), getX(i + 1), getY(j - 1));
		g.drawLine(getX(i), getY(j), getX(i), getY(j - 1));
		g.drawLine(getX(i + 1), getY(j), getX(i + 1), getY(j - 1));
	}
	
	/**
	 * Draws a filled square over the space specified by i and j.
	 * @param g The graphics component.
	 * @param i The number of the space.
	 * @param j The letter of the space.
	 */
	private void fillSquare(Graphics g, int i, int j) {
		j++;
		g.fillRect(getX(i), getY(j), squareSize, squareSize);
	}
	/*
	private void paintMoveSet(Graphics g, String movesOrCaptures, Color moveColor) {
		if(clickedSquare != null && board.getPiece(clickedSquare) != null) {
			g.setColor(moveColor);
			Piece p = board.getPiece(clickedSquare);
			ArrayList<Move> moves = getMovesOrCaptures(p, movesOrCaptures);
			if(!moves.isEmpty()) {
				for(Move m: moves) {
					Touple sum = Touple.add(p.getPosition(), m);
					paintMoveOrCapture(g, sum, movesOrCaptures);
					
				}
			}
		}
	}
	
	private void paintMoveOrCapture(Graphics g, Touple t, String moveOrCapture) {
		if(moveOrCapture.equals("Moves")) {
			paintMove(g, t);
		}else {
			paintCapture(g, t);
		}
	}
	
	private ArrayList<Move> getMovesOrCaptures(Piece p, String movesOrCaptures){
		if(movesOrCaptures.equals("Moves")) {
			return p.getMoves();
		}else {
			return p.getCaptures();
		}
	}
	
	private void paintMove(Graphics g, Touple move) {
		if(board.isValidSquare(move)) {
			drawHighlightedSquare(g,move.getXNumeral(),move.getYNumeral());
			g.setColor(color.edge());
			drawSquare(g,move.getXNumeral(),move.getYNumeral());
		}
	}
	
	private void paintCapture(Graphics g, Touple move) {
		g.setColor(color.clicked());
		fillSquare(g, move.getXNumeral(), move.getYNumeral());
		drawSquare(g,move.getXNumeral(),move.getYNumeral());
	}
	*/
	
	private void paintMoves(Graphics g) {
		if(clickedSquare != null && board.getPiece(clickedSquare) != null) {
			Piece p = board.getPiece(clickedSquare);
			ArrayList<Move> moves = p.getMoves();
			if(!moves.isEmpty()) {
				for(Move m: moves) {
					Touple sum = Touple.add(p.getPosition(), m);
					
					
					if(board.isValidSquare(sum)) {
						drawHighlightedSquare(g,sum.getXNumeral(),sum.getYNumeral());
						g.setColor(color.edge());
						drawSquare(g,sum.getXNumeral(),sum.getYNumeral());
					}
					
					
				}
			}
		}
	}
	
	private void paintCaptures(Graphics g) {
		if(clickedSquare != null && board.getPiece(clickedSquare) != null) {
			Piece p = board.getPiece(clickedSquare);
			ArrayList<Move> moves = p.getCaptures();
			if(!moves.isEmpty()) {
				for(Move m: moves) {
					Touple sum = Touple.add(p.getPosition(), m);
					
					
					if(board.isValidSquare(sum) && !board.spaceEmpty(sum) && board.getPiece(sum).getColor() != p.getColor()) {
						g.setColor(color.clicked());
						fillSquare(g, sum.getXNumeral(), sum.getYNumeral());
						drawSquare(g,sum.getXNumeral(),sum.getYNumeral());
					}
					
					
				}
			}
		}
	}
	
	/**
	 * Draws a square with the selected colors.
	 * @param g The graphics component.
	 * @param i The file of the space.
	 * @param j The rank of the space.
	 */
	private void drawHighlightedSquare(Graphics g, int i, int j){
			g.setColor(color.selectedSquare(Board.getSpaceColor(i, j)));
			fillSquare(g, i, j);
	}
	
	/**
	 * Overloads the default paintComponent. Systematically paints the board, pieces, and user interactions.
	 * @param g The graphics component.
	 */
	protected void paintComponent(Graphics g) {
		paintBorders(g);	
		paintGrid(g);
		paintCoords(g);
		
		//paintMoveSet(g, "Moves", color.edge());
		//paintMoveSet(g, "Captures", color.clicked());
		
		if(active) {
			paintMoves(g);
			paintCaptures(g);
			paintHighlightedSquare(g);
			paintClickedSquare(g);
		}
		paintPieces(g);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}
