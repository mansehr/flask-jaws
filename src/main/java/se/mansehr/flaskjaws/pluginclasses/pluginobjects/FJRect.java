package se.mansehr.flaskjaws.pluginclasses.pluginobjects;

import java.awt.Graphics;
import java.awt.Color;

public class FJRect extends FJObject 
{
	public FJRect(int x, int y, int width, int height, Color color)
    {
        super(x,y,width,height, color);
    }
    
	public FJRect()
	{
		super();
	}
	
	public void paint(Graphics g)
    {
		if (isVisible()) {
			Color c = g.getColor();
			g.setColor(getColor());
			g.fillRect(getX(), getY(), getWidth(), getHeight());
			g.setColor(c);
		}
    }
}