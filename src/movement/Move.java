package movement;

/**
 * A move is a set of relative coordinates, to which a piece may move.
 * @author Alexander
 *
 */
public class Move extends Touple {

	/**
	 * These are inclusive.
	 * Range of moves is [minMove,maxMove] in other words.
	 */
	private int minMove, maxMove;
	private String name;
	private boolean active = true;
	private boolean chains = false;
	/**
	 * An effect makes another piece do something if this move is used.
	 */
	private MoveHistory effect = null;
	
	/**
	 * A move with an x and y coordinate, relative to the piece which wants to move.
	 * @param x How many squares in the x-direction to move.
	 * @param y How many squares in the y-direction to move.
	 */
	public Move(int x, int y) {
		super(x, y);
		minMove = 0;
		maxMove = Integer.MAX_VALUE;
	}
	
	/**
	 * A move with an x and y coordinate, relative to the piece which wants to move.
	 * @param x How many squares in the x-direction to move.
	 * @param y How many squares in the y-direction to move.
	 * @param effect Another move (usually on another piece) that this move will cause if it is activated.
	 */
	public Move(int x, int y, MoveHistory effect) {
		super(x ,y);
		minMove = 0;
		maxMove = Integer.MAX_VALUE;
		this.effect = effect;
	}
	
	/**
	 * A move with an x and y coordinate, relative to the piece which wants to move.
	 * @param x How many squares in the x-direction to move.
	 * @param y How many squares in the y-direction to move.
	 * @param maxMove The maximum times this piece can move before this move is no longer allowed.
	 */
	public Move(int x, int y, int maxMove) {
		super(x,y);
		this.maxMove = maxMove;
		minMove = 0;
	}
	
	/**
	 * A move with an x and y coordinate, relative to the piece which wants to move.
	 * @param x How many squares in the x-direction to move.
	 * @param y How many squares in the y-direction to move.
	 * @param maxMove The maximum times this piece can move before this move is no longer allowed.
	 * @param minMove The minimum times this piece can move before this move is first allowed.
	 */
	public Move(int x, int y, int maxMove, int minMove) {
		super(x,y);
		this.minMove = minMove;
		this.maxMove = maxMove;
	}
	
	/**
	 * A move with an x and y coordinate, relative to the piece which wants to move.
	 * @param x How many squares in the x-direction to move.
	 * @param y How many squares in the y-direction to move.
	 * @param maxMove The maximum times this piece can move before this move is no longer allowed.
	 * @param minMove The minimum times this piece can move before this move is first allowed.
	 * @param chains Whether this move can allow its piece to move again on the same turn.
	 */
	public Move(int x, int y, int maxMove, int minMove, boolean chains) {
		super(x,y);
		this.minMove = minMove;
		this.maxMove = maxMove;
		this.chains = chains;
	}

	/**
	 * Get the name of the piece as a string.
	 * @return The unlocalized name of this piece.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Changes the name of this piece.
	 * @param newName The string to change the unlocalized name to.
	 */
	public void setName(String newName) {
		name = newName;
	}
	
	/**
	 * Returns if this is an active move based on how many times the piece has moved and its "active" state.
	 * @param moveNumber The move # that the parent piece is on.
	 * @return Whether or not this move is available to be used.
	 */
	public boolean isActive(int moveNumber) {
		return (active && (moveNumber >= minMove && moveNumber <= maxMove));
	}
	
	/**
	 * Sets the "active" state of this piece to true, making is conditionally able to be used.
	 */
	public void activate() {
		active = true;
	}
	
	/**
	 * Sets the "active" state of this piece as false, making it not able to be used.
	 */
	public void deactivate() {
		active = false;
	}
	
	/**
	 * Returns if this move can allow its piece to immediately take another move.
	 * @return True if this move can be chained, false if it cannot.
	 */
	public boolean chains() {
		return chains;
	}
	
	/**
	 * Determines whether or not this move has a secondary effect.
	 * @return true if it has an effect, false if it does not.
	 */
	public boolean hasEffect() {
		return !(effect == null);
	}
	
	/**
	 * Gives the secondary effect as a MoveHistory.
	 * @return The effect to be used to effect another piece.
	 */
	public MoveHistory getEffect() {
		return effect;
	}
}
