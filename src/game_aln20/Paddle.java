package game_aln20;

import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Paddle extends MovingScreenObject{
	public static final double PADDLE_WIDTH = 20;
	public static final double PADDLE_HEIGHT = 75;
	public static final double PADDLE_SPEED = 200;
	public static final String IMAGE_NAME = "paddle.gif";
	
	private boolean sticky;
	private boolean abilityOn;
	private Ball stuckBall;
	
	public Paddle() {
		super();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
		setFitWidth(PADDLE_WIDTH);
		setFitHeight(PADDLE_HEIGHT);
		setCurrentSpeed(PADDLE_SPEED);
		setDirection(0,0);
		sticky = false;
		abilityOn = false;
	}
	public void shrink(int level){
		setFitHeight(PADDLE_HEIGHT - 10*level);
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
			return false;
		}
		
		if(ball.getDirection().getY() > 0 && ball.getCenter().getY() < this.getCenter().getY()){
			ball.setDirection(-ball.getDirection().getX(), -ball.getDirection().getY());
		}else if(ball.getDirection().getY() < 0 && ball.getCenter().getY() < this.getCenter().getY()){
			ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
		}else{
			ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
		}
		
		/*
		if(ball.getCenter().getX() >= getLeft() && ball.getCenter().getX() <= getRight()){
			System.out.println("hit top or bottom side of paddle");
			ball.setDirection(ball.getDirection().getX(), -ball.getDirection().getY());
		}// hit left or right
		else if(ball.getCenter().getY() <= getBottom() && ball.getCenter().getY() >= getTop()){
			System.out.println("hit left or right side of paddle");
			ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
		}
		*/
		return true;
	}
	public void reset(int level){
		super.reset();
		setDirection(0,0);
		setFitHeight(PADDLE_HEIGHT);
		shrink(level);
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
		System.out.println("launched");
		gd.launchBallFromStickyPaddle(stuckBall, this);
		stuckBall = null;
	}
}
