package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class StatsData {
	private Properties defaultProps;
	private Properties applicationProps;
	private FileInputStream in;
	private FileOutputStream out;
	private File defaultFile;
	private File applicationFile;
	
	public StatsData() {
		loadProperties();
	}
	
	// records a win or loss to the statistics
	public void updateStats(boolean win, int newTime) {
		// increase games played by 1
		String gamesPlayed = getProperty(Stats.GAMES_PLAYED + "");
		int incrementGamesPlayed = Integer.parseInt(gamesPlayed) + 1;
		setProperty(Stats.GAMES_PLAYED + "", incrementGamesPlayed + "");
		
		if (win) {
			// increase wins by 1
			String gamesWon = getProperty(Stats.GAMES_WON + "");
			int incrementGamesWon = Integer.parseInt(gamesWon) + 1;
			setProperty(Stats.GAMES_WON + "", incrementGamesWon + "");
			
			// check if new time is a best
			String bestTime = getProperty(Stats.BEST_TIME + "");
			int prevBestTime = Integer.parseInt(bestTime);
			if (newTime < prevBestTime || prevBestTime == 0)
				setProperty(Stats.BEST_TIME + "", newTime + "");
		}
		
		saveProperties();
	}
	
	// loads statistics file, creating one if it doesn't exist
	public void loadProperties() {
		defaultProps = new Properties();
		
		defaultFile = new File("defaultProperties");
		if (!defaultFile.exists()) {
			try {
				defaultFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			in = new FileInputStream(defaultFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			defaultProps.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		applicationProps = new Properties(defaultProps);
		
		applicationFile = new File("appProperties");
		if (!applicationFile.exists()) {
			try {
				applicationFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			in = new FileInputStream(applicationFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			applicationProps.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// saves statistics to file
	public void saveProperties() {
		try {
			out = new FileOutputStream(applicationFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			applicationProps.store(out, "---Minesweeper Statistics---");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setProperty(String key, String value) {
		applicationProps.setProperty(key, value);
	}
	
	public String getProperty(String key) {
		return applicationProps.getProperty(key, "0");
	}
	
	public enum Stats {
		GAMES_PLAYED, GAMES_WON, BEST_TIME;
	}
}
