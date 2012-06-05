package se.mansehr.flaskjaws.pluginclasses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JPanel;

import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJObject;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJSprite;

/**
 * This is the panel that all plugins use by default.
 * This is an extended JPanel that also can handle/paint all the FJPLuginObjects.
 * @author Andreas Sehr
 */
public class FJMainPanel extends JPanel 
{
	private static final long serialVersionUID = 3575448509383929608L;
	
	private final Color STANDARD_COLOR = Color.WHITE;
	private Color backGroundColor;
	private ArrayList<FJObject> fjObjects;
	
	public FJMainPanel()
	{
		init();
	}

	public FJMainPanel(LayoutManager arg0)
	{
		super(arg0);
		init();
	}

	public FJMainPanel(boolean arg0)
	{
		super(arg0);
		init();
	}

	public FJMainPanel(LayoutManager arg0, boolean arg1)
	{
		super(arg0, arg1);
		init();
	}
	
	private void init()
	{
		setBackgroundColor(STANDARD_COLOR);
		fjObjects = new ArrayList<FJObject>();
	}
	
	public Color getBackgroundColor()
	{
		return backGroundColor;
	}
	
	public void setBackgroundColor(Color color)
	{
		backGroundColor = color;
	}
	
	public boolean add(FJObject fjObject)
	{
		return fjObjects.add(fjObject);
	}
	
	public boolean remove(FJObject fjObject)
	{
		return fjObjects.remove(fjObject);
	}
	
	public void paint(Graphics g)
	{	
		g.setColor(getBackgroundColor());
		g.fillRect(0,0,getWidth(), getHeight());
		
		for(FJObject obj : fjObjects) {
			if (FJSprite.class.isInstance(obj)) {
				((FJSprite)obj).paint(g, this);
			} else {
				obj.paint(g);
			}
		}
		super.paintComponents(g);
	}
}
