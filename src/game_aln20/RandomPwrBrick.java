package game_aln20;

import javafx.scene.image.Image;

public class RandomPwrBrick extends MultiHitBrick{
	public static final String IMAGE_NAME = "random_brick.gif";
	public RandomPwrBrick() {
		super(1);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
	}
	
	/*
	 * has the GameDelegate activate a random powerup
	 */
	public void activateRandomPowerUp(GameDelegate gd){
		gd.activateRandomPowerUp();
	}
	
}
