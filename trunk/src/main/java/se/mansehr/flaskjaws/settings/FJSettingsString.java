package se.mansehr.flaskjaws.settings;

import java.io.Serializable;

/**
 * FJSettingString is a part of the FJSettingsPackage
 * FjSettingsString is used by FJSettings to save strings
 * in the settings menu.
 * 
 * See FJSettingsObject, FJSettingsInteger, FJSettingsBoolean, FJSettingsCombo
 * FJSettingsObject
 * 
 * @author Sehr
 *
 */
public class FJSettingsString extends FJSettingsObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String value;
	
	/**
	 * Default constructor sets the value to the default value ""
	 * @param name the name
	 */
	public FJSettingsString(String  name)
	{
		this(name, "");
	}
	
	/**
	 * 
	 * @param name the settings name
	 * @param value the value
	 */
	public FJSettingsString(String name, String value)
	{
		super(name);
		this.value = value;
	}
	
	/**
	 * Constructor that sets all init values
	 * @param name name of the variable
	 * @param text the info text
	 * @param value the initvalue on the variable
	 */
	public FJSettingsString(String name, String text, String value)
	{
		super(name, text);
		this.value = value;
	}
	
	/**
	 * Accesory method for the value
	 * @return the value on the setting 
	 */
	public String getValue()
	{
		return value;
	}

	/*
	 * Sets the value
	 * @param value the new value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	
	/**
	 * Returns a clone of the settings object
	 */
	public FJSettingsString clone()
	{
		FJSettingsString cloneObj = new FJSettingsString(getName(),
				getValue());
		cloneObj.setText(getText());
		return cloneObj;
	}
	
	/**
	 * Returns a true if values and name are equal
	 */
	public boolean equals(Object obj)
	{
		if (FJSettingsString.class.isInstance(obj)) {
			FJSettingsString objString = (FJSettingsString)obj;
			if (objString.getName().equals(getName()) &&
					objString.getValue().equals(getValue())) {
				return true;
			}
		}
		return false;
	}
}