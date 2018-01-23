package game_aln20;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.awt.geom.Point2D.Double;
import java.util.Random;


public class Ball extends MovingScreenObject{
	public static final double DEFAULT_RADIUS = 20;
	public static final double DEFAULT_SPEED = 500;
	public static final String IMAGE_NAME = "ball.gif";
	
	public Ball(){
		super();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
		setFitWidth(DEFAULT_RADIUS);
		setFitHeight(DEFAULT_RADIUS);
		setCurrentSpeed(DEFAULT_SPEED);
		setStartingSpeed(DEFAULT_SPEED);
	}
	
	/*
	 * returns a boolean indicating whether the ball is colliding with a ScreenObject
	 */
	public boolean intersects(ScreenObject screenObject){
		return intersects(screenObject.getBoundsInLocal());
	}
	
	/*
	 * updates the ball's position and center over a given elapsed time, taking into
	 * account for wall bounces
	 */
	public void update(Scene scene, double elapsedTime){
		super.update(elapsedTime);
		redirectOffscreen(scene);
	}
	
	/*
	 * returns the radius of the ball
	 */
	public double getRadius(){
		return getBoundsInLocal().getHeight()/2;
	}
	
	/*
	 * checks to see if the ball is off either the left or right side of the screen.
	 * returns -1 for off the left side, 1 for off the right side, 0 for not offscreen
	 */
	public int getOffscreenStatus(Scene scene){
		if(getRight() <= 0){
			return -1;
		}else if(getLeft() >= scene.getWidth()){
			return 1;
		}else{
			return 0;
		}
	}
	
	/*
	 * resets the ball position, ball speed, and sets a new random direction for the ball
	 * also @see game_aln20.MovingScreenObject#reset()
	 */
	public void reset(){
		super.reset();
		Double normalizedDir = getRandomNormalizedDirection();
		setDirection(normalizedDir.getX(), normalizedDir.getY());
	}
	
	/*
	 * redirects the ball off the top and bottom screens if needed
	 */
	private void redirectOffscreen(Scene scene){
		if(getTop() <= 0 || getBottom() >= scene.getHeight()){
			setDirection(getDirection().getX(), -getDirection().getY());
		}
	}
	
	/*
	 * returns a random normalized direction for the ball in the form of a Point2D.Double Object
	 */
	private Double getRandomNormalizedDirection(){
		Random random = new Random();
		double y = random.nextDouble();
		double x = random.nextDouble() + y/2; //prevents y direction from being extremely larger than x direction
		double mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return new Double((random.nextBoolean() ? 1 : -1) * (x/mag), (random.nextBoolean() ? 1 : -1) * (y/mag));
	}
}
