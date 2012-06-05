package se.mansehr.flaskjaws.pluginclasses.pluginobjects;

import java.io.*;
import java.awt.*;

import javax.swing.JOptionPane;

public class FJHighscore implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HighScoreObj[] highscores;
	private transient int lastHighScorePos = -1;
	
	public  FJHighscore()
	{
		highscores = new HighScoreObj[10];
		for (int i = 0; i < highscores.length; i++) {
			highscores[i] = new HighScoreObj(0, "");
		}
	}
	
	private class HighScoreObj implements Serializable
    {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public final int points;
        public final String name;

        public HighScoreObj(int points, String name)
        {
            this.points = points;
            this.name = name;
        }
    }
	
	/**
	 * Test if the points qualify for the list, returns true if the do.
	 * @param points
	 * @return
	 */
	public boolean qualify(int points)
	{
		if (points > highscores[9].points) {
			return true;
        } else {
        	return false;
        }
	}
	
	public int addHighScore(int points)
    {
		if (qualify(points)) {
			String name = JOptionPane.showInputDialog(null, "Enter Your Name:",
					"Your name", JOptionPane.DEFAULT_OPTION);
			
			return addHighScore(points, name);
        }
		return -1;
    }
	
	/**
	 * Test if points qualify for the liste and the add the name & points
	 * to the list at the right position. 
	 * Returns -1 if the points didnt qualify and the position if it did.
	 * @param points
	 * @param name
	 * @return
	 */
	public int addHighScore(int points, String name)
    {
        if (qualify(points)) {
            int position = 9;
            for (int i = 0; i < 9; i++ ) {
                if (points > highscores[i].points) {
                    position = i;
                    for ( i = 9; i > position; i--) {
                        highscores[i] = highscores[i - 1];
                    }
                    i = 9; //End loop
                }
            }
            highscores[position] = new HighScoreObj(points, name);
            return lastHighScorePos = position;
        }
        return lastHighScorePos = -1;	//Didnt qualify for the list...
    }
	
	public int getLastPos()
	{
		return lastHighScorePos;
	}
	
	public void resetLastPos()
	{
		lastHighScorePos = -1;
	}
	
	public void drawHighScore(Graphics g, int x, int y)
	{
		g.setColor(Color.black);
        Font highFont = new Font("Serif", Font.BOLD, 16);
        g.setFont(highFont);
        g.drawString("High scorelist:", x, y);
        Font normalFont = new Font("Serif", Font.BOLD, 12);
        g.setFont(normalFont);
        for (int i = 0; i < 10; i++) {
            if (lastHighScorePos == i) {
                g.setColor(Color.red);
            }

            int top = y + (i * 16) + 20;
            g.drawString("" + (i + 1) + ". " + highscores[i].name , x, top);
            g.drawString("" +  highscores[i].points , x + 150, top);

            if(lastHighScorePos == i) {
                g.setColor(Color.black);
            }
        }
	}
	
	public static FJHighscore loadFromFile(String filename) 
	throws FileNotFoundException, IOException, ClassNotFoundException
	{
			FJHighscore fj;
			FileInputStream fIn = new FileInputStream(filename);
			ObjectInputStream os = new ObjectInputStream(fIn);				
			fj = (FJHighscore)os.readObject();
			os.close();
			return fj;
	}
	
	public void saveToFile(String filename) throws IOException
	{
		FileOutputStream fOut = new FileOutputStream(filename);
		ObjectOutputStream os = new ObjectOutputStream(fOut);
		
		os.writeObject(this);
		
		os.close();
	}
}