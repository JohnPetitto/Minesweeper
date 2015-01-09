package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class NumberSquare extends Square {
	private static final long serialVersionUID = 1L; // make compiler happy
	
	private JDialog dialog;
	
	public NumberSquare(int value) {
		super();
		super.setValue(value);
		
		// select appropriate image for number indicator
		switch (value) {
		case 1:
			super.setImage("images/square1.png");
			break;
		case 2:
			super.setImage("images/square2.png");
			break;
		case 3:
			super.setImage("images/square3.png");
			break;
		case 4:
			super.setImage("images/square4.png");
			break;
		default:
			super.setImage("");
		}
	}
	
	public void click() {
		super.setClick();
		squareCount++;
		
		MainPanel mp = (MainPanel) getParent();
		
		// check if user cleared all squares (victory)
		if (squareCount >= MainWindow.GAME_WON) {
			mp.stopTimer();
			
			// create play again button
			JButton playAgain = new JButton("Play Again");
			playAgain.setFocusable(false);
			playAgain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
				}
			});
			
			// create quit game button
			JButton quitGame = new JButton("Quit");
			quitGame.setFocusable(false);
			quitGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			
			// create and add option pane to dialog
			JButton[] buttons = {playAgain, quitGame};
			JOptionPane optionPane = new JOptionPane();
			optionPane.setMessage("You won! It took you " + mp.getTime() + " seconds");
			optionPane.setOptions(buttons);
			dialog = optionPane.createDialog("Game Over!");
			
			// record win in statistics
			StatsData data = new StatsData();
			data.updateStats(true, mp.getTime());
			
			// show dialog
			dialog.setLocationRelativeTo(mp);
			dialog.setVisible(true);
			
			mp.createGame();
		} else {
			// update empty square list
			if (!mp.getEmptySquareList().isEmpty())
				mp.getEmptySquareList().remove(0).doClick(0);
		}
	}
}
