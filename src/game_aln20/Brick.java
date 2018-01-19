package game_aln20;

import javafx.scene.image.Image;

public abstract class Brick extends ScreenObject{
	public static final double BRICK_WIDTH = 19;
	public static final double BRICK_HEIGHT = 49;
	
	public Brick() {
		super();
		setFitWidth(BRICK_WIDTH);
		setFitHeight(BRICK_HEIGHT);
	}
	public boolean collide(Ball ball){
		if(!ball.intersects(this)){
			return false;
		}
		// hit top or bottom
		if(ball.getCenter().getX() >= getLeft() && ball.getCenter().getX() <= getRight()){
			System.out.println("hit top or bottom side");
			ball.setDirection(ball.getDirection().getX(), -ball.getDirection().getY());
		}// hit left or right
		else if(ball.getCenter().getY() <= getBottom() && ball.getCenter().getY() >= getTop()){
			System.out.println("hit left or right side");
			ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
		}else{
			System.out.println("collide method failed");
			return false;
		}
		return true;
	}
}
