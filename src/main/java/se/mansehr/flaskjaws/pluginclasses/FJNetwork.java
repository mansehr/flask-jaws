package se.mansehr.flaskjaws.pluginclasses;

import java.io.*;
import java.net.*;

/**
 * Stores and creates sockets, readers and writers for plugins that needs to
 * communicate over a network. Developers may for instance: <p>
 * <code>FJNetwork fjn = FJNetwork.getInstance(); <p>
 * <code>fjn.{@link #createServer()}; <p>
 * <code>fjn.{@link #accept()}; <p>
 * <code>Socket client = fjn.{@link #getClient()}; <p>
 * </code>
 * to create a new ServerSocket, accept an incoming connection request and
 * retrive the Socket that made the connection. Further on, to open a reader
 * on the servers input stream (which processes the clients output): <p>
 * <code>fjn.{@link #createServerInputStreamReader()}; <p>
 * <code>BufferedReader in = fjn.{@link #getServerInputStreamReader()}; <p>
 * </code>
 * then <code>in</code> will represent the reader. <p>
 * A FJNetwork object can NOT represent both a server and client. This class
 * uses Singleton design, no public constructor is avaliable. To get the only
 * FJNetwork instance possible, {@link #getInstance() FJNetwork.getInstance()}
 * must be used.
 */
public class FJNetwork
{
	private static FJNetwork fjn = null;
	
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private BufferedReader clientStdIn = null;
	private BufferedReader serverStdIn = null;
	private PrintWriter clientOut = null;
	private BufferedReader clientIn = null;
	private PrintWriter serverOut = null;
	private BufferedReader serverIn = null;
	private String targetIP = null;
	
	private static final int DEFAULT_PORT = 5012;
	private int port = DEFAULT_PORT;
	private boolean autoFlush = true;
	
	private FJNetwork()
	{
		
	}
	
	/**
	 * This method is the substitute for the private constructor. This method
	 * may be called to access the only FJNetwork object possible.
	 * @return the only FJNetwork instance avaliable.
	 */
	public static FJNetwork getInstance()
	{
		if (fjn == null) {
			fjn = new FJNetwork();
		}
		return fjn;
	}
	
	/**
	 * Attempts to create a new ServerSocket listening over the default port
	 * or one specified in {@link #setPort(int)}. If a ServerSocket already
	 * exists in this FJNetwork instance, none will be created. <p>
	 * If a new ServerSocket successfully was created, true will be returned,
	 * if not, false will be returned.
	 * @return true if a new ServerSocket was created.
	 * @throws IOException if an I/O error occurs when opening the socket.
	 */
	public boolean createServer() throws IOException
	{
		if (serverSocket == null) {
			serverSocket = new ServerSocket(getPort());
			return true;
		}
		return false;
	}
	
