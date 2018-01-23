package game_aln20;

import javafx.scene.image.Image;

public class PaddleSpeedAdjuster extends SpeedAdjuster{
	public static final String IMAGE_NAME = "paddle_speed_pwr.gif";
	
	private Paddle paddleAffected;
	
	public PaddleSpeedAdjuster(double multiplier) {
		super(multiplier);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
	}
	
	/*
	 * has the GameDelegate multiply the paddle speed by some multiplier
	 * also @see game_aln20.PowerUp#activate(game_aln20.GameDelegate)
	 */
	public void activate(GameDelegate gd){
		super.activate(gd);
		paddleAffected = gd.changePaddleSpeed(speedMultiplier);
	}

	/*
	 * has the GameDelegate divide the paddle speed by the same constant
	 * also @see game_aln20.PowerUp#revertChanges(game_aln20.GameDelegate)
	 */
	@Override
	public void revertChanges(GameDelegate gd) {
		gd.revertPaddleSpeed(paddleAffected);
	}
}
