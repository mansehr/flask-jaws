package se.mansehr.flaskjaws.frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.Dimension;

public class SettingsFrame extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	JTabbedPane settingsTabs;
	JButton okButton;
	JButton cancelButton;
	boolean exitOK;
	
	public SettingsFrame(JFrame owner, JPanel fjSettings, 
			JPanel pluginSettings, JPanel networkSettings)
	{
		super(owner, "Settings", true);
		setLocationRelativeTo(owner);
		
		settingsTabs = new JTabbedPane();
		settingsTabs.addTab("Flask jaws Settings", fjSettings);
		settingsTabs.addTab("Plugin Settings", pluginSettings);
		settingsTabs.addTab("Network Settings", networkSettings);
		if (networkSettings == null) {
			settingsTabs.setEnabledAt(2, false);
		}
		
		settingsTabs.setPreferredSize(new Dimension(400, 300));
			
		exitOK = false;
		
		JPanel buttonsPanel = new JPanel();
		
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		
		add(settingsTabs, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
		pack();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton activeButton = (JButton)e.getSource();
		if (activeButton.equals(okButton)) {
			exitOK = true;
			setVisible(false);
		} else if (activeButton.equals(cancelButton)) {
			setVisible(false);
		}
	}
	
	public boolean didExitOK()
	{
		return exitOK;
	}
}