	/**
	 * Attempts to create a new ServerSocket over port specified in this method
	 * call. If a ServerSocket already exists in this FJNetwork, none will be
	 * created. <p>
	 * If a new ServerSocket successfully was created, true will be returned,
	 * if not, false will be returned.
	 * @param port the port this ServerSocket should listen to.
	 * @return true if a new ServerSocket was created.
	 * @throws IOException if an I/O error occurs when opening the socket. 
	 */
	public boolean createServer(int port) throws IOException
	{
		if (serverSocket == null) {
			setPort(port);
			serverSocket = new ServerSocket(getPort());
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the ServerSocket created by the {@link #createServer()} or
	 * {@link #createServer(int)} methods.
	 * @return this FJNetwork instance's ServerSocket.
	 */
	public ServerSocket getServer()
	{
		return serverSocket;
	}
	
	/**
	 * Attempts to create a new Socket connected to the host name returned
	 * from {@link #getTargetIP()} and over the port returned from {@link
	 * #getPort()}. If a Socket already exists in this FJNetwork, none will be
	 * created. <p>
	 * If a new Socket successfully was created, true will be returned, if not,
	 * false will be returned.
	 * @return true if a new Socket was created.
	 * @throws IOException if an I/O error occurs when creating the socket.
	 * @throws UnknownHostException if the IP address couldn't be determined.
	 */
	public boolean createClient() throws IOException, UnknownHostException
	{
		if (clientSocket == null) {
			clientSocket = new Socket(getTargetIP(), getPort());
			return true;
		}
		return false;
	}
	
	/**
	 * Attempts to create a new Socket connected to the host name explicitly
	 * given as a parameter and over the port returned from {@link #getPort()}.
	 * If a Socket already exists in this FJNetwork, none will be created. <p>
	 * If a new Socket successfully was created, true will be returned, if not,
	 * false will be returned.
	 * @param ip the IP or host name the Socket will connect to.
	 * @return true if a new Socket was created.
	 * @throws IOException if an I/O error occurs when creating the socket.
	 * @throws UnknownHostException if the IP address couldn't be determined.
	 */
	public boolean createClient(String ip) throws IOException,
	UnknownHostException
	{
		if (clientSocket == null) {
			setTargetIP(ip);
			clientSocket = new Socket(getTargetIP(), getPort());
			return true;
		}
		return false;
	}
	
	/**
	 * Attempts to create a new Socket connected over the port explicitly given
	 * as a parameter and to the IP returned from {@link #getTargetIP()}. If a
	 * Socket already exists in this FJNetwork, none will be created. <p>
	 * If a new Socket successfully was created, true will be returned, if not,
	 * false will be returned.
	 * @param port the port this Socket will connect through.
	 * @return true if a new Socket was created.
	 * @throws IOException if an I/O error occurs when creating the socket.
	 * @throws UnknownHostException if the IP address couldn't be determined.
	 */
	public boolean createClient(int port) throws IOException,
	UnknownHostException
	{
		if (clientSocket == null) {
			setPort(port);
			clientSocket = new Socket(getTargetIP(), getPort());
			return true;
		}
		return false;
	}
	
	/**
	 * Attempts to create a new Socket connected to the host name and over the
	 * port explicitly given as a parameters. If a Socket already exists in
	 * this FJNetwork, none will be created. <p>
	 * If a new Socket successfully was created, true will be returned, if not,
	 * false will be returned.
	 * @param ip the IP or host name the Socket will connect to.
	 * @param port the port this Socket will connect through.
	 * @return true if a new Socket was created.
	 * @throws IOException if an I/O error occurs when creating the socket.
	 * @throws UnknownHostException if the IP address couldn't be determined.
	 */
	public boolean createClient(String ip, int port) throws IOException,
	UnknownHostException
	{
		if (clientSocket == null) {
			setTargetIP(ip);
			setPort(port);
			clientSocket = new Socket(getTargetIP(), getPort());
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the Socket created by the {@link #createClient()}, {@link
	 * #createClient(String)}, {@link #createClient(int)} or {@link
	 * #createClient(String, int)} methods.
	 * @return this FJNetwork instance's Socket.
	 */
	public Socket getClient()
	{
		return clientSocket;
	}
	
	/**
	 * Attempts to create a new BufferedReader reading from the client's {@link
	 * java.lang.System#in} stream. Only one reader on {@link
	 * java.lang.System#in} may be created per client. <p>
	 * If a new reader successfully was created, true will be returned, if not,
	 * false will be returned.
	 * @return true if a new BufferedReader was created.
	 * @see #getClientStdInReader()
	 */
	public boolean createClientStdInReader()
	{
		if (clientStdIn == null) {
			clientStdIn = new BufferedReader(new InputStreamReader(System.in));
			return true;
		}
		return false;
	}
	
	/**
	 * Accessor method for the client's Standard Input Reader.
	 * @return the BufferedReader reading from the client's Standard Input.
	 * @see #createClientStdInReader()
	 */
	public BufferedReader getClientStdInReader()
	{
		return clientStdIn;
	}
	
	/**
	 * Attempts to create a new BufferedReader reading from the server's {@link
	 * java.lang.System#in} stream. Only one reader on {@link
	 * java.lang.System#in} may be created per server. <p>
	 * If a new reader successfully was created, true will be returned, if not,
	 * false will be returned.
	 * @return true if a new BufferedReader was created.
	 * @see #getClientStdInReader()
	 */
	public boolean createServerStdInReader()
	{
		if (serverStdIn == null) {
			serverStdIn = new BufferedReader(new InputStreamReader(System.in));
			return true;
		}
		return false;
	}
	
	/**
	 * Accessor method for the server's Standard Input Reader.
	 * @return the BufferedReader reading from the server's Standard Input.
	 * @see #createClientStdInReader()
	 */
	public BufferedReader getServerStdInReader()
	{
		return serverStdIn;
	}
	
	/**
	 * Attempts to create a new BufferedReader reading from the client's
	 * input stream. Only one input stream reader may be created per client.
	 * <p>
	 * If a new reader successfully was created, true will be returned, if not,
	 * false will be returned.
	 * @return true if a new reader was created.
	 * @throws IOException if an I/O error occurs when creating the input
	 * stream, the socket is closed, the socket is not connected, or the socket
	 * input has been shutdown using shutdownInput().
	 */
	public boolean createClientInputStreamReader() throws IOException
	{
		if (clientIn == null) {
			clientIn = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			return true;
		}
		return false;
	}
	
	/**
	 * Accessor method for the client's input stream reader.
	 * @return the BufferedReader reading from the client's input stream.
	 * @see #createClientInputStreamReader()
	 */
	public BufferedReader getClientInputStreamReader()
	{
		return clientIn;
	}
	
	/**
	 * Attempts to create a new PrintWriter writing to the client's
	 * output stream. Only one output stream writer may be created per client.
	 * <p>
	 * If a new writer successfully was created, true will be returned, if not,
	 * false will be returned.
	 * @return true if a new writer was created.
	 * @throws IOException if an I/O error occurs when creating the output
	 * stream or if the socket is not connected.
	 */
	public boolean createClientOutputStreamWriter() throws IOException
	{
		if (clientOut == null) {
			clientOut = new PrintWriter(clientSocket.getOutputStream(),
					isAutoFlush());
			return true;
		}
		return false;
	}
	
	/**
	 * Accessor method for the client's output stream writer.
	 * @return the PrintWriter writing to the client's output stream.
	 * @see #createClientOutputStreamWriter()
	 */
	public PrintWriter getClientOutputStreamWriter()
	{
		return clientOut;
	}
	
	/**
	 * Attempts to create a new BufferedReader reading from the server's
	 * input stream. Only one input stream reader may be created per server.
	 * <p>
	 * If a new reader successfully was created, true will be returned, if not,
	 * false will be returned.
	 * @return true if a new reader was created.
	 * @throws IOException
	 */
	public boolean createServerInputStreamReader() throws IOException
	{
		if (serverIn == null) {
			serverIn = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			return true;
		}
		return false;
	}
	
	/**
	 * Accessor method for the server's input stream reader.
	 * @return the BufferedReader reading from the server's input stream.
	 * @see #createServerInputStreamReader()
	 */
	public BufferedReader getServerInputStreamReader()
	{
		return serverIn;
	}
	
	/**
	 * Attempts to create a new PrintWriter writing to the server's
	 * output stream. Only one output stream writer may be created per server.
	 * <p>
	 * If a new writer successfully was created, true will be returned, if not,
	 * false will be returned.
	 * @return true if a new writer was created.
	 * @throws IOException if an I/O error occurs when creating the output
	 * stream or if the socket is not connected.
	 */
	public boolean createServerOutputStreamWriter() throws IOException
	{
		if (serverOut == null) {
			serverOut = new PrintWriter(clientSocket.getOutputStream(),
					isAutoFlush());
			return true;
		}
		return false;
	}
	
	/**
	 * Accessor method for the server's output stream writer.
	 * @return the PrintWriter writing to the server's output stream.
	 * @see #createServerOutputStreamWriter()
	 */
	public PrintWriter getServerOutputSteamWriter()
	{
		return serverOut;
	}
	
	/**
	 * Listens for a connection to be made to the ServerSocket and accepts it.
	 * The Socket that it's connected to is saved and accessible through {@link
	 * #getClient()} once the connection has been established.
	 * @throws IOException if an I/O error occurs when waiting for a
	 * connection.
	 */
	public void accept() throws IOException
	{
		clientSocket = serverSocket.accept();
	}
	
	/**
	 * Closes all open readers, writers and sockets. Should be invoked when the
	 * active plugin is terminated, either because the active plugin is
	 * switched or Flask Jaws is closed. Placing a close() call in a plugins
	 * stop() method is a good idea. 
	 * @throws IOException if an I/O error occurs when closing any
	 * reader/writer/socket.
	 */
	public void close() throws IOException
	{
		if (clientStdIn != null) {
			clientStdIn.close();
		}
		if (serverStdIn != null) {
			serverStdIn.close();
		}
		if (clientIn != null) {
			clientIn.close();
		}
		if (clientOut != null) {
			clientOut.close();
		}
		if (serverIn != null) {
			serverIn.close();
		}
		if (serverOut != null) {
			serverOut.close();
		}
		if (clientSocket != null) {
			clientSocket.close();
		}
		if (serverSocket != null) {
			serverSocket.close();
		}
	}
	
	/**
	 * Returns the IP address of the computer this object is connected to. <p>
	 * Can be used to get the predefined target IP for viewing in preferences
	 * etc, but is risky to use as a server before any connection is made (i.e.
	 * before {@link #accept()} is invoked.)
	 * @return the targets IP address specified in network preferences or in a
	 * plugin.
	 */
	public String getTargetIP()
	{
		if (serverSocket != null) {
			String[] s = clientSocket.getInetAddress().toString().split("/"); 
			return s[1];
		}
		return targetIP;
	}
	
	/**
	 * Sets the targets IP. Only useful for FJNetwork instances representing a
	 * client, since a server can't decide who will connect to it. <p>
	 * From the Javadoc: <p>
	 * The host name can either be a machine name, such as "java.sun.com",  
	 * or a textual representation of its IP address. If a literal IP address
	 * is supplied, only the validity of the address format is checked.
	 * @param ip String containing the IP.
	 * @throws UnknownHostException if the IP address of the target not
	 * could be determined.
	 */
	public void setTargetIP(String ip) throws UnknownHostException
	{
		String[] s = InetAddress.getByName(ip).toString().split("/"); 
		targetIP = s[1];
	}
	
	/**
	 * Returns the port this socket or server socket is connected through or is
	 * set to connect through.
	 * @return the port to connect through.
	 */
	public int getPort()
	{
		return port;
	}
	
	/**
	 * Sets the prefered port, overwrites the default port.
	 * @param port the new port.
	 * @see #resetPort()
	 */
	public void setPort(int port)
	{
		this.port = port;
	}
	
	/**
	 * Resets the port to the default value, 5012.
	 */
	public void resetPort()
	{
		port = DEFAULT_PORT;
	}
	
	/**
	 * Checks if auto flushing is enabled. If auto flush is enabled, the
	 * buffers will automatically be flushed and written to the connected
	 * stream. <p>
	 * It is by default set to true.
	 * @return true if auto flush is enabled on the writers for this FJNetwork
	 * instance.
	 * @see #setAutoFlush(boolean)
	 */
	public boolean isAutoFlush()
	{
		return autoFlush;
	}
	
	/**
	 * Sets the instance variable autoFlush to the given value. <p>
	 * It is by default set to true.
	 * @param autoFlush new value of autoFlush.
	 * @see #isAutoFlush()
	 */
	public void setAutoFlush(boolean autoFlush)
	{
		this.autoFlush = autoFlush;
	}
}