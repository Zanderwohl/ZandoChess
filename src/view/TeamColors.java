package view;

import java.awt.Color;

public class TeamColors {
	
	private String teamName;
	private Color space;
	private Color spaceSelected;

	public TeamColors(String name) {
		teamName = name;
	}
	
	public String getTeam() {
		return teamName;
	}
	
	public void setSpaceColor(int r, int g, int b) {
		space = new Color(r,g,b);
	}
	
	public Color getSpaceColor() {
		return space;
	}
	
	public void setSelectedSpaceColor(int r, int g, int b) {
		spaceSelected = new Color(r,g,b);
	}
	
	public Color getSelectedSpaceColor() {
		return spaceSelected;
	}
	
}
