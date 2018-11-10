package triggers;

import java.util.ArrayList;

import events.Event;
import events.WinEvent;
import piece.Piece;

public class CaptureTrigger extends Trigger {

	String[] attackers;
	String[] victims;
	
	ArrayList<Event> events = new ArrayList<Event>();
	
	/**
	 * CaptureListener will put an event on the event stack if certain Pieces capture certain other Pieces.
	 * @param attackerList A list of all attackers that this trigger MUST have. Passing null means any piece type may trigger.
	 * @param victimList A list of all victims that this trigger MUST have. Passing null means any piece type may trigger.
	 */
	public CaptureTrigger(String[] attackerList, String[] victimList) {
		attackers = attackerList;
		victims = victimList;
	}
	
	@Override
	public void signal() {
		
	}
	
	/**
	 * Signals this Capture
	 * @param attacker
	 * @param victim
	 */
	public void signal(Piece attacker, Piece victim) {
		if(isValidAttacker(attacker) && isValidVictim(victim)) {
			System.out.println("Valid attack event!");
			trigger(attacker, victim);
		}
	}
	
	private void trigger(Piece attacker, Piece victim) {
		for(Event e: events) {
			if(e instanceof WinEvent) {
				WinEvent we = (WinEvent) e;
				we.activate(attacker.getColor());
			} else {
				e.activate();
			}
			
		}
	}

	private boolean isValidAttacker(Piece attacker) {
		if(attackers == null) {
			return true;
		} else {
			for(String s: attackers) {
				if(s.equals(attacker.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isValidVictim(Piece victim) {
		if(victims == null) {
			return true;
		} else {
			for(String s: victims) {
				if(s.equals(victim.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void addEvent(Event e) {
		events.add(e);
	}
	
}
