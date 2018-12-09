import java.awt.Color;
import java.util.Random;
import javax.swing.JFrame;

public class GameBoard {
	
	private GameSquare [] startingBoard;
	public GameSquare [] playingBoard;
	
	private int boardSize;

	public int boardSideLength;

	private int [][] boardRestrictions;
	
	private final int HARD_DIFFICULTY=2;
	private final int MEDIUM_DIFFICULTY=1;
	private final int EASY_DIFFICULTY=0;
	
	private int difficulty;
	
	private boolean wonGame;
	
	private Color plainSquareColor;
	
	private JFrame gameJFrame;
	
	// Can change or add restrictions to EASY_BOARD_RESTRICTIONS, MEDIUM_BOARD_RESTRICTIONS, or HARD_BOARD_RESTRICTIONS
	// Only include {0,0} spin restriction if there are no other spin restrictions
	private final int [][] EASY_BOARD_RESTRICTIONS = {{0,0}};
	private final int [][] MEDIUM_BOARD_RESTRICTIONS = {{1,1}};
	private final int [][] HARD_BOARD_RESTRICTIONS = {{2,1}}; 
	
	public GameBoard(int boardSize, int difficulty, Color plainSquareColor, JFrame passedInJFrame)
	{
		this.boardSize = boardSize;
		this.difficulty = difficulty;
		this.boardSideLength = (int) Math.sqrt(boardSize);
		setBoardRestrictions();
		this.plainSquareColor = plainSquareColor;
		this.gameJFrame = passedInJFrame;
		
		createStartingBoard();
		createPlayingBoard();		
	}
	
	private void createStartingBoard()
	{
		startingBoard = new GameSquare[boardSize];
		
		for(int i = 0; i < boardSize; i++)
		{
			int currentGameSquareNumber = i + 1;

			GameSquare currentGameSquare = new GameSquare(false, false, currentGameSquareNumber, plainSquareColor, gameJFrame);
			startingBoard[i] = currentGameSquare;
		}
		
		randomizeBoard();
	}
	
	private void createPlayingBoard()
	{	
		playingBoard = new GameSquare[boardSize];
		
		for(int i = 0; i < boardSize; i++)
		{
			GameSquare currentGameSquare = new GameSquare(false, startingBoard[i].isUpsideDown(), startingBoard[i].getGameSquareNumber(), plainSquareColor, gameJFrame);
			playingBoard[i] = currentGameSquare;
		}
	}
	
	private void randomizeBoard()
	{	
		int randomSpinsLeft = ((boardSideLength)^2)*(difficulty+1); // change this based on difficulty
		
		while(needMoreRandomization(getRandomizationFactor()) || randomSpinsLeft > 0)
		{
			if(makeRandomSpin(startingBoard))
				randomSpinsLeft--;
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
		}
		
		return madeRandomSpin;
	}
	
	private int getRandomizationFactor()
	{
		int randomizationFactor = 0;
		for(int i = 0; i < boardSize; i ++)
		{
			int currentSquareLocation = i + 1;
			if(currentSquareLocation != startingBoard[i].getGameSquareNumber())
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
	
	public void setSelected(GameSquare[] board, int spinLength, int spinHeight, int spinXPosition, int spinYPosition)
	{
		GameSquare[] selected = gameSquaresToSpin(board, spinLength, spinHeight, spinXPosition, spinYPosition);
		
		int selectedArraySize = selected.length;
		
		for(int i = 0; i < selectedArraySize; i ++)
		{
			int spinningPosition = (spinXPosition + i%spinLength) + boardSideLength*(spinYPosition + i/spinLength); 
			board[spinningPosition].setSelected(true);
		}
	}

	private void setBoardRestrictions()
	{ 
		if(difficulty == EASY_DIFFICULTY)
		{
			this.boardRestrictions = EASY_BOARD_RESTRICTIONS;
		}
		else if (difficulty == MEDIUM_DIFFICULTY)
		{
			this.boardRestrictions = MEDIUM_BOARD_RESTRICTIONS;
		}
		else if (difficulty == HARD_DIFFICULTY)
		{
			this.boardRestrictions = HARD_BOARD_RESTRICTIONS;
		}
	}
	
	public int [][] getSpinRestrictions()
	{
		return this.boardRestrictions;
	}
	
	public String getBoardRestrictionsString()
	{
		String boardRestrictions = "";
		int [][] currentBoardRestrictions = getSpinRestrictions();
		int numberOfBoardRestrictions = currentBoardRestrictions.length;
		int numberOfBoardRestrictionsLeft = numberOfBoardRestrictions;
		
		if(numberOfBoardRestrictions == 1 && currentBoardRestrictions[0][0] == 0 && currentBoardRestrictions[0][1] == 0) // no board restrictions
		{
			boardRestrictions = "do not have any spin restrictions, you can rotate all rectangles!";
		}
		else
		{
			boardRestrictions = "cannot rotate ";
			for(int i = 0; i < numberOfBoardRestrictions; i++)
			{
				boardRestrictions = boardRestrictions + currentBoardRestrictions[i][0] + " square by " 
			+ currentBoardRestrictions[i][1] + " square rectangles";
				
				numberOfBoardRestrictionsLeft--;
				if(numberOfBoardRestrictionsLeft > 0)
				{
					boardRestrictions = boardRestrictions + ", ";
				}
				if(numberOfBoardRestrictionsLeft == 1)
				{
					boardRestrictions = boardRestrictions + " and ";
				}
			}
		}
		
		return boardRestrictions;
	}
	
	public boolean validSpin(int spinLength, int spinHeight)
	{
		boolean validSpin = true;
		
		for(int i = 0; i < boardRestrictions.length; i++)
		{
			if(( boardRestrictions[i][0] == spinLength && boardRestrictions[i][1] == spinHeight) || 
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
			int currentSquareLocation = i + 1;
			if(currentSquareLocation != playingBoard[i].getGameSquareNumber())
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
			playingBoard[i].setUpsideDown(startingBoard[i].isUpsideDown());
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
