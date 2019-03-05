package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class ControlBarFactory {

	ResourceBundle language;
	
	JPanel panel;
	int WIDTH = 0;
	
	JButton quitButton;
	JButton undoButton;
	JSlider scaleSlider;
	JLabel highlightLabel;
	
	public ControlBarFactory(ResourceBundle language) {
		panel = new JPanel(new GridBagLayout());
		this.language = language;
		setupQuitButton();
		setupUndoButton();
		setupScaleSlider();
		setupHighlightLabel();
		addElements();
		panel.setPreferredSize(new Dimension(10,30));
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public void setPixelWidth() {
		
	}
	
	private void setupQuitButton() {
		WIDTH++;
		quitButton = new JButton();
		quitButton.setText(language.getString("quitButton"));
	}
	
	public void addQuitListener(ActionListener a) {
		quitButton.addActionListener(a);
	}
	
	private void setupUndoButton() {
		WIDTH++;
		undoButton = new JButton();
		undoButton.setText(language.getString("undoButton"));
		//undoButton.setEnabled(false);
	}
	
	public void addUndoListener(ActionListener a) {
		undoButton.addActionListener(a);
	}
	
	private void setupScaleSlider() {
		WIDTH++;
		scaleSlider = new JSlider(30,70,50);
		scaleSlider.createStandardLabels(5, 30);
		scaleSlider.setEnabled(false);
	}
	
	public void addScaleListener(ChangeListener a) {
		scaleSlider.addChangeListener(a);
	}
	
	private void setupHighlightLabel() {
		WIDTH++;
		highlightLabel = new JLabel("");
		
	}
	
	public JLabel getHighlightLabel() {
		return highlightLabel;
	}
	
	/**
	 * Sets up the GUI elements of the top panel.
	 */
	private void addElements() {
		JComponent[] components = new JComponent[WIDTH];
		panel.setLayout(new GridLayout(1,WIDTH));
		
		components[0] = quitButton;
		components[1] = undoButton;
		components[2] = scaleSlider;
		components[3] = highlightLabel;
		
		GridBagConstraints c = new GridBagConstraints();
		//c.anchor = LINE_START;
		
		for(int i = 0; i < WIDTH; i++) {
			panel.add(components[i],c);
		}
	}
}
