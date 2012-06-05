package se.mansehr.flaskjaws.frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import se.mansehr.flaskjaws.PluginListElement;

/**
 * 
 */
public class LoadPluginFrame extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JList gameList;
	private JButton loadButton;
	private JButton cancelButton;
	
	/**
	 *
	 */
	public LoadPluginFrame(JFrame owner, 
			ArrayList<PluginListElement> pluginList)
	{
		super(owner, "Load Plugin", true);
		setSize(175, 250);
		setLocationRelativeTo(owner);
		
		DefaultListModel d = new DefaultListModel();
		
		for(int i = 0; i < pluginList.size(); i++) {
			PluginListElement e = (PluginListElement)pluginList.get(i);
			d.addElement(e.name);
		}
		
		gameList = new JList(d);
		gameList.setSelectionMode(ListSelectionModel.
				SINGLE_INTERVAL_SELECTION);
		gameList.setLayoutOrientation(JList.VERTICAL);
		gameList.setVisibleRowCount(-1);
		JScrollPane listScroll = new JScrollPane(gameList);
		getContentPane().add(listScroll, BorderLayout.CENTER);
		
		loadButton = new JButton("Load");
		cancelButton = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(loadButton);
		loadButton.addActionListener(this);
		buttonPanel.add(cancelButton);
		cancelButton.addActionListener(this);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Someone pressed a button. Aight.
	 */
	public void actionPerformed(ActionEvent e)
	{
		JButton source = (JButton)e.getSource();
		if (source == loadButton) {
			if(getSelectedIndex() != -1) {	
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this,
					    "You must chose a game in the list to load.",
					    "Load error",
					    JOptionPane.ERROR_MESSAGE);
			}
		} else if (source == cancelButton) {
			gameList.clearSelection();
			setVisible(false);
		}
	}
	
	/**
	 * @return the selected index in the list
	 */
	public int getSelectedIndex()
	{
		return gameList.getSelectedIndex();
	}
}