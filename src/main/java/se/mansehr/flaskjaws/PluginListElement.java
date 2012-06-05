package se.mansehr.flaskjaws;

import java.io.File;

import se.mansehr.flaskjaws.PluginLoader.DynamicPlugin;

public class PluginListElement
{
	public final Class<?> plugin;
	public final String name;
	public final File file;
	public final boolean isDynamicalyLoaded;
	
	private PluginListElement(Class<?> plugin, String name, File file)
	{
		this.plugin = plugin;
		this.name = name;
		this.file = file;
		isDynamicalyLoaded = (file != null);
	}
	
	
	/**
	 * Default make for ordinary pluginclasses
	 * @param plugin
	 * @returna a new PluginList Element with the right values
	 */
	public static PluginListElement make(Class<?> plugin)
	{
		return make(plugin, null);
	}
	
	/**
	 * Make for classes dynamicaly loaded from jar libraries
	 * @param plugin
	 * @return a new PluginList Element with the right values
	 */
	public static PluginListElement make(DynamicPlugin plugin)
	{
		return make(plugin.pluginClass, plugin.fileHolder);
	}
	
	/**
	 * Creates the pluginlist element
	 * @param plugin
	 * @param file
	 * @return a new PluginList Element with the right values
	 */
	private static PluginListElement make(Class<?> plugin, File file)
	{
		String s = plugin.getName();
		String name = s.substring(s.lastIndexOf(".") + 1);
		
		if(file == null) {
			name = "*" + name;
		}
		
		return new PluginListElement(plugin, name, file);
	}
}