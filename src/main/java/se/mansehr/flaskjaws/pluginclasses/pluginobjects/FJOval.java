package se.mansehr.flaskjaws.pluginclasses.pluginobjects;

import java.awt.Color;
import java.awt.Graphics2D;

public class FJOval extends FJLine {

	public FJOval(int x, int y, int width, int height, Color color,
			int thickness)
	{
		super(x, y, width, height, color, thickness);
	}

	public FJOval(int x, int y, int width, int height, Color color)
	{
		super(x, y, width, height, color);
	}

	public FJOval()
	{
		super();
	}
	
	public void paintObject(Graphics2D g)
	{
		if (isVisible()) {
			Color c = g.getColor();
			g.setColor(getColor());
			g.drawOval(getX(), getY(), getWidth(), getHeight());
			g.setColor(c);
		}
	}
}