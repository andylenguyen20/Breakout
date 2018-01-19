package game_aln20;

public interface GameDelegate {
	void changeBallSpeed(double multiplier);
	void changePaddleSpeed(double multiplier);
	void cloneBall();
	void removeBall();
	void activateRandomPowerUp();
	void changePaddleSize(double multiplier);
	void activateStickyPaddle();
}
