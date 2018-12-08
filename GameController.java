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
	private final int HARD_DIFFICULTY=2;
	private final int MEDIUM_DIFFICULTY=1;
	private final int EASY_DIFFICULTY=0;
	
	private final int BIG_BOARD=0;
	
	private final int CLASSIC_GAME=0;
	private final int TIMED_GAME=1;
	
	private int xMouseOffsetToContentPaneFromJFrame = 0;
	private int yMouseOffsetToContentPaneFromJFrame = 0;
	
	private JButton resetButton;
	private JButton spinButton;
	private JButton newGameButton;
	
	private GameBoard board;
	
	private int [] gameSquaresToSpin = new int [2];
	private final int NO_GAME_SQUARE_TO_SPIN = -1;
	
	private int spinCounter=0;
	private JLabel spinCounterJLabel;
	
	private java.util.Timer gameTimer = new java.util.Timer();
	private int elapsedTime = 0;
	private int timerWentOff = 0;
	private final int oneSecond = 1000;
	private JLabel timerJLabel;

	private final int TIMER_INCREMENT = 125;
	private int timeSinceLastSpin = 0;
	private final int TIME_TO_SPIN = 5*oneSecond;
	
	private boolean isPausing;
	private int timePaused = 0;
	private final int TIME_TO_PAUSE = oneSecond;
	
	private JLabel restrictionJLabel;
	
	private int game;
	
	private boolean gameIsReady;
	
	private int squareSideLength;
	
	private static final Color PLAIN_SQUARE_COLOR = new Color(0,0,0);
	private static final Color HIGHLIGHT_SQUARE_COLOR = new Color(0,0,255);
	private static final Color VALID_SPIN_SQUARE_COLOR = new Color(0,255,0);
	private static final Color INVALID_SPIN_SQUARE_COLOR = new Color(255,0,0);
	
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
        restrictionJLabel.setSize(buttonWidth, 4*buttonHeight);
        restrictionJLabel.setLocation(buttonXLocation, 9*buttonYLocationChange);
        restrictionJLabel.setVisible(false);
        
        game = setGame();
        
        board = new GameBoard(setSize(),setDifficulty(),gameJFrame);
        updateRestrictionJLabel();
        restrictionJLabel.setVisible(true);

        gameTimer.schedule(this, 0, TIMER_INCREMENT);
        
        gameIsReady = true;
        
        isPausing = false;
        
        resetGameSquaresToSpin();
	}
	
	public int setGame()
	{
		int game;
		Object[] choices = {"Classic", "Timed"};
		int answer = JOptionPane.showOptionDialog(null, "What game would you like to play?", "Game", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);
		if (answer == TIMED_GAME)
		{	
			game = TIMED_GAME;
		}
		else
		{	
			game = CLASSIC_GAME;
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
		}
		else
		{
			size = 9;
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
		}
		else if (answer == 1)
		{	
			difficulty = MEDIUM_DIFFICULTY;
			difficultyOfGame = MEDIUM_DIFFICULTY;
		}
		else
		{	
			difficulty = EASY_DIFFICULTY;
			difficultyOfGame = EASY_DIFFICULTY;
		}
		return difficulty;
	}
	
	
	public boolean hasWon()
	{ 
		return board.hasWon();		
	}
	
	public void draw(Color c)
	{
		squareSideLength = (gameJFrameHeight-yMouseOffsetToContentPaneFromJFrame)/board.boardSideLength;
		for(int i = 0; i < board.getSize(); i++)
		{
			GameSquare currentGameSquare = board.playingBoard[i];
			currentGameSquare.setXPosition(squareSideLength*(i%board.boardSideLength));
			currentGameSquare.setYPosition(squareSideLength*(i/board.boardSideLength));
			
			currentGameSquare.drawGameSquare(squareSideLength, c);
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
					
					draw(VALID_SPIN_SQUARE_COLOR);
					
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
					draw(INVALID_SPIN_SQUARE_COLOR);
				}

				resetTimeSinceLastSpin();
				isPausing = true;
			}
		}
	}
	
	public void updateSpinCounterJLabel()
	{
		spinCounterJLabel.setText("Spins: " + spinCounter);
	}
	
	public void run()
	{
		timerWentOff++;
		elapsedTime = TIMER_INCREMENT*timerWentOff;

		if((elapsedTime)%oneSecond == 0)
		{
			updateTimerJLabel();
		}
		
		if(isPausing)
		{
			timePaused++;
			if((TIMER_INCREMENT*timePaused) == TIME_TO_PAUSE)
			{
				resetGameSquaresToSpin();
				isPausing = false;
				timePaused = 0;
			}
		}
		
		if(game == TIMED_GAME)
		{
			timeSinceLastSpin++;
			if((TIMER_INCREMENT*timeSinceLastSpin) >= TIME_TO_SPIN)
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
		String timerJLabelText = String.format("%02d:%02d", (elapsedTime/oneSecond)/60, (elapsedTime/oneSecond)%60);
		timerJLabel.setText(timerJLabelText);
	}
	
	public void updateRestrictionJLabel()
	{
		String restrictionMessage= "";
		
		if(difficultyOfGame==HARD_DIFFICULTY)
		{
			restrictionMessage = " In the grid, the 9 is in bold text, do not mistake it for the 6! <BR> You have chosen the 'Hard' difficulty. This means that you cannot rotate 1 square by 2 square rectangles.";
		}
		else if (difficultyOfGame==MEDIUM_DIFFICULTY)
		{
			restrictionMessage = "In the grid, the 9 is in bold text, do not mistake it for the 6! <BR> You have chosen the 'Medium' difficulty."
					+ "This means that you cannot rotate 1 square by 1 square rectangles.";
		}
		else
		{
			restrictionMessage = " In the grid, the 9 is in bold text, do not mistake it for the 6! <BR> You have chosen the 'Easy' difficulty. This means that you do not have any spin restrictions, you can rotate all rectangles!";
		}
		
		Font LabelFont = restrictionJLabel.getFont();
		int componentHeight = restrictionJLabel.getHeight();
		
		int newFontSize = (int)(LabelFont.getSize() *1.1);
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		restrictionJLabel.setFont(new Font(LabelFont.getName(), Font.PLAIN, fontSizeToUse));
		restrictionJLabel.setText("<html>"+ restrictionMessage+"</html>");
	}
	public boolean haveSpin()
	{
		boolean haveSpin = false;
		if(gameSquaresToSpin[0] != NO_GAME_SQUARE_TO_SPIN && gameSquaresToSpin[1] != NO_GAME_SQUARE_TO_SPIN)
		{
			haveSpin = true;
		}
		return haveSpin;
	}
	
	public void resetButtonPressed()
	{
		board.resetGame();
		resetGameSquaresToSpin();
	}
	
	public void gameSquarePressed(int gameSquarePosition)
	{
		if(gameSquaresToSpin[0] == NO_GAME_SQUARE_TO_SPIN || (gameSquaresToSpin[0] != NO_GAME_SQUARE_TO_SPIN && gameSquaresToSpin[1] != NO_GAME_SQUARE_TO_SPIN))
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
				draw(HIGHLIGHT_SQUARE_COLOR);
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
		gameSquaresToSpin[0] = NO_GAME_SQUARE_TO_SPIN;
		gameSquaresToSpin[1] = NO_GAME_SQUARE_TO_SPIN;
		for(int i = 0; i < board.getSize(); i++)
		{
			board.playingBoard[i].setSelected(false);
		}
		draw(PLAIN_SQUARE_COLOR);
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
		isPausing = false;
		timePaused = 0;
		if(e.getSource() == spinButton)
		{
			spinButtonPressed();
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
