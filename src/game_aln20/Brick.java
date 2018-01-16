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
		boolean cornerHit = distBallCenter < maxNonCornerDist && distBallCenter > minNonCornerDist;
		// hit top or bottom
		if(ball.getCenter().getX() >= getLeft() && ball.getCenter().getX() <= getRight()){
			System.out.println("hit top or bottom side");
			ball.setDirection(ball.getDirection().getX(), -ball.getDirection().getY());
			loseHealth();
		}// hit left or right
		else if(ball.getCenter().getY() <= getBottom() && ball.getCenter().getY() >= getTop()){
			System.out.println("hit left or right side");
			ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
			loseHealth();
		}// hit top right corner
		else if(cornerHit && ball.getCenter().getX() > getRight() && ball.getCenter().getY() < getTop()){
			if(ball.getDirection().getX() < 0 && ball.getDirection().getY() > 0){
				ball.setDirection(-ball.getDirection().getX(), -ball.getDirection().getY());
			}
			System.out.println("hit top right corner");
		}// hit bottom right corner
		else if(cornerHit && ball.getCenter().getX() > getRight() && ball.getCenter().getY() > getBottom()){
			if(ball.getDirection().getX() < 0 && ball.getDirection().getY() < 0){
				ball.setDirection(-ball.getDirection().getX(), -ball.getDirection().getY());
			}
			System.out.println("hit bottom right corner");
		}// hit bottom left corner
		else if(cornerHit && ball.getCenter().getX() < getLeft() && ball.getCenter().getY() > getBottom()){
			if(ball.getDirection().getX() > 0 && ball.getDirection().getY() < 0){
				ball.setDirection(-ball.getDirection().getX(), -ball.getDirection().getY());
			}
			System.out.println("hit bottom left corner");
		}// hit top left corner
		else if(cornerHit && ball.getCenter().getX() < getLeft() && ball.getCenter().getY() < getTop()){
			if(ball.getDirection().getX() > 0 && ball.getDirection().getY() > 0){
				ball.setDirection(-ball.getDirection().getX(), -ball.getDirection().getY());
			}
			System.out.println("hit top left corner");
		}else{
			System.out.println("collide method failed");
		}
	}
	public Double getCornerRedirection(Ball ball){
		return null;
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
