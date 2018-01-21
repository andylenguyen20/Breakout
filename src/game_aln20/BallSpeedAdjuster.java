package game_aln20;

import javafx.scene.image.Image;

public class BallSpeedAdjuster extends SpeedAdjuster{
	public static final String IMAGE_NAME = "ball_speed_pwr.gif";
	public static final double BALL_DOUBLE_SPEED = 2;
	public static final double BALL_HALF_SPEED = 0.5;
	
	public BallSpeedAdjuster(double multiplier) {
		super(multiplier);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
	}
	
	public void activate(GameDelegate gd){
		super.activate(gd);
		gd.changeBallSpeed(speedMultiplier);
	}
	@Override
	public void revertChanges(GameDelegate gd) {
		gd.changeBallSpeed(1/speedMultiplier);
	}
}
