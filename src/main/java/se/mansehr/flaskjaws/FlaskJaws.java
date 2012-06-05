package se.mansehr.flaskjaws;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import se.mansehr.flaskjaws.frames.MainFrame;

/**
 * The class that starts everything. Contains the only main method avaliable.
 */
public class FlaskJaws
{
	private static MainFrame mf;
	
	public static void main(String[] args)
	{
		try {
			mf = new MainFrame();
			mf.setLocation(100,100);
			mf.buildSettings();
			mf.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"Error, Handled by main!",
		       		JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Changes the Look and Feel of Flask Jaws. There is only two L&Fs
	 * avaliable, Java's default cross platform metal look, or the one looking
	 * like the operative system you run Flask Jaws on.
	 * @param isDefaultLF true if Java's default L&F should be enabled.
	 */
	public static void setLookAndFeel(boolean isDefaultLF)
	{
	    try {
	    	if (isDefaultLF) {
		        UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
	    	} else {
	    		UIManager.setLookAndFeel(
	    	            UIManager.getCrossPlatformLookAndFeelClassName());
	    	}
	        SwingUtilities.updateComponentTreeUI(mf);
	    } catch (Exception ex) {
	       JOptionPane.showMessageDialog(null, "There was an error loading " +
	       		"the Look & Feel", "Look & Feel error",
	       		JOptionPane.ERROR_MESSAGE);
	    }
	}
}