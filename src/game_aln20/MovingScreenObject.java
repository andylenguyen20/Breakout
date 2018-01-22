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
	public void setStartingPosition(double x, double y){
		startingPos = new Double(x,y);
	}
	public void reset(){
		setPosition(startingPos.getX(), startingPos.getY());
		System.out.println(startingPos.getX() + " " + startingPos.getY());
	}
	public void update(double elapsedTime){
		setPosition(getX() + currentSpeed * direction.getX() * elapsedTime,
				getY() + currentSpeed * direction.getY() * elapsedTime);
	}
	public void setCurrentSpeed(double speed){
		currentSpeed = speed;
	}
	public double getCurrentSpeed(){
		return currentSpeed;
	}
	public void setDirection(double x, double y){
		direction.setLocation(x,y);
	}
	public Double getDirection(){
		return direction;
	}
}
