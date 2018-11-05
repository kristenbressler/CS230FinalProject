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
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		// Does the reset button get pushed or does the spin button get pushed (if, else)
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		for(int i = 0; i < board.getSize(); i++)
		{
			GameSquare currentGameSquare = board.playingBoard[i];
			if(currentGameSquare.isGameSquarePushed(e.getX(),e.getY()))
					System.out.println("GameSquare " + currentGameSquare.getGameSquareNumber() + " was pushed.");
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
