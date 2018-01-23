package game_aln20;

import java.util.concurrent.CopyOnWriteArrayList;

public interface GameDelegate {
	void changeBallSpeed(double multiplier);
	void revertBallSpeed();
	Paddle changePaddleSpeed(double multiplier);
	void revertPaddleSpeed(Paddle paddle);
	void cloneBall();
	void removeBall();
	void activateRandomPowerUp();
	void launchBallFromStickyPaddle(Ball ball, Paddle paddle);
	void awardExtraLife();
	CopyOnWriteArrayList<Brick> turnBricksIntoCement();
	void revertBricksToNormal(CopyOnWriteArrayList<Brick> copy);
}
