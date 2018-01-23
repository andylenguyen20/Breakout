package game_aln20;

import javafx.scene.image.Image;

public class MultiHitBrick extends Brick{
	private int health;
	private boolean active;
	
	public MultiHitBrick(int health) {
		super();
		this.health = health;
		this.active = true;
		setImage();
	}
	
	/*
	 * loses health and returns true if the brick has collided with the ball
	 * returns false if it has not collided or if it is inactive
	 * @see game_aln20.Brick#collide(game_aln20.Ball)
	 */
	public boolean collide(Ball ball){
		if(!active) return false;
		if(super.collide(ball)){
			loseHealth();
			return true;
		}
		return false;
	}
	
	/*
	 * sets an image for this MultiHitBrick depending on how much health it has left
	 */
	public void setImage(){
		Image brickImg = new Image(getClass().getClassLoader().getResourceAsStream("brick" + health + ".gif"));
		setImage(brickImg);
	}
	
	/*
	 * decrements health and sets the brick to be inactive and invisible if health reaches 0. Else,
	 * changes the image of the MultiHitBrick to indicate a change in health
	 */
	public void loseHealth(){
		health--;
		if(health == 0){
			active = false;
			this.setVisible(false);
		}else{
			setImage();
		}
	}
	
	/*
	 * returns a boolean indicating whether the current brick is active
	 */
	public boolean isActive(){
		return active;
	}
}
