package se.mansehr.flaskjaws.settings;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * FJSettings is a class that controll different settings for different plugins
 * FjSettings can also generate a JPanel 
 * @author Sehr
 *
 */
public class FJSettings implements Serializable, Cloneable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7663791884143503438L;
	
	private HashMap<String, FJSettingsObject> objects;
	private String name;
	
	public static final String version = "0.1.1274";
	
	/**
	 * The default constructor. Every FJSetting must have a name.
	 * used when saving the settings.
	 * @param name
	 */
	public FJSettings(String name)
	{
		this.name = name;
		objects = new HashMap<String, FJSettingsObject>();
	}
	
	/**
	 * Adds a new setting to the list.
	 * @param obj new FJSettingsObject
	 */
	public void add(FJSettingsObject obj)
	{
		objects.put(obj.getName(), obj);
	}
	
	/**
	 * Removes a setting from the list
	 * @param obj
	 * @return
	 */
	public FJSettingsObject remove(FJSettingsObject obj)
	{
		return objects.remove(obj.getName());
	}
	
	/**
	 * Generates and returns a new settingspanel(JPanel).
	 * Its allways the same variable in the settings panel as in the settings 
	 * object. If the panel is updated then the settings variable is updated 
	 * to by using listeners. 
	 * @return
	 */
	public JPanel getSettingsPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel objectPanel = new JPanel();
		objectPanel.setLayout(new GridLayout(objects.size(), 2, 5, 5));
		
		JLabel lblName = new JLabel(name+" settings", JLabel.CENTER);
		lblName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		panel.add(lblName);
		
		if( objects.size() == 0) {
			panel.add(new JLabel("No settings to set.", JLabel.CENTER));
		} else {
			for(FJSettingsObject obj : objects.values()) {
				
				objectPanel.add(new JLabel(obj.getText()+"  ", JLabel.RIGHT));
				
				if (FJSettingsString.class.isInstance(obj))	{
					JTextField txt = new JTextField((String)obj.getValue(), 15);
					txt.addFocusListener(new ChangeValueOnAction(obj));
					objectPanel.add(txt);
				} else if (FJSettingsInteger.class.isInstance(obj)) {				
					Integer value = (Integer)obj.getValue();
					JFormattedTextField txt = new JFormattedTextField(value);
					txt.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
					txt.setColumns(15);
					txt.addFocusListener(new ChangeValueOnAction(obj));
					objectPanel.add(txt);
				} else if (FJSettingsCombo.class.isInstance(obj)) {
					FJSettingsCombo combo = (FJSettingsCombo)obj;
					JComboBox box = new JComboBox(combo.getObjects().toArray());
					box.addActionListener(new ComboboxListener(combo));
					box.setSelectedIndex(combo.getActiveIndex());
					objectPanel.add(box);
				} else if (FJSettingsBoolean.class.isInstance(obj)) {
					FJSettingsBoolean boolObj = (FJSettingsBoolean)obj;
					JCheckBox box = new JCheckBox("", boolObj.getValue());
					box.addActionListener(new ChangeValueOnAction(obj));
					objectPanel.add(box);
				}		
			}
			
		}
		panel.add(objectPanel);
		
		return panel;
	}
	
	/**
	 * Returns a specified setting.
	 * @param settingName
	 * @return
	 */
	public FJSettingsObject getSetting(String settingName)
	{
		return objects.get(settingName);
	}
	
	/**
	 * Actionlistener that changes the value in the sertting
	 * 
	 * ActionListener for the checkboxes
	 * Change value when status is changed
	 * 
	 * FocusListener for the Textfields with strings
	 * Change value when focus is lost
	 * 
	 * FocusListener for the TextFields with integers
	 * Change value when focus is lost
	 * @author Sehr
	 *
	 */
	private class ChangeValueOnAction
	implements ActionListener, FocusListener, Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8940199121239680565L;
		
		private FJSettingsObject obj;
		
		public ChangeValueOnAction(FJSettingsObject obj)
		{
			this.obj = obj;
		}
		
		public void focusLost(FocusEvent e)
		{
			JTextField txt = (JTextField)e.getSource();
			if (FJSettingsString.class.isInstance(obj))	{
				takeStringAction(txt);
			} else if (FJSettingsInteger.class.isInstance(obj)) {
				takeIntegerAction(txt);
			}
		}
		
		//Do nothing when gaining focus!
		public void focusGained(FocusEvent e) {}
		
		public void actionPerformed(ActionEvent e)
		{
			JCheckBox box = (JCheckBox)e.getSource();
			FJSettingsBoolean fjBool = (FJSettingsBoolean)obj;
			fjBool.setValue(box.isSelected());
			
			//System.out.println("Event Action, object: " + obj.getName() +
			//" Value: " + obj.getValue().toString());
		}
		
		private void takeStringAction(JTextField txt)
		{
			FJSettingsString fjString = (FJSettingsString)obj;
			fjString.setValue(txt.getText());
			
			//System.out.println("Event Action, object: " + obj.getName()+
			//" Value: " + fjString.getValue());
		}
		
		private void takeIntegerAction(JTextField txt)
		{
			FJSettingsInteger fjInteger = (FJSettingsInteger)obj;
			
			try {
				fjInteger.setValue(Integer.parseInt(txt.getText()));
			} catch(Exception ex) {
				//System.out.println("Integer exception.");
				//Do nothing when value isint Integer  
			}
			
			//System.out.println("Event Action, object: "+obj.getName()+
			//		" Value: "+fjInteger.getValue());
		}
	}

	/**
	 * Listener for the ComboBox item. Changes to the right settings value
	 * @author Sehr
	 */
	private class ComboboxListener implements ActionListener, Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 385837064481062352L;
		
		private FJSettingsCombo comboObject;
		
		public ComboboxListener(FJSettingsCombo newObject)
		{
			comboObject = newObject;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			JComboBox box = (JComboBox)e.getSource();
			comboObject.setActiveObject(box.getSelectedIndex());
			System.out.println("Combo ActiveIndex name: " +
					comboObject.getActiveObject().getName() + " ActiveObject: "
					+ comboObject.getValue().toString());
		}
	}
	
	/**
	 * Accesor method for the name
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	
	/////////////// Loading And Saving \\\\\\\\\\\\\
	/**
	 * Loads the default filename "'name'+'.set'" and the returns that object
	 */ 
	public FJSettings loadFromFile()
	throws FileNotFoundException, IOException, ClassNotFoundException
	{
		return loadFromFile(getName() + ".set");
	}

	/**
	 * Loads the object from a specified file and then returns it.
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static FJSettings loadFromFile(String fileName)
	throws FileNotFoundException, IOException, ClassNotFoundException
	{
		FJSettings fj;

		FileInputStream fIn = new FileInputStream(fileName);
		ObjectInputStream os = new ObjectInputStream(fIn);
			
		fj = (FJSettings)os.readObject();
			
		os.close();
		
		return fj;
	}
	
	public void saveToFile() throws IOException
	{
		saveToFile(getName() + ".set");
	}
	
	public void saveToFile(String fileName) throws IOException
	{
		FileOutputStream fOut = new FileOutputStream(fileName);
		ObjectOutputStream os = new ObjectOutputStream(fOut);
		
		os.writeObject(this);
		
		os.close();
	}
	
	public FJSettings clone()
	{
		FJSettings settingsClone = new FJSettings(this.getName());
		for(FJSettingsObject obj : objects.values()) {
			settingsClone.add(obj.clone());
		}
		return settingsClone; 
	}
}