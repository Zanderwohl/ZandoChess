package gameLogic;

import team.Team;
import java.util.ArrayList;

/**
 * The list of all teams in this game, as well as infromation about their turn order.
 * @author Alexander
 *
 */
public class Teams {
	
	private ArrayList<Team> teamRoster;
	private ArrayList<Team> turnOrder;
	private int turn = 0;
	
	/**
	 * Initialized with no teams.
	 */
	public Teams() {
		teamRoster = new ArrayList<Team>();
		turnOrder = new ArrayList<Team>();
	}
	
	/**
	 * Adds a team to the roster, named by a String. Does not add to turn rotation.
	 * @param name The name of the team.
	 */
	public void addTeam(Team team) {
		teamRoster.add(team);
	}
	
	/**
	 * Gives a list of all the team names, in no particular order.
	 * @return The list of teams.
	 */
	public Team[] getTeams() {
		Team[] teamList = new Team[teamRoster.size()];
		for(int i = 0; i < teamRoster.size(); i++) {
			teamList[i] = teamRoster.get(i);
		}
		return teamList;
	}
	
	/**
	 * Call this to add a turn in the rotation for an existing team.
	 * @param team
	 */
	public void addTurnOrder(Team team) {
		boolean teamInList = false;
		for(Team i: teamRoster) {
			if(team == i) {
				teamInList = true;
			}
		}
		if(teamInList) {
			turnOrder.add(team);
		}
	}
	
	/**
	 * Gets the name of the team whose turn it is.
	 * @return The name of the team which has the current turn.
	 */
	public Team getTurn() {
		return turnOrder.get(turn);
	}
	
	/**
	 * Advances to the next turn on the turn order list.
	 */
	public void incrementTurn() {
		turn++;
		if(turn >= turnOrder.size()) {
			turn = 0;
		}
	}
	
	/**
	 * Determines if the current team's control is the user or something else.
	 * @return true if it's the current user, false if it's a CPU or non-local player.
	 */
	public boolean currentTurnLocal() {
		return true;
	}
}
