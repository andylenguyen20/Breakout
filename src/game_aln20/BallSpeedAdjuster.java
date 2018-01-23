package game_aln20;

import javafx.scene.image.Image;

public class BallSpeedAdjuster extends SpeedAdjuster{
	public static final String IMAGE_NAME = "ball_speed_pwr.gif";
	
	public BallSpeedAdjuster(double multiplier) {
		super(multiplier);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
	}
	
	/*
	 * Has the GameDelegate multiply the ball speed by some multiplier
	 * also @see game_aln20.PowerUp#activate(game_aln20.GameDelegate)
	 */
	public void activate(GameDelegate gd){
		super.activate(gd);
		gd.changeBallSpeed(speedMultiplier);
	}
	/*
	 * Has the GameDelegate divide the ball speed by the same speed multiplier
	 * also @see game_aln20.PowerUp#revertChanges(game_aln20.GameDelegate)
	 */
	@Override
	public void revertChanges(GameDelegate gd) {
		gd.revertBallSpeed();
	}
}
