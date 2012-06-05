package se.mansehr.flaskjaws.plugins;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import se.mansehr.flaskjaws.pluginclasses.CollisionEngine;
import se.mansehr.flaskjaws.pluginclasses.FJGamePlugin;
import se.mansehr.flaskjaws.pluginclasses.FJMainPanel;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJBall;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJLabel;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJRect;

/**
 * @author Sehr
 */
public class UltimatePong extends FJGamePlugin 
{
	private final int SCREEN_WIDTH = 640;
	private final int SCREEN_HEIGHT = 420;
	private final int BALL_SIZE = 15;
	private final int PLATFORM_WIDTH = 20;
	private final int PLATFORM_HEIGHT = BALL_SIZE * 6;
	private final int WALLS_WIDTH = 15;
	private final int STEPS = 5;
	
	private FJBall ball;
	private FJRect platformUser;
	private FJRect platformOponent;
	private FJLabel playerUserPoints;
	private FJLabel playerOponentPoints;
	private FJLabel gotPointsText;
	private CollisionEngine ce;
	
	private int oponentPoints;
	private int sleepTimer;
	
	/**
	 * 
	 */
	public UltimatePong()
	{
		super("Ultimate Pong", 20);
		getMainPanel().setPreferredSize(new Dimension(SCREEN_WIDTH,
				SCREEN_HEIGHT));
		
		ce = new CollisionEngine();
		oponentPoints = 0;
		sleepTimer = 0;
		
		ball = new FJBall(SCREEN_WIDTH / 2 - (BALL_SIZE / 2), SCREEN_HEIGHT /
				2 - (BALL_SIZE / 2), BALL_SIZE, BALL_SIZE, 1.0f, 1.0f,
				Color.GREEN);
		ball.setColor(Color.RED);
		platformUser = new FJRect(SCREEN_WIDTH - (2 * PLATFORM_WIDTH),
				SCREEN_HEIGHT / 2 - (PLATFORM_HEIGHT / 2), PLATFORM_WIDTH,
				PLATFORM_HEIGHT, Color.BLUE);
		platformOponent = new FJRect(PLATFORM_WIDTH, SCREEN_HEIGHT / 2 -
				(PLATFORM_HEIGHT / 2), PLATFORM_WIDTH, PLATFORM_HEIGHT,
				Color.BLUE);
		
		playerUserPoints = new FJLabel(SCREEN_WIDTH - 75, 12, 100, 20,
				Color.RED, "Points: 0");
		playerOponentPoints = new FJLabel(20, 12, 100, 20, Color.RED,
				"Points: 0");
		gotPointsText = new FJLabel(SCREEN_WIDTH / 2 - 120, 200, 100, 100,
				Color.BLUE, "Got the Points");
		gotPointsText.setFont(new Font("Serif", Font.BOLD, 32));
		gotPointsText.setVisible(false);
		
		int maxY = SCREEN_HEIGHT - WALLS_WIDTH;
		int minY = WALLS_WIDTH;
		
		platformUser.setMaxY(maxY - PLATFORM_HEIGHT);
		platformUser.setMinY(minY);
		platformOponent.setMaxY(maxY - PLATFORM_HEIGHT);
		platformOponent.setMinY(minY);
		
		ball.setMaxPoint(SCREEN_WIDTH + 10+BALL_SIZE, maxY - BALL_SIZE);
		ball.setMinPoint(-10 - BALL_SIZE, minY);
		
		ce.add(ball);
		ce.add(platformUser);
		ce.add(platformOponent);
			
		((FJMainPanel)getMainPanel()).add(ball);
		((FJMainPanel)getMainPanel()).add(platformUser);
		((FJMainPanel)getMainPanel()).add(platformOponent);
		
		((FJMainPanel)getMainPanel()).add(new FJRect(0,0, SCREEN_WIDTH,
				WALLS_WIDTH, Color.BLACK));
		((FJMainPanel)getMainPanel()).add(new FJRect(0,SCREEN_HEIGHT -
				WALLS_WIDTH, SCREEN_WIDTH, WALLS_WIDTH, Color.BLACK));
		
		((FJMainPanel)getMainPanel()).add(playerUserPoints);
		((FJMainPanel)getMainPanel()).add(playerOponentPoints);
		((FJMainPanel)getMainPanel()).add(gotPointsText);
	}

	/* (non-Javadoc)
	 * @see flaskJaws.Grundobject.PluginClasses.FJGamePlugin#tick()
	 */
	@Override
	protected void tick()
	{
		keysPressed(getKeys());
		
		if (sleepTimer > 0) { //Pausing
			sleepTimer--;
		} else {
			ce.run(); //Move ball and calculate collisions
		}
		
		updateLabels();
		getMainPanel().repaint();
	}

	public void keysPressed(boolean[] keys)
	{
        if(!isStarted()) {
            if(keys[KeyEvent.VK_ENTER]) {    //Enter key code = 10
                start();
            }
        } else {
            if (keys[KeyEvent.VK_UP]) {
                platformUser.moveY(-STEPS); //Arrow Up
            }
            if (keys[KeyEvent.VK_DOWN]) {
            	platformUser.moveY(STEPS);  //Arrow Down
            }
            if (keys[KeyEvent.VK_Q]) {
            	platformOponent.moveY(-STEPS);
            }
            if (keys[KeyEvent.VK_A]) {
            	platformOponent.moveY(STEPS);  
            }
         }
	}
	
	public void keyPressed(KeyEvent e)
    {
		if (e.getKeyCode() == KeyEvent.VK_P) { //P key code = 80
            if(isPaused()) {
            	resume();
            } else {
            	pause();
            }
		}
		getMainPanel().repaint();
    }
	 
	public void updateLabels()
	{
		boolean reset = false;
		String player = "";
		if (ball.getRight() < platformOponent.getLeft() - ball.getWidth()) {
			 addPoints(10);
			 playerUserPoints.setText("Points: " + getPoints());
			 reset = true;
			 player = "Player 1";
		}
		if (ball.getLeft() > platformUser.getRight() + ball.getWidth()) {
			oponentPoints += 10;
			playerOponentPoints.setText("Points: " + oponentPoints);
			reset = true;
			player = "Player 2";
		}
		if(reset) {
			sleepTimer = 80;
			ball.setLocation(SCREEN_WIDTH / 2 - (BALL_SIZE / 2), SCREEN_HEIGHT
					/ 2 - (BALL_SIZE / 2));
			gotPointsText.setVisible(true);
			gotPointsText.setText(player + " got some points...");
		}
		
		if( sleepTimer == 1) {
			gotPointsText.setVisible(false);
		}
	}
	
	public boolean needKeyboardInput()
	{
		return true;
	}
}