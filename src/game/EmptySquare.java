package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class EmptySquare extends Square {	
	private static final long serialVersionUID = 1L; // make compiler happy
	
	private Square[][] board;
	private MainPanel mp;
	private JDialog dialog;
	
	public EmptySquare() {
		super();
		super.setImage("images/square.png");
		super.setValue(0);
	}
	
	public void click() {
		super.setClick();
		squareCount++;
		
		// check if user cleared all squares (victory)
		if (squareCount >= MainWindow.GAME_WON) {
			mp = (MainPanel) getParent();
			mp.stopTimer();
			
			// create play again button
			JButton playAgain = new JButton("Play Again");
			playAgain.setFocusable(false);
			playAgain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
				}
			});
			
			// create quit button
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
			// clear surrounding squares
			clearSquares();
		}
	}
	
	// this method clears all surrounding squares that are empty,
	// including the first set of encountered numbered squares
	private void clearSquares() {
		mp = (MainPanel) getParent();
		board = mp.getBoard();
		
		int x = getConstraints().gridx;
		int y = getConstraints().gridy;
		
		if (validClearSquare(x - 1, y - 1)) {
			mp.emptySquareList(mp.getBoard()[x - 1][y - 1]);
			mp.getBoard()[x - 1][y - 1].setClick();
		}
		
		if (validClearSquare(x - 1, y)) {
			mp.emptySquareList(mp.getBoard()[x - 1][y]);
			mp.getBoard()[x - 1][y].setClick();
		}
		
		if (validClearSquare(x - 1, y + 1)) {
			mp.emptySquareList(mp.getBoard()[x - 1][y + 1]);
			mp.getBoard()[x - 1][y + 1].setClick();
		}
		
		if (validClearSquare(x, y - 1)) {
			mp.emptySquareList(mp.getBoard()[x][y - 1]);
			mp.getBoard()[x][y - 1].setClick();
		}
		
		if (validClearSquare(x, y + 1)) {
			mp.emptySquareList(mp.getBoard()[x][y + 1]);
			mp.getBoard()[x][y + 1].setClick();
		}
		
		if (validClearSquare(x + 1, y - 1)) {
			mp.emptySquareList(mp.getBoard()[x + 1][y - 1]);
			mp.getBoard()[x + 1][y - 1].setClick();
		}
		
		if (validClearSquare(x + 1, y)) {
			mp.emptySquareList(mp.getBoard()[x + 1][y]);
			mp.getBoard()[x + 1][y].setClick();
		}
		
		if (validClearSquare(x + 1, y + 1)) {
			mp.emptySquareList(mp.getBoard()[x + 1][y + 1]);
			mp.getBoard()[x + 1][y + 1].setClick();
		}
		
		if (!mp.getEmptySquareList().isEmpty())
			mp.getEmptySquareList().remove(0).doClick(0);
	}
	
	private boolean validClearSquare(int x, int y) {
		// location not on board
		if (x < 0 || y < 0 || x >= 9 || y >= 9)
			return false;
		
		if (board[x][y].isClicked())
			return false;
		
		if (board[x][y].isFlagged())
			return false;
		
		return true;
	}
}
