package se.mansehr.flaskjaws.plugins;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Calendar;

import se.mansehr.flaskjaws.pluginclasses.FJMainPanel;
import se.mansehr.flaskjaws.pluginclasses.FJPluginRunnable;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJLine;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJOval;
import se.mansehr.flaskjaws.settings.FJSettingsBoolean;
import se.mansehr.flaskjaws.settings.FJSettingsCombo;
import se.mansehr.flaskjaws.settings.FJSettingsInteger;

public class AnalogClock extends FJPluginRunnable
{
	private final int CLOCK_WIDTH = 400;
	private final int CLOCK_HEIGHT = 400;
	private final int CLOCK_CENTER_X = CLOCK_WIDTH / 2;
	private final int CLOCK_CENTER_Y = CLOCK_HEIGHT / 2;
	private final int CLOCK_MARGIN = CLOCK_CENTER_X / 10;
	
	private final int hourRadius = CLOCK_CENTER_Y - CLOCK_MARGIN * 6;
	private final int minuteRadius = CLOCK_CENTER_Y - CLOCK_MARGIN * 2;
	private final int secondRadius = CLOCK_CENTER_Y - CLOCK_MARGIN * 2;
	
	private FJLine hourPoint;
	private FJLine minutePoint;
	private FJLine secondPoint;
	
	private FJSettingsBoolean tickSmoth;
	
	
	/**
	 *
	 */
	public AnalogClock()
	{
		super("Analog Clock", 40);
		getMainPanel().setPreferredSize(new Dimension(CLOCK_WIDTH,
				CLOCK_HEIGHT));
		
		hourPoint = new FJLine(0, 0, CLOCK_CENTER_X, CLOCK_CENTER_Y,
				Color.BLACK, 4);		
		minutePoint = new FJLine(0, 0, CLOCK_CENTER_X, CLOCK_CENTER_Y,
				Color.BLACK, 3);
		secondPoint = new FJLine(0, 0, CLOCK_CENTER_X, CLOCK_CENTER_Y,
				Color.RED, 1);
		//Small lines inside the clock
		for (int i = 0; i < 60; i++) {
			double rad = pointerRad(i, 60);
			int thickness = 1;
			Point p1 = updatePointer(new Point(), rad, CLOCK_CENTER_X - CLOCK_MARGIN);
			Point p2 = updatePointer(new Point(), rad, CLOCK_CENTER_X - (2 * CLOCK_MARGIN));
			if (i % 5 == 0) { 
				thickness = 4;
			}
			
			FJLine line = new FJLine(p1.x, p1.y, p2.x, p2.y, Color.BLACK, thickness);
			((FJMainPanel)getMainPanel()).add(line);
		}
		
		((FJMainPanel)getMainPanel()).add(new FJOval(
				CLOCK_MARGIN, CLOCK_MARGIN, CLOCK_WIDTH - 2 * CLOCK_MARGIN,
				CLOCK_HEIGHT - 2 * CLOCK_MARGIN, Color.BLACK, 10));
		
		((FJMainPanel)getMainPanel()).add(hourPoint);
		((FJMainPanel)getMainPanel()).add(minutePoint);
		((FJMainPanel)getMainPanel()).add(secondPoint);
		
		BuildSettings();
	}
	
	/**
	 * Builds the settings for this plugin
	 *
	 */
	public void BuildSettings()
	{
		FJSettingsCombo tickType = new FJSettingsCombo("TickType",
				FJSettingsCombo.TYPE_DROPDOWN_LIST);
		tickSmoth = new FJSettingsBoolean("Tick Smooth", true);
		tickType.add(tickSmoth);
		tickType.add(new FJSettingsBoolean("Tick tock", false));
		getSettings().add(tickType);
		getSettings().add(new FJSettingsInteger("Test"));
	}
	
	/**
	 *
	 */
	private void updatePointers()
	{
		Calendar currentTime = Calendar.getInstance();
		//Calculate the angle for the pointers
		double hourRad = pointerRad(currentTime.get(Calendar.HOUR), 12);
		double minuteRad = pointerRad(currentTime.get(Calendar.MINUTE), 60);
		double secondRad = pointerRad(currentTime.get(Calendar.SECOND), 60);
		double milliSecondRad = pointerRad(currentTime.get(
				Calendar.MILLISECOND), 1000);
		
		// To avoid the hacks, make the pointers move soft
		hourRad += (minuteRad / 12) - (Math.PI / 2);
		minuteRad += (secondRad / 60) - (Math.PI / 2);
		secondRad -= (Math.PI / 2);
		
		FJSettingsCombo tickType =
			(FJSettingsCombo)getSettings().getSetting("TickType");
		if ((Boolean)tickType.getValue()) {
			secondRad += (milliSecondRad / 60);
		}
		
		hourPoint.setLocation(updatePointer(hourPoint.getLocation(), hourRad,
				hourRadius));
		minutePoint.setLocation(updatePointer(minutePoint.getLocation(),
				minuteRad, minuteRadius));
		secondPoint.setLocation(updatePointer(secondPoint.getLocation(),
				secondRad, secondRadius));
	}
	
	/**
	 * Update a specific hand
	 * @param rad angle in radians
	 * @param radius the radian, distance from origin
	 */
	private Point updatePointer(Point p, double rad, int radius)
	{
		double x = (radius * Math.cos(rad)) + CLOCK_CENTER_X;
		double y = (radius * Math.sin(rad)) + CLOCK_CENTER_Y;
		
		p.setLocation(x, y);
		return p;
	}
	
	/**
	 * Calculate Radians
	 */
	private double pointerRad(double currentTick, int ticksACycle)
	{
		return (currentTick / ticksACycle * 2 * Math.PI);
	}
	
	@Override
	protected void tick()
	{
		updatePointers();
		getMainPanel().repaint();
	}
}