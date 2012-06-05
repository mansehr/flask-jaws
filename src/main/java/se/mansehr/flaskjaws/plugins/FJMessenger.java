package se.mansehr.flaskjaws.plugins;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import se.mansehr.flaskjaws.pluginclasses.FJNetwork;
import se.mansehr.flaskjaws.pluginclasses.FJPlugin;

public class FJMessenger extends FJPlugin implements ActionListener, Runnable
{
	private FJNetwork fjn = null;
	private JTextArea textArea = null;
	private JTextField textField = null;
	private JLabel talkingToLabel = null;
	private JScrollPane scrollPane = null;
	private PrintWriter clientOut = null;
	private PrintWriter serverOut = null;
	private Thread thread = null;
	
	private static final String newline = "\n";
	private boolean autoScroll = true;
	
	public FJMessenger()
	{
		super("FJMessenger");
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textField = new JTextField(15);
		textField.addActionListener(this);
		talkingToLabel = new JLabel("Not connected to anyone");
		getMainPanel().setLayout(new BorderLayout(5, 5));
		getMainPanel().add(talkingToLabel, BorderLayout.NORTH);
		getMainPanel().add(textField, BorderLayout.SOUTH);
		getMainPanel().add(scrollPane, BorderLayout.CENTER);
		getMainPanel().setPreferredSize(new Dimension(500, 650));
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		          textField.requestFocus();
		    }
		}); 
		
		fjn = FJNetwork.getInstance();
	}
	
	private void startThread()
	{
		if (thread == null) {
			thread = new Thread(this);
		}
		thread.start();
	}
	
	public void run()
	{
		String socketInput;
		if (fjn.getServer() == null) {
			while (thread != null) {
				try {
					talkingToLabel.setText("Talking to " + fjn.getTargetIP());
					fjn.createClientOutputStreamWriter();
					clientOut = fjn.getClientOutputStreamWriter();
					fjn.createClientInputStreamReader();
					BufferedReader in = fjn.getClientInputStreamReader();
					while ((socketInput = in.readLine()) != null) {
						getScrollPos();
						textArea.append(fjn.getTargetIP() + 
								" wrote: " + socketInput + newline);
						autoScroll();
					}
				} catch (IOException ex) {
					System.err.println(ex);
				}
			}
		} else {
			while (thread != null) {
				try {
					fjn.accept();
					talkingToLabel.setText("Talking to " + fjn.getTargetIP());
					fjn.createServerInputStreamReader();
					fjn.createServerOutputStreamWriter();
					serverOut = fjn.getServerOutputSteamWriter();
					BufferedReader in = fjn.getServerInputStreamReader();
					while ((socketInput = in.readLine()) != null) {
						getScrollPos();
						textArea.append(fjn.getTargetIP() + 
								" wrote: " + socketInput + newline);
						autoScroll();
					}
				} catch (IOException ex) {
					System.err.println(ex);
				}
			}
		}

	}

	public void pause()
	{
		
	}

	public void resume()
	{
		
	}

	public void start()
	{
		
	}

	public void stop()
	{
		try {
			fjn.close();
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	public void hostMenu()
	{
		try {
			fjn.createServer();
			startThread();
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	public void joinMenu()
	{
		try {
			fjn.createClient(fjn.getTargetIP());
			startThread();
		} catch (UnknownHostException ex) {
			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	private void processText()
	{
		if (fjn.getServer() == null) {
			String s = textField.getText();
			if (!s.equals("")) {
				textField.setText("");
				getScrollPos();
				textArea.append("You wrote: " + s + newline);
				autoScroll();
				clientOut.println(s);
			}
		} else {
			String s = textField.getText();
			if (!s.equals("")) {
				textField.setText("");
				getScrollPos();
				textArea.append("You wrote: " + s + newline);
				autoScroll();
				serverOut.println(s);
			}
		}
	}
	
	private void getScrollPos()
	{
		JScrollBar vbar = scrollPane.getVerticalScrollBar();
		autoScroll = ((vbar.getValue() + vbar.getVisibleAmount()) ==
			vbar.getMaximum());
	}
	
	private void autoScroll()
	{
		if (autoScroll) {
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
	}
	
	public boolean hasNetworkSupport()
	{
		return true;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JComponent source = (JComponent)e.getSource();
		if (source == textField) {
			processText();
		}
	}
}