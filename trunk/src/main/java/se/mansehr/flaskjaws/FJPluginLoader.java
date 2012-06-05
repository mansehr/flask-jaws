package se.mansehr.flaskjaws;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;


/**
 * My own urlclass loader. 
 * The only thing this does is making the method findClass(String s) public. 
 * Dont do much right now but its good to have...
 * 
 * @author Andreas Sehr
 *
 */
public class FJPluginLoader extends URLClassLoader
{

	public FJPluginLoader(URL[] arg0)
	{
		super(arg0);
	}

	public FJPluginLoader(URL[] arg0, ClassLoader arg1)
	{
		super(arg0, arg1);
	}

	public FJPluginLoader(URL[] arg0, ClassLoader arg1, URLStreamHandlerFactory
			arg2)
	{
		super(arg0, arg1, arg2);
	}
	
	/**
	 * Searches the url after the right class and if it is found then it is 
	 * returned.
	 */
	public Class<?> findClass(String s) throws ClassNotFoundException
	{
		return super.findClass(s);
	}
}