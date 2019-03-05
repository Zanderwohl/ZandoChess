package events;

import javax.swing.JOptionPane;
import view.UserView;

/**
 * Event for when any team wins the game.
 * @author Alexander
 *
 */
public class WinEvent extends Event{
	
	/**
	 * Activate 
	 */
	public void activate() {
		System.err.println("WinEvent given incorrect number of args in activate(String winningTeam)!");
		JOptionPane.showMessageDialog(null, UserView.getText("ambiguousWin"));
	}
	
	/**
	 * Activates the win, with a winning team name provided.
	 * @param winningTeam The team which won the game.
	 */
	public void activate(String winningTeam) {
		String winner = winningTeam.substring(0,1).toUpperCase() + winningTeam.substring(1);
		String winMessage = UserView.getText("win").replace("WINNING_TEAM",winner);
		JOptionPane.showMessageDialog(null, winMessage);
	}

}
