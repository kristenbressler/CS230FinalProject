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
	private int xPosition;
	private int yPosition;
	
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
	
	public void setXPosition(int xPosition)
	{
		this.xPosition = xPosition;
	}
	
	public void setYPosition(int yPosition)
	{
		this.yPosition = yPosition;
	}
	
	public void drawGameSquare()
	{
		gameSquareJLabel.setVisible(false);
		
		ImageIcon currentImage = getCurrentImage();
		
		gameSquareJLabel.setIcon(currentImage);
		gameSquareJLabel.setBounds(xPosition, yPosition, currentImage.getIconWidth(), currentImage.getIconHeight());
		
		gameSquareJLabel.setVisible(true);
	}
	
	private ImageIcon getCurrentImage()
	{
		ImageIcon currentImage;
		
		if(!isUpsideDown())
		{
			currentImage = rightSideUpImage;
		}
		else
		{
			currentImage = upsideDownImage;
		}
		
		return currentImage;
	}
	
	public boolean isGameSquarePushed(int xMousePosition, int yMousePosition)
	{
		ImageIcon currentImage = getCurrentImage();
		
		int topLeft = xPosition;
		int topRight = xPosition + currentImage.getIconWidth();
		
		int bottomLeft = yPosition;
		int bottomRight = yPosition + currentImage.getIconHeight();
		
        if ((xPosition <= xMousePosition && xMousePosition <= xPosition + currentImage.getIconWidth())
            && (yPosition <= yMousePosition && yMousePosition <= yPosition + currentImage.getIconHeight()))
        {
                return true;
        }
        else 
        {
            return false;
        }
    }

}
