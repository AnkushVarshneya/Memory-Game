/**
 * @(#)MemoryGameView.java
 * Assignment#3
 * @author Ankush Varshneya
 * @student# 100853074
 * @version 1.00 2012/2/24
 */

import javax.swing.*;
import java.awt.*;

public class MemoryGameView extends JPanel {
	private MemoryGame			model; // The model to which this view is attached.
	private MemoryGameDisplay	grid; // The Grid to which this view is attached.

	private JLabel				styleLabel, scoreLabel, timeLabel;
	private JList				styleList;
	private JTextField			scoreField, timeField;
	private JButton				startStopButton;

	private GridBagConstraints 	constraints;
	private GridBagConstraints 	gridConstraints;
	private GridBagLayout 		layout;

	// Public method to allow acess to grid.
	public MemoryGameDisplay	getGrid() { return grid;}

	// Public method to allow access to JComponents.
	public JLabel				getStyleLabel() { return styleLabel; }
	public JLabel				getScoreLabel() { return scoreLabel; }
	public JLabel				getTimeLabel() { return timeLabel; }
	public JList				getStyleList() { return styleList; }
	public JTextField			getScoreField() { return scoreField; }
	public JTextField			getTimeField() { return timeField; }
	public JButton				getStartStopButton() { return startStopButton; }

    public MemoryGameView(MemoryGame m) {
    	this.model = m; // Store the model for access later.
    	this.grid = new MemoryGameDisplay(this.model); // Store the grid for access later.

		constraints = new GridBagConstraints();
		gridConstraints = new GridBagConstraints();
		layout = new GridBagLayout(); // Choose gridbag layout.

		this.setLayout(layout);

		//Style
		this.styleLabel = new JLabel("Style:");
		this.constraints = this.makeConstraints(1, 0, 1, 1, 0, 0, new Insets(2, 2, 2, 4), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH);
		this.layout.setConstraints(this.styleLabel, this.constraints);
		this.add(this.styleLabel);

		String[] Options = {"Times - (2 min)", "Times - (10 min)", "No Time Limit"};
		this.styleList = new JList(Options);
		this.styleList.setSelectedIndex(0);
		this.constraints = this.makeConstraints(1, 1, 1, 1, 0, 1, new Insets(2, 2, 2, 4), GridBagConstraints.BOTH, GridBagConstraints.NORTH);
		this.layout.setConstraints(this.styleList, this.constraints);
		this.add(this.styleList);

		//Score
		this.scoreLabel = new JLabel("Score:");
		this.constraints = this.makeConstraints(1, 3, 1, 1, 0, 0, new Insets(2, 2, 2, 4), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH);
		this.layout.setConstraints(this.scoreLabel, this.constraints);
		this.add(this.scoreLabel);

		this.scoreField = new JTextField();
		this.scoreField.setEditable(false);
		this.constraints = this.makeConstraints(1, 4, 1, 1, 0, 0, new Insets(2, 2, 2, 4), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH);
		this.layout.setConstraints(this.scoreField, this.constraints);
		this.add(this.scoreField);

		//Time
		this.timeLabel = new JLabel("Time Remaining:");
		this.constraints = this.makeConstraints(1, 5, 1, 1, 0, 0, new Insets(2, 2, 2, 4), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH);
		this.layout.setConstraints(this.timeLabel, this.constraints);
		this.add(this.timeLabel);

		this.timeField = new JTextField();
		this.timeField.setEditable(false);
		this.constraints = this.makeConstraints(1, 6, 1, 1, 0, 0, new Insets(2, 2, 2, 4), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH);
		this.layout.setConstraints(this.timeField, this.constraints);
		this.add(this.timeField);

		//Start/Stop button
		this.startStopButton = new JButton();
		this.constraints = this.makeConstraints(1, 2, 1, 1, 0, 0, new Insets(2, 2, 2, 4), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH);
		this.layout.setConstraints(this.startStopButton, this.constraints);
		this.add(this.startStopButton);

		//Grid
		this.gridConstraints = this.makeConstraints(0, 0, 1, 7, 1, 1, new Insets(0, 0, 0, 0), GridBagConstraints.BOTH, GridBagConstraints.CENTER);
		this.layout.setConstraints(this.grid, this.gridConstraints);
		this.add(this.grid);

		// Update the view
		this.update();
	}

	// Used make constraints and to shorten code.
	private GridBagConstraints makeConstraints(int gX, int gY, int gW, int gH, int wX, int wY, Insets insets, int fill, int anchor) {
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = gX;
		c.gridy = gY;
		c.gridwidth = gW;
		c.gridheight = gH;
		c.weightx = wX;
		c.weighty = wY;
		c.insets = insets;
		c.fill = fill;
		c.anchor = anchor;

		return c;
	}

	// Updates the view based on the inputted model.
	public void update() {

		// If the button is not selected its labled "start" otherwize its lables stop.
		if(!this.startStopButton.isSelected()) this.startStopButton.setText("Start");
		else this.startStopButton.setText("Stop");
		// Update Score and Time testFields.
		this.scoreField.setText(this.model.getCorrectGuesses() + "/" + this.model.getGuessesMade());
		this.timeField.setText(this.model.getTimeRemaining());

	}

	// Makes a newGrid based on the model passed in.
	public void getNewCard(MemoryGame game) {
		// Remove the grid button, then the grid.
		this.grid.removeAll();
		this.remove(this.grid);
		// Update the model to the one passed in to this function.
		this.model = game;
		// Make a new grid, then used the gridConstraints to add a new grid.
		this.grid = new MemoryGameDisplay(this.model);
		this.layout.setConstraints(this.grid, this.gridConstraints);
		this.add(this.grid);
		// Update the UI to mach the new grid.
		this.grid.updateUI();
		this.updateUI();
	}
}