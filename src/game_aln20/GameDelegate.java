package game_aln20;

import java.util.ArrayList;

/*
 * Purpose: The GameDelegate is an interface that contains methods that certain classes need to use in order
 * to affect certain elements in the Breakout Game. The Breakout class holds information on most of the objects
 * in play in a given game. On certain occasions, classes like PowerUp, ExtraLifeBrick, and RandomPwrBrick need access 
 * to some game information in order to perform their external-class tasks, such as, for example, change the ball speed in the current
 * game of Breakout. The Breakout class implements this interface, giving these classes the ability to use these methods
 * when the Breakout class calls upon them to do so.
 * 
 * Why I think it is well designed: I think this interface is well-designed for two reasons. The first reason is to allow for
 * genericity among PowerUps. By using this interface, I can have all PowerUps call the same methods: activate(GameDelegate gd) and
 * revertChanges(GameDelegate gd). While the subclass powerups have different implementations of these methods that extend the generic PowerUp,
 * I can call within my Breakout class (which houses the PowerUps in the game) powerUp.activate(this) rather than having a large
 * if-else tree of "instanceof" checks. The second reason why I think it is well-designed is that it greatly limits the amount of information
 * that is needed to give to the classes that need external class information. By only giving PowerUp or ExtraLifeBrick access to the methods
 * in this interface, they only have access to methods that they need in order to do their specific method call. In essence, I am minimizing
 * the amount of information that is available to them, while giving them the ability to do their jobs. A PaddleSpeedAdjuster powerUp, for 
 * instance can change a paddle's speed with the changePaddleSpeed(double multiplier) method, but it can't alter a player's score or change the 
 * Level, since the GameDelegate doesn't even have access to these.
 *
 * What this shows I have learned: I think this interface shows that I understand that encapsulation is an important component in programming, in which
 * some classes shouldn't have access to certain information in other classes. This interface helps encapsulate the data from the Breakout class whenever it calls
 * powerup.activate(this). I also think this interface shows that I understand the idea of delegation, in which you can call upon another object to do work for you.
 * In the case with powerups, a PowerUp subclass doesn't need to do much "work" in the sense that it can get the GameDelegate to clone a ball for it.
 */
public interface GameDelegate {
	
	/*
	 * Called by the BallSpeedAdjuster class. The Breakout class provides this implementation: multiplies the speed of its Ball objects by this multiplier.
	 */
	void changeBallSpeed(double multiplier);
	
	/*
	 * Called by the BallSpeedAdjuster class. The Breakout class provides this implementation: reverts all the balls back to their original starting speed.
	 */
	void revertBallSpeed();
	
	/*
	 * Called by the PaddleSpeedAdjuster class. The Breakout class provides this implementation: multiplies the speed of a Paddle (determined by the Breakout class which paddle) by this multiplier
	 * Returns: a reference to the Paddle whose speed was changed.
	 */
	Paddle changePaddleSpeed(double multiplier);
	
	/*
	 * Called by the PaddleSpeedAdjuster class. The Breakout class provides this implementation: reverts the inputed Paddle to its original starting speed.
	 */
	void revertPaddleSpeed(Paddle paddle);
	
	/*
	 * Called by the BallCloner class. The Breakout class provides this implementation: adds another Ball object to the game's list of Ball objects
	 */
	void cloneBall();
	
	/*
	 * Called by the BallCloner class. The Breakout class provides this implementation: removes a Ball object from the game's current list of Ball objects
	 */
	void removeBall();
	
	/*
	 * Called by the RandomPwrBrick class. The Breakout class provides this implementation: create and activate a random PowerUp object
	 */
	void activateRandomPowerUp();
	
	/*
	 * Called by the Paddle class. The Breakout class provides this implementation: launches the inputed Ball from the inputed Paddle. This method is necessary
	 * because only the Breakout class knows which side of the paddle to launch the ball from, depending on whether it is Player 1's paddle or Player 2's paddle.
	 */
	void launchBallFromStickyPaddle(Ball ball, Paddle paddle);
	
	/*
	 * Called by the ExtraLifeBrick class. The Breakout class provides this implementation: adds another life to a player (determined by the Breakout class which player)
	 */
	void awardExtraLife();
	
	/*
	 * Called by the BrickCementer class. The Breakout class provides this implementation: turns all of a player's bricks (determined by the Breakout class which player's bricks) to CementBrick objects
	 * Returns: a copy of the player's original bricks
	 */
	ArrayList<Brick> turnBricksIntoCement();
	
	/*
	 * Called by the BrickCementer class. The Breakout class provides this implementation: reverts all of the player's bricks to the inputed bricks.
	 */
	void revertBricksToNormal(ArrayList<Brick> copy);
}