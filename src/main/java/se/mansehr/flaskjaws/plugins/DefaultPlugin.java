package se.mansehr.flaskjaws.plugins;

import java.awt.Dimension;

import javax.swing.JLabel;

import se.mansehr.flaskjaws.pluginclasses.FJPlugin;

/**
 * A plugin class representing the default plugin that is loaded when
 * Flask Jaws is opened. Does nothing.
 */
public class DefaultPlugin extends FJPlugin 
{
	public DefaultPlugin() 
	{
		super("Start");
		getMainPanel().add(new JLabel("Chose a new Flask Jaws plugin" +
				" to load from the file menu."));
		getMainPanel().setPreferredSize(new Dimension(350, 50));
	}
	
	public void start()
	{
		
	}
	
	public void stop()
	{
		
	}
	
	public void pause()
	{
		
	}
	
	public void resume()
	{
		
	}
}