package game_aln20;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public interface GameDelegate {
	void changeBallSpeed(double multiplier);
	void changePaddleSpeed(double multiplier);
	void cloneBall();
	void removeBall();
	void activateRandomPowerUp();
	void changePaddleSize(double multiplier);
	void launchBallFromStickyPaddle(Ball ball, Paddle paddle);
	void awardExtraLife();
	CopyOnWriteArrayList<Brick> turnBricksIntoCement();
	void revertBricksToNormal(CopyOnWriteArrayList<Brick> copy);
}
