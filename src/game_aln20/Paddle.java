package game_aln20;


import java.awt.geom.Point2D.Double;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Paddle extends MovingScreenObject{
	public static final double PADDLE_WIDTH = 20;
	public static final double PADDLE_HEIGHT = 75;
	public static final double PADDLE_SPEED = 100;
	public static final String IMAGE_NAME = "paddle.gif";
	private boolean sticky;
	private boolean abilityOn;
	private Ball stuckBall;
	//private Double ballDirection;
	
	public Paddle(Image img) {
		super(img);
		setFitWidth(PADDLE_WIDTH);
		setFitHeight(PADDLE_HEIGHT);
		setDirection(0,0);
		setCurrentSpeed(PADDLE_SPEED);
		sticky = false;
		abilityOn = false;
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
	public void reset(){
		super.reset();
		setDirection(0,0);
		setFitHeight(PADDLE_HEIGHT);
		sticky = false;
		abilityOn = false;
	}
	public void activateSpecialAbility(GameDelegate gd){
		if(abilityOn) return;
		abilityOn = true;
		Random random = new Random();
		int num = random.nextInt(3);
		sticky = true;
		/*
		switch(num){
			case 0: gd.changePaddleSize(2);
			case 1: gd.changePaddleSize(.5);
			case 2: sticky = true;
		}
		*/
	}
	public void stickToBall(Ball ball){
		if(stuckBall == null){
			System.out.println("center");
			//ballDirection = ball.getDirection();
			ball.setPosition(getCenter().getX(), getCenter().getY());
			ball.setCurrentSpeed(0);
			stuckBall = ball;
		}else{
			ball.setPosition(getCenter().getX(), getCenter().getY());
		}
	}
	public void launchBall(GameDelegate gd){
		if(!sticky || stuckBall == null) return;
		System.out.println("launched");
		gd.launchBallFromStickyPaddle(stuckBall, this);
		stuckBall = null;
	}
	/*
	public Double getBallDirection(){
		return ballDirection;
	}
	*/

}
