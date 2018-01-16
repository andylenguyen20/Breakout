package game_aln20;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.awt.geom.Point2D.Double;


public class Ball extends MovingScreenObject{
	public static final double BALL_RADIUS = 20;
	public static final double BALL_SPEED = 400;
	
	public Ball(Image img){
		super(img);
		setFitWidth(BALL_RADIUS);
		setFitHeight(BALL_RADIUS);
		setCurrentSpeed(BALL_SPEED);
	}
	
	public boolean intersects(ScreenObject screenObject){
		return intersects(screenObject.getBoundsInLocal());
	}
	
	public void update(Scene scene, double elapsedTime){
		super.update(elapsedTime);
	}
	public double getRadius(){
		return getBoundsInLocal().getHeight()/2;
	}
	public void redirectOffScreen(Scene scene){
		if(getCenter().getY() - getRadius() <= 0
				|| getRadius() + getCenter().getY() >= scene.getHeight()){
			setDirection(getDirection().getX(), -getDirection().getY());
		}
	}
}
