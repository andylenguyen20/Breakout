package game_aln20;

import java.util.ArrayList;

public interface GameDelegate {
	void changeBallSpeed(double multiplier);
	void changePaddleSpeed(double multiplier);
	void cloneBall();
	void removeBall();
	void activateRandomPowerUp();
	void changePaddleSize(double multiplier);
	void launchBallFromStickyPaddle(Ball ball, Paddle paddle);
	void awardExtraLife();
	ArrayList<Brick> turnBricksIntoCement();
	void revertBricksToNormal(ArrayList<Brick> copy);
}
