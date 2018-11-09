import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane; // messages are displayed using JOptionPane
import javax.swing.ImageIcon; // messages have an icon
import javax.swing.JButton;

import java.awt.*; // for graphics & MouseListener 
import java.awt.event.*; // need for events and MouseListener
import java.util.TimerTask; // use as a timer 

public class GameController extends TimerTask implements MouseListener 
{	
	private JFrame gameJFrame;
	private Container gameContentPane;
	
	private int gameJFrameXPosition = 50;
	private int gameJFrameYPosition = 50;
	private int gameJFrameWidth = 1200;
	private int gameJFrameHeight = 900;
	
	private int xMouseOffsetToContentPaneFromJFrame = 0;
	private int yMouseOffsetToContentPaneFromJFrame = 0;
	
	private JButton resetButton;
	private JButton spinButton;
	
	private GameBoard board;
	
	private int [] gameSquaresToSpin = new int [2];
	
	private int spinCounter = 0;
	private JLabel spinCounterJLabel;
	
	private java.util.Timer gameTimer = new java.util.Timer();
	private int elapsedTime = 0;
	private JLabel timerJLabel;
	
	private boolean gameIsReady;
	
	private int imageSideLength;
	
	private Graphics graphics;
	private Graphics2D rectangleAroundSelectedSquares = (Graphics2D)graphics;
	
	private static final Color RED = new Color(255,0,0);
	private static final Color GREEN = new Color(0,255,0);;
	private static final Color BLUE = new Color(0,0,255);
	
	public GameController()
	{
		gameJFrame = new JFrame();
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
        
        resetButton = new JButton("Reset");
        gameContentPane.add(resetButton);
        resetButton.addMouseListener(this);
        resetButton.setSize(100,50);
        resetButton.setLocation(gameJFrameWidth - yMouseOffsetToContentPaneFromJFrame - 10 - 100, 200);
        
        spinButton = new JButton("Spin");
        gameContentPane.add(spinButton);
        spinButton.addMouseListener(this);
        spinButton.setSize(100, 50);
        spinButton.setLocation(gameJFrameWidth - yMouseOffsetToContentPaneFromJFrame - 10 - 100,300);
        
        spinCounterJLabel = new JLabel();
        gameContentPane.add(spinCounterJLabel);
        spinCounterJLabel.setSize(100, 20);
        spinCounterJLabel.setLocation(gameJFrameWidth - yMouseOffsetToContentPaneFromJFrame - 10 - 100, 100);
        updateSpinCounterJLabel();
        
        timerJLabel = new JLabel();
        gameContentPane.add(timerJLabel);
        timerJLabel.setSize(100, 20);
        timerJLabel.setLocation(gameJFrameWidth - yMouseOffsetToContentPaneFromJFrame - 10 - 100, 150);
        updateTimerJLabel();
        
        //gameJFrame.add(rectangleAroundSelectedSquares);
        //gameContentPane.add(rectangleAroundSelectedSquares);
        
        board = new GameBoard(setSize(),setDifficulty(),gameJFrame);
        
        draw();

        gameTimer.schedule(this, 0, 1000);
        
        gameIsReady = true;
	}

	public int setSize()
	{	
		int size;
		Object[] choices = {"4X4", "3X3"};
		int answer = JOptionPane.showOptionDialog(null, "What size would you like your board?", "Board Size", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);
		if (answer == 0)
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
			difficulty = 2;
			//System.out.println("You picked the difficulty to be '" + choices[0] + "'.");
			// add restrictions for the hard difficulty
		}
			
		else if (answer == 1) // they chose Medium
		{	
			difficulty = 1;
			//System.out.println("You picked the difficulty to be '" + choices[1] + "'.");
		}
		
		else// if (difficulty==2) // they chose Easy
		{	
			difficulty = 0;
			//System.out.println("You picked the difficulty to be '"  +  choices[2] + "'.");
			// no restrictions
		}
		return difficulty;
	}
	
	public boolean hasWon()
	{ 
		return board.hasWon();		
	}
	
	public void draw()
	{
		imageSideLength = (gameJFrameHeight-yMouseOffsetToContentPaneFromJFrame)/board.boardSideLength;
		for(int i = 0; i < board.getSize(); i++)
		{
			GameSquare currentGameSquare = board.playingBoard[i];
			currentGameSquare.setXPosition(imageSideLength*(i%board.boardSideLength));
			currentGameSquare.setYPosition(imageSideLength*(i/board.boardSideLength));
			currentGameSquare.drawGameSquare(imageSideLength);
		}
	}
	
	public void drawRectAroundSelectedSquares(Color color)
	{
		int xSpinPosition = board.getLeftXSpinPosition(gameSquaresToSpin);
		int ySpinPosition = board.getUpperYSpinPosition(gameSquaresToSpin);
		int length = board.getSpinLength(gameSquaresToSpin);
		int height = board.getSpinHeight(gameSquaresToSpin);
		
		/*graphics = gameContentPane.getGraphics();
		
		graphics.setColor(color);
		
		rectangleAroundSelectedSquares.setStroke(new BasicStroke(3F));
		
		rectangleAroundSelectedSquares.drawRect(xSpinPosition,ySpinPosition, length, height);*/
	}
	
	public void eraseRectAroundSelectedSquares()
	{
		/*graphics.drawRect(0,0,0,0);*/
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
					drawRectAroundSelectedSquares(GREEN);
					
					board.spin(board.playingBoard, spinLength, spinHeight, leftXSpinPosition, upperYSpinPosition);
					
					draw();
					
					/*try 
					{
					Thread.sleep(500);
					}
					catch(InterruptedException e)
					{}*/
					
					//eraseRectAroundSelectedSquares();
					
					resetGameSquaresToSpin();
					
					if(hasWon())
					{
						gameTimer.cancel();
						gameIsReady = false;
						System.out.println("You won");
					}
				}
				else
				{
					drawRectAroundSelectedSquares(RED);
					
					try 
					{
					Thread.sleep(500);
					}
					catch(InterruptedException e)
					{}
					
					eraseRectAroundSelectedSquares();
				}
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
	}
	
	public void updateTimerJLabel()
	{
		String timerJLabelText = String.format("%02d:%02d", elapsedTime/60, elapsedTime%60);
		timerJLabel.setText(timerJLabelText);
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
		draw();
	}
	
	public void gameSquarePressed(int gameSquarePosition)
	{
		if(gameSquaresToSpin[0] == -1)
		{
			gameSquaresToSpin[0] = gameSquarePosition;
		}
		else
		{
			gameSquaresToSpin[1] = gameSquarePosition;
			if(haveSpin())
			{
				drawRectAroundSelectedSquares(BLUE);
			}
		}
	}
	
	public void resetGameSquaresToSpin()
	{
		gameSquaresToSpin[0] = -1;
		gameSquaresToSpin[1] = -1;
		eraseRectAroundSelectedSquares();
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
