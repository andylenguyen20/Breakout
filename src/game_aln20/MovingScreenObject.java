package game_aln20;

import java.awt.geom.Point2D.Double;

import javafx.scene.image.Image;

public abstract class MovingScreenObject extends ScreenObject{
	private double currentSpeed;
	private Double direction;
	private Double startingPos;
	
	public MovingScreenObject(Image img){
		super(img);
		direction = new Double();
	}
	
	public void setDirection(double x, double y){
		direction.setLocation(x,y);
	}
	public Double getDirection(){
		return direction;
	}
	public void setPosition(double x, double y){
		setX(x);
		setY(y);
	}
	public Double getPosition(){
		return new Double(getX(),getY());
	}
	public void setStartingPosition(double x, double y){
		startingPos = new Double(x,y);
		setCenter(x + getBoundsInLocal().getWidth()/2, y + getBoundsInLocal().getHeight()/2);
	}
	public void reset(){
		setX(startingPos.getX());
		setY(startingPos.getY());
		setCenter(getX() + getBoundsInLocal().getWidth()/2, getY() + getBoundsInLocal().getHeight()/2);
	}
	public void setCurrentSpeed(double speed){
		currentSpeed = speed;
	}
	public double getCurrentSpeed(){
		return currentSpeed;
	}
	public void update(double elapsedTime){
		setPosition(getX() + currentSpeed * direction.getX() * elapsedTime,
				getY() + currentSpeed * direction.getY() * elapsedTime);
		setCenter(getCenter().getX() + currentSpeed * direction.getX() * elapsedTime,
				getCenter().getY() + currentSpeed * direction.getY() * elapsedTime);
	}
}
