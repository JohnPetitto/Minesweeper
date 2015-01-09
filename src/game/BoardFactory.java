package game;

/*
 * Generates a game board based on the requested
 * size (numOfRows, numOfCols) and it fills it with randomly
 * placed mines and numerical indicators.
 */

import java.util.Random;

public class BoardFactory {
	private static final int MINE = 9;
	private static Square[][] board;
	
	private BoardFactory() {
		// class is non-instantiable
	}
	
	public static Square[][] getBoard(int numOfRows, int numOfCols) {
		board = new Square[numOfRows][numOfCols];
		
		Random rowRand = new Random();
		Random colRand = new Random();
		
		// randomly place mines on board
		int numberOfMines = numOfRows + 1;
		for (int i = 0; i < numberOfMines; i++) {
			int row = rowRand.nextInt(numOfRows);
			int col = colRand.nextInt(numOfCols);
			
			if (board[row][col] == null) {
				board[row][col] = new MineSquare(); // place mine
				board[row][col].setConstraints(row, col);
			} else
				i--; // check for another valid square
		}
		
		// calculate mine indicators for non-mine squares
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfCols; j++) {
				int mineCount = 0;
				
				if (board[i][j] != null)
					continue; // is a mine
				
				if (isMine(i - 1, j - 1))
					mineCount++; // top-left
				if (isMine(i - 1, j))
					mineCount++; // top
				if (isMine(i - 1, j + 1))
					mineCount++; // top-right
				if (isMine(i, j - 1))
					mineCount++; // left
				if (isMine(i, j + 1))
					mineCount++; // right
				if (isMine(i + 1, j - 1))
					mineCount++; // bottom-left
				if (isMine(i + 1, j))
					mineCount++; // bottom
				if (isMine(i + 1, j + 1))
					mineCount++; // bottom-right
				
				if (mineCount == 0)
					board[i][j] = new EmptySquare();
				else
					board[i][j] = new NumberSquare(mineCount);
				
				board[i][j].setConstraints(i, j);
			}
		}
		
		return board;
	}
	
	public static boolean isMine(int x, int y) {
		// location not on board
		if (x < 0 || y < 0 || x >= 9 || y >= 9)
			return false;
		
		if (board[x][y] == null)
			return false;
		
		return board[x][y].getValue() == MINE;
	}
}