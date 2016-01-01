/**
 * @(#)MemoryGameApplication.java
 * Assignment#3
 * @author Ankush Varshneya
 * @student# 100853074
 * @version 1.00 2012/2/24
 */

import java.awt.event.*; // Needed for ActionListener.
import javax.swing.event.*; // Needed for ListSelectionListener, DocumentListener.
import javax.swing.*; // Needed for JFrame.
import java.awt.*; //Needed for Component.

public class MemoryGameApplication extends JFrame implements ActionListener{
	private final int UNLIMITED_TIME = -1;

	private MemoryGame model; // The model to which this view is attached.
	private MemoryGameView view; // The view will show the state of the model.

	private int buttonSelectionCounter;
	private JButton firstSelectedButton;
	private JButton secondSelectedButton;
	private boolean gameInProgress;
	private long timeElapsed;
	private long timeAllowed;

	private Timer myTimer;

    public MemoryGameApplication(String title) {
    	super(title); // Sets the title for the window.

    	this.model = new MemoryGame();
    	this.view = new MemoryGameView(this.model);

		// Ads action listener to the start/stop button clicking the start button will start a new game.
		this.view.getStartStopButton().addActionListener(this);

		// Get All The componets of the grid (the Jbuttons) and Disable all of them.
		Component c[] = this.view.getGrid().getComponents();
		for(int i=0; i<c.length; i++) ((JButton)c[i]).setEnabled(false);

		this.view.getTimeField().setText("0:00");

		this.getContentPane().add(view); // Add the view.

		myTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleTimerTick();
			}});

		// Manually computed sizes.
		this.setSize(510, 390);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void handleTimerTick() {
    	String timeToDisplay;
    	if(this.timeAllowed!=this.UNLIMITED_TIME) {
			this.timeElapsed++;
    		timeToDisplay = (int)((this.timeAllowed-this.timeElapsed)/60) + ":";
			timeToDisplay+=(((this.timeAllowed-this.timeElapsed)%60)<10)? ("0"+((this.timeAllowed-this.timeElapsed)%60)):((this.timeAllowed-this.timeElapsed)%60);
			this.model.setTimeRemaining(timeToDisplay);
    	}
		gameInProgress=!(model.isOver());
		// If the game is not in progress simulate pressing the stop button.
		if(!gameInProgress)	this.handleStopButtonSelection();
		this.view.update(); // Update the view.
	}


    // This is the single event handler for all the buttons.
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.view.getStartStopButton()){
			if(e.getActionCommand()=="Start") {	this.handleStartButtonSelection(); } // Deal with the start button.
			else if (e.getActionCommand()=="Stop") { this.handleStopButtonSelection(); } // Deal with the stop button.
		}
		else { handleGridButtonSelection(e); } // Grid buttons ate the other buttons remaining.

     	this.view.update();	// Update the view.
    }

	// This handles the starts button.
	public void handleStartButtonSelection() {

		// This starts a new game.
		this.gameInProgress = true;
		this.timeElapsed = 0;
		this.model = new MemoryGame();
		this.view.getNewCard(this.model);
		this.model.DisplayAwnsers();// Displays the awsners (for testing purposes).

		switch(this.view.getStyleList().getSelectedIndex()) {
			case 0:	this.timeAllowed = 2*60; // 2 minutes in seconds.
					this.model.setTimeRemaining("2:00");
					break;
			case 1:	this.timeAllowed = 10*60; // 10 minutes in seconds.
					this.model.setTimeRemaining("10:00");
					break;
			case 2:	this.timeAllowed = UNLIMITED_TIME; // Unlimited time.
					this.model.setTimeRemaining("Unlimited!");
					break;
		}

    	// Reset the button counter and selected buttons.
    	buttonSelectionCounter = 0;
		JButton firstSelectedButton = null;
		JButton secondSelectedButton = null;

	   	// Add action listener to all grid buttons.
    	for(int row=0; row<this.model.DIMENSION; row++) {
    		for(int col=0; col<this.model.DIMENSION; col++) {
    			this.view.getGrid().getCardButtons()[row][col].addActionListener(this);
	  		}
	   	}

	   	this.view.getStartStopButton().setSelected(true); // This labels the button "Stop".
       	this.view.getStyleList().setEnabled(false); // Disable the style list.
		myTimer.start();// Start Timer.
	}

	// This handles the stop button.
    public void handleStopButtonSelection() {
		// Get All The componets of the grid (the Jbuttons) and Disable all of them so we can show the cards.
		Component c[] = this.view.getGrid().getComponents();
		for(int i=0; i<c.length; i++) ((JButton)c[i]).setEnabled(false);

		this.view.getStartStopButton().setSelected(false); // This labels the button "Start".
	   	this.view.getStyleList().setEnabled(true); // Enable the style list.
		myTimer.stop();// Stop timer.
    }

    // This handles the grid buttons.
    public void handleGridButtonSelection(ActionEvent e) {
    	JButton aButton = new JButton();
		for(int row=0; row<this.model.DIMENSION; row++) {
    		for(int col=0; col<this.model.DIMENSION; col++) {
    			aButton = this.view.getGrid().getCardButtons()[row][col];
    			if(e.getSource() == aButton){
    				aButton.setSelected(true); // Set the button as selected.
					// If the buttons been found then break the loop.
					row=this.model.DIMENSION;
					col=this.model.DIMENSION;
    			}}}

		if(this.buttonSelectionCounter==0) {
    		this.firstSelectedButton = aButton;
    		this.buttonSelectionCounter++;
		}
		// This makes sure the same button is not clicked twice.
		else if(aButton!=this.firstSelectedButton){
	   			this.secondSelectedButton = aButton;
	   			// This Delay the second button for a while.
	   			this.secondSelectedButton.paint(this.secondSelectedButton.getGraphics());
				try {Thread.sleep(500);} catch(InterruptedException ex) {}
	    		this.model.flipWith(firstSelectedButton, secondSelectedButton);
	    		this.buttonSelectionCounter = 0;
    	}
    }

	// This is where the program begins.
    public static void main(String[] args) {
		JFrame frame = new MemoryGameApplication("The Memory Game Grid");
		frame.setVisible(true);
	}


}