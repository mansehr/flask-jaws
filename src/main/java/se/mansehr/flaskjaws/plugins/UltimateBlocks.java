package se.mansehr.flaskjaws.plugins;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import se.mansehr.flaskjaws.pluginclasses.FJGamePlugin;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJBall;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJHighscore;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJRect;

public class UltimateBlocks extends FJGamePlugin 
{
	//Definitions readonlyvariables
    private static int BRICK_ROWS = 8;
    private static int BRICK_COLUMNS = 12;
    private static int TOTAL_BRICKS = BRICK_ROWS * BRICK_COLUMNS;
    private static int BRICK_MARGIN = 1;
    private static int BRICK_WIDTH = 30;
    private static int BRICK_HEIGHT = 20;
    private static int PLATFORM_WIDTH = 100;
    private static int PLATFORM_HEIGHT = 20;
    private static int BALL_SIZE = 10;
    
    //instance variables
    private int bricksLeft;
    
    private FJRect platform;
    private FJBall ball; 
    private BlocksBrick[] bricks;
    private FJRect[] walls;
    private BlockPanel ultimatePanel;
    
    private FJHighscore highscore;
	
    public UltimateBlocks()
	{
		super("Ultimate Blocks", 40);
		System.out.println("Skapar ultimate Blocks.");
		ultimatePanel = new BlockPanel();
    	ultimatePanel.setBackground(Color.white);
    	ultimatePanel.setPreferredSize(new Dimension(500, 500));
    	ultimatePanel.setFocusable(true);
    	setMainPanel(ultimatePanel);
    	
    	highscore = new FJHighscore();
	}
    
    public void start()
    {
    	super.start();
    	reset();
    	setDead(false);
    }
    
    public void reset()
    {
    	super.reset();
    	
    	setMainPanel(ultimatePanel);
    	
    	bricksLeft = 0;
    	bricks = new BlocksBrick[TOTAL_BRICKS];
        for (int j = 0; j < BRICK_ROWS; j++) {
            for(int i = 0; i < BRICK_COLUMNS; i++) {
                int x = ((i + 1) * (BRICK_WIDTH + BRICK_MARGIN)) + 30;
                int y = ((j + 1) * (BRICK_HEIGHT + BRICK_MARGIN)) + 30;

                bricks[i + (j * BRICK_COLUMNS)] = new BlocksBrick(x, y,
                		BRICK_WIDTH, BRICK_HEIGHT, 1);

                bricksLeft++;
            }
        }

        ball = new FJBall(100, 300, BALL_SIZE, BALL_SIZE, 1, 1, Color.red);
        platform = new FJRect(200, 450, PLATFORM_WIDTH, PLATFORM_HEIGHT, Color.orange);
        
        walls = new FJRect[4];
        walls[0] = new FJRect(0, 0, 20, 500, Color.BLACK);		//Left
        walls[1] = new FJRect(0, 0, 500, 20, Color.BLACK);      //Top
        walls[2] = new FJRect(480, 0, 20, 500, Color.BLACK);    //Right
        walls[3] = new FJRect(0, 480, 500, 20, Color.BLACK);    //Bottom
        walls[3].setVisible(false);
    }
    
	@Override
	protected void tick()
	{
		keysPressed(getKeys());
		ball.move();
        testCollision();
        if (bricksLeft <= 0) {
            //NewLevel();
            setDelay(20000);
        }
        if (ball.getY() > 500) {
            //NewLevel();
            setDelay(20000);
        }
        getMainPanel().repaint();
	}
	
