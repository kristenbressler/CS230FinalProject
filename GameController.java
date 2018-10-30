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
	private Container gameContenetPane;
	private java.util.Timer gameTimer = new java.util.Timer();
	
	private GameBoard board;
	
	public GameController()
	{
		
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
		
	}
	
}
