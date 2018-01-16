package game_aln20;

import javafx.scene.image.Image;
import java.awt.geom.Point2D.Double;

public class Brick extends ScreenObject{
	private int health;
	private boolean active;
	public static final double BRICK_WIDTH = 19;
	public static final double BRICK_HEIGHT = 49;
	
	public Brick(Image img) {
		super(img);
		setFitWidth(BRICK_WIDTH);
		setFitHeight(BRICK_HEIGHT);
		health = 1;
		active = true;
	}
	
	public void collide(Ball ball){
		if(!ball.intersects(this) || !active){
			return;
		}
		//redirect
		
		double distBallCenter = this.distanceFromCenter(ball.getCenter());
		double maxNonCornerDist = this.distanceFromCenter(new Double(getLeft(), getTop() + ball.getRadius()));
		double minNonCornerDist = distanceFromCenter(new Double(getLeft() + ball.getRadius(), getTop()));
		// hit top
		if(ball.getBottom() >= getTop() && ball.getCenter().getX() >= getLeft()
				&& ball.getCenter().getX() <= getRight() && ball.getBottom() <= getCenter().getY()){
			System.out.println("hit top side");
			ball.setDirection(ball.getDirection().getX(), -ball.getDirection().getY());
			loseHealth();
		}// hit right
		else if(ball.getLeft() <= getRight() && ball.getCenter().getY() <= getBottom()
				&& ball.getCenter().getY() >= getTop() && ball.getLeft() >= getCenter().getX()){
			System.out.println("hit right side");
			ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
			loseHealth();
		}// hit bottom
		else if(ball.getTop() <= getBottom() && ball.getCenter().getX() >= getLeft()
				&& ball.getCenter().getX() <= getRight() && ball.getTop() >= getCenter().getY()){
			System.out.println("hit bottom side");
			ball.setDirection(ball.getDirection().getX(), -ball.getDirection().getY());
			loseHealth();
		}// hit left
		else if(ball.getRight() >= getLeft() && ball.getCenter().getY() >= getBottom()
				&& ball.getCenter().getY() >= getTop() && ball.getRight() <= getCenter().getX()){
			System.out.println("hit left side");
			ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
			loseHealth();
		}// hit top right corner
		else if(ball.getCenter().getX() - ball.getRadius() <= getRight()){
			
		}// hit bottom right corner
		else if(ball.getCenter().getX() - ball.getRadius() <= getRight()){
			
		}// hit bottom left corner
		else if(ball.getCenter().getX() - ball.getRadius() <= getRight()){
			
		}// hit top left corner
		else if(ball.getCenter().getX() - ball.getRadius() <= getRight()){
			
		}else{
			System.out.println("collide method failed");
		}
	}
	
	public void loseHealth(){
		health--;
		if(health == 0){
			active = false;
			this.setVisible(false);
		}
	}
	public int getHealth(){
		return health;
	}
	
}
