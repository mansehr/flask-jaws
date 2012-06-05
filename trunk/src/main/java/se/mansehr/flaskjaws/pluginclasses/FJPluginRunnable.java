package se.mansehr.flaskjaws.pluginclasses;
/**
 * The FJPluginRunnable has support for threading and cyclic calling the 
 * tick function. The delay varibale has to be set when initinating the plugin
 * but it can be changed on demand.
 * Child plugins must remember to call super.methodname() for most methods 
 * when overiding them.
 * @author Andreas Sehr
 *
 */
public abstract class FJPluginRunnable extends FJPlugin implements Runnable,
FJPluginInterface
{
	private Thread thread;
	private int delay;
	private int ticks;
	private boolean pause;
	
	/**
	 * The constructor take two parameters
	 * @param name pluginName
	 * @param delay between method tick() calls
	 */
	public FJPluginRunnable(String name, int delay)
	{
		super(name);
		this.delay = delay;
		ticks = 0;
		pause = false;
	}

	/**
	 * Start plugin
	 */
	public void start()
	{
		startThread();
	}
	
	/**
	 * Start the actual thread
	 */
	private void startThread()
	{
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
    }
	
	/**
	 * Stop the thread
	 */
	public void stop()
	{
		thread.interrupt();
        thread = null;
	}
	
	/**
	 * Pause, just stop calling tick() method, the thread is still 
	 * running in the back
	 */
	public void pause()
	{
		pause = true;
	}
	
	/**
	 * Resume calling the tick method
	 */
	public void resume()
	{
		pause = false;
	}
	
	/**
	 * Run is used by interface Runnable. Called when thread is started
	 */
	public void run()
	{
		while(thread != null) {
			try {
				if(pause != true) {
					tick();
					ticks++;
				}
				Thread.sleep(delay);
			} catch(Exception ex) {
                if (thread != null && thread.isInterrupted()) {
                    thread = null;
                }
            }
		}
	}
	
	/**
	 * 
	 * @return the number of tick calls
	 */
	public int getTicks()
	{
		return ticks;
	}

	/**
	 * @return the delay between each run.
	 */
	public int getDelay()
	{
		return delay;
	}
	
	/**
	 * Sets the delay after each run.
	 * @param delay time in milliseconds that the thread will pause.
	 */
	public void setDelay(int delay)
	{
		if(delay >= 0) {
			this.delay = delay;
		} else {
			this.delay = 0;
		}
	}
	
	/**
	 * Must overide this method to get the cyclic methodcalls
	 */
	protected abstract void tick();
}