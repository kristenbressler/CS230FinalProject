import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

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
	
	public void setSelected(boolean selected)
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
	
	public void drawGameSquare(int sideLength, Color c)
	{
		gameSquareJLabel.setVisible(false);
		
		gameSquareJLabel.setBounds(xPosition, yPosition, sideLength, sideLength);
		
		gameSquareJLabel.setText(Integer.toString(getGameSquareNumber()));
		//gameSquareJLabel.setText("<html><u>"+Integer.toString(getGameSquareNumber())+"</u></html>");

		
		gameSquareJLabel.setOpaque(true);
		
		if(isSelected())
			gameSquareJLabel.setBackground(c);
		else
			gameSquareJLabel.setBackground(Color.WHITE);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);

		gameSquareJLabel.setBorder(border);
		
		gameSquareJLabel.setVerticalAlignment(SwingConstants.CENTER);
		gameSquareJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		if(getGameSquareNumber() == 9)
		{
		if(isUpsideDown())
			gameSquareJLabel.setFont(new Font("Dialog Input", Font.BOLD, (int) -sideLength/2));
		else
			gameSquareJLabel.setFont(new Font("Dialog", Font.BOLD, (int) sideLength/2));
		}
		
		else
		{
			if(isUpsideDown())
				gameSquareJLabel.setFont(new Font("Dialog", Font.PLAIN, (int) -sideLength/2));
			else
				gameSquareJLabel.setFont(new Font("Dialog", Font.PLAIN, (int) sideLength/2));
			}
	
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
