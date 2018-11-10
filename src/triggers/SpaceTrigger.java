package triggers;

import java.util.ArrayList;

import events.Event;
import movement.Touple;
import tools.Base;

/**
 * Trigger which monitors movement onto a space, rank, or file.
 * @author Alexander
 *
 */
public class SpaceTrigger extends Trigger{
	
	ArrayList<Event> events = new ArrayList<Event>();
	
	private static final int RANK = 1; //Row
	private static final int FILE = 2; //Column
	private static final int SPACE = 3;
	
	private int rank;
	private int file;
	
	private int type = 0; //Whether this checks a rank, a file, or both
	
	/**
	 * Constructor for just one specific space.
	 * @param t The space to be checked.
	 */
	public SpaceTrigger(Touple t) {
		type = SPACE;
		rank = t.getYNumeral();
		file = t.getXNumeral();
	}
	
	/**
	 * Constructor used for a specific rank.
	 * @param rank The rank to be checked.
	 */
	public SpaceTrigger(int rank) {
		type = RANK;
		this.rank = rank - 1;
		file = 0;
	}
	
	/**
	 * Constructor used for a specific file.
	 * @param file The file to be checked.
	 */
	public SpaceTrigger(String file) {
		type = FILE;
		this.file = Base.convertFromLetters(file);
		rank = 0;
	}
	
	public void signal() {
		System.err.println("Please use correct signal() call for SpaceTrigger: signal(Touple t)");
	}
	
	/**
	 * Signal that this event needs to be checked.
	 * @param t The space that caused this.
	 */
	public void signal(Touple t) {
		if(type == SPACE && t.getXNumeral() == rank && t.getYNumeral() == file) {
			activate(t);
		}
		if(type == RANK && rank == t.getXNumeral()) {
			activate(t);
		}
		if(type == FILE && file == t.getYNumeral()) {
			activate(t);
		}
	}
	
	/**
	 * If the signal results in an activation.
	 * @param t The space to be acted upon.
	 */
	private void activate(Touple t){
		for(Event e: events) {
			e.activate();
		}
	}

	@Override
	public void addEvent(Event e) {
		events.add(e);
	}
}
