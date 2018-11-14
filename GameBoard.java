import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameBoard {
	
	private GameSquare [] startingBoard;
	public GameSquare [] playingBoard;
	private GameSquare [] winningBoard;
	
	private int boardSize;
	public int boardSideLength;
	private int [][] boardRestrictions;
	
	private int [][] easyBoardRestrictions = {{0,0}};
	private int [][] mediumBoardRestrictions = {{1,1}};
	private int [][] hardBoardRestrictions = {{2,1}};
	
	private int difficulty;
	
	private boolean wonGame;
	
	private JFrame gameJFrame;
	
	public GameBoard(int boardSize, int difficulty, JFrame passedInJFrame)
	{
		this.boardSize = boardSize;
		this.difficulty = difficulty;
		this.boardSideLength = (int) Math.sqrt(boardSize);
		setBoardRestrictions();
		this.gameJFrame = passedInJFrame;
		
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
			String currentNumber = Integer.toString(currentGameSquareNumber);
			String currentImageUpFileName = "Images/number" + currentNumber + "Up.png";
			String currentImageDownFileName = "Images/number" + currentNumber + "Down.png";
			
			ImageIcon currentUpImage = new ImageIcon(currentImageUpFileName);
			ImageIcon currentDownImage = new ImageIcon(currentImageDownFileName);
			
			GameSquare currentGameSquare = new GameSquare(false, false, currentUpImage, currentDownImage, i+1, gameJFrame);
			winningBoard[i] = currentGameSquare;
		}
	}
	
	private void createStartingBoard()
	{
		startingBoard = new GameSquare[boardSize];
		
		for(int i = 0; i < boardSize; i++)
		{
			startingBoard[i] = winningBoard[i];
		}
		
		randomizeBoard();
	}
	
	private void createPlayingBoard()
	{	
		playingBoard = new GameSquare[boardSize];
		
		for(int i = 0; i < boardSize; i++)
		{
			playingBoard[i] = startingBoard[i];
		}
	}
	
	private void randomizeBoard()
	{
		//Random random = new Random();
		
		int randomSpinsLeft = ((boardSideLength)^2)*(difficulty+1); // change this based on difficulty
		
		while(needMoreRandomization(getRandomizationFactor()) || randomSpinsLeft > 0)
		{
			if(makeRandomSpin(startingBoard))
				randomSpinsLeft--;
			/*int[] spinPositions = {random.nextInt(boardSize), random.nextInt(boardSize)};
			
			int spinLength = getSpinLength(spinPositions);
			int spinHeight = getSpinHeight(spinPositions);
			int spinXPosition = getLeftXSpinPosition(spinPositions);
			int spinYPosition = getUpperYSpinPosition(spinPositions);
			
			if(validSpin(spinLength, spinHeight))
			{
				spin(startingBoard, spinLength, spinHeight, spinXPosition, spinYPosition);
				randomSpinsLeft--;
			}*/
		}
	}
	
	public boolean makeRandomSpin(GameSquare[] board)
	{
		Random random = new Random();
		
		boolean madeRandomSpin = false;

		int[] spinPositions = {random.nextInt(boardSize), random.nextInt(boardSize)};
		
		int spinLength = getSpinLength(spinPositions);
		int spinHeight = getSpinHeight(spinPositions);
		int spinXPosition = getLeftXSpinPosition(spinPositions);
		int spinYPosition = getUpperYSpinPosition(spinPositions);
		
		if(validSpin(spinLength, spinHeight))
		{
			madeRandomSpin = true;
			spin(board, spinLength, spinHeight, spinXPosition, spinYPosition);
			//randomSpinsLeft--;
		}
		
		return madeRandomSpin;
	}
	
	private int getRandomizationFactor()
	{
		int randomizationFactor = 0;
		for(int i = 0; i < boardSize; i ++)
		{
			if(winningBoard[i].getGameSquareNumber() != startingBoard[i].getGameSquareNumber())
				randomizationFactor++;
			if(startingBoard[i].isUpsideDown())
				randomizationFactor++;;
		}
		
		return randomizationFactor;
	}
	
	private boolean needMoreRandomization(int randomizationFactor)
	{
		boolean needMoreRandomization;
		
		if(boardSize + 1 >= randomizationFactor)
			needMoreRandomization = true;
		else
			needMoreRandomization = false;
		
		return needMoreRandomization;
	}
	
	public int getSpinLength(int[] spinPositions)
	{
		int firstSpinPosition = spinPositions[0];
		int secondSpinPosition = spinPositions[1];
		
		int firstXSpinPosition = firstSpinPosition%boardSideLength;
		int secondXSpinPosition = secondSpinPosition%boardSideLength;
		
		int length = Math.abs(firstXSpinPosition - secondXSpinPosition) + 1;
		
		return length;
	}
	
	public int getSpinHeight(int[] spinPositions)
	{
		int firstSpinPosition = spinPositions[0];
		int secondSpinPosition = spinPositions[1];
		
		int firstYSpinPosition = firstSpinPosition/boardSideLength;
		int secondYSpinPosition = secondSpinPosition/boardSideLength;
		
		int height = Math.abs(firstYSpinPosition - secondYSpinPosition) + 1;
		
		return height;
	}
	
	public int getLeftXSpinPosition(int[] spinPositions)
	{
		int firstSpinPosition = spinPositions[0];
		int secondSpinPosition = spinPositions[1];
		
		int firstXSpinPosition = firstSpinPosition%boardSideLength;
		int secondXSpinPosition = secondSpinPosition%boardSideLength;
		
		int leftXSpinPosition;
		
		if(firstXSpinPosition > secondXSpinPosition)
			leftXSpinPosition = secondXSpinPosition;
		else
			leftXSpinPosition = firstXSpinPosition;
		
		return leftXSpinPosition;
	}
	
	public int getUpperYSpinPosition(int[] spinPositions)
	{
		int firstSpinPosition = spinPositions[0];
		int secondSpinPosition = spinPositions[1];
		
		int firstYSpinPosition = firstSpinPosition/boardSideLength;
		int secondYSpinPosition = secondSpinPosition/boardSideLength;
		
		int upperYSpinPosition;
		
		if(firstYSpinPosition > secondYSpinPosition)
			upperYSpinPosition = secondYSpinPosition;
		else
			upperYSpinPosition = firstYSpinPosition;
		
		return upperYSpinPosition;
	}
	
	public GameSquare [] gameSquaresToSpin(GameSquare[] board, int spinLength, int spinHeight, int spinXPosition, int spinYPosition)
	{
		int numOfTilesToSpin = spinLength * spinHeight;
		
		GameSquare [] spin = new GameSquare [numOfTilesToSpin];
		
		for(int i = 0; i < numOfTilesToSpin; i++)
		{
			int position = (spinXPosition + i%spinLength) + boardSideLength*(spinYPosition + i/spinLength);
			spin[i] = board[position];
		}
		
		return spin;
	}
	
	private void setBoardRestrictions()
	{ 
		if(difficulty == 0)
		{
			this.boardRestrictions = easyBoardRestrictions;
		}
		else if (difficulty == 1)
		{
			this.boardRestrictions = mediumBoardRestrictions;
		}
		
		else if (difficulty == 2)
		{
			this.boardRestrictions = hardBoardRestrictions;
		}
	}
	
	/*private int [][] getBoardRestrictions()
	{
		return this.boardRestrictions;
	}*/
	
	public boolean validSpin(int spinLength, int spinHeight)
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
	
	public void spin(GameSquare [] board, int spinLength, int spinHeight, int spinXPosition, int spinYPosition)
	{
		GameSquare[] toSpin = gameSquaresToSpin(board, spinLength, spinHeight, spinXPosition, spinYPosition);
		
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
		playingBoard = new GameSquare[boardSize];
		
		for(int i = 0; i < boardSize; i++)
		{
			playingBoard[i] = startingBoard[i];
		}
	}
	
	
	public int getDifficulty()
	{
		return this.difficulty;
	}
	
	public int getSize()
	{
		return this.boardSize;
	}

}
