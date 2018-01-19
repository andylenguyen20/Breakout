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
	public boolean collide(Ball ball){
		if(!active) return false;
		if(super.collide(ball)){
			loseHealth();
			return true;
		}
		return false;
	}
	public void setImage(){
		Image brickImg = new Image(getClass().getClassLoader().getResourceAsStream("brick" + health + ".gif"));
		setImage(brickImg);
	}
	public void loseHealth(){
		health--;
		if(health == 0){
			active = false;
			this.setVisible(false);
		}else{
			setImage();
		}
	}
	public boolean isActive(){
		return active;
	}
}
