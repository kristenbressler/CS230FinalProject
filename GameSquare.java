import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GameSquare {
	
	private boolean selected;
	private boolean upsideDown;
	private JLabel gameSquareJLabel;
	private int gameSquareNumber;
	private JFrame gameJFrame;
	private int xPosition;
	private int yPosition;
	
	public GameSquare(boolean selected, boolean upsideDown, int gameSquareNumber, JFrame passedInJFrame)
	{
		this.selected = selected;
		this.upsideDown = upsideDown;
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
	
	public void setUpsideDown(boolean upsideDown)
	{
		this.upsideDown = upsideDown;
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
		
		gameSquareJLabel.setOpaque(true);
		
		if(isSelected())
			gameSquareJLabel.setBackground(c);
		else
			gameSquareJLabel.setBackground(Color.WHITE);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);

		gameSquareJLabel.setBorder(border);
		
		gameSquareJLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		gameSquareJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		gameSquareJLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		String gameSquareNumber = String.valueOf(getGameSquareNumber());
		if(gameSquareNumber.contains("9"))
		{
			if(isUpsideDown())
				gameSquareJLabel.setFont(new Font("Dialog", Font.BOLD, (int) -sideLength/2));
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
