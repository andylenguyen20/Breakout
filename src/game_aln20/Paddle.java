package game_aln20;

import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Paddle extends MovingScreenObject{
	public static final double DEFAULT_WIDTH = 20;
	public static final double DEFAULT_HEIGHT = 90;
	public static final double DEFAULT_SPEED = 200;
	public static final String IMAGE_NAME = "paddle.gif";
	
	private boolean sticky;
	private boolean abilityOn;
	private Ball stuckBall;
	
	private double startingHeight;
	
	public Paddle() {
		super();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
		setFitWidth(DEFAULT_WIDTH);
		setFitHeight(DEFAULT_HEIGHT);
		setCurrentSpeed(DEFAULT_SPEED);
		setDirection(0,0);
		sticky = false;
		abilityOn = false;
	}
	public void setStartingHeight(double height){
		startingHeight = height;
	}
	
	public void update(Scene scene, double elapsedTime){
		if(getY() >= scene.getHeight() - getBoundsInLocal().getHeight() 
				&& getDirection().getY() != -1){
			return;
		}else if(getY() <= 0 && getDirection().getY() != 1){
			return;
		}
		update(elapsedTime);
	}
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
	public void reset(){
		super.reset();
		setDirection(0,0);
		setFitHeight(startingHeight);
		sticky = false;
		abilityOn = false;
	}
	public void setAbilityOn(){
		abilityOn = true;
	}
	public boolean abilityOn(){
		return abilityOn;
	}
	public void activateSticky(){
		sticky = true;
	}
	public void stickToBall(Ball ball){
		if(stuckBall == null){
			ball.setPosition(getCenter().getX() - ball.getRadius(), getCenter().getY()- ball.getRadius());
			ball.setCurrentSpeed(0);
			stuckBall = ball;
		}else{
			ball.setPosition(getCenter().getX() - ball.getRadius(), getCenter().getY()- ball.getRadius());
		}
	}
	public void launchBall(GameDelegate gd){
		if(!sticky || stuckBall == null) return;
		gd.launchBallFromStickyPaddle(stuckBall, this);
		stuckBall = null;
	}
}
