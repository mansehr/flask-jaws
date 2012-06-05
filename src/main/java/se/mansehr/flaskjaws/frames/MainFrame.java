package se.mansehr.flaskjaws.frames;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import se.mansehr.flaskjaws.FlaskJaws;
import se.mansehr.flaskjaws.PluginListElement;
import se.mansehr.flaskjaws.PluginLoader;
import se.mansehr.flaskjaws.PluginLoader.DynamicPlugin;
import se.mansehr.flaskjaws.pluginclasses.FJKeyboard;
import se.mansehr.flaskjaws.pluginclasses.FJPlugin;
import se.mansehr.flaskjaws.plugins.AnalogClock;
import se.mansehr.flaskjaws.plugins.DefaultPlugin;
import se.mansehr.flaskjaws.plugins.DigitalClock;
import se.mansehr.flaskjaws.plugins.FJMessenger;
import se.mansehr.flaskjaws.plugins.UltimateBlocks;
import se.mansehr.flaskjaws.plugins.UltimatePong;
import se.mansehr.flaskjaws.settings.FJSettings;
import se.mansehr.flaskjaws.settings.FJSettingsBoolean;

/**
 * 
 */
public class MainFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private static final boolean DEBUG = false;
	private static final String APPLICATION_NAME = "Flask Jaws";
	
	private LoadPluginFrame loadPluginFrame;
	
	private JMenu fileMenu;
	private JMenu networkMenu;
	private JMenu updateMenu;
	private JMenu helpMenu;
	
	private JMenuItem loadPluginItem;
	private JMenuItem preferencesItem;
	private JMenuItem exitItem;
	private JMenuItem hostItem;
	private JMenuItem joinItem;
	private JMenuItem networkPreferencesItem;
	private JMenuItem getPluginListItem;
	private JMenuItem updatePlatformItem;
	private JMenuItem aboutItem;
	private JMenuItem helpItem;
	
	private FJPlugin activePlugin;
	private JPanel mainPanel;
	private FJKeyboard activeKeyboard;
	
	private FJSettings settings;
	private FJNetworkPreferencesPanel networkSettings;
	
	private ArrayList<PluginListElement> pluginList;
	
	/**
	 * 
	 */
	public MainFrame()
	{
		super("Flask Jaws");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addWindowListener(new WindowClosingListener());
		
		buildMenus();
		
		switchPlugin(new DefaultPlugin());
		fillPluginList();
	}
	
	/**
	 * Builds the entire menu for the Main Frame. Should only be called
	 * by the constructor.
	 */
	private void buildMenus()
	{
		JMenuBar menuBar = new JMenuBar();
		
		//Build File Menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		loadPluginItem = new JMenuItem("Load Plugin...", KeyEvent.VK_L);
		loadPluginItem.addActionListener(this);
		preferencesItem = new JMenuItem("Preferences", KeyEvent.VK_P);
		preferencesItem.addActionListener(this);
		exitItem = new JMenuItem("Exit", KeyEvent.VK_X);
		exitItem.addActionListener(this);
		
		fileMenu.add(loadPluginItem);
		fileMenu.add(preferencesItem);
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		
		//Build Network Menu
		networkMenu = new JMenu("Network");
		networkMenu.setMnemonic(KeyEvent.VK_N);
	
		joinItem = new JMenuItem("Join", KeyEvent.VK_J);
		joinItem.addActionListener(this);
		hostItem = new JMenuItem("Host", KeyEvent.VK_H);
		hostItem.addActionListener(this);
		networkPreferencesItem = new JMenuItem("Network Preferences...",
				KeyEvent.VK_P);
		networkPreferencesItem.addActionListener(this);
		
		networkMenu.add(joinItem);
		networkMenu.add(hostItem);
		networkMenu.add(networkPreferencesItem);
		menuBar.add(networkMenu);
		
		//Build Update Menu
		updateMenu = new JMenu("Update");
		updateMenu.setMnemonic(KeyEvent.VK_U);
		getPluginListItem = new JMenuItem("Get Plugin List...", KeyEvent.VK_P);
		getPluginListItem.addActionListener(this);
		updatePlatformItem = new JMenuItem("Update Platform", KeyEvent.VK_U);
		updatePlatformItem.addActionListener(this);
		updatePlatformItem.setEnabled(false);
		
		updateMenu.add(getPluginListItem);
		updateMenu.add(updatePlatformItem);
		menuBar.add(updateMenu);
		
		//Build Help Menu
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		aboutItem = new JMenuItem("About", KeyEvent.VK_A);
		aboutItem.addActionListener(this);
		helpItem = new JMenuItem("Help", KeyEvent.VK_H);
		helpItem.addActionListener(this);
		
		helpMenu.add(aboutItem);
		helpMenu.add(helpItem);
		menuBar.add(helpMenu);
		
		//Add the entire Menubar to the JFrame
		setJMenuBar(menuBar);
		
		debug("Main Window Menu Created");
	}
	
	/**
	 * Builds the settings menuPanel. Called only once!
	 */
	public void buildSettings()
	{
		settings = new FJSettings("Flask Jaws");
		try {
			settings = FJSettings.loadFromFile(settings.getName()+".set");
		} catch(Exception ex) { 	//TODO: Better bugraport
			System.out.println("Error loading settings, building new.");
			
			FJSettingsBoolean look = new FJSettingsBoolean("look", false);
			look.setText("Look and feel by OS?");
			settings.add(look);
		}		
		settingsChanged(null); //Change everything
	}
	
	/**
	 * Invoked when an action occurs. Used to track keypresses on the menus.
	 * @param e the event that fired the method.
	 */
	public void actionPerformed(ActionEvent e)
	{		
		activePlugin.pause();
		JMenuItem source = (JMenuItem)e.getSource();
		if (source == loadPluginItem) {
			loadPluginFrame = new LoadPluginFrame(this, pluginList);
			debug("Opened load game frame.");
			loadPluginFrame.setVisible(true);
			//Lägg in testkod för vad användaren valde
			debug("Closed load game frame." );
			int chosen = loadPluginFrame.getSelectedIndex();
			if(chosen != -1) {
				debug("Chosed element: " + chosen);
				PluginListElement element = 
					(PluginListElement)pluginList.get(chosen);
				try {
					FJPlugin newPlugin;
					if(element.isDynamicalyLoaded) {
						newPlugin = PluginLoader.createPluginFromJar(
								new DynamicPlugin(element.plugin,
										element.file));
					} else {						
						Class<?> c = element.plugin;
						newPlugin = (FJPlugin)c.newInstance();
					}
					switchPlugin(newPlugin);
				} catch(IllegalAccessException ex) {
					//Error loading plugin
					System.out.println("Error accessing plugin:" + element.name
							+ "\n"+ ex.getMessage());
				} catch(InstantiationException ex) {
					System.out.println("Error instantiating plugin:" +
							element.name + "\n"+ ex.getMessage());
				}
			}
		} else if (source == preferencesItem) {
			showSettingsForm(0);
		} else if (source == exitItem) {
			quit();
		} else if (source == joinItem) {
			debug("joinItem");
			activePlugin.joinMenu();
		} else if (source == hostItem) {
			debug("hostItem");
			activePlugin.hostMenu();
		} else if (source == networkPreferencesItem) {
			showSettingsForm(2);			
		} else if (source == getPluginListItem) {
			debug("getPluginListItem");
			JOptionPane.showMessageDialog(null, "Not implemented.", 
					"Something is missing huh?!?", JOptionPane.ERROR_MESSAGE);
		} else if (source == updatePlatformItem) {
			debug("updatePlatformItem");
			JOptionPane.showMessageDialog(null, "Not implemented.", 
					"Something is missing huh?!?", JOptionPane.ERROR_MESSAGE);
		} else if (source == aboutItem) {
			debug("aboutItem");
			new AboutFrame(this);
		} else if (source == helpItem) {
			debug("helpItem");
			openHelp();
		}
		activePlugin.resume();
	}
	
	/**
	 * Opens the help file, readme.txt.
	 */
	private void openHelp()
	{
		Desktop d = Desktop.getDesktop();
		try {
			d.open(new File(System.getProperty("user.dir") + File.separator
					+ "readme.txt"));
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Unable to open " +
					"\"readme.txt\"", "Can't open file",
					JOptionPane.ERROR_MESSAGE);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(null, "The help file doesn't " +
					"exist.", "File not found", JOptionPane.ERROR_MESSAGE);
		} catch (UnsupportedOperationException ex) {
			JOptionPane.showMessageDialog(null, "Your OS doesn't support " +
					"this feature.\n To manually view the help, go to Flask " +
					"Jaws install directory and open \"readme.txt\".",
					"Unsupported action", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Switches the active plugin
	 */
	private void switchPlugin(FJPlugin newPlugin)
	{
		if (activePlugin != null) {
			activePlugin.stop();
		}
		
		if (mainPanel != null) {
			remove(mainPanel);
			mainPanel.removeAll();
		}
		
		activePlugin = newPlugin;
		mainPanel = activePlugin.getMainPanel();
		
		
		if (activePlugin.needKeyboardInput()) {
			activeKeyboard = new FJKeyboard(activePlugin);
			addKeyListener(activeKeyboard);
			activePlugin.setKeyListner(activeKeyboard);
		} else {
			removeKeyListener(activeKeyboard);
			activeKeyboard = null;
		}
		
		if (activePlugin.hasNetworkSupport()) {
			networkMenu.setEnabled(true);
			networkSettings = new FJNetworkPreferencesPanel();
		} else {
			networkMenu.setEnabled(false);
			networkSettings = null;
		}
			
		add(mainPanel);
		pack();
		activePlugin.start();
		setTitle(APPLICATION_NAME + " : " + activePlugin.getName());
	}
	
	private void showSettingsForm(int activePanel)
	{
		FJSettings settingsClone = settings.clone();
		FJSettings pluginSettingsClone = activePlugin.getSettings().clone();
		SettingsFrame sFrame = new SettingsFrame(this,
				settingsClone.getSettingsPanel(),
				pluginSettingsClone.getSettingsPanel(), networkSettings);
		sFrame.settingsTabs.setSelectedIndex(activePanel);
		sFrame.setVisible(true);
		//Did we close the form with ok the 
		// set actual settings
		if(sFrame.didExitOK())
		{
			FJSettings oldSettings = settings;
			settings = settingsClone;
			settingsChanged(oldSettings);
			
			activePlugin.setSettings(pluginSettingsClone);
		}
	}
	
	/**
	 * If param oldSettings == null change everything
	 * @param oldSettings
	 */
	private void settingsChanged(FJSettings oldSettings)
	{
		if (oldSettings == null || 
			settings.getSetting("look").equals(oldSettings.getSetting("look"))
				== false) {
			FJSettingsBoolean valueHolder = 
				(FJSettingsBoolean)settings.getSetting("look");
			FlaskJaws.setLookAndFeel(valueHolder.getValue());
		}
		//Ändringra gjorda 
		try {
			settings.saveToFile();
		} catch(IOException ex) {
			System.out.println("Error saving new settings.");
		}
	}
	
	/**
	 * Fill the plugin ArrayList with startable plugins
	 */
	private void fillPluginList()
	{
		pluginList = new ArrayList<PluginListElement>();
		pluginList.add(PluginListElement.make(
				AnalogClock.class));
		pluginList.add(PluginListElement.make(
				DigitalClock.class));
		pluginList.add(PluginListElement.make(
				FJMessenger.class));
		pluginList.add(PluginListElement.make(
				UltimateBlocks.class));
		pluginList.add(PluginListElement.make(
				UltimatePong.class));
		
		//Load external classes dynamicly
		ArrayList<DynamicPlugin> externPlugins = 
			PluginLoader.loadPlugins();
		for(DynamicPlugin c : externPlugins) {
			pluginList.add(PluginListElement.make(c));
		}
	}
	
	/**
	 * A debugging method, should only be used in testing purposes.
	 * @param s string describing what action invoked this method.
	 */
	private void debug(String s)
	{
		if(DEBUG) {
			System.out.println(s);
		}
	}
	
	/**
	 * Exit all running threads and exit the application.
	 */
	private void quit()
	{
		if(activePlugin != null) {
			activePlugin.stop();
		}
		
		dispose();
		
		System.exit(0);
	}
	
	private class WindowClosingListener implements WindowListener {

		public void windowActivated(WindowEvent arg0)
		{
			
		}

		public void windowClosed(WindowEvent arg0)
		{
			
		}

		public void windowClosing(WindowEvent arg0)
		{
			quit();
		}

		public void windowDeactivated(WindowEvent arg0)
		{
			
		}

		public void windowDeiconified(WindowEvent arg0)
		{
			
		}

		public void windowIconified(WindowEvent arg0)
		{
			
		}

		public void windowOpened(WindowEvent arg0)
		{
			
		}
	}
}