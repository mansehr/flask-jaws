package se.mansehr.flaskjaws.pluginclasses.pluginobjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class FJObject 
{
	 private Rectangle obj;
	 private Point maxPoint = null;
	 private Point minPoint = null;
	 private boolean visible;
	 private Color color;
	 private Point center;
	 
	 public FJObject(int x, int y, int width, int height, Color color)
	 {
		 this();
		 setXY(x, y);
		 setWidth(width);
		 setHeight(height);
		 setColor(color);
		 calculateCenter();
	 }
	 
	 public FJObject()
	 {
		 obj = new Rectangle();
		 visible = true;
		 color = Color.WHITE;
		 center = new Point();
	 }
	 
    public void setXY(int x, int y)
    {
        setX(x);
        setY(y);
    }
    
    public void setY(int y)
    {
    	int min = Integer.MIN_VALUE;
    	int max = Integer.MAX_VALUE;
    	if (minPoint != null) {
    		min = minPoint.y;
    	}
    	if (maxPoint != null) {
    		max = maxPoint.y;
    	}
    	if (y >= min ) {
    		if (y <= max) {
    			obj.y = y;
    		} else {
    			obj.y = max;
    		}
    	} else {
    		obj.y = min;
    	}
    	calculateCenter();
    }
    
    public void setX(int x)
    {
    	int min = Integer.MIN_VALUE;
    	int max = Integer.MAX_VALUE;
    	if (minPoint != null) {
    		min = minPoint.x;
    	}
    	if (maxPoint != null) {
    		max = maxPoint.x;
    	}
    	if (x >= min ) {
    		if (x <= max) {
    			obj.x = x;
    		} else {
    			obj.x = max;
    		}
    	} else {
    		obj.x = min;
    	}
    	calculateCenter();	
    }
    
    public void setWidth(int width)
    {
        obj.width = width;
        calculateCenter();
    }
    
    public void setHeight(int height)
    {
        obj.height = height;
        calculateCenter();
    } 
    
	public void setColor(Color color)
	{
		this.color = color;
	}
	
    public void moveX(int steps)
    {
    	setX(getX() + steps);
    }
    
    public void moveY(int steps)
    {
    	setY(getY() + steps);
    }
    
    public int getX() 
    { 
    	return obj.x; 
    }
    
    public int getY() 
    { 
    	return obj.y; 
    }
    
    public int getWidth()
    {
        return obj.width;
    }
    
    public int getHeight()
    {
        return obj.height;
    }
    
    public int getLeft()
    {
    	return getX();
    }
    
    public int getTop()
    {
    	return getY();
    }
    
    public int getRight()
    {
    	return getLeft() + getWidth();
    }
    
    public int getBottom()
    {
    	return getTop() + getHeight();
    }
    
    public Color getColor()
	{
		return color;
	}
    
    public void setVisible(boolean visible)
    {
    	this.visible = visible;
    }
    
    public boolean isVisible()
    {
    	return visible;
    }
    
    public Rectangle getRectangle()
    {
    	return obj;
    }
    
    public boolean intersects(FJObject testObj)
    { 
      //Testar top bottom
      if (obj.intersects(testObj.getRectangle())) {
           return true;
      }
      return false;
    }
    
    public abstract void paint(Graphics g);

	/**
	 * @param arg0
	 * @param arg1
	 * @see java.awt.Rectangle#setLocation(int, int)
	 */
	public void setLocation(int arg0, int arg1)
	{
		obj.setLocation(arg0, arg1);
		calculateCenter();
	}

	/**
	 * @param arg0
	 * @see java.awt.Rectangle#setLocation(java.awt.Point)
	 */
	public void setLocation(Point arg0)
	{
		obj.setLocation(arg0);
		calculateCenter();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @see java.awt.geom.Rectangle2D#contains(double, double, double, double)
	 */
	public boolean contains(double arg0, double arg1, double arg2, double arg3)
	{
		return obj.contains(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 * @see java.awt.geom.Rectangle2D#contains(double, double)
	 */
	public boolean contains(double arg0, double arg1)
	{
		return obj.contains(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @see java.awt.Rectangle#contains(int, int, int, int)
	 */
	public boolean contains(int arg0, int arg1, int arg2, int arg3)
	{
		return obj.contains(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 * @see java.awt.Rectangle#contains(int, int)
	 */
	public boolean contains(int arg0, int arg1)
	{
		return obj.contains(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.awt.Rectangle#contains(java.awt.Point)
	 */
	public boolean contains(Point arg0)
	{
		return obj.contains(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.awt.geom.RectangularShape#contains(java.awt.geom.Point2D)
	 */
	public boolean contains(Point2D arg0)
	{
		return obj.contains(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.awt.Rectangle#contains(java.awt.Rectangle)
	 */
	public boolean contains(Rectangle arg0)
	{
		return obj.contains(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.awt.geom.RectangularShape#contains(java.awt.geom.Rectangle2D)
	 */
	public boolean contains(Rectangle2D arg0)
	{
		return obj.contains(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.awt.Rectangle#createIntersection(java.awt.geom.Rectangle2D)
	 */
	public Rectangle2D createIntersection(Rectangle2D arg0)
	{
		return obj.createIntersection(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.awt.Rectangle#createUnion(java.awt.geom.Rectangle2D)
	 */
	public Rectangle2D createUnion(Rectangle2D arg0)
	{
		return obj.createUnion(arg0);
	}

	/**
	 * @return
	 * @see java.awt.Rectangle#getBounds()
	 */
	public Rectangle getBounds()
	{
		return obj.getBounds();
	}

	/**
	 * @return
	 * @see java.awt.Rectangle#getBounds2D()
	 */
	public Rectangle2D getBounds2D()
	{
		return obj.getBounds2D();
	}

	/**
	 * @return
	 * @see java.awt.geom.RectangularShape#getCenterX()
	 */
	public double getCenterX()
	{
		return center.getX();
	}

	/**
	 * @return
	 * @see java.awt.geom.RectangularShape#getCenterY()
	 */
	public double getCenterY()
	{
		return center.getY();
	}

	/**
	 * @return
	 * @see java.awt.geom.RectangularShape#getFrame()
	 */
	public Rectangle2D getFrame()
	{
		return obj.getFrame();
	}

	/**
	 * @return
	 * @see java.awt.Rectangle#getLocation()
	 */
	public Point getLocation()
	{
		return obj.getLocation();
	}

	/**
	 * @return
	 * @see java.awt.Rectangle#getSize()
	 */
	public Dimension getSize()
	{
		return obj.getSize();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @see java.awt.geom.Rectangle2D#intersects(double, double, double,
	 * double)
	 */
	public boolean intersects(double arg0, double arg1, double arg2,
			double arg3)
	{
		return obj.intersects(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.awt.Rectangle#intersects(java.awt.Rectangle)
	 */
	public boolean intersects(Rectangle arg0)
	{
		return obj.intersects(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see 
	 * java.awt.geom.RectangularShape#intersects(java.awt.geom.Rectangle2D)
	 */
	public boolean intersects(Rectangle2D arg0)
	{
		return obj.intersects(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @see java.awt.geom.Rectangle2D#intersectsLine(double, double, double,
	 * double)
	 */
	public boolean intersectsLine(double arg0, double arg1, double arg2,
			double arg3)
	{
		return obj.intersectsLine(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.awt.geom.Rectangle2D#intersectsLine(java.awt.geom.Line2D)
	 */
	public boolean intersectsLine(Line2D arg0)
	{
		return obj.intersectsLine(arg0);
	}

	/**
	 * @param arg0
	 * @see java.awt.Rectangle#setSize(java.awt.Dimension)
	 */
	public void setSize(Dimension arg0)
	{
		obj.setSize(arg0);
		calculateCenter();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see java.awt.Rectangle#setSize(int, int)
	 */
	public void setSize(int arg0, int arg1)
	{
		obj.setSize(arg0, arg1);
		calculateCenter();
	}
	
	public void calculateCenter()
	{
		center.setLocation(obj.getCenterX(), obj.getCenterY());
	}
	
	///////////////////////////// Max & Min Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public void setMinX(int x)
	{
		if (minPoint == null) {
			minPoint = new Point(x, Integer.MIN_VALUE);
		} else {
			minPoint.x = x;
		}
	}
	
	public void setMinY(int y)
	{
		if (minPoint == null) {
			minPoint = new Point(Integer.MIN_VALUE, y);
		} else {
			minPoint.y = y;
		}
	}
	
	public void setMaxX(int x)
	{
		if (maxPoint == null) {
			maxPoint = new Point(x, Integer.MAX_VALUE);
		} else {
			maxPoint.x = x;
		}
	}
	
	public void setMaxY(int y)
	{
		if (maxPoint == null) {
			maxPoint = new Point(Integer.MAX_VALUE, y);
		} else {
			maxPoint.y = y;
		}
	}
	
	/**
	 * @deprecated use isInsideBounds(x, y) insted!
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isOutOfBounds(int x, int y)
	{
		return !isInsideBounds(x,y);
	}
	
	public boolean isInsideBounds(int x, int y)
	{
		if (isInsideBoundsMin(x,y) && isInsideBoundsMax(x,y)) {
			return true;
		} else {
			return false;
		}	
	}
	
	/**
	 * Returns true if parameters x and y is both more than what
	 * is set in the minPoint. 
	 * If minPoint is not set then it also returns true;
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInsideBoundsMin(int x, int y)
	{
		if (minPoint != null) {
			if (x > minPoint.x && y > minPoint.y) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	
	/**
	 * Returns true if parameters x and y is both less than what
	 * is set in the maxPoint. 
	 * If maxPoint is not set then it also returns true;
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInsideBoundsMax(int x, int y)
	{
		if (maxPoint != null) {
			if (x < maxPoint.x && y < maxPoint.y) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	
	public void resetMax()
	{
		maxPoint = null;
	}
	
	public void resetMin()
	{
		minPoint = null;
	}
	
	public void setMinPoint(Point p)
	{
		minPoint = p;
	}
	
	public void setMaxPoint(Point p)
	{
		maxPoint = p;
	}
	
	public void setMinPoint(int x, int y)
	{
		setMinPoint(new Point(x,y));
	}
	
	public void setMaxPoint(int x, int y)
	{
		setMaxPoint(new Point(x,y));
	}
}