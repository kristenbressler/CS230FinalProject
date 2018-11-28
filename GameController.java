import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;

public class GameController extends TimerTask implements MouseListener 
{	
	private JFrame gameJFrame;
	private Container gameContentPane;
	
	private int gameJFrameXPosition;
	private int gameJFrameYPosition = 0;
	private int gameJFrameWidth;
	private int gameJFrameHeight;
	
	private int difficultyOfGame=0;
	private final int HARD_DIFFICULTY=0;
	private final int MEDIUM_DIFFICULTY=1; // may have to change all of these? my computer is mess up.
	private final int EASY_DIFFICULTY=2;
	private final int BIG_BOARD=0;
	private final int CLASSIC_GAME=0;
	
	
	private int xMouseOffsetToContentPaneFromJFrame = 0;
	private int yMouseOffsetToContentPaneFromJFrame = 0;
	
	private JButton resetButton;
	private JButton spinButton;
	private JButton newGameButton;
	
	private GameBoard board;
	
	private int [] gameSquaresToSpin = new int [2];
	
	private int spinCounter=0;
	private JLabel spinCounterJLabel;
	
	private java.util.Timer gameTimer = new java.util.Timer();
	private int elapsedTime = 0;
	private JLabel timerJLabel;
	
	private JLabel restrictionJLabel;

	private int timeSinceLastSpin = 0;
	private final int TIMETOSPIN = 5;
	
	private int game;
	
	private boolean gameIsReady;
	
	private int imageSideLength;
	
	private static final Color RED = new Color(255,0,0);
	private static final Color GREEN = new Color(0,255,0);
	private static final Color BLUE = new Color(0,0,255);
	
	public GameController()
	{
		gameJFrame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = (int) screenSize.getHeight() - (int) screenSize.getHeight()/10;
		gameJFrameWidth =  (int) (screenHeight + 0.35*screenHeight);

		gameJFrameHeight = screenHeight;
		gameJFrameXPosition = (int)(screenSize.getWidth() - gameJFrameWidth)/2;
		gameJFrame.setLocation(gameJFrameXPosition, gameJFrameYPosition);
		gameJFrame.setSize(gameJFrameWidth, gameJFrameHeight);
		gameJFrame.setResizable(false);
		gameJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameContentPane = gameJFrame.getContentPane();
        gameContentPane.setLayout(null);
        gameJFrame.setVisible(true);
        
        gameJFrame.addMouseListener(this);
        
        int borderWidth = (gameJFrameWidth - gameContentPane.getWidth())/2;
        xMouseOffsetToContentPaneFromJFrame = borderWidth;
        yMouseOffsetToContentPaneFromJFrame = gameJFrameHeight - gameContentPane.getHeight()-borderWidth;
        
        int buttonWidth = gameJFrameWidth-gameJFrameHeight - (int)gameJFrameWidth/20;
        int buttonHeight = (int)gameJFrameHeight/20;
        int buttonXLocation = gameJFrameHeight + (int)gameJFrameWidth/80;
        int buttonYLocationChange = gameJFrameHeight/15;
        
        resetButton = new JButton("Reset");
        gameContentPane.add(resetButton);
        resetButton.addMouseListener(this);
        resetButton.setSize(buttonWidth,buttonHeight);
        resetButton.setLocation(buttonXLocation, 5*buttonYLocationChange);
        
        spinButton = new JButton("Spin");
        gameContentPane.add(spinButton);
        spinButton.addMouseListener(this);
        spinButton.setSize(buttonWidth,buttonHeight);
        spinButton.setLocation(buttonXLocation, 4*buttonYLocationChange);
        
        newGameButton = new JButton("New Game");
        gameContentPane.add(newGameButton);
        newGameButton.addMouseListener(this);
        newGameButton.setSize(buttonWidth, buttonHeight);
        newGameButton.setLocation(buttonXLocation, 7*buttonYLocationChange);
        
        spinCounterJLabel = new JLabel();
        gameContentPane.add(spinCounterJLabel);
        spinCounterJLabel.setSize(buttonWidth, buttonHeight);
        spinCounterJLabel.setLocation(buttonXLocation, 1*buttonYLocationChange);
        spinCounterJLabel.setFont(new Font("Dialog", Font.PLAIN, (int) buttonHeight/2));
        updateSpinCounterJLabel();
        
        timerJLabel = new JLabel();
        gameContentPane.add(timerJLabel);
        timerJLabel.setSize(buttonWidth, buttonHeight);
        timerJLabel.setLocation(buttonXLocation, 2*buttonYLocationChange);
        timerJLabel.setFont(new Font("Dialog", Font.PLAIN, buttonHeight));
        updateTimerJLabel();
        
        restrictionJLabel = new JLabel();
        gameContentPane.add(restrictionJLabel);
        restrictionJLabel.setSize(200, 200);
        restrictionJLabel.setLocation(buttonXLocation, 7*buttonYLocationChange);
        restrictionJLabel.setVisible(false);
        
        game = setGame();
        
        board = new GameBoard(setSize(),setDifficulty(),gameJFrame);
        updateRestrictionJLabel();
        restrictionJLabel.setVisible(true);

        gameTimer.schedule(this, 0, 1000);
        
        gameIsReady = true;
        
        resetGameSquaresToSpin();
	}
	
