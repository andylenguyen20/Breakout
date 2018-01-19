package game_aln20;

import java.util.concurrent.CopyOnWriteArrayList;

public class Player {
	public static final int DEFAULT_LIVES = 3;
	
	private int score;
	private int lives;
	private Paddle paddle;
	private CopyOnWriteArrayList<Brick> bricks;
	
	public Player(){
		this.bricks = new CopyOnWriteArrayList<Brick>();
		this.paddle = new Paddle();
		this.lives = DEFAULT_LIVES;
	}
	public void loseLife(){
		lives--;
	}
	public int getLives(){
		return lives;
	}
	public void addLife(){
		lives++;
	}
	public void incrementScore(){
		score++;
	}
	public int getScore(){
		return score;
	}
	public Paddle getPaddle(){
		return paddle;
	}
	public void reset(){
		this.lives = 3;
		this.score = 0;
	}
	public void resetLives(){
		this.lives = 3;
	}
	public CopyOnWriteArrayList<Brick> getBricks(){
		return bricks;
	}
}
