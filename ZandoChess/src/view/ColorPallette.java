package view;

import java.awt.Color;
import java.util.ArrayList;

public class ColorPallette {

	private Color edge = new Color(142,181,242);
	private Color text = Color.black;
	private Color icon = Color.red;
	private Color warning = Color.magenta;
	private Color clicked = Color.red;
	
	private ArrayList<TeamColors> teams = new ArrayList<TeamColors>();
	
	public ColorPallette() {
		teams.add(new TeamColors("Black"));
		teams.get(0).setSpaceColor(57,60,73);
		teams.get(0).setSelectedSpaceColor(97,102,124);
		teams.add(new TeamColors("White"));
		teams.get(1).setSpaceColor(255,251,219);
		teams.get(1).setSelectedSpaceColor(255, 248, 188);
	}
	
	private TeamColors getTeam(String team) {
		for(TeamColors t: teams) {
			if(t.getTeam().equals(team)) {
				return t;
			}
		}
		return null;
	}
	
	private Color getColor(String team, String field) {
		if(field.equals("SpaceColor")) {
			return getTeam(team).getSpaceColor();
		}
		if(field.equals("SelectedSpaceColor")) {
			return getTeam(team).getSelectedSpaceColor();
		}
		return null;
	}
	
	public Color getBoardColor(String attribute) {
		if(attribute.equals("Edge")) {
			return edge;
		}
		if(attribute.equals("Text")) {
			return text;
		}
		if(attribute.equals("Icon")) {
			return icon;
		}
		if(attribute.equals("Warning")) {
			return warning;
		}
		if(attribute.equals("Clicked")) {
			return clicked;
		}
		return null;
	}
	
	public Color square(String team){
		return getColor(team,"SpaceColor");
	}
	
	public Color selectedSquare(String team) {
		return getColor(team,"SelectedSpaceColor");
	}
	
	public Color edge() {
		return edge;
	}
	
	public Color text() {
		return text;
	}
	
	public Color icon() {
		return icon;
	}
	
	public Color warning() {
		return warning;
	}
	
	public Color clicked() {
		return clicked;
	}
}
