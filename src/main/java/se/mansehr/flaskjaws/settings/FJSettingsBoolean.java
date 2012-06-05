package se.mansehr.flaskjaws.settings;

import java.io.Serializable;

/**
 * FJSettingBoolean is a part of the flaskJaws.FJSettings package
 * FjSettingsBoolean is used by FJSettings to save Boolean values
 * in the settings menu.
 * 
 * See FJSettingsObject, FJSettingsInteger, FJSettingsString, FJSettingsCombo
 * FJSettingsObject
 * 
 * @author Sehr
 *
 */
public class FJSettingsBoolean extends FJSettingsObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Boolean value;
	
	/**
	 * Constructor - Sets the default value to false.
	 * @param name object name
	 */
	public FJSettingsBoolean(String  name)
	{
		this(name, false);
	}
	
	/**
	 * Constructor - sets all the variables to parametervalues
	 * @param name object name
	 * @param value object value
	 */
	public FJSettingsBoolean(String name, Boolean value)
	{
		super(name);
		this.value = value;
	}
	
	/**
	 * Return the value
	 * @see flaskJaws.Settings.FJSettingsObject#getValue()
	 */
	public Boolean getValue()
	{
		return value;
	}
	
	/**
	 * Sets the value
	 * @param value the new value
	 */
	public void setValue(Boolean value)
	{
		this.value = value;
	}
	
	/**
	 * Returns a clone of the settings object
	 */
	public FJSettingsBoolean clone()
	{
		FJSettingsBoolean cloneObj = new FJSettingsBoolean(getName(),
				getValue());
		cloneObj.setText(getText());
		return cloneObj;
	}
	
	/**
	 * Returns a true if values and name are equal
	 */
	public boolean equals(Object obj)
	{
		if(FJSettingsBoolean.class.isInstance(obj)) {
			FJSettingsBoolean objBool = (FJSettingsBoolean)obj;
			if(objBool.getName().equals(getName()) &&
					objBool.getValue().equals(getValue())) {
				return true;
			}
		}
		return false;
	}
}