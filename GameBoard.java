import java.util.Random;

import javax.swing.ImageIcon;

public class GameBoard {
	
	private GameSquare [] startingBoard;
	public GameSquare [] playingBoard;
	private GameSquare [] winningBoard;
	
	private int boardSize;
	private int boardSideLength;
	private int [][] boardRestrictions;
	private int difficulty;
	
	private boolean wonGame;
	
	public GameBoard(int boardSize, int difficulty, int [][] boardRestrictions)
	{
		this.boardSize = boardSize;
		this.difficulty = difficulty;
		this.boardSideLength = (int) Math.sqrt(boardSize);
		this.boardRestrictions = boardRestrictions;
		
		createWinningBoard();
		createStartingBoard();
		createPlayingBoard();		
	}
	
	private void createWinningBoard()
	{
		winningBoard = new GameSquare[boardSize];
		
		for(int i = 0; i < boardSize; i++)
		{
			int currentGameSquareNumber = i + 1;
			String currentNumber = Integer.toString(i+1);
			String currentImageUpFileName = "number" + currentNumber + "Up";
			String currentImageDownFileName = "number" + currentNumber + "Down";
			
			ImageIcon currentUpImage = new ImageIcon(currentImageUpFileName);
			ImageIcon currentDownImage = new ImageIcon(currentImageDownFileName);
			
			GameSquare currentGameSquare = new GameSquare(false, false, currentUpImage, currentDownImage, i+1);
			winningBoard[i] = currentGameSquare;
		}
	}
	
	private void createStartingBoard()
	{
		startingBoard = winningBoard;
		randomizeBoard();
	}
	
	private void createPlayingBoard()
	{
		playingBoard = startingBoard;
	}
	
	private void randomizeBoard()
	{
		Random random = new Random();
		
		int randomSpinsLeft = 10; // change this based on difficulty
		
		while(randomSpinsLeft != 0)
		{
			int spinLength = random.nextInt(boardSideLength) + 1;
			int spinHeight = random.nextInt(boardSideLength) + 1;
			
			int spinXPosition = 0;
			if(boardSideLength - spinLength > 0)
				spinXPosition = random.nextInt(boardSideLength - spinLength + 1);
			int spinYPosition = 0;
			if(boardSideLength - spinHeight > 0)
				spinYPosition = random.nextInt(boardSideLength - spinHeight + 1);
			
			int numOfTilesToSpin = spinLength * spinHeight;
			
			GameSquare [] spin = new GameSquare [numOfTilesToSpin];
			
			for(int i = 0; i < numOfTilesToSpin; i++)
			{
				int position = (spinXPosition + i%spinLength) + boardSideLength*(spinYPosition + i/spinLength);
				spin[i] = startingBoard[position];
			}
			
			if(validSpin(spinLength, spinHeight))
			{
				spin(startingBoard, spin, spinLength, spinHeight, spinXPosition, spinYPosition);
				randomSpinsLeft--;
			}
		}
	}
	
	private void setBoardRestrictions(int [][] boardRestrictions)
	{
		this.boardRestrictions = boardRestrictions;
	}
	
	private int [][] getBoardRestrictions()
	{
		return this.boardRestrictions;
	}
	
	private boolean validSpin(int spinLength, int spinHeight)
	{
		boolean validSpin = true;
		
		for(int i = 0; i < boardRestrictions.length; i++)
		{
			if((boardRestrictions[i][0] == spinLength && boardRestrictions[i][1] == spinHeight) || 
					(boardRestrictions[i][0] == spinHeight && boardRestrictions[i][1] == spinLength))
				validSpin = false;
		}
		
		return validSpin;
	}
	
	public void spin(GameSquare [] board, GameSquare [] toSpin, int spinLength, int spinHeight, int spinXPosition, int spinYPosition)
	{
		int toSpinArraySize = toSpin.length;
		
		for(int i = 0; i < toSpinArraySize; i ++)
		{
			int spinningPosition = (spinXPosition + i%spinLength) + boardSideLength*(spinYPosition + i/spinLength); 
			board[spinningPosition] = toSpin[toSpinArraySize - i - 1];
			board[spinningPosition].flipGameSquare();
		}
	}
	
	public boolean hasWon()
	{
		wonGame = true;
		for(int i = 0; i < boardSize; i ++)
		{
			if(winningBoard[i].getGameSquareNumber() != playingBoard[i].getGameSquareNumber())
				wonGame = false;
			else if(playingBoard[i].isUpsideDown())
				wonGame = false;
		}
	
		return wonGame;
	}
	
	public void resetGame()
	{
		playingBoard = startingBoard;
	}
	
	public void setDifficulty(int difficulty)
	{
		this.difficulty = difficulty;
	}
	
	public int getDifficulty()
	{
		return this.difficulty;
	}

}
