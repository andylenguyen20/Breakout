package game_aln20;

public class MovingScreenObject extends ScreenObject{
	private double currentSpeed;
	private double xDirection;
	private double yDirection;
	
	public MovingScreenObject(){
		
	}
	
	public void updatePosition(){
		double x = getX();
		double y = getY();
		setX(x + x * xDirection * currentSpeed);
		setY(y + y * yDirection * currentSpeed);
	}
}
