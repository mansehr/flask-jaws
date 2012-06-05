package se.mansehr.flaskjaws.pluginclasses.pluginobjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class FJLabel extends FJObject 
{
	private String text;
	private Font font;
	
	public FJLabel(int x, int y, int width, int height, Color color, String text)
	{
		super(x, y, width, height, color);
		this.text = text;
		this.font = null;
	}
	
	public FJLabel(int x, int y, int width, int height, Color color)
	{
		this(x, y, width, height, color, "");
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setFont(Font font)
	{
		this.font = font;
	}
	
	public Font getFont()
	{
		return font;
	}
	
	@Override
	public void paint(Graphics g) 
	{
		if (isVisible()) {
			Color c = g.getColor();
			Font normalFont = null;
			if (font != null) {
				normalFont = g.getFont();
				g.setFont(font);
			}
			if (c != getColor()) {
				g.setColor(getColor());
			}
			
			g.drawString(text, getX(), getY());
			
			if (normalFont != null) {
				g.setFont(normalFont);
			}
			if (c != getColor()) {
				g.setColor(c);
			}
		}
	}
}