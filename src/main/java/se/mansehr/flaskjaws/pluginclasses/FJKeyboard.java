package se.mansehr.flaskjaws.pluginclasses;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * FjKeyboard handles all the keyboard inputs.
 * It holds a vector of all keys and sets true if key is pressed. 
 * It also calls the KeyListener method in the activeplugin if it has set 
 * the needKeyBoard() method to true.
 * 
 * @author Andreas Sehr
 */
public class FJKeyboard implements KeyListener
{
	private FJPlugin activePlugin;
	private boolean[] keys;
	
	/**
	 * Plugin that will get all the methodcalls
	 * @param plugin
	 */
	public FJKeyboard(FJPlugin plugin)
	{
		activePlugin = plugin;
		keys = new boolean[101];
    	for(int i = 0; i < keys.length; i++) {
    		keys[i] = false;
    	}
	}
	
	/**
	 * Sets the active plugin
	 * @param p
	 */
	public void setActivePlugin(FJPlugin p)	throws IllegalArgumentException
	{
		if(p == null) {
			throw new IllegalArgumentException(
					"Active plugin must not be null.");
		} else {
			activePlugin = p;
		}
	}
	
	/**
	 * Lets activePlugin handle the event
	 */
    public void keyTyped(KeyEvent e) 
    {
    	activePlugin.keyTyped(e);
    }
    
    /**
     * Lets activePlugin handle the event
     * and records it in the keys variable, sets its position to false. 
     */
    public void keyReleased(KeyEvent e) 
    {
    	keys[e.getKeyCode()] = false;
    	activePlugin.keyReleased(e);
    }
    
    /**
     * Lets activePlugin handle the event
     * and records it in the keys variable, sets its position to true.
     */
    public void keyPressed(KeyEvent e)
    {
    	keys[e.getKeyCode()] = true;
    	activePlugin.keyPressed(e);
    }
    
    /**
     * Get the vector whit all keys. 
     * i.e. keys[KeyEvent.VK_UP] equals true if the up key is pressed and not
     * yet released. 
     * @return
     */
    public boolean[] getKeys()
    {
    	return keys;
    }
}