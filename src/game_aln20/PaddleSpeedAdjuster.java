package game_aln20;

public class PaddleSpeedAdjuster extends SpeedAdjuster{
	public static final String IMAGE_NAME = "paddle_speed_pwr.gif";
	
	private Paddle paddleAffected;
	
	public PaddleSpeedAdjuster(double multiplier) {
		super(IMAGE_NAME, multiplier);
	}
	
	/*
	 * has the GameDelegate multiply the paddle speed by some multiplier
	 * also @see game_aln20.PowerUp#activate(game_aln20.GameDelegate)
	 */
	
	public void activate(GameDelegate gd){
		super.activate(gd);
		paddleAffected = gd.changePaddleSpeed(super.getSpeedMultiplier());
	}

	/*
	 * has the GameDelegate divide the paddle speed by the same constant
	 * also @see game_aln20.PowerUp#revertChanges(game_aln20.GameDelegate)
	 */
	public void deactivate(GameDelegate gd) {
		super.deactivate(gd);
		gd.revertPaddleSpeed(paddleAffected);
	}
}
