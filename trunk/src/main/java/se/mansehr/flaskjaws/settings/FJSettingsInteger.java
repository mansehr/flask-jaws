package se.mansehr.flaskjaws.settings;

import java.io.Serializable;

/**
 * FJSettingString is a part of the FJSettingsPackage
 * FjSettingsInteger is used by FJSettings to save integers
 * in the settings menu.
 * 
 * See FJSettingsObject, FJSettingsString, FJSettingsBoolean, FJSettingsCombo
 * FJSettingsObject
 * @author Sehr
 */
public class FJSettingsInteger extends FJSettingsObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Integer value;
	
	/**
	 * Default constructor sets the value to the default value 0
	 * @param name the name
	 */
	public FJSettingsInteger(String  name)
	{
		this(name, 0);
	}
	
	/**
	 * 
	 * @param name the settings name
	 * @param value the value
	 */
	public FJSettingsInteger(String name, Integer value)
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
	public FJSettingsInteger(String name, String text, Integer value)
	{
		super(name, text);
		this.value = value;
	}
	
	/**
	 * Accesory method for the value
	 * @return the value on the setting 
	 */
	public Integer getValue()
	{
		return value;
	}
	
	/**
	 * Sets the value
	 * @param value the new value
	 */
	public void setValue(Integer value)
	{
		this.value = value;
	}
	
	/**
	 * Returns a clone of the settings object
	 */
	public FJSettingsInteger clone()
	{
		FJSettingsInteger cloneObj = 
			new FJSettingsInteger(getName(), getValue());
		cloneObj.setText(getText());
		return cloneObj;
	}
	
	/**
	 * Returns a true if values and name are equal
	 */
	public boolean equals(Object obj)
	{
		if (FJSettingsInteger.class.isInstance(obj)) {
			FJSettingsInteger objInteger = (FJSettingsInteger)obj;
			if (objInteger.getName().equals(getName()) &&
					objInteger.getValue().equals(getValue())) {
				return true;
			}
		}
		return false;
	}
}