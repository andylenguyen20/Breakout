package game_aln20;

public abstract class Brick extends ScreenObject{
	public static final double BRICK_WIDTH = 19;
	public static final double BRICK_HEIGHT = 49;
	
	public Brick(String imageName) {
		super(imageName);
		setFitWidth(BRICK_WIDTH);
		setFitHeight(BRICK_HEIGHT);
	}
	
	/*
	 * returns a boolean indicating whether the ball has collided with this brick.
	 * returns true if the ball has collided, returns false if not
	 */
	public boolean collide(Ball ball){
		if(!ball.intersects(this)){
			return false;
		}
		if(ball.getCenter().getX() >= getLeft() && ball.getCenter().getX() <= getRight()){
			ball.setDirection(ball.getDirection().getX(), -ball.getDirection().getY());
		}
		else if(ball.getCenter().getY() <= getBottom() && ball.getCenter().getY() >= getTop()){
			ball.setDirection(-ball.getDirection().getX(), ball.getDirection().getY());
		}
		return true;
	}
}
