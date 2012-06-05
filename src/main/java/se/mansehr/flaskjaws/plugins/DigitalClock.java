package se.mansehr.flaskjaws.plugins;

import java.awt.Dimension;
import java.util.Calendar;

import javax.swing.JLabel;

import se.mansehr.flaskjaws.pluginclasses.FJPluginRunnable;

public class DigitalClock extends FJPluginRunnable
{
	private JLabel label;
	private Calendar currentTime;
	
	public DigitalClock()
	{
		super("Digital Clock", 1000);
		label = new JLabel(getName());
		updateTimeLabel();
		getMainPanel().add(label);
		getMainPanel().setPreferredSize(new Dimension(250, 200));
	}

	public void start()
	{
		super.start();
		label.setText(this.getName() + ", Started.");
	}

	protected void tick()
	{
		updateTimeLabel();
		getMainPanel().repaint();	
	}
	
	public void stop()
	{
		super.stop();
        label.setText("Stopped!");
        getMainPanel().repaint();
    }
	
	private void updateTimeLabel()
	{
		currentTime = Calendar.getInstance();
		
		int hours = currentTime.get(Calendar.HOUR_OF_DAY);
		int minutes = currentTime.get(Calendar.MINUTE);
		int seconds = currentTime.get(Calendar.SECOND);
		
		label.setText("Current Time: " + addFirstZero(hours) + ":" + 
				addFirstZero(minutes) + ":" +addFirstZero(seconds));
	}
	
	private String addFirstZero(int value)
	{
		if(value < 10) {
			return "0" + value;
		} else {
			return "" + value;
		}
	}
}