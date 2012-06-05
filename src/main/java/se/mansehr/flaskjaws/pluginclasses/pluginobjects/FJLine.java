package se.mansehr.flaskjaws.pluginclasses.pluginobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;

public class FJLine extends FJObject 
{
	Stroke stroke;
	
	public FJLine(int x, int y, int width, int height, Color color,
			int thickness)
    {
        this(x, y, width, height, color);
        stroke = new BasicStroke(thickness);
    }
	
	public FJLine(int x, int y, int width, int height, Color color)
    {
        super(x, y, width, height, color);
        stroke = new BasicStroke(1);
    }
    
	public FJLine()
	{
		super();
		stroke = new BasicStroke(1);
	}
	
	
	public void paint(Graphics2D g)
    {
		if (isVisible()) {
			Color tempColor = g.getColor();
			Stroke tempStroke = g.getStroke();
			g.setColor(getColor());
			g.setStroke(getStroke());
			paintObject(g);
			g.setColor(tempColor);
			g.setStroke(tempStroke);
		}
    }
	
	public void paint(Graphics g)
	{
		this.paint((Graphics2D)g);
	}
	
	public void paintObject(Graphics2D g)
	{
		g.drawLine(getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * @return the stroke
	 */
	public Stroke getStroke()
	{
		return stroke;
	}

	/**
	 * @param stroke the stroke to set
	 */
	public void setStroke(Stroke stroke)
	{
		this.stroke = stroke;
	}
}