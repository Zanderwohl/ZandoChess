package events;

/**
 * An event is anything which a trigger can cause; a game win, draw, promotion, or any "effect" a player's turn can have.
 * @author Alexander
 *
 */
public abstract class Event {
	
	protected String eventMessage;

	/**
	 * @return The message that this event will send.
	 */
	public String eventMessage() {
		return eventMessage;
	}
	
	/**
	 * Method is called when its effects should happen.
	 */
	public abstract void activate();
}