	public int setGame()
	{
		int game;
		Object[] choices = {"Classic", "Timed"};
		int answer = JOptionPane.showOptionDialog(null, "What game would you like to play?", "Game", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);
		if (answer == CLASSIC_GAME)
		{	
			game = 0;
		}
		else
		{	
			game = 1;
		}

		return game;
	}


	public int setSize()
	{	
		int size;
		Object[] choices = {"4X4", "3X3"};
		int answer = JOptionPane.showOptionDialog(null, "What size would you like your board?", "Board Size", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);
		if (answer == BIG_BOARD)
		{
			size = 16;
			//System.out.println("You picked the size to be '" + choices[0] + "'.");
			
			// make board 3x3
		}
		else// if (answer == 1)
		{
			size = 9;
			//System.out.println("You picked the size to be '" + choices[1] + "'.");
			// make board 4x4
		}
		return size;

	}
	
	public int setDifficulty()
	{
		int difficulty;
		Object[] choices = {"Hard", "Medium", "Easy"}; //  want to make easy be choices[0], but it did not come up in the order easy, medium, hard in JOptionPane.

		int answer = JOptionPane.showOptionDialog(null, "What difficulty would you like?", "Difficulty", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);
		if (answer == 0) //they chose Hard
		{	
			difficulty = HARD_DIFFICULTY;
			difficultyOfGame = HARD_DIFFICULTY;
			//System.out.println("You picked the difficulty to be '" + choices[0] + "'.");
			// add restrictions for the hard difficulty
		}
			
		else if (answer == 1) // they chose Medium
		{	
			difficulty = MEDIUM_DIFFICULTY;
			difficultyOfGame = MEDIUM_DIFFICULTY;
			//System.out.println("You picked the difficulty to be '" + choices[1] + "'.");
		}

		
		else// if (difficulty==2) // they chose Easy
		{	
			difficulty = EASY_DIFFICULTY;
			difficultyOfGame = EASY_DIFFICULTY;
			//System.out.println("You picked the difficulty to be '"  +  choices[2] + "'.");
			// no restrictions
		}
		return difficulty;
	}
	
	
	public boolean hasWon()
	{ 
		return board.hasWon();		
	}
	
	public void draw(Color c)
	{
		imageSideLength = (gameJFrameHeight-yMouseOffsetToContentPaneFromJFrame)/board.boardSideLength;
		for(int i = 0; i < board.getSize(); i++)
		{
			GameSquare currentGameSquare = board.playingBoard[i];
			currentGameSquare.setXPosition(imageSideLength*(i%board.boardSideLength));
			currentGameSquare.setYPosition(imageSideLength*(i/board.boardSideLength));
			
			currentGameSquare.drawGameSquare(imageSideLength, c);
		}
	}
	
	public void spinButtonPressed()
	{
		if(gameIsReady)
		{
			if(haveSpin())
			{		
				spinCounter++;
				updateSpinCounterJLabel();
				
				int spinLength = board.getSpinLength(gameSquaresToSpin);
				int spinHeight = board.getSpinHeight(gameSquaresToSpin);
				
				int leftXSpinPosition = board.getLeftXSpinPosition(gameSquaresToSpin);
				int upperYSpinPosition = board.getUpperYSpinPosition(gameSquaresToSpin);
				
				if(board.validSpin(spinLength, spinHeight))
				{
					board.spin(board.playingBoard, spinLength, spinHeight, leftXSpinPosition, upperYSpinPosition);
					
					draw(Color.GREEN);
					
					if(hasWon())
					{
						resetGameSquaresToSpin();
						gameTimer.cancel();
						gameIsReady = false;
						playAgainMessage();
					}
				}
				
				else
				{
					draw(RED);
				}
				
				try 
				{
				Thread.sleep(500);
				}
				catch(InterruptedException e)
				{}

				resetTimeSinceLastSpin();
				
			}
		}
	}
	
	public void updateSpinCounterJLabel()
	{
		spinCounterJLabel.setText("Spins: " + spinCounter);
	}
	
	public void run()
	{
		elapsedTime++;
		updateTimerJLabel();
		
		if(game == 1)
		{
			timeSinceLastSpin++;
			if(timeSinceLastSpin >= TIMETOSPIN)
			{
				boolean madeRandomSpin = false;
				while(!madeRandomSpin)
				{
					madeRandomSpin = board.makeRandomSpin(board.playingBoard);
				}
				resetGameSquaresToSpin();
				resetTimeSinceLastSpin();
			}
		}
	}
	
