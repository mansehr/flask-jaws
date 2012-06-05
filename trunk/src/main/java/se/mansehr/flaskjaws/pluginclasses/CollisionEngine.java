package se.mansehr.flaskjaws.pluginclasses;

import java.util.ArrayList;

import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJMovingObject;
import se.mansehr.flaskjaws.pluginclasses.pluginobjects.FJObject;

/**
 * The collision engine that controls all the physics in 2D.
 * If the user wants it you only need to add all objects to a new 
 * instance of the class and then call the run method in every turn.
 * The run method calculates all FJMovingObjects new positions
 * and then at last moves them to their new position.
 * 
 * This class i not yet fully functional. Still have to do more testing.
 * Only tested with one FJMovingObject(FJBall) and some FJMovingObject.
 * @author Sehr
 */
public class CollisionEngine
{
	private ArrayList<FJObject> 		objects;
	private ArrayList<FJMovingObject> 	movingObjects;
	
	public CollisionEngine()
	{
		objects 		= new ArrayList<FJObject>();
		movingObjects 	= new ArrayList<FJMovingObject>();
	}	
	
	/**
	 * This method adds new FJObject to the CollisionEngine class and then uses
	 * them when calculating the movingObjects
	 * @param obj new object to ad
	 * @return true if it was addes succefully
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public boolean add(FJObject obj)
	{
		return objects.add(obj);
	}
	
	/**
	 * This method adds new FJMovingObject to the CollisionEngine class and 
	 * then clalculates their new position. 
	 * @param obj
	 * @return true if it was addes succefully
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public boolean add(FJMovingObject obj)
	{
		return movingObjects.add(obj);
	}


	/**
	 * Removes the object from the list.
	 * @param obj object to remove
	 * @return true if it was succefully removed 
	 * @see java.util.ArrayList#remove(java.lang.Object)
	 */
	public boolean remove(Object obj)
	{
		if(FJMovingObject.class.isInstance(obj)) {
			return movingObjects.remove(obj);
		} else {
			return objects.remove(obj);
		}
	}


	/**
	 * This method tests all the objects
	 * And handles the collisions and calculates all movingobjects next 
	 * position, and at last moves them. 
	 * First it tests if movingobjects next move is valid by calling 
	 * FJMovingObject.isNextXMoveValid(). 
	 */
	public void run()
	{
		for (FJMovingObject mO : movingObjects) {
			if (mO.isNextXMoveValid() == false) {
				mO.setDX(-mO.getDX());
			}
			if (mO.isNextYMoveValid() == false) {
				mO.setDY(-mO.getDY());
			}
			
			for (FJObject obj : objects) {
				// mO hit something solid! 
				if (mO.intersects(obj)) {
					// First we see wich direction the moving object is moving
					// and where the objects hit eachother
					// Different code for four different directions.
					if (mO.getDX() > 0) { //Moving object moving to right
						// Moving objects hits from the left, change moving 
						// direction
						if (mO.getRight() > obj.getLeft() 
								&& mO.getCenterX() < obj.getCenterX()
								&& mO.getCenterY() > obj.getTop() 
								&& mO.getCenterY() < obj.getBottom()) {
							mO.setDX(-mO.getDX());
						}
					} else {
						if (mO.getLeft() < obj.getRight() 
								&& mO.getCenterX() > obj.getCenterX()
								&& mO.getCenterY() > obj.getTop() 
								&& mO.getCenterY() < obj.getBottom()) {
							mO.setDX(-mO.getDX());
						}
					}
					
					if (mO.getDY() > 0) { // Moving object moving to bottom
						// Moving objects hits from the top, change moving 
						// direction
						if (mO.getBottom() > obj.getTop()
								&& mO.getCenterY() < obj.getCenterY()
								&& mO.getCenterX() > obj.getLeft() 
								&& mO.getCenterX() < obj.getRight()) {
							mO.setDY(-mO.getDY());
						}
					} else {
						if (mO.getTop() < obj.getBottom()
								&& mO.getCenterY() > obj.getCenterY()
								&& mO.getCenterX() > obj.getLeft() 
								&& mO.getCenterX() < obj.getRight()) {
							mO.setDY(-mO.getDY());
						}
					}
				}
			}
				
			mO.move();
		}
	}
}
