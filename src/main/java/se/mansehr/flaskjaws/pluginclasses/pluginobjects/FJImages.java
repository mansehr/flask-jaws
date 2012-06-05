package se.mansehr.flaskjaws.pluginclasses.pluginobjects;

import java.awt.*;
import java.net.*;
import javax.swing.ImageIcon;;


public class FJImages 
{
	/**
	 * Loads a image file no matter where it is. The filename must be valid.
	 * @param itsClass
	 * @param filename
	 * @return
	 */
    public static Image getImage(Class<?> itsClass, String filename) {

        // to read from file
        ImageIcon icon = new ImageIcon(filename);
        

        // try to read from URL
        if ((icon == null) || (icon.getImageLoadStatus() != 
        	MediaTracker.COMPLETE)) {
            try {
                URL url = new URL(filename);
                icon = new ImageIcon(url);
            } catch (Exception e) { 
            	/* not a url */
            }
        }

        //in case file is inside a .jar
        if ((icon == null) || (icon.getImageLoadStatus() != 
        	MediaTracker.COMPLETE)) {
            URL url = itsClass.getResource(filename);
            if (url == null) throw new RuntimeException("image " + filename 
            		+ " not found");
            icon = new ImageIcon(url);
        }
        
        return icon.getImage();
    }
}