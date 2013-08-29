package game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L; // make compiler happy
	
	private ArrayList<Square> emptySquareList;
	private Square[][] board;
	private int rows;
	private int cols;
	private JLabel timerLabel;
	private int seconds;
	private Timer timer;
	private JLabel flagLabel;
	
	public MainPanel(int rows, int cols) {
		super(new GridBagLayout());
		
		this.rows = rows;
		this.cols = cols;
		
		createGame();
	}
	
	public Square[][] getBoard() {
		return board;
	}
	
	public void emptySquareList(Square square) {
		emptySquareList.add(square);
	}
	
	public ArrayList<Square> getEmptySquareList() {
		return emptySquareList;
	}
	
	public void stopTimer() {
		timer.stop();
	}
	
	public int getTime() {
		return seconds;
	}
	
	public void createGame() {
		// initialize game data
		Square.squareCount = 0;
		Square.flagCount = 0;
		seconds = 0;
		removeAll();
		revalidate();
		repaint();
		
		emptySquareList = new ArrayList<Square>();
		
		// create time and label (updates every second)
		timerLabel = new JLabel("Timer: " + seconds);
		ActionListener updateTimer = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timerLabel.setText("Timer: " + ++seconds);
			}
		};
		
		// add timer to panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = rows;
		c.gridwidth = 3;
		add(timerLabel, c);
		timer = new Timer(1000, updateTimer);
		
		// create and add flag label
		flagLabel = new JLabel("Flags: " + Square.flagCount + "/10");
		c.gridx = cols - 3;
		c.gridy = rows;
		c.gridwidth = 3;
		add(flagLabel, c);
		
		// generate new board
		board = BoardFactory.getBoard(rows, cols);
		
		// loop through board and add functionality to each Square
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				// add left click (default) functionality
				board[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Square square = (Square) e.getSource();
						
						// start timer if first square clicked
						if (Square.squareCount == 0)
							timer.start();
						
						// don't allow click if flagged by user
						if (square.isFlagged())
							return;
						
						// calls appropriate click method based on Square subclass
						square.click();
						
						square.removeActionListener(this);
						square.removeMouseListener(square.getMouseListeners()[0]);
					}
				});
				
				// add right click (flag) functionality
				board[i][j].addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent e) {
						Square square = (Square) e.getSource();
						
						// Square has already been clicked, can't add flag
						if (square.isClicked())
							return;
						
						// detect right click and flag
						if (e.getButton() == MouseEvent.BUTTON3) {
							square.flagSquare();
							flagLabel.setText("Flags: " + Square.flagCount + "/10");
						}
					}
					
					public void mouseEntered(MouseEvent arg0) {
						// not implemented
					}

					public void mouseExited(MouseEvent arg0) {
						// not implemented
					}

					public void mousePressed(MouseEvent arg0) {
						// not implemented
						
					}

					public void mouseReleased(MouseEvent arg0) {
						// not implemented
					}
					
				});
				
				// add square to panel (screen)
				this.add(board[i][j], board[i][j].getConstraints());
			}
	}
}
