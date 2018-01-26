package game_aln20;

import javafx.scene.Scene;

public class Paddle extends MovingScreenObject{
	public static final double DEFAULT_WIDTH = 20;
	public static final double DEFAULT_HEIGHT = 90;
	public static final double DEFAULT_SPEED = 200;
	public static final String IMAGE_NAME = "paddle.gif";
	
	private boolean sticky;
	private boolean abilityOn;
	private Ball stuckBall;
	
	private double startingHeight;
	
	
	// you may think constructor has duplication with the reset() function, but the constructor
	// cannot call reset() yet since it's starting position has yet to be established
	public Paddle() {
		super(IMAGE_NAME);
		setFitWidth(DEFAULT_WIDTH);
		setStartingHeight(DEFAULT_HEIGHT);
		setStartingSpeed(DEFAULT_SPEED);
		resetPaddleSpecificAttributes();
	}
	
	/*
	 * sets the starting height of the paddle. Whenever the paddle is reset,
	 * its height should go to this starting height.
	 */
	public void setStartingHeight(double height){
		startingHeight = height;
	}
	
	/*
	 * updates the paddle's position over an elapsed time, also restricting it from
	 * moving offscreen
	 */
	public void update(Scene scene, double elapsedTime){
		if(getY() >= scene.getHeight() - getBoundsInLocal().getHeight() 
				&& getDirection().getY() != -1){
			return;
		}else if(getY() <= 0 && getDirection().getY() != 1){
			return;
		}
		super.update(elapsedTime);
	}
	
	/*
	 * returns a boolean indicating whether the paddle has redirected the ball
	 * returns true if the ball has been redirected, either through stickyAbility or through
	 * normal redirection
	 * returns false if the paddle is not in contact with the ball
	 */
	public boolean redirectBall(Ball ball){
		if(!ball.intersects(this)){
			return false;
		}
		if(sticky) {
			stickToBall(ball);
			return true;
		}
		if(ball.getDirection().getY() > 0 && ball.getCenter().getY() < this.getCenter().getY()){
			ball.setDirection(-ball.getDirection().getX(), -ball.getDirection().getY());
		}else if(ball.getDirection().getY() < 0 && ball.getCenter().getY() < this.getCenter().getY()){
			ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
		}else if(ball.getDirection().getY() < 0 && ball.getCenter().getY() > this.getCenter().getY()){
			ball.setDirection(-ball.getDirection().getX(), -ball.getDirection().getY());
		}else if(ball.getDirection().getY() > 0 && ball.getCenter().getY() > this.getCenter().getY()){
			ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
		}
		return true;
	}
	
	/*
	 * resets the ball's sticky ability and abilityOn boolean, as well as starting height, starting
	 * position, and starting direction
	 * also @see game_aln20.MovingScreenObject#reset()
	 */
	public void reset(){
		super.reset();
		resetPaddleSpecificAttributes();
	}
	
	private void resetPaddleSpecificAttributes(){
		setDirection(0,0);
		setFitHeight(startingHeight);
		setCurrentSpeed(getStartingSpeed());
		sticky = false;
		abilityOn = false;
	}
	
	/*
	 * switches on abilityOn boolean to indicate the paddle currently has an ability
	 */
	public void setAbilityOn(){
		abilityOn = true;
	}
	
	/*
	 * returns a boolean indicating whether the paddle currently has an ability
	 */
	public boolean abilityOn(){
		return abilityOn;
	}
	
	/*
	 * activates the paddle's sticky ability
	 */
	public void activateSticky(){
		sticky = true;
	}
	
	/*
	 * activates whenever the paddle makes contact with the ball.
	 * sticks the ball to the center of the paddle
	 */
	private void stickToBall(Ball ball){
		if(stuckBall == null){
			ball.setPosition(getCenter().getX() - ball.getRadius(), getCenter().getY()- ball.getRadius());
			ball.setCurrentSpeed(0);
			stuckBall = ball;
		}else{
			ball.setPosition(getCenter().getX() - ball.getRadius(), getCenter().getY()- ball.getRadius());
		}
	}
	
	/*
	 * gets the GameDelegate to launch the ball
	 */
	public void launchBall(GameDelegate gd){
		if(!sticky || stuckBall == null) return;
		gd.launchBallFromStickyPaddle(stuckBall, this);
		stuckBall = null;
	}
}
