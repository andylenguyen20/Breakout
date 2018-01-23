package game_aln20;

import java.util.concurrent.CopyOnWriteArrayList;

/*
 * Role: the GameDelegate interface will be implemented by the Breakout
 * class. It's purpose is to limit the information that classes such as Paddle,
 * PowerUp and its subclasses, RandomPwrBrick, and ExtraLifeBrick have access
 * to. 
 * 
 * Breakout has all the information in a given game, and on certain occasions,
 * PowerUp needs some game information in order to e.g. change the game's paddle speeds
 * or ball speeds. The same goes for the Paddle class, which needs to know (from the info in the game)
 * which side of the paddle to launch the ball from.
 * 
 * Having this interface allows these given classes to obtain these necessary pieces of information, while
 * still limiting what is available to them. For example, the GameDelegate only has access to the functions within
 * this interface, so a PowerUp can only call these specifics. A powerUp, would for example, be able to change a paddle's
 * speed, but it can't alter a player's score or change the Level, since it doesn't have access to these.
 */
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
