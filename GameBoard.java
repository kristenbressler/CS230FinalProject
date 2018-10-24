

import java.util.Random;

public class GameBoard {
	
	private GameSquare [] startingBoard;
	private GameSquare [] playingBoard;
	private GameSquare [] winningBoard;
	
	private int boardSize;
	private int boardSideLength = (int) Math.sqrt(boardSize);
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
		int DECIDEHOWMANYSPINSTHEYGET = 10;
		
		Random random = new Random();
		for(int i = 0; i < DECIDEHOWMANYSPINSTHEYGET; i++)
		{
			int spinLength = random.nextInt(boardSideLength) + 1;
			int spinHeight = random.nextInt(boardSideLength) + 1;
			int spinXPosition = random.nextInt(boardSideLength - spinLength);
			int spinYposition = random.nextInt(boardSideLength - spinHeight);
			
			int numOfTilesToSpin = spinLength * spinHeight;
			
			GameSquare [] temp = new GameSquare [numOfTilesToSpin];
			
			for(int j = 0; j < numOfTilesToSpin; j++)
			{
				// NO!!! temp[j] = playingBoard[spinLength*spinHeight]
			}
			
			spin(temp, spinLength, spinHeight);
		}
	}
	
	private void boardRestrictions()
	{
		
	}
	
	private GameSquare [] spin(GameSquare [] toMove, int moveLength, int moveHeight)
	{
		int toMoveArraySize = toMove.length;
		
		GameSquare [] moved = toMove;
		
		for(int i = 0; i < toMoveArraySize; i ++)
		{
			moved[i] = toMove[toMoveArraySize - i - 1];
			moved[i].flipGameSquare();
		}
		
		return moved;
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

}
