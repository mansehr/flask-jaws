package se.mansehr.flaskjaws.plugins;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import se.mansehr.flaskjaws.pluginclasses.FJGamePlugin;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJHighscore;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJImages;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJSprite;

/********************************************************************************
 *
 * Snake.java
 *
 * Ultimate Snake for Flask Jaws
 *
 * Copyright (c) 2007 Andreas Sehr
 * All rights reserved.
 *
 * @version         0.1     Beta Version
 *
 *******************************************************************************/


public class UltimateSnake extends FJGamePlugin
{	
    private final int RIGHT   = 1001;
    private final int UP      = 1002;
    private final int LEFT    = 1003;
    private final int DOWN    = 1004;

    private final int GRID_SIZE  = 20;
    private final int GRIDS_X    = 20;
    private final int GRIDS_Y    = 20;
    private final int STEP       = GRID_SIZE;
    private final int VIEWTOP    = GRID_SIZE / 2;
    private final int VIEWLEFT   = GRID_SIZE / 2;
    private final int VIEWRIGHT  = (GRID_SIZE * GRIDS_X);
    private final int VIEWBOTTOM = (GRID_SIZE * GRIDS_Y);

    private final int APPLE_IMGS = 9;
    private final int START_TAIL_SIZE = 3;
    private final int MAX_TAIL_SIZE = GRIDS_X * GRIDS_Y;
    private static final int START_SPEED = 250;    //Delay in milliseconds each turn
    private final String IMAGE_DIR = "imgs/";
    
    //private static final String SAVE_FILE_NAME = "data.sna";

    private boolean moved;
    private int headDirection = RIGHT;
    
    private int antTail;
    private boolean showScull;
    private int     flagTicks;  //Flag ticks left
    
    //private int lastHighScorePos;

    private FJSprite head;
    private FJSprite[] tail;
    private FJSprite apple;
    private FJSprite scull;
    private FJSprite flag;
    private Image[] appleImgs;
    private Image headUp;
    private Image headDown;
    private Image headLeft;
    private Image headRight;
    
    private SnakeField ultimatePanel;
    
    private FJHighscore highscore;

    public UltimateSnake()
    {
    	super("Ultimate Snake", START_SPEED);
    	ultimatePanel = new SnakeField();
    	ultimatePanel.setBackground(Color.white);
    	ultimatePanel.setPreferredSize(new Dimension(VIEWRIGHT + 20, VIEWBOTTOM + 20));
    	ultimatePanel.setFocusable(true);
    	setMainPanel(ultimatePanel);
    }

    /**
     * Calls from FlaskJaws to start the plugin
     */
    public void start()
    {
    	super.start();
    	reset();
    }
    
    /**
     *Calls when the user presses enter
     */
    public void startGame()
    {    	
        reset();
        setDead(false);
        setDelay(START_SPEED);
        setMainPanel(ultimatePanel);
        super.start();
    }


    public void tick() {
        if (isStarted() && !isPaused()) {
            moveSnake();
            testKrash();
            if (flagTicks > 0) {
                flagTicks--;
            }
        }
        getMainPanel().repaint();
    }

    private boolean loadImages()
    {
    	 headUp      = FJImages.getImage(getClass(), IMAGE_DIR + "headUp.gif");
         headDown    = FJImages.getImage(getClass(), IMAGE_DIR + "headDown.gif");
         headLeft    = FJImages.getImage(getClass(), IMAGE_DIR + "headLeft.gif");
         headRight   = FJImages.getImage(getClass(), IMAGE_DIR + "headRight.gif");
         
         appleImgs = new Image[APPLE_IMGS];
         for(int i = 0; i < APPLE_IMGS; i++)
         {
             appleImgs[i] = FJImages.getImage(getClass(), IMAGE_DIR+"apple" + (i+1) + ".gif");
         }
         apple = new FJSprite(appleImgs[0]);
         head = new FJSprite(headRight);
         scull = new FJSprite(FJImages.getImage(getClass(), IMAGE_DIR+"scull.gif"));
         flag = new FJSprite(FJImages.getImage(getClass(), IMAGE_DIR+"flag.gif"));
         Image img = FJImages.getImage(getClass(), IMAGE_DIR+"tail.gif");
         tail = new FJSprite[MAX_TAIL_SIZE];
         for(int i = 0; i < MAX_TAIL_SIZE; ++i)
         {
             tail[i] = new FJSprite(img);
         }

         return true;
    }

