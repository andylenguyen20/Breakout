package game_aln20;

import javafx.scene.image.Image;

public class PaddleSpeedAdjuster extends SpeedAdjuster{
	public static final String IMAGE_NAME = "paddle_speed_pwr.gif";
	public static final double PADDLE_DOUBLE_SPEED = 2;
	public static final double PADDLE_HALF_SPEED = 0.5;
	
	public PaddleSpeedAdjuster(double multiplier) {
		super(multiplier);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
	}
	public void activate(GameDelegate gd){
		super.activate(gd);
		gd.changePaddleSpeed(speedMultiplier);
	}
	@Override
	public void revertChanges(GameDelegate gd) {
		gd.changePaddleSpeed(1/speedMultiplier);
	}
}
