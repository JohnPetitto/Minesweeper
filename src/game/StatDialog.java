package game;

import game.StatsData.Stats;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatDialog extends JDialog {
	private static final long serialVersionUID = 1L; // make compiler happy
	
	private JPanel panel;
	
	public StatDialog(JFrame frame) {
		// initialize dialog
		super(frame, true);
		setTitle("Game Statistics");
		setResizable(false);
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setPreferredSize(new Dimension(200, 150));
		getContentPane().add(panel);
		
		// load statistical data
		StatsData data = new StatsData();
		data.loadProperties();
		
		// grab appropriate stats
		String gamesPlayed = data.getProperty(Stats.GAMES_PLAYED + "");
		String gamesWon = data.getProperty(Stats.GAMES_WON + "");
		String bestTime = data.getProperty(Stats.BEST_TIME + "");
		
		// calculate winning percentage
		int winningPercentage = (int) (Double.parseDouble(gamesWon) / Double.parseDouble(gamesPlayed) * 100);
		
		// create labels for stats
		JLabel gamesPlayedLabel = new JLabel("Games Played: " + gamesPlayed);
		JLabel gamesWonLabel = new JLabel("Games Won: " + gamesWon);
		JLabel winningPercentageLabel = new JLabel("Winning Percentage: " + winningPercentage + "%");
		JLabel bestTimeLabel = new JLabel("Best Time: " + bestTime + " seconds");
		
		// set initial layout constraints
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		
		// add stats to panel of dialog
		panel.add(gamesPlayedLabel, c);
		c.gridy = 1;
		panel.add(gamesWonLabel, c);
		c.gridy = 2;
		panel.add(winningPercentageLabel, c);
		c.gridy = 3;
		panel.add(bestTimeLabel, c);
		
		// create reset stats button
		JButton resetButton = new JButton("Reset");
		resetButton.setFocusable(false);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StatsData data = new StatsData();
				
				// clear existing stats
				data.setProperty(Stats.GAMES_PLAYED + "", "0");
				data.setProperty(Stats.GAMES_WON + "", "0");
				data.setProperty(Stats.BEST_TIME + "", "0");
				data.saveProperties();
				
				// disable reset button
				JButton resetButton = (JButton) e.getSource();
				resetButton.setEnabled(false);
			}
		});
		
		// add reset button to panel of dialog
		c.gridy = 4;
		panel.add(resetButton, c);
		
		pack();
		setLocationRelativeTo(frame);
		setVisible(true);
	}
}
