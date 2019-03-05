package movement;

/**
 * Records the old position and new position of a piece.
 * @author Alexander
 *
 */
public class MoveHistory {

	Touple oldPosition, newPosition;
	
	/**
	 * Records a move from one place to another.
	 * @param oldPosition Position the piece began the turn in.
	 * @param newPosition Position the piece ended the turn in.
	 */
	public MoveHistory(Touple oldPosition, Touple newPosition) {
		this.oldPosition = oldPosition;
		this.newPosition = newPosition;
	}
	
	/**
	 * Gets the Touple it would take to move from the old to the new position, relatively.
	 * @return The relative Touple move.
	 */
	public Touple getPositive() {
		return new Touple(newPosition.getXNumeral() - oldPosition.getXNumeral(),newPosition.getYNumeral() - oldPosition.getYNumeral());
	}
	
	/**
	 * Gets the Touple it would take to move form the new to the old position, relatively.
	 * @return The relative Touple move.
	 */
	public Touple getNegative() {
		return new Touple(0 - (newPosition.getXNumeral() - oldPosition.getXNumeral()),0 - (newPosition.getYNumeral() - oldPosition.getYNumeral()));
	}
	
	/**
	 * Accessor for the old position.
	 * @return The old position as a Touple.
	 */
	public Touple getOldPosition() {
		return oldPosition;
	}
	
	/**
	 * Accessor for the new position.
	 * @return The new position as a Touple.
	 */
	public Touple getNewPosition() {
		return newPosition;
	}
	
}
