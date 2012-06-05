package se.mansehr.flaskjaws.pluginclasses;

import java.util.Random;

/**
 * The FJGamePlugin!
 * This super pluginclass is specialized on simple games. It has some extra
 * methods and variables that are usefull when making games, such as points, 
 * level, random, dead & started. It also uses the same tecnuiqe as 
 * FJPLuginRunnable, a method named tick that will be called at each run. This 
 * Method must be overided by the child class.  
 * See flaskJaws.PluginClasses.FJPluginRunnable
 * @author Andreas Sehr
 */
public abstract class FJGamePlugin extends FJPluginRunnable 
implements FJPluginInterface 
{
	private int points;
	private boolean dead;
	private boolean started;
	private boolean paused;
	protected Random random;
	private int level;

	/**
	 * Default constructor takes name and delay time betwen tick-methodcalls
	 */
	public FJGamePlugin(String name, int delay)
	{
		super(name, delay);
		reset();
	}

	/**
	 * Resets the variables
	 */
	public void reset()
	{
		super.reset();
		points = 0;
		level = 0;
		dead = false;
		started = false;
		paused = false;
		random = new Random(System.currentTimeMillis());
	}

	/**
	 * Sets the points.
	 * @param newPoints the new points value.
	 */
	public void setPoints(int newPoints)
	{
		points = newPoints;
	}

	/**
	 * Add extra points to the point variable
	 * @param i points to add.
	 */
	public void addPoints(int i)
	{
		points += i;
	}

	/**
	 * Returns the actual points
	 * @return the actual points.
	 */
	public int getPoints()
	{
		return points;
	}

	/**
	 * Sets the dead variable
	 * @param dead new dead value
	 */
	public void setDead(boolean dead)
	{
		this.dead = dead;
	}

	/**
	 * Returns if the player is dead
	 * @return if dead variable is true
	 */
	public boolean isDead()
	{
		return dead;
	}

	/**
	 * Increase the level variable by one.
	 *
	 */
	public void nextLevel()
	{
		level++;
	}

	/**
	 * Pause the game plugin
	 */
	public void pause()
	{
		super.pause();
		paused = true;
	}

	/**
	 * Resumes the GamePlugin
	 */
	public void resume()
	{
		super.resume();
		paused = false;
	}

	/**
	 * Is the plugin paused
	 * @return true if the plugin is paused
	 */
	public boolean isPaused()
	{
		return paused;
	}

	/**
	 * Varp to a new level
	 * @param newLevel to warp to (must be more than zero)
	 */
	public void gotoLevel(int newLevel)
	{
		if(newLevel >= 0) {
			level = newLevel;
		}
		//else
		//Throw new IllegalArgumentException("Level out of range");
	}

	/**
	 * Get the level variable
	 * @return the level variable
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * Start the gameplugin
	 */
	public void start()
	{
		super.start();
		setStarted(true);
	}

	/**
	 * @param started new started value
	 */
	public void setStarted(boolean started)
	{
		this.started = started;
	}

	/**
	 * @return if started variable is true
	 */
	public boolean isStarted()
	{
		return started;
	}

	/**
	 * @return true if the active plugin has support for network communications
	 */
	public boolean hasNetworkSupport()
	{
		return false;
	}

	/**
	 * Tick method must be overided by the child-class
	 */
	protected abstract void tick();
}