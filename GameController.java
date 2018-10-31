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
				// add restrictions for medium difficulty
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