    public void reset()
    {
    	super.reset();
    	
    	loadImages();
    	
    	try {
    		highscore = FJHighscore.loadFromFile(getName()+".sco");
    	} catch (Exception e) {
    		highscore = new FJHighscore();
    	}
    	
        head.setXY(GRID_SIZE*(GRIDS_X/2), GRID_SIZE*(GRIDS_Y/2));
        setDirection(RIGHT);
        moved = false;

        apple.setXY(GRID_SIZE*(GRIDS_X/2), GRID_SIZE*(GRIDS_Y/3));
        newRandomPosition(scull);

        antTail = START_TAIL_SIZE;

        flagTicks = 0;        
        this.setStarted(false);

        for(int i = 0; i < MAX_TAIL_SIZE; ++i)
        {
            if(i < antTail)
                tail[i].setXY(head.getX()-((i+1)*STEP), head.getY());
            else
                tail[i].setXY(0,0);
        }
        
        showScull = false;
    }

    private class SnakeField extends JPanel
    {
		public static final long serialVersionUID = 732823763;
		
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            //Reset panel
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
              
            Font normalFont = g2.getFont();

            Stroke stroke = new BasicStroke(GRID_SIZE);
            g2.setStroke(stroke);
            if(isPaused())
            {
                Font bigFont = new Font("Serif", Font.BOLD, 32);
                g2.setFont(bigFont);
                g2.setPaint(Color.black);
                g2.drawString("Game paused!!", VIEWRIGHT/4, VIEWBOTTOM/4);
                g2.drawString("Press \'p\' to continue!", VIEWRIGHT/5, VIEWBOTTOM/3);
            }
            else if(!isStarted())
            {
                Font bigFont = new Font("Serif", Font.BOLD, 32);
                g2.setFont(bigFont);
                g2.setPaint(Color.red);
                g2.drawString("Press enter to start!", VIEWRIGHT/5, VIEWBOTTOM - VIEWBOTTOM/10);

                highscore.drawHighScore(g, VIEWRIGHT/3, VIEWBOTTOM/4);
            }
              else
              {
            	g.setColor(Color.PINK);
                for (int i = 0; i < antTail; ++i)
                    tail[i].paint(g, this);

                g.setColor(Color.MAGENTA);
                head.paint(g, this);
                
                
                if(showScull)
                {
                	g.setColor(Color.RED);
                    scull.paint(g, this);
                }
                else
                {
                	g.setColor(Color.CYAN);
                    apple.paint(g, this);
                }

                g.setColor(Color.GREEN);
                if(flagTicks > 0)
                {
                    flag.paint(g, this);
                }
            }

            if (isDead())
            {
            	Font bigFont = new Font("Serif", Font.BOLD, 20);
                g2.setFont(bigFont);
                g2.setPaint(Color.black);
                String gameOver = "Game Over!";
                int gameOverLeft = VIEWRIGHT/3;
                if(highscore.getLastPos() >= 0)
                {
                    gameOver = "You have made the top ten!";
                    gameOverLeft = VIEWRIGHT/6;
                }
            	g2.drawString(gameOver, gameOverLeft, VIEWBOTTOM/7);
                g2.drawString("Score: " + getPoints(), (VIEWRIGHT/3), (VIEWBOTTOM/7)+20);
            }

            g2.setPaint(Color.black);
            g2.drawRect(VIEWLEFT, VIEWTOP, VIEWRIGHT, VIEWBOTTOM);

            
            
            g2.setFont(normalFont);
            g2.setPaint(Color.WHITE);
            g2.drawString("Points: " + getPoints(), 22, 15);
            g2.drawString("Ultimate Snake", 300, 15);
            g2.drawString("Level: " + getLevel(), 150, 15);

