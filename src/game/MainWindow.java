package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainWindow {
	private static final int ROWS = 9;
	private static final int COLS = 9;
	public static final int GAME_WON = ROWS * COLS - 10;
	
	private MainPanel mp;
	private JFrame frame;
	
	public MainWindow() {
		// initialize the frame
		frame = new JFrame("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		// create drop-down menu
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		
		// create new game menu item
		JMenuItem newGameItem = new JMenuItem("New Game");
		newGameItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mp.stopTimer();
				
				// record game loss if in progress
				if (mp.getTime() > 0) {
					StatsData data = new StatsData();
					data.updateStats(false, 0);
				}
				
				mp.createGame();
			}
		});
		
		// create stats menu item
		JMenuItem statsItem = new JMenuItem("Statistics");
		statsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StatDialog(frame);
			}
		});
		
		// create exit menu item
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mp.stopTimer();
				
				// record game loss if in progress
				if (mp.getTime() > 0) {
					StatsData data = new StatsData();
					data.updateStats(false, 0);
				}
				
				System.exit(0);
			}
		});
		
		// add menu items
		gameMenu.add(newGameItem);
		gameMenu.addSeparator();
		gameMenu.add(statsItem);
		gameMenu.addSeparator();
		gameMenu.add(exitItem);
		
		// add menu
		menuBar.add(gameMenu);
		frame.setJMenuBar(menuBar);
		
		// create program icon
		URL imageURL = MainWindow.class.getResource("images/icon.png");
		ImageIcon icon = new ImageIcon(imageURL);
		frame.setIconImage(icon.getImage());
		
		createGame();
	}
	
	public void createGame() {
		mp = new MainPanel(ROWS, COLS);
		
		frame.getContentPane().add(mp);
		frame.pack();
		
		// center the frame
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenLocX = (screenSize.width / 2) - (frame.getWidth() / 2);
		int screenLocY = (screenSize.height / 2) - (frame.getHeight() / 2);
		frame.setLocation(screenLocX, screenLocY);
		
		frame.setVisible(true);
	}
	
	// start application
	public static void main(String[] args) {
		new MainWindow();
	}
}
