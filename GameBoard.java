import java.util.Random;

public class GameBoard {
	
	private GameSquare [] startingBoard;
	private GameSquare [] playingBoard;
	private GameSquare [] winningBoard;
	
	private int boardSize;
	private int boardSideLength = (int) Math.sqrt(boardSize);
	private int [][] boardRestrictions;
	private int difficulty;
	
	private boolean wonGame;
	
	public GameBoard(int boardSize, int difficulty)
	{
		this.boardSize = boardSize;
		this.difficulty = difficulty;
		
		createWinningBoard();
		createStartingBoard();
		createPlayingBoard();		
	}
	
	private void createWinningBoard()
	{
		for(int i = 0; i < boardSize; i++)
		{
			int currentGameSquareNumber = i + 1;
			//GameSquare temp = new GameSquare(false, false, Image, Image, upperLeft, currentGameSquraeNumber);
			//winningBoard[i] = temp;
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
			int spinXPosition = random.nextInt(boardSideLength - spinLength);
			int spinYPosition = random.nextInt(boardSideLength - spinHeight);
			
			int numOfTilesToSpin = spinLength * spinHeight;
			
			GameSquare [] temp = new GameSquare [numOfTilesToSpin];
			
			for(int i = 0; i < numOfTilesToSpin; i++)
			{
				temp[i] = playingBoard[(spinXPosition + i)%spinLength + (spinYPosition + i)%spinHeight]; // may not be right, but going with it for now
			}
			
			if(validSpin(spinLength, spinHeight))
			{
				spin(startingBoard, temp, spinLength, spinHeight, spinXPosition, spinYPosition);
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
	
	private void spin(GameSquare [] board, GameSquare [] toSpin, int spinLength, int spinHeight, int spinXPosition, int spinYPosition)
	{
		int toSpinArraySize = toSpin.length;
		
		for(int i = 0; i < toSpinArraySize; i ++)
		{
			int spinningPosition = (spinXPosition + i)%spinLength + (spinYPosition + i)%spinHeight; // may not be right, but going with it for now 
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
