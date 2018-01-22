package game_aln20;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.awt.geom.Point2D.Double;
import java.util.Random;


public class Ball extends MovingScreenObject{
	public static final double DEFAULT_RADIUS = 20;
	public static final double DEFAULT_SPEED = 500;
	public static final String IMAGE_NAME = "ball.gif";
	
	public double startingSpeed;
	
	public Ball(){
		super();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
		setFitWidth(DEFAULT_RADIUS);
		setFitHeight(DEFAULT_RADIUS);
		setCurrentSpeed(DEFAULT_SPEED);
		setStartingSpeed(DEFAULT_SPEED);
	}
	public void setStartingSpeed(double speed){
		startingSpeed = speed;
	}
	public double getStartingSpeed(){
		return startingSpeed;
	}
	public boolean intersects(ScreenObject screenObject){
		return intersects(screenObject.getBoundsInLocal());
	}
	public void update(Scene scene, double elapsedTime){
		super.update(elapsedTime);
		redirectOffscreen(scene);
	}
	public double getRadius(){
		return getBoundsInLocal().getHeight()/2;
	}
	public int getOffscreenStatus(Scene scene){
		if(getRight() <= 0){
			return -1;
		}else if(getLeft() >= scene.getWidth()){
			return 1;
		}else{
			return 0;
		}
	}
	public void reset(){
		super.reset();
		setCurrentSpeed(startingSpeed);
		Double normalizedDir = getRandomNormalizedDirection();
		setDirection(normalizedDir.getX(), normalizedDir.getY());
	}
	
	/*
	 * HELPERS
	 */
	private void redirectOffscreen(Scene scene){
		if(getTop() <= 0 || getBottom() >= scene.getHeight()){
			setDirection(getDirection().getX(), -getDirection().getY());
		}
	}
	private Double getRandomNormalizedDirection(){
		Random random = new Random();
		double y = random.nextDouble();
		double x = random.nextDouble() + y/2; //prevents y direction from being extremely larger than x direction
		double mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return new Double((random.nextBoolean() ? 1 : -1) * (x/mag), (random.nextBoolean() ? 1 : -1) * (y/mag));
	}
}
