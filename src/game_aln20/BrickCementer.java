package game_aln20;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class BrickCementer extends PowerUp{
	private ArrayList<Brick> originalBricks;
	public static final String IMAGE_NAME = "ball.gif";
	
	public void activate(GameDelegate gd){
		super.activate(gd);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
		originalBricks = gd.turnBricksIntoCement();
	}
	@Override
	public void revertChanges(GameDelegate gd) {
		// TODO Auto-generated method stub
		gd.revertBricksToNormal(originalBricks);
	}

}
