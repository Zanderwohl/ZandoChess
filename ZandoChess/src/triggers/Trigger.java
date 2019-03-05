package triggers;

import events.Event;

public abstract class Trigger {

	//protected 
	
	public abstract void signal();
	
	public abstract void addEvent(Event e);
}
