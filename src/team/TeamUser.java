package team;

/**
 * A team controlled by the user UI window.
 * @author Alexander
 *
 */
public class TeamUser extends Team{

	private String name;
	
	public TeamUser(String name) {
		this.name = name;
	}
	
	@Override
	public String teamName() {
		return name;
	}

	@Override
	public boolean isLocalTeam() {
		return true;
	}

}