            if(flagTicks > 0)
            {
            	g2.setPaint(Color.GREEN);
            	if(flagTicks <= 20 && flagTicks % 2 == 0)
            	{
            		g2.setPaint(Color.RED);
            	}	
                g2.drawString("Flag: " + flagTicks, 22, VIEWBOTTOM+15);
            }
         }   
   }

    private void testKrash()
    {
        if(head.getX() <= VIEWLEFT || head.getX() >= VIEWRIGHT ||
           head.getY() <= VIEWTOP || head.getY() >= VIEWBOTTOM)
        {
            dead("Dead Head outside");
            return;
        }

        // Apple test
        if(showScull == false && head.getY() == apple.getY() && head.getX() == apple.getX())
        {
            antTail++;
            addPoints(10);
            newRandomPosition(apple);
            apple.setImage(appleImgs[random.nextInt(APPLE_IMGS)]);
            
            if(getPoints()%100 == 0)
            {
                showScull = true;
                newRandomPosition(scull);
            }

            if(getPoints()%300 == 0)
            {
                nextLevel();
                flagTicks = 80;
                newRandomPosition(flag);
            }
        }

        // Scull test
        if(showScull && head.getY() == scull.getY() && head.getX() == scull.getX())
        {
            antTail = antTail*2;
            showScull = false;
        }

        // flag test
        if(flagTicks > 0 && head.getY() == flag.getY() && head.getX() == flag.getX())
        {
            antTail = START_TAIL_SIZE;
            setDelay(getDelay() - 20);
            flagTicks = 0;
            for(int i = antTail; i < MAX_TAIL_SIZE; i++)
                tail[i].setXY(0,0);
        }

        for (int i = 3; i < antTail; ++i)
            if ( head.getY() == tail[i].getY() &&
                 head.getX() == tail[i].getX() )
            {
                dead("Dead Head = tailpos[" + i + "] == head.getY():(" + head.getY() + ") == tail[i].getY():("+
                    tail[i].getY()+") && head.getX():(" +head.getX()+")== tail[i].getX():("+tail[i].getX()+").");

                return;
            }
    }

    void moveHead(int initX, int initY)
    {
        for(int i = antTail-1; i > 0; --i)
            tail[i].setXY(tail[i-1].getX(), tail[i-1].getY());

        tail[0].setXY(head.getX(), head.getY());

        head.setXY(initX, initY);
    }

    private void newRandomPosition(FJSprite obj)
    {
        boolean colision;
        int loops = 0;
        do{
            colision = false;
            obj.setXY((random.nextInt(GRIDS_X-1)+1)*GRID_SIZE , (random.nextInt(GRIDS_Y-1)+1)*GRID_SIZE);
            for (int i = 0; i < antTail; ++i)
                if ( obj.getY() == tail[i].getY() &&
                     obj.getX() == tail[i].getX() )
                {
                    colision = true;
                }
        } while(colision == true && loops++ < 1000);
    }
    
    public void keyPressed(KeyEvent e)
    {		
		if (isStarted()) {
			if (e.getKeyCode() == KeyEvent.VK_P) {    // P key code = 80
	            if(isPaused()) {
	            	resume();
	            } else {
	            	pause();
	            }   // Enter key code = 10
	        }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            	setDirection(LEFT);  // Arrow Left
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
            	setDirection(UP);  // Arrow Up
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            	setDirection(RIGHT);  // Arrow Right
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            	setDirection(DOWN);   // Arrow Down
            }
         } else if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
             startGame();
         }
		getMainPanel().repaint();
    }

    public void stop()
    {
    	super.stop();
    	dead("Stoped");
    }
    
    void dead(String arg)
    {
    	//System.out.println(arg);
        setDelay(40);
       
        this.setDead(true);
        this.setStarted(false);
        
        if(highscore.qualify(getPoints())) {
        	highscore.addHighScore(getPoints());
        	try {
        		highscore.saveToFile(getName()+".sco");
        	} catch (Exception e) {} // Darken this error... 
        } else {
        	highscore.resetLastPos();	// Last pos no longer good
        }
        setPoints(0);
        
        //stop();
    } 
    
    
     private void moveSnake()
     {
     	switch (getDirection())
        {
            default:
            case RIGHT: moveHead(head.getX() + STEP, head.getY()); break;
            case UP: moveHead(head.getX(), head.getY() - STEP); break;
            case LEFT: moveHead(head.getX() - STEP, head.getY()); break;
            case DOWN: moveHead(head.getX(), head.getY() + STEP); break;
        }
     	moved = true;
     }
     
    void setDirection(int initDirection)
    {
        if(moved)
        {
            if (initDirection == RIGHT)
                if (headDirection != LEFT)
                    headDirection = RIGHT;

            if (initDirection == UP)
                if (headDirection != DOWN)
                    headDirection = UP;

            if (initDirection == LEFT)
                if (headDirection != RIGHT)
                    headDirection = LEFT;

            if (initDirection == DOWN)
                if (headDirection != UP)
                    headDirection = DOWN;

            switch(headDirection)
            {
                default:
                case RIGHT: head.setImage(headRight); break;      // Right
                case UP: head.setImage(headUp); break;         // Up
                case LEFT: head.setImage(headLeft); break;       // Left
                case DOWN: head.setImage(headDown); break;       // Down
            }
            moved = false;
        }
    }
    public boolean needKeyboardInput()
    {
    	return true;
    }
    
    private int getDirection() 
    {
    	return headDirection;
    }
    
    public boolean hasNetworkSupport()
    {
    	return true;
    }
}