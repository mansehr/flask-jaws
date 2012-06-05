package se.mansehr.flaskjaws.frames;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import se.mansehr.flaskjaws.settings.FJSettings;

/**
 * Represents the about frame when selected from the menu.
 */
public class AboutFrame extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;

	public AboutFrame(JFrame owner)
	{
		super(owner, "About",  true);
		setSize(300, 200);
		setLocationRelativeTo(owner);
		
		setLayout(new GridLayout(0, 1));
		
		add(new JLabel("Flask Jaws"));
		add(new JLabel("Version " + FJSettings.version));
		add(new JLabel("Homepage: http://www.siti.mine.nu/FlaskJaws"));
		add(new JLabel("Authors: Andreas Sehr & Jonas Westman Skog"));
		
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(this);
		add(okButton);
		
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		setVisible(false);
	}
}