package game_aln20;

public abstract class SpeedAdjuster extends PowerUp{
	public static final double SLOWER_SPEED_MULTIPLIER = .75;
	public static final double FASTER_SPEED_MULTIPLIER = 1.5;
	private double speedMultiplier;
	
	public SpeedAdjuster(String imageName, double multiplier) {
		super(imageName);
		speedMultiplier = multiplier;
	}
	
	/*
	 * returns a speed multiplier
	 */
	public double getSpeedMultiplier(){
		return speedMultiplier;
	}	
}