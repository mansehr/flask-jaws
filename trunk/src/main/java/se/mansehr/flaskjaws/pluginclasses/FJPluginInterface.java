package se.mansehr.flaskjaws.pluginclasses;

/**
 * The interface on top of the plugin class hierarchy. All plugin classes must
 * implement this interface in some way or it will not run, as simple as that.
 * <p>
 * For more information about creating plugin classes, please view the readme.
 */
public interface FJPluginInterface
{
	/**
	 * When the plugin is loaded, the pluin's implementation of this method
	 * will be invoked. If nothing needs to be done on startup in a plugin,
	 * keep the method empty. For instance you might do all the work in the
	 * constructor, or you could wait for user input, which will make this
	 * method unnecessary.
	 */
	public void start();
	
	/**
	 * Invoked when a plugin is unloaded and another plugin is loaded or when
	 * Flask Jaws is terminated. Useful for shutting down sockets and readers
	 * when you exit your plugin. If nothing needs to be done upon exit or no
	 * cleaning needs to be done, this method may be left empty.
	 * <p>
	 * If a plugin supports networking of any kind, it should call
	 * {@link se.mansehr.flaskjaws.pluginclasses.FJNetwork#close()} in this method.
	 */
	public void stop();
	
	/**
	 * Whenever there's a plugin loaded and you select a menu item from the
	 * menu bar, this method will be called. It should pause the plugin
	 * implementing this method. If no pausing is neccessary, it can be
	 * implemented as an emtpy method.
	 */
	public void pause();
	
	/**
	 * Has the opposite function as {@link #pause()}, it resumes the plugin
	 * once the actions invoked by selecting a menu item is done.
	 */
	public void resume();
}