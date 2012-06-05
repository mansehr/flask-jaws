package se.mansehr.flaskjaws.settings;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * FJSettingCombo is a part of the FJSettingsPackage
 * FjSettingsCombo is used by FJSettings to save combinations of other 
 * Fjsettings in the settings menu.
 * 
 * See FJSettingsObject, FJSettingsInteger, FJSettingsBoolean, FJSettingsString
 * FJSettingsObject
 * 
 * @author Sehr
 */

public class FJSettingsCombo extends FJSettingsObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<FJSettingsObject> objects;
	private int active;
	private int type;
	
	public static final int TYPE_LIST = 1298283;
	public static final int TYPE_DROPDOWN_LIST = 1298284;
	//public static final int TYPE_RADIO = 1298285; //TODO: Implement later
	
	
	
	public FJSettingsCombo(String name, int type)
	{
		super(name);
		active = 0;
		this.type = type;
		objects = new ArrayList<FJSettingsObject>();
	}
	
	
	public int getType()
	{
		return type;
	}
	
	public void setActiveObject(int active)
	{
		this.active = active;
	}
	
	public int getActiveIndex()
	{
		return active;
	}
	
	/**
	 * Accesory method for the value
	 * @return the active objects value on the setting 
	 */
	public Object getValue()
	{
		return getActiveObject().getValue();
	}
	
	public FJSettingsObject getActiveObject()
	{
		return objects.get(active);
	}

	public ArrayList<FJSettingsObject> getObjects()
	{
		return objects;
	}

	public boolean add(FJSettingsObject arg0)
	{
		return objects.add(arg0);
	}


	public void clear() {
		objects.clear();
	}


	public boolean contains(Object arg0)
	{
		return objects.contains(arg0);
	}


	public boolean isEmpty() {
		return objects.isEmpty();
	}


	public boolean remove(Object arg0)
	{
		return objects.remove(arg0);
	}


	public int size()
	{
		return objects.size();
	}

	/**
	 * Returns a clone of the settings object
	 */
	public FJSettingsCombo clone()
	{
		FJSettingsCombo settingsClone = new FJSettingsCombo(getName(),
				getType());
		settingsClone.setActiveObject(active);
		settingsClone.setText(getText());
		for (FJSettingsObject obj : objects) {
			settingsClone.add(obj.clone());
		}
		return settingsClone;
	}
	
	/**
	 * Returns a true if values and name are equal
	 */
	public boolean equals(Object obj)
	{
		if (FJSettingsCombo.class.isInstance(obj)) {
			FJSettingsCombo objCombo = (FJSettingsCombo)obj;
			if (objCombo.getName().equals(getName())) {
				for (FJSettingsObject o : objects) {
					if (objCombo.contains(o) != true) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
}