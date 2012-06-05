package se.mansehr.flaskjaws.pluginclasses.pluginobjects;

import java.awt.Graphics;
import java.awt.Color;

public class FJBall extends FJMovingObject
{
    /**
     * Constructor for objects of class Ball
     */
    public FJBall(int x, int y, int width, int height, float dX, float dY, 
    		Color color)
    {
        super(x, y, width, height, dX, dY, color);
    }
    
    /**
     * Constructor for objects of class Ball sets dy & dx to zero(0)
     */
    public FJBall(int x, int y, int width, int height)
    {
        this(x, y, width, height, 0, 0, Color.black);
    }
    
    public void paint(Graphics g)
    {
        if (isVisible()) {
        	Color c = g.getColor();
            g.setColor(getColor());
            g.fillOval(getX(), getY(), getWidth(), getHeight());
            g.setColor(c);
        }
    }
}