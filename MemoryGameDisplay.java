/**
 * @(#)MemoryGameDisplay.java
 * Assignment#3
 * @author Ankush Varshneya
 * @student# 100853074
 * @version 1.00 2012/2/24
 */

import javax.swing.*;
import java.awt.*;

public class MemoryGameDisplay extends JPanel {
	private MemoryGame model; // The model to which this view is attached.
	private JButton[][]	cardButtons; // CardButtons array.

	// Public method to allow access to JComponents.
	public JButton[][]	getCardButtons() { return this.cardButtons; }
	public MemoryGame	getModel() { return this.model; }

    public MemoryGameDisplay(MemoryGame m) {
    	this.model = m; // Store the model for access later.

    	// Choose grid layout.
    	this.setLayout(new GridLayout(this.model.DIMENSION, this.model.DIMENSION, 2, 2));

		// Initilize arrays of JButtons.
    	this.cardButtons = new JButton[model.DIMENSION][model.DIMENSION];

    	// Sets buttons.
    	for(int row=0; row<this.model.DIMENSION; row++) {
    		for(int col=0; col<this.model.DIMENSION; col++) {
    			// Unselected icon is blank.
    			this.cardButtons[row][col] = new JButton((new ImageIcon("blank.jpg")));
    			// Selected icon is picture from a file name in the model.
    			this.cardButtons[row][col].setSelectedIcon(new ImageIcon("icons" + java.io.File.separator + this.model.getCards()[row][col]));
				// Eliminate border around button.
				//this.cardButtons[row][col].setMargin(new Insets(-30,-30,0,0));
				// Add the button to the panel.
				this.add(cardButtons[row][col]);
				// Set the button unselected.
				this.cardButtons[row][col].setSelected(false);
				// Set all buttons enabled.
				this.cardButtons[row][col].setEnabled(true);
    		}
    	}
    }

    public static void main(String[] args) {
    	MemoryGame aModel = new MemoryGame();
		MemoryGameDisplay aView = new MemoryGameDisplay(aModel);

		JFrame frame = new JFrame("The Memory Game Grid Tester");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(352, 371); // Manually computed sizes.

		// Get All The componets and select all of them so we can show the cards.
		Component c[] = aView.getComponents();
		for(int i=0; i<c.length; i++) ((JButton)c[i]).setSelected(true);

		frame.getContentPane().add(aView);
		frame.setVisible(true);
	}
}