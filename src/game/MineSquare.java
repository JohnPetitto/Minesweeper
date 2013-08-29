package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class MineSquare extends Square {
	private static final long serialVersionUID = 1L; // make compiler happy
	
	JDialog dialog;
	
	public MineSquare() {
		super();
		super.setImage("images/squaremine.png");
		super.setValue(9);
	}
	
	public void click() {		
		MainPanel mp = (MainPanel) getParent();
		mp.stopTimer();
		
		// if first square clicked is a mine, reset game
		if (squareCount == 0) {
			mp.createGame();
			return;
		}
		
		super.setClick();
		
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
		optionPane.setMessage("You clicked a mine, game over!");
		optionPane.setOptions(buttons);
		dialog = optionPane.createDialog("Game Over!");
		
		// record loss in statistics
		StatsData data = new StatsData();
		data.updateStats(false, 0);
		
		// show dialog
		dialog.setLocationRelativeTo(mp);
		dialog.setVisible(true);
		
		mp.createGame();
	}
}
