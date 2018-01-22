package game_aln20;

public abstract class SpeedAdjuster extends PowerUp{
	public double speedMultiplier;
	public static final double SLOWER_SPEED_MULTIPLIER = .75;
	public static final double FASTER_SPEED_MULTIPLIER = 1.5;
	public SpeedAdjuster(double multiplier) {
		super();
		speedMultiplier = multiplier;
	}
	public double getSpeedMultiplier(){
		return speedMultiplier;
	}	
}
