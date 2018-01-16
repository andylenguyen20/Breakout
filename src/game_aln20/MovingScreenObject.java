package game_aln20;

import java.awt.geom.Point2D.Double;

import javafx.scene.image.Image;

public abstract class MovingScreenObject extends ScreenObject{
	private double currentSpeed;
	private Double direction;
	
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
	public void setCurrentSpeed(double speed){
		currentSpeed = speed;
	}
	public void update(double elapsedTime){
		setPosition(getX() + currentSpeed * direction.getX() * elapsedTime,
				getY() + currentSpeed * direction.getY() * elapsedTime);
		setCenter(getCenter().getX() + currentSpeed * direction.getX() * elapsedTime,
				getCenter().getY() + currentSpeed * direction.getY() * elapsedTime);
	}
}
