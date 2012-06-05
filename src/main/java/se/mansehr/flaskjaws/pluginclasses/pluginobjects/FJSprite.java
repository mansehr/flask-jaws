package se.mansehr.flaskjaws.pluginclasses.pluginobjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JPanel;

public class FJSprite extends FJObject
{
    private Image img;

    public FJSprite(Image initImg)
    {
        img = initImg;       
    }

    public void setImage(Image img)
    {
        this.img = img;
    }

    public void paint(Graphics g, JPanel panel)
    {
    	if (img != null)
    		g.drawImage(img, getX(), getY(), panel);
    	else
    		g.fillRect(getX(), getY(), 20, 20);
    }
    
    public void paint(Graphics g)
    {
        if (isVisible()) {
        	Color c = g.getColor();
        	g.setColor(img.getGraphics().getColor());
        	g.fillRect(getX(), getY(), getWidth(), getHeight());
        	g.setColor(c);
        }
    }
}