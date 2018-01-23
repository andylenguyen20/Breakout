package game_aln20;

import java.awt.geom.Point2D.Double;

public abstract class MovingScreenObject extends ScreenObject{
	private double currentSpeed;
	private Double direction;
	private Double startingPos;
	
	public MovingScreenObject(){
		super();
		direction = new Double();
	}
	
	/*
	 * sets the starting position of the object
	 */
	public void setStartingPosition(double x, double y){
		startingPos = new Double(x,y);
	}
	
	/*
	 * respawns object at the starting position, assuming starting position has been set
	 */
	public void reset(){
		setPosition(startingPos.getX(), startingPos.getY());
	}
	
	/*
	 * updates the position and center of the moving object given current speed and elapsed time
	 * @see game_aln20.ScreenObject#setPosition for more info on what this does
	 */
	public void update(double elapsedTime){
		setPosition(getX() + currentSpeed * direction.getX() * elapsedTime,
				getY() + currentSpeed * direction.getY() * elapsedTime);
	}
	
	/*
	 * sets the current speed of this moving object
	 */
	public void setCurrentSpeed(double speed){
		currentSpeed = speed;
	}
	
	/*
	 * returns the current speed of this moving object
	 */
	public double getCurrentSpeed(){
		return currentSpeed;
	}
	
	/*
	 * sets the direction of this moving object
	 */
	public void setDirection(double x, double y){
		direction.setLocation(x,y);
	}
	
	/*
	 * returns the direction of this moving object as a Point2D.Double Object
	 */
	public Double getDirection(){
		return direction;
	}
}
