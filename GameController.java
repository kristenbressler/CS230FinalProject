import javax.swing.JFrame;
import javax.swing.JOptionPane; // messages are displayed using JOptionPane
import javax.swing.ImageIcon; // messages have an icon
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
        board = new GameBoard(9,1,temp, gameJFrame);
        
        draw();
	}
	
	public int setSize()
	{	int boardSize;
		Object[] choices = {"3x3", "4x4"};
		JOptionPane.showOptionDialog(null, "What size would you like your board?", "Board Size", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);
		
	}
	
	public int setDifficulty()
	{
		int difficulty;
		
		return difficulty;
		
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
			currentGameSquare.drawGameSquare(10,10);
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
		// TODO Auto-generated method stub
		
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
