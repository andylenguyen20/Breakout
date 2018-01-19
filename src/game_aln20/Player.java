package game_aln20;

import java.awt.geom.Point2D;

public class Player {
	private int score;
	private int lives;
	private Paddle paddle;
	public Player(int score, int lives, Paddle paddle){
		this.paddle = paddle;
		this.score = score;
		this.lives = lives;
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
	}
}
