/**
 * @(#)MemoryGame.java
 * Assignment#3
 * @author Ankush Varshneya
 * @student# 100853074
 * @version 1.00 2012/2/23
 */

import javax.swing.*;

public class MemoryGame {
	public final int DIMENSION = 10;// Dimentions of the 2D cards array.

	private int			guessesMade;
	private int			correctGuesses;
	private int			remainingMatches;
	private String[][]	cards;
	private String		timeRemaining;

	// Public methods to allow access to view private variable.
	public int			getGuessesMade() { return this.guessesMade; }
	public int			getCorrectGuesses() { return this.correctGuesses; }
	public int			getRemainingMatches() { return this.remainingMatches; }
	public String[][]	getCards() { return this.cards; }
	public String		getTimeRemaining() { return this.timeRemaining; }

	// Public method to set time remaining.
	public void			setTimeRemaining(String t) {this.timeRemaining = t;	}

    public MemoryGame() {
		this.guessesMade = 0;
		this.correctGuesses = 0;
		this.remainingMatches = 50;
		this.timeRemaining = "";

		String[] fileNames = new java.io.File("icons").list(); // Gets file names.

		// Doubles the file names.
		String[] temp = new String[fileNames.length*2];
		for(int i=0; i<fileNames.length; i++) {
			temp[i*2] = fileNames[i];
			temp[(i*2)+1] = fileNames[i];
		}
		fileNames = temp;

		// Applies the cards in random order.
		this.cards = new String[DIMENSION][DIMENSION];
		int counter = 0;
		for(int row = 0; row<this.DIMENSION; row++) {
			for(int col = 0; col<this.DIMENSION; col++) {
				int index = (int)(Math.random()*(100-counter));// Renerates random index.
				counter++;
				this.cards[row][col] = fileNames[index];
				// Remove the value at index and move every thing back.
				for(int k = index; k<(fileNames.length-1); k++) fileNames[k] = fileNames[k+1];
				// Make the last value null as we don't need it anymore.
				fileNames[fileNames.length-1]=null;
			}
		}
    }

	// Returns game is over, if there are no more matches to be made or it the timer reashes 0:00.
	public boolean isOver() { return((this.remainingMatches==0)||(this.timeRemaining.equals("0:00"))); }

	// Checks to see it the cards match and then adjusts the score.
	public void flipWith(JButton a, JButton b) {
		String aTitle, bTitle;
		aTitle = a.getSelectedIcon()+"";
		bTitle = b.getSelectedIcon()+"";

		// If the buttons match disable them
		if(aTitle.equals(bTitle)){
			a.setEnabled(false);
			b.setEnabled(false);
			this.correctGuesses++;
			this.remainingMatches--;
		}
		// If the buttons dont match unselect the buttons.
		else {
			a.setSelected(false);
			b.setSelected(false);
			a.setEnabled(true);
			b.setEnabled(true);
		}
		this.guessesMade++;
	}

	// Displays the awsners (for testing purposes).
	public void DisplayAwnsers() {
		for(int row = 0; row<this.DIMENSION; row++) {
			for(int col = 0; col<this.DIMENSION; col++) System.out.print(cards[row][col] + "\t");
			System.out.println("");
		}
	}
}