	public void testCollision()
    {
		boolean changeDX = false;
        boolean changeDY = false;
        if (platform.intersects(ball)) {
        	float newDX = 1;	//New speed dx
        	float ballWidth = ball.getWidth();
        	float ballLeft = ball.getLeft();
        	float platformWidth = platform.getWidth();
        	float platformLeft = platform.getLeft();
        	newDX = (ballLeft + (ballWidth / 2) - (platformLeft +
        			(platformWidth / 2))) /( platformWidth / 4);

        	ball.setDY(-ball.getDY());
        	/*NewDX = (ball.getLeft() + (ball.getWidth() / 2) -
        	 * (platform.getLeft() + (platform.getWidth() / 2))) /
        	 * (platform.getWidth() / 2);
        	
        	Om newDX är inom intervallet -0.2 < newDX < 0.2 så ändras inte x dvs den studdsar rakt upp och ner
        	Annars om newDX är inom intervallet -0.5 < newDX < -0.2 eller 0.2 < newDX < 0.5
        	Sätts newDX till -0.5 resp 0.5
        	if (newDX < 0.5 && newDX > - 0.5 && newDX > 0.2 && newDX < -0.2)*/
        	if (newDX > - 0.9 && newDX < 0.9) {
        		/*if (newDX < 0) {
                	newDX = -0.5f;
            	} else {
                	newDX = 0.5f;
                }*/

        } else {
            ball.setDX(newDX);
        }
      }
      if (walls[0].intersects(ball)) { //Left wall
          ball.setDX(-ball.getDX());
      }
      if (walls[1].intersects(ball)) { //Top wall
          ball.setDY(-ball.getDY());
      }
      if (ball.intersects(walls[2])) { //Right wall
          ball.setDX(-ball.getDX());
      }
      if (walls[3].isVisible() && ball.intersects(walls[3])) { //Bottom wall
          ball.setDY(-ball.getDY());
      }
      for (int i = 0; i < TOTAL_BRICKS; i++) {
          if (bricks[i] != null && bricks[i].isVisible() &&
        		  ball.intersects(bricks[i])) {
              //Sees how much the side of the ball is inside the brick
              int topInside = 0;
              int bottomInside = 0;
              int leftInside = 0;
              int rightInside = 0;
              //Right side of ball hits Left side of the brick
              if (ball.getRight() >= bricks[i].getLeft() && ball.getRight() <=
            	  bricks[i].getRight()) {
                  rightInside = ball.getRight() - bricks[i].getLeft();
              }
              //Left side of ball hits Right side of the brick
              if (ball.getLeft() >= bricks[i].getLeft() && ball.getLeft() <=
            	  bricks[i].getRight()) {
                  leftInside = bricks[i].getRight() - ball.getLeft();
              }
              //Top side of ball hits Bottom side of the brick
              if (ball.getTop() <= bricks[i].getBottom() && ball.getTop() >=
            	  bricks[i].getTop()) {
                  topInside = bricks[i].getBottom() - ball.getTop();
              }
              //Bottom side of ball hits Top side of the brick
              if (ball.getBottom() <= bricks[i].getBottom() && ball.getBottom()
            		  >= bricks[i].getTop()) {
                  bottomInside = ball.getBottom() - bricks[i].getTop();
              }
              
              if (topInside+bottomInside > leftInside+rightInside) {
                changeDX = true;
              } else {
                changeDY = true;
              }
              
              addPoints(10);
              bricks[i].hited();
              
              if(bricks[i].isVisible() == false) {
                  bricksLeft--;
              }
          }
        }
        if (changeDX == true) {
            ball.setDX(-ball.getDX());
        }
        if (changeDY == true) {
            ball.setDY(-ball.getDY());
        }
    }

    /**
     * Called when the user has pressed a key, which can be
     * a special key such as an arrow key.  If the key pressed
     * was one of the arrow keys, move the platform
     */ 
	public void keysPressed(boolean[] keys)
	{	
	    if (keys[KeyEvent.VK_LEFT]) {
	       platform.moveX(-5);
	       if (platform.getLeft() < 20) {
	          platform.setX(20);
	       }
	    } else if (keys[KeyEvent.VK_RIGHT]) {
	       platform.moveX(5);
	       if (platform.getRight() > 480) {
	          platform.setX(480 - platform.getWidth());
	       }
	    }
	}
	
	private class BlockPanel extends JPanel
	{
		public void paint (Graphics g)
		{
			g.setColor (Color.white);
		    g.fillRect (0, 0, getWidth(), getHeight());
		    
		    //Draws the for and background
		    for (int i = 0; i < 4; i++) {
		    	walls[i].paint(g);
		    }
		    
		    // Draw a red, filled circle:
		    ball.paint(g);
		    
		    for (int i = 0; i < TOTAL_BRICKS; i++) {
		    	if(bricks[i] != null) {
		    		bricks[i].paint(g);
		    	}
		    }
		    
		    platform.paint(g);
		    
		    //g.drawString("ball.getDX = " + ball.getDX(), 10, 10);
		    g.drawString("Poäng: " + getPoints(), 300, 10);
		    
		    if (bricksLeft <= 0) {
		    	g.setColor (Color.red);
		        g.drawString("Grattis du klarade banan!", 200, 250);
		        g.setColor (Color.black);
		        g.drawString("Laddar ny nivå...!", 220, 270);
		    }
		    if (ball.getY() > 500) {
		    	g.setColor (Color.red);
		        g.drawString("YOU SUCK!!!!!", 200, 250);
		    }
		         
		    //highscore.drawHighScore(g, 10, 10);
		    //g.drawString("Temp = " + temp, 10, 40)
		}
	}
	
	private class BlocksBrick extends FJRect
	{
	    //instance variables 
	    private int hardnes;

	    /**
	     * Constructor for objects of class Brick
	     */
	    public BlocksBrick(int x, int y, int width, int height, int hardnes)
	    {
	        super(x,y,width,height, Color.black);
	        setHardnes(hardnes);
	    }
	    
	    public void setHardnes(int init)
	    {
	        this.hardnes = init;
	        switch (hardnes) {
	            case 0: setVisible( false ); break;
	            case 1: this.setColor(Color.yellow); break;
	            case 2: this.setColor(Color.orange); break;
	            case 3: this.setColor(Color.red); break;
	            case 4: this.setColor(Color.green); break;
	            case 5: this.setColor(Color.blue); break;
	            default: this.setColor(Color.black); break;
	        }
	    }
	    public void hited()
	    {
	        setHardnes(hardnes - 1);
	    }
	}
	
	public boolean needKeyboardInput()
    {
    	return true;
    }
    
    public boolean hasNetworkSupport()
    {
    	return true;
    }
}