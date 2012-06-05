package se.mansehr.flaskjaws.settings;

import java.io.Serializable;

/**
 * FJSettingsObjectis a part of the FJSettings package. Its the top 
 * object of the settings objects hierarchy. Its abstract and controll the 
 * name of the setting. The name is used by the user to get it back from
 * FJSettings.
 * 
 * @see FJSettingsObject, FJSettingsInteger, FJSettingsBoolean, FJSettingsCombo
 * FJSettingsString
 * 
 * @author Sehr
 */
public abstract class FJSettingsObject implements Serializable, Cloneable 
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String text;
	
	/**
	 * Default constructor sets text to null
	 * @param name
	 */
	public FJSettingsObject(String name)
	{
		this(name, null);
	}
	
	/**
	 * Default constructor sets text to what its given in the parameter
	 * @param name
	 */
	public FJSettingsObject(String name, String text)
	{
		this.name = name;
		this.text = text;
	}
	
	/**
	 * Returns the name of the setting,
	 * The name is used as a key in the FJSettings class, 
	 * it is also shown in the settings panel if the text varible is not set.  
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the text, null by default must be set by the user.
	 * if set it will be shown in the settings panel instead of the name.
	 * If the string 'text' is not set it will be set to the objects name.
	 * @return the text
	 */
	public String getText()
	{
		if(text != null) {
			return text;
		} else {
			return getName();
		}
	}
	
	/**
	 * Sets the new text that i displayed in the settingpanel.
	 * Text is null by default and if not set the name will be shown in 
	 * the settings panel.
	 * If the user sends the parameter to be "" then the text will be set 
	 * to null and the name will then again be show in the settings panel.  
	 * @param text the new text/question
	 */
	public void setText(String text)
	{
		if(text.equals("")) {
			this.text = null;
		} else {
			this.text = text;
		}
	}
	

	/**
	 * Overided toString method to return object name
	 */
	public String toString()
	{
		return getName();
	}
	
	/**
	 * Get the settings value must be overided by the child to
	 * set the value type, no value types are allowed. 
	 * @return Object
	 */
	public abstract Object getValue();
	
	/**
	 * Returns a clone of the settings object
	 */
	public abstract FJSettingsObject clone();
	
	/**
	 * Returns a true if values and name are equal
	 */
	public abstract boolean equals(Object obj);
}