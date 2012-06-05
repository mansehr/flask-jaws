package se.mansehr.flaskjaws.frames;

import javax.swing.*;

import se.mansehr.flaskjaws.pluginclasses.FJNetwork;

import java.net.UnknownHostException;
import java.awt.*;
import java.awt.event.*;

/**
 * Represents the Network tab in settings.
 */
public class FJNetworkPreferencesPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private JButton setTargetIPButton = null;
	private JButton setPortButton = null;
	
	private JLabel setTargetIPLabel = new JLabel();
	private JLabel setPortLabel = new JLabel();
	private JLabel autoFlushLabel = new JLabel();
	private JTextField txtNick;
	
	private FJNetwork fjn = FJNetwork.getInstance();
	
	public FJNetworkPreferencesPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 5, 5));
		
		setTargetIPButton = new JButton("Change target IP");
		setTargetIPButton.addActionListener(this);
		
		setPortButton = new JButton("Change port");
		setPortButton.addActionListener(this);
		
		updateIPLabel();
		panel.add(setTargetIPLabel);
		panel.add(setTargetIPButton);
		
		updatePortLabel();
		panel.add(setPortLabel);
		panel.add(setPortButton);
		
		txtNick = new JTextField();
		panel.add(new JLabel("Enter your nick"));
		panel.add(txtNick);
		
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel("Advanced settings"));
		panel.add(new JLabel());
		
		updateAutoFlush();
		panel.add(autoFlushLabel);
		
		add(panel);
	}
	
	private void opponentIPDialog()
	{
		String s = JOptionPane.showInputDialog(null, "Enter target " +
				"IP address.", "Target IP", JOptionPane.DEFAULT_OPTION);
		try {
			if (s != null) {
				fjn.setTargetIP(s);
			}
		} catch (UnknownHostException ex) {
			JOptionPane.showMessageDialog(null, "Flask Jaws doesn't " +
					"recognize the host.", "Invalid address",
					JOptionPane.ERROR_MESSAGE);
		}
		updateIPLabel();
	}
	
	private void portDialog()
	{
		String s = JOptionPane.showInputDialog(null, "Enter port to connect " +
				"through.", "Port", JOptionPane.DEFAULT_OPTION);
		if (s != null) {
			fjn.setPort(Integer.parseInt(s));
		}
		updatePortLabel();
	}
	
	private void updateIPLabel()
	{
		if (fjn.getTargetIP() == null) {
			setTargetIPLabel.setText("IP address is not set.");
		} else {
			setTargetIPLabel.setText("IP address: " + 
					fjn.getTargetIP());
		}
	}
	
	private void updatePortLabel()
	{
		if (fjn.getPort() == 5012) {
			setPortLabel.setText("Port: " + fjn.getPort() + " (default)");
		} else {
			setPortLabel.setText("Port: " + fjn.getPort());
		}
	}
	
	private void updateAutoFlush()
	{
		String s;
		if (fjn.isAutoFlush()) {
			s = "enabled.";
		} else {
			s = "disabled.";
		}
		autoFlushLabel.setText("Auto flush is currently " + s);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton source = (JButton)e.getSource();
		if (source == setTargetIPButton) {
			opponentIPDialog();
		} else if (source == setPortButton) {
			portDialog();
		}
	}
	
	public String getNick()
	{
		return txtNick.getText();
	}
	
	public boolean isAutoFlush()
	{
		return fjn.isAutoFlush();
	}
	
	public int getPort()
	{
		return fjn.getPort();
	}
	
	public String getTargetIp()
	{
		return fjn.getTargetIP();
	}
}