	public void resetTimeSinceLastSpin()
	{
		timeSinceLastSpin = 0;
	}
	
	public void updateTimerJLabel()
	{
		String timerJLabelText = String.format("%02d:%02d", elapsedTime/60, elapsedTime%60);
		timerJLabel.setText(timerJLabelText);
	}
	
	public void updateRestrictionJLabel()
	{
		String restrictionMessage= "";
		
		if(difficultyOfGame==2)
		{
			restrictionMessage = " In the grid, the 9 is in bold text, do not mistake it for the 6! You have chosen the 'Hard' difficulty. This means that you cannot rotate 1 square by 2 square rectangles.";
		}
		else if (difficultyOfGame==1)
		{
			restrictionMessage = "In the grid, the 9 is in bold text, do not mistake it for the 6! You have chosen the 'Medium' difficulty."
					+ "This means that you cannot rotate 1 square by 1 square rectangles.";
		}
		else
		{
			restrictionMessage = " In the grid, the 9 is in bold text, do not mistake it for the 6! You have chosen the 'Easy' difficulty. This means that you do not have any spin restrictions, you can rotate all rectangles!";
		}
		
		restrictionJLabel.setText("<html>"+ restrictionMessage+"</html>");
	}
	public boolean haveSpin()
	{
		boolean haveSpin = false;
		if(gameSquaresToSpin[0] != -1 && gameSquaresToSpin[1] != -1)
		{
			haveSpin = true;
		}
		return haveSpin;
	}
	
	public void resetButtonPressed()
	{
		board.resetGame();
		resetGameSquaresToSpin();
		//draw(Color.WHITE);
	}
	
	public void gameSquarePressed(int gameSquarePosition)
	{
		if(gameSquaresToSpin[0] == -1 || (gameSquaresToSpin[0] != -1 && gameSquaresToSpin[1] != -1))
		{
			resetGameSquaresToSpin();
			gameSquaresToSpin[0] = gameSquarePosition;
		}
		else
		{
			gameSquaresToSpin[1] = gameSquarePosition;
			if(haveSpin())
			{
				int spinLength = board.getSpinLength(gameSquaresToSpin);
				int spinHeight = board.getSpinHeight(gameSquaresToSpin);
				int spinXPosition = board.getLeftXSpinPosition(gameSquaresToSpin);
				int spinYPosition = board.getUpperYSpinPosition(gameSquaresToSpin);
				
				board.setSelected(board.playingBoard, spinLength, spinHeight, spinXPosition, spinYPosition);
				draw(BLUE);
				
			}
		}
	}
	
	public void newGameButtonPressed()
	{
		makeNewGame();
	}
	
	public void makeNewGame()
	{
		setGameJFrameLocation();
		gameJFrame.dispose();
		GameController game = new GameController();
	}
	
	public void setGameJFrameLocation()
	{
		Point currentGameJFrameLocation = gameJFrame.getLocationOnScreen();
		int currentGameJFrameXLocation = (int) currentGameJFrameLocation.getX();
		int currentGameJFrameYLocation = (int) currentGameJFrameLocation.getY();
		gameJFrameXPosition = currentGameJFrameXLocation;
		gameJFrameYPosition = currentGameJFrameYLocation;
	}
	
	public void resetGameSquaresToSpin()
	{
		gameSquaresToSpin[0] = -1;
		gameSquaresToSpin[1] = -1;
		for(int i = 0; i < board.getSize(); i++)
		{
			board.playingBoard[i].setSelected(false);
		}
		draw(Color.WHITE);
	}
	
	public void playAgainMessage()
	{
		Object[] choices = {"Yes", "No"};
		int answer = JOptionPane.showOptionDialog(null, "You won!\nPlay again?", "Play again?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);
		if(answer == 0)
		{
			makeNewGame();
		}
		else
		{
			gameJFrame.dispose();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(e.getSource() == spinButton)
		{
			spinButtonPressed();
			resetGameSquaresToSpin();
		}
		else if(e.getSource() == resetButton)
		{
			resetButtonPressed();
			resetGameSquaresToSpin();
		}
		else if(e.getSource() == newGameButton)
		{
			newGameButtonPressed();
			resetGameSquaresToSpin();
		}
		else
		{
			boolean gameSquarePressed = false;
			
			for(int i = 0; i < board.getSize(); i++)
			{
				GameSquare currentGameSquare = board.playingBoard[i];
				if(currentGameSquare.isGameSquarePushed(e.getX() - xMouseOffsetToContentPaneFromJFrame,e.getY() - yMouseOffsetToContentPaneFromJFrame))
				{
					gameSquarePressed = true;
					gameSquarePressed(i);
				}
			}
			if(gameSquarePressed == false)
			{
				resetGameSquaresToSpin();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
	}

	
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
	}
	public static void main (String args[]) 
	{
		GameController game = new GameController();
	}
	
}
