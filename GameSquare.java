import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GameSquare {
	
	private boolean selected;
	private boolean upsideDown;
	private ImageIcon rightSideUpImage;
	private ImageIcon upsideDownImage;
	private JLabel gameSquareJLabel;
	//private int[] upperLeft;
	private int gameSquareNumber;
	
	public GameSquare(boolean selected, boolean upsideDown, ImageIcon rightSideUpImage, ImageIcon upsideDownImage, int gameSquareNumber)

	{
		this.selected = selected;
		this.upsideDown = upsideDown;
		this.upsideDownImage = upsideDownImage;
		this.rightSideUpImage = rightSideUpImage;
		//this.upperLeft = upperLeft;
		this.gameSquareNumber = gameSquareNumber;
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
	
	public void drawGameSquare()
	{
		if(!isUpsideDown())
			gameSquareJLabel.setIcon(rightSideUpImage);
		else
			gameSquareJLabel.setIcon(upsideDownImage);
	}

}
