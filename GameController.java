import javax.swing.JFrame;
import javax.swing.JOptionPane; // messages are displayed using JOptionPane
import javax.swing.ImageIcon; // messages have an icon
import javax.swing.JButton;

import java.awt.*; // for graphics & MouseListener 
import java.awt.event.*; // need for events and MouseListener
import java.util.TimerTask; // use as a timer 

public class GameController implements MouseListener 
{
	public int spinsMade;
	public int elapsedTime;
	
	private JFrame gameJFrame;
	private Container gameContentPane;
	private java.util.Timer gameTimer = new java.util.Timer();
	
	private int gameJFrameXPosition = 50;
	private int gameJFrameYPosition = 50;
	private int gameJFrameWidth = 1000;
	private int gameJFrameHeight = 1000;
	
	private JButton resetButton;
	private JButton spinButton;
	
	private GameBoard board;
	
	private int [] gameSquaresToSpin = new int [2];
	
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
        
        int [][] temp = {{0},{0}};
        board = new GameBoard(9,1,gameJFrame);
        
        draw();
        
        resetButton = new JButton("Reset");
        gameContentPane.add(resetButton);
        resetButton.addMouseListener(this);
        resetButton.setSize(100,50);
        resetButton.setLocation(750, 200);
        
        spinButton = new JButton("Spin");
        gameContentPane.add(spinButton);
        spinButton.addMouseListener(this);
        spinButton.setSize(100, 50);
        spinButton.setLocation(750,300);
        
	}

	public void setSize()
	{	
		Object[] choices = {"4X4", "3X3"};
		int size = JOptionPane.showOptionDialog(null, "What size would you like your board?", "Board Size", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);
		if (size==0)
			System.out.println("You picked the size to be '" + choices[0] + "'.");
			// make board 3x3
		else if (size==1)
			System.out.println("You picked the size to be '" + choices[1] + "'.");
			// make board 4x4
	}
	
	public void setDifficulty()
	{
		Object[] choices = {"Hard", "Medium", "Easy"}; //  want to make easy be choices[0], but it did not come up in the order easy, medium, hard in JOptionPane.
		int difficulty=JOptionPane.showOptionDialog(null, "What difficulty would you like?", "Difficulty", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);
		if (difficulty==0) //they chose Hard
			{	//System.out.println("You picked the difficulty to be '" + choices[0] + "'.");
				// add restrictions for the hard difficulty
			}
			
		else if (difficulty==1) // they chose Medium
			{	//System.out.println("You picked the difficulty to be '" + choices[1] + "'.");
	
			}
		
		else if (difficulty==2) // they chose Easy
			{	//System.out.println("You picked the difficulty to be '"  +  choices[2] + "'.");
				// no restrictions
			}

		
		
	}
	
	public boolean hasWon()
	{ 
		return board.hasWon();		
	}
	
	public void draw()
	{
		for(int i = 0; i < board.getSize(); i++)
		{
			GameSquare currentGameSquare = board.playingBoard[i];
			currentGameSquare.setXPosition(10+100*(i%3));
			currentGameSquare.setYPosition(10+100*(i/3));
			currentGameSquare.drawGameSquare();
		}
	}
	
	public void spinButtonPressed()
	{
		if(haveSpin())
		{
			System.out.println(gameSquaresToSpin[0]);
			System.out.println(gameSquaresToSpin[1]);
			
			int leftXSpinPosition;
			int rightXSpinPosition;
			int upperYSpinPosition;
			int lowerYSpinPosition;
			
			if(gameSquaresToSpin[0]%3 > gameSquaresToSpin[1]%3)
			{
				leftXSpinPosition = gameSquaresToSpin[1]%3;
				rightXSpinPosition = gameSquaresToSpin[0]%3;
			}
			else 
			{
				leftXSpinPosition = gameSquaresToSpin[0]%3;
				rightXSpinPosition = gameSquaresToSpin[1]%3;
			}
			
			if(gameSquaresToSpin[0]/3 > gameSquaresToSpin[1]/3)
			{
				upperYSpinPosition = gameSquaresToSpin[1]/3;
				lowerYSpinPosition = gameSquaresToSpin[0]/3;
			}
			else
			{
				upperYSpinPosition = gameSquaresToSpin[0]/3;
				lowerYSpinPosition = gameSquaresToSpin[1]/3;
			}
			
			int spinLength = rightXSpinPosition - leftXSpinPosition + 1;
			int spinHeight = lowerYSpinPosition - upperYSpinPosition + 1;
			
			System.out.println("Spin Length: " + spinLength);
			System.out.println("Spin Height: " + spinHeight);

			System.out.println("X Position: " + leftXSpinPosition);
			System.out.println("Y Position: " + upperYSpinPosition);
			
			GameSquare [] squaresSpinning = board.gameSquaresToSpin(spinLength, spinHeight, leftXSpinPosition, upperYSpinPosition);
			
			board.spin(board.playingBoard, squaresSpinning, spinLength, spinHeight, leftXSpinPosition, upperYSpinPosition);
			
			resetGameSquaresToSpin();
			
			draw();
			
			if(hasWon())
			{
				System.out.println("You won");
			}
		}
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
	}
	
	public void gameSquarePressed(int gameSquarePosition)
	{
		System.out.println("GameSquare in position " + gameSquarePosition + " was pushed.");
		if(gameSquaresToSpin[0] == -1)
		{
			gameSquaresToSpin[0] = gameSquarePosition;
		}
		else
		{
			gameSquaresToSpin[1] = gameSquarePosition;
		}
	}
	
	public void resetGameSquaresToSpin()
	{
		gameSquaresToSpin[0] = -1;
		gameSquaresToSpin[1] = -1;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		// Does the reset button get pushed or does the spin button get pushed (if, else)
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(e.getSource() == spinButton)
		{
			spinButtonPressed();
		}
		else if(e.getSource() == resetButton)
		{
			resetButtonPressed();
		}
		else
		{
			boolean gameSquarePressed = false;
			for(int i = 0; i < board.getSize(); i++)
			{
				GameSquare currentGameSquare = board.playingBoard[i];
				if(currentGameSquare.isGameSquarePushed(e.getX(),e.getY()))
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	public static void main (String args[]) 
	{
		GameController game = new GameController();
	}
	
}
