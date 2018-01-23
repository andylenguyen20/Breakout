package game_aln20;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class ExtraLifeBrick extends MultiHitBrick{
	public static final String IMAGE = "images/extra_life_brick.gif";
	public ExtraLifeBrick() {
		super(1);
		try {
			setImage(new Image(new FileInputStream(IMAGE)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/*
	 * has the GameDelegate award the player an extra life
	 */
	public void awardExtraLife(GameDelegate gd){
		gd.awardExtraLife();
	}
}
