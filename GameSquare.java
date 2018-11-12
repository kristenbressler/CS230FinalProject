import java.awt.Color;
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
	
	public void drawGameSquare(int sideLength)
	{
		gameSquareJLabel.setVisible(false);
		
		gameSquareJLabel.setBounds(xPosition, yPosition, sideLength, sideLength);
		
		ImageIcon currentImageIcon = getCurrentImage();
		Image currentImage = currentImageIcon.getImage();
		
		Image currentImageResized = currentImage.getScaledInstance(gameSquareJLabel.getWidth(), gameSquareJLabel.getHeight(), currentImage.SCALE_SMOOTH);
		
		ImageIcon currentImageIconResized = new ImageIcon(currentImageResized);
		
		gameSquareJLabel.setIcon(currentImageIconResized);
		
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
		boolean isGameSquarePushed = false;
        if ((xPosition <= xMousePosition && xMousePosition <= xPosition + gameSquareJLabel.getWidth())
            && (yPosition <= yMousePosition && yMousePosition <= yPosition + gameSquareJLabel.getHeight()))
        {
                isGameSquarePushed = true;
        }
         return isGameSquarePushed;
    }

}
