package se.mansehr.flaskjaws;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import se.mansehr.flaskjaws.pluginclasses.FJPlugin;

/**
 * Looks up and loads flaskjawsplugins dynamicaly.
 * 
 * @author Andreas Sehr
 */
public class PluginLoader 
{
	public static class DynamicPlugin
	{
		public final Class<?> pluginClass;
		public final File fileHolder;
		
		public DynamicPlugin(Class<?> pluginClass, File fileHolder)
		{
			this.pluginClass = pluginClass;
			this.fileHolder = fileHolder;
		}
	}
	
	/**
	 * Iterates through the default directory("UserDir"/Plugins)
	 * @return
	 */
	public static ArrayList<DynamicPlugin> loadPlugins()
	{
		return loadPlugins(System.getProperty("user.dir") + File.separator +
				"Plugins");
	}
	
	/**
	 * Iterates through all *.jar files in a specified directory. if a jar file
	 * is found then this method tries to get a FJPLugin class from it.
	 * @param path
	 * @return
	 */
	public static ArrayList<DynamicPlugin> loadPlugins(String path) 
	{
		ArrayList<DynamicPlugin> plugins = new ArrayList<DynamicPlugin>();
		try {
			File jardir = new File(path); 
			if (jardir.exists()) {
				for (File f : jardir.listFiles(new FJFileFilter("jar"))) {
					Class<?>  plugin = loadClassInJar(f);
					if (plugin != null) {
						plugins.add(new DynamicPlugin(plugin, f));
					}
						
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return plugins;
	}
	
	/**
	 * Finds one pluginfile from the jar file and then returns it
	 * If no plugin file is found in the jar then it returns null 
	 * @param jarFile
	 * @return
	 */
	private static Class<?> loadClassInJar(File jarFile)
	{
		//File jarfile = new File(jarFilePath);
		Class<?> pluginClass;
		//FJPlugin plugin;
		FJFileFilter classFilter = new FJFileFilter("class");
		Class<?>[] interfaces;
		String pluginPath;
		JarFile fileinjar = null;
		try {
			FJPluginLoader pluginLoader = new FJPluginLoader(new URL[] 
			                                        {jarFile.toURI().toURL()});
			//System.out.println("JarFile: "+jarFile.toURI().toURL());
			fileinjar = new JarFile(jarFile);
			Enumeration<JarEntry> all = fileinjar.entries();
			while (all.hasMoreElements()) {
				JarEntry jarentry = all.nextElement();
				if(classFilter.accept(jarentry.getName())) { //.class file?
					pluginPath = jarentry.getName().replace('/','.');
					
					pluginClass = pluginLoader.findClass(
							pluginPath.substring(0, pluginPath.length() - 6));
					interfaces = pluginClass.getSuperclass().getInterfaces();
					
					for (int i = 0; i < interfaces.length; i++) {
						if (interfaces[i].getName().equals(
								"flaskJaws.PluginClasses.FJPluginInterface")) {
							return pluginClass;
						}	
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (fileinjar != null) {
					fileinjar.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Looks up the specified class in its jar file and loads it dynamicaly by 
	 * calling Class.newInstance(). 
	 * @param dp
	 * @return
	 */
	public static FJPlugin createPluginFromJar(DynamicPlugin dp)
	{
		try {
			FJPluginLoader pluginLoader = new FJPluginLoader(new URL[] 
			                                  {dp.fileHolder.toURI().toURL()});
			Class<?> c = pluginLoader.findClass(dp.pluginClass.getName());
			return (FJPlugin) c.newInstance();
		} catch(Exception ex) {
			System.out.println("Error loading class by url.");
		}
		return null;
	}
	
	/**
	 * Private class for selecting only the right files
	 * 
	 * @author Andreas Sehr
	 */
	private static class FJFileFilter implements FileFilter
	{
		private String extension;
		
		/**
		 * New filefilter that selects files with the right extensions
		 * @param extension
		 */
		public FJFileFilter (String extension)
		{
			this.extension = extension;
		}
		
		public boolean accept (File pathname)
		{
			return accept (pathname.getName());
		}
		
		public boolean accept (String pathname)
		{
			return pathname.endsWith(extension);
		}
		
		@SuppressWarnings("unused")
		public String getExtension()
		{
			return extension;
		}
	}
}