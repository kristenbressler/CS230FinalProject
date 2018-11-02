import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameSquare {
	
	private boolean selected;
	private boolean upsideDown;
	private ImageIcon rightSideUpImage;
	private ImageIcon upsideDownImage;
	private JLabel gameSquareJLabel;
	//private int[] upperLeft;
	private int gameSquareNumber;
	private JFrame gameJFrame;
	
	public GameSquare(boolean selected, boolean upsideDown, ImageIcon rightSideUpImage, ImageIcon upsideDownImage, int gameSquareNumber, JFrame passedInJFrame)
	{
		this.selected = selected;
		this.upsideDown = upsideDown;
		this.upsideDownImage = upsideDownImage;
		this.rightSideUpImage = rightSideUpImage;
		//this.upperLeft = upperLeft;
		this.gameSquareNumber = gameSquareNumber;
		
		gameJFrame = passedInJFrame;
		gameSquareJLabel = new JLabel();
		gameJFrame.getContentPane().add(gameSquareJLabel);
	}
	
	private boolean isSelected()
	{
		return this.selected;
	}
	
	private void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	
	public boolean isUpsideDown()
	{
		return this.upsideDown;
	}
	
	public void flipGameSquare()
	{
		if(!isUpsideDown())
			this.upsideDown = true;
		else
			this.upsideDown = false;
	}
	
	public int getGameSquareNumber()
	{
		return this.gameSquareNumber;
	}
	
	public void drawGameSquare(int xPosition, int yPosition)
	{
		gameSquareJLabel.setVisible(false);
		if(!isUpsideDown())
		{
			gameSquareJLabel.setIcon(rightSideUpImage);
			gameSquareJLabel.setBounds(xPosition, yPosition, rightSideUpImage.getIconWidth(), rightSideUpImage.getIconHeight());
		}
		else
		{
			gameSquareJLabel.setIcon(upsideDownImage);
			gameSquareJLabel.setBounds(xPosition, yPosition, upsideDownImage.getIconWidth(), upsideDownImage.getIconHeight());

		}
		
		gameSquareJLabel.setVisible(true);
	}

}
