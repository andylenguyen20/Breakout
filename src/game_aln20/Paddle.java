package game_aln20;


import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Paddle extends MovingScreenObject{
	public static final double PADDLE_WIDTH = 20;
	public static final double PADDLE_HEIGHT = 75;
	public static final double PADDLE_SPEED = 100;
	
	public Paddle(Image img) {
		super(img);
		setFitWidth(PADDLE_WIDTH);
		setFitHeight(PADDLE_HEIGHT);
		setDirection(0,0);
		setCurrentSpeed(PADDLE_SPEED);
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
	public void redirectBall(Ball ball){
		if(!ball.intersects(this)){
			return;
		}
		ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
	}
	
}
