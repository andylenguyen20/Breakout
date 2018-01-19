package game_aln20;

public abstract class SpeedAdjuster extends PowerUp{
	public double speedMultiplier;
	public SpeedAdjuster(double multiplier) {
		super();
		speedMultiplier = multiplier;
	}
	public double getSpeedMultiplier(){
		return speedMultiplier;
	}	
}
