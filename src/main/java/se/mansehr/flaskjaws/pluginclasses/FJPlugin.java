package se.mansehr.flaskjaws.pluginclasses;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import se.mansehr.flaskjaws.settings.FJSettings;

/**
 * The super class FJPlugin is the heart of FLaskJaws.
 * All plugins extends from this plugin. It controlls the name and the JPanels.
 * It allso has the keyboard variable. Some of the methods has to be overided 
 * by its cildren to get the right function i the plugin.  
 */
public abstract class FJPlugin implements FJPluginInterface
{
	private JPanel mainPanel;
	private FJSettings settings;
	private String pluginName;
	private FJKeyboard keyListener;
	
	
	/**
	 * Default constructor. All plugins MUST have a name
	 */
	public FJPlugin(String name)
	{
		pluginName = name;
		reset();
		keyListener = null;
	}
	
	/**
     * Resets all variables.
     */
    public void reset()
    {
    	if(getMainPanel() != null) {
    		getMainPanel().removeAll();
    	}
    		
    	setMainPanel(new FJMainPanel());
    	setSettings(new FJSettings("Plugin \"" + getName() + "\""));
    }
    
    /**
     * @param p new main JPanel
     */
    public void setMainPanel(JPanel p)
    {
    	mainPanel = p;
    }
    
    /**
     * @param p new settings JPanel
     */
    public void setSettings(FJSettings setting)
    {
    	settings = setting;
    }
    
    /**
     * @return main JPanel
     */
    public JPanel getMainPanel()
    {
    	return mainPanel;
    }
    
    /**
     * @return settings JPanel
     */
    public JPanel getSettingsPanel()
    {
    	return settings.getSettingsPanel();
    }
    
    /**
     * @return settings FJSettings
     */
    public FJSettings getSettings()
    {
    	return settings;
    }
    
    /**
     * @return plugin name
     */
    public String getName()
    {
    	return pluginName;
    }
    
    /**
     * overided toString() returns the plugins name..
     */
    public String toString()
    {
    	return getName();
    }
    
    /**
     * Override this method if the plugin needs input via keyboard.
     * @return false as default value
     */
    public boolean needKeyboardInput()
    {
    	return false;
    }
    
    /**
     * If the plugin inheriting from this class needs support for networking,
     * override this method with a single <code>return true;</code> line. <p>
     * If the plugin overrides this method, {@link #hostMenu()} and {@link
     * #joinMenu()} must also be overwritten.
     * @return false as default in FJPlugin
     */
    public boolean hasNetworkSupport()
    {
    	return false;
    }
    
    
    /**
     * Overwrite this method if the plugin also overwrites {@link
     * #hasNetworkSupport()}. It is invoked when the "Host..." option is pushed
     * from the Network menu. <p>
     * In FJPlugin this method is empty.
     * @see #hasNetworkSupport()
     */
    public void hostMenu()
    {
    	
    }
    
    /**
     * Overwrite this method if the plugin also overwrites {@link
     * #hasNetworkSupport()}. It is invoked when the "Join..." option is pushed
     * from the Network menu. <p>
     * In FJPlugin this method is empty.
     * @see #hasNetworkSupport()
     */
    public void joinMenu()
    {
    	
    }
    
    /**
     * Sets active keylistener
     */
    public void setKeyListner(FJKeyboard keyboard)
    {
    	keyListener = keyboard;
    }
    
    protected boolean[] getKeys()
    {
    	return keyListener.getKeys();
    }
    
    /**
     * Override this method if the plugin need keyboard functions
     * @param e keyEvent 
     */
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	/**
     * Override this method if the plugin need keyboard functions
     * @param e keyEvent 
     */
	public void keyReleased(KeyEvent e)
	{
		
	}
	
	/**
     * Override this method if the plugin need keyboard functions
     * @param e keyEvent 
     */
	public void keyPressed(KeyEvent e)
	{
		
	}
}