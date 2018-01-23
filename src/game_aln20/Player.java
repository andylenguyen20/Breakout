package game_aln20;

import java.util.concurrent.CopyOnWriteArrayList;

public class Player {
	public static final int DEFAULT_LIVES = 3;
	
	private int score;
	private int lives;
	private Player opponent;
	private Paddle paddle;
	private CopyOnWriteArrayList<Brick> bricks;
	private boolean isCemented;
	
	public Player(){
		bricks = new CopyOnWriteArrayList<Brick>();
		paddle = new Paddle();
		lives = DEFAULT_LIVES;
		isCemented = false;
	}
	
	/*
	 * sets the player's lives
	 */
	public void setLives(int lives){
		this.lives = lives;
	}
	
	/*
	 * returns the player's lives
	 */
	public int getLives(){
		return lives;
	}

	/*
	 * sets the player's score
	 */
	public void setScore(int score){
		this.score = score;
	}
	
	/*
	 * returns the player's score
	 */
	public int getScore(){
		return score;
	}
	
	/*
	 * returns the player's paddle
	 */
	public Paddle getPaddle(){
		return paddle;
	}
	
	/*
	 * resets the player's lives and score
	 */
	public void reset(){
		this.lives = 3;
		this.score = 0;
	}
	
	/*
	 * resets the player's lives to the default amount
	 */
	public void resetLives(){
		this.lives = DEFAULT_LIVES;
	}
	
	/*
	 * returns the player's bricks
	 */
	public CopyOnWriteArrayList<Brick> getBricks(){
		return bricks;
	}
	
	/*
	 * returns the player's opponent
	 */
	public Player getOpponent(){
		return opponent;
	}
	
	/*
	 * sets the player's opponent
	 */
	public void setOpponent(Player opponent){
		this.opponent = opponent;
	}
	
	/*
	 * returns true if the player's bricks are currently cemented
	 */
	public boolean isCemented(){
		return isCemented;
	}
	
	/*
	 * sets the boolean indicating whether the player's bricks are currently cemented
	 */
	public void setCemented(boolean status){
		isCemented = status;
	}
}
