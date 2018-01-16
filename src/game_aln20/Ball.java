package game_aln20;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.awt.geom.Point2D.Double;
import java.util.Random;


public class Ball extends MovingScreenObject{
	public static final double BALL_RADIUS = 20;
	public static final double BALL_SPEED = 400;
	private Double startingPos;
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
		if(getTop() <= 0 || getBottom() >= scene.getHeight()){
			setDirection(getDirection().getX(), -getDirection().getY());
		}
	}
	public boolean isOffscreen(Scene scene){
		return getRight() <= 0 || getLeft() >= scene.getWidth();
	}
	public void setStartingPosition(double x, double y){
		startingPos = new Double(x,y);
		setCenter(x + getRadius(), y + getRadius());
	}
	public void resetPosition(){
		setX(startingPos.getX());
		setY(startingPos.getY());
	}
	public Double getRandomNormalizedDirection(){
		Random random = new Random();
		double x = random.nextDouble() * (random.nextBoolean() ? 1 : -1);
		double y = random.nextDouble() * (random.nextBoolean() ? 1 : -1);
		double mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return new Double(x/mag, y/mag);
	}
}
