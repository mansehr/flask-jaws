package se.mansehr.flaskjaws.pluginclasses.pluginobjects;

import java.awt.Color;

public abstract class FJMovingObject extends FJObject 
{
    private float dy, dx;
    private float speed;
    
	public FJMovingObject(int x, int y, int width, int height, float dX,
			float dY, Color color)
	{
		super(x, y, width, height, color);

        this.speed = 2;
        this.dx = dX;
        this.dy = dY;
	}

	/**
	 * Default constructor set all values to zero.
	 *
	 */
	public FJMovingObject()
	{
		super();
		this.speed = 0;
	    this.dx = 0;
	    this.dy = 0;
	}
    
    public void setDY(float init)
    {
        this.dy = init;
    }
    
    public void setDX(float init)
    {
        this.dx = init;
    }
    
    public void setSpeed(float newSpeed)
    {
        speed = newSpeed;
    }
    
    public float getDY()
    {
        return this.dy;
    }
    
    public float getDX()
    {
        return this.dx;
    }
    
    public float getSpeed()
    {
        return speed; 
    }
    
    public void move()
    {
        setX(getNextX());
        setY(getNextY());
    }
    
    public int getNextX()
    {
    	return getX() + (int)(dx * speed);
    }
    
    public int getNextY()
    {
    	return getY() + (int)(dy * speed);
    }
	
	public boolean isNextYMoveValid()
	{
		return isInsideBounds(getX(), getNextY());
	}
	
	public boolean isNextXMoveValid()
	{
		return isInsideBounds(getNextX(), getY());
	}
}