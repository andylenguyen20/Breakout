package game_aln20;

import javafx.scene.image.Image;

public class ExtraLifeBrick extends MultiHitBrick{
	public static final String IMAGE_NAME = "extra_life_brick.gif";
	public static final int HEALTH = 1;
	public ExtraLifeBrick() {
		super(HEALTH);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
	}
	/*
	 * has the GameDelegate award the player an extra life
	 */
	public void awardExtraLife(GameDelegate gd){
		gd.awardExtraLife();
	}
}
