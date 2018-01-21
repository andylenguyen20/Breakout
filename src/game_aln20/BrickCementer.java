package game_aln20;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class BrickCementer extends PowerUp{
	private ArrayList<Brick> originalBricks;
	public static final String IMAGE_NAME = "brick_cementer_pwr.gif";
	
	public BrickCementer(){
		super();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
	}
	
	public void activate(GameDelegate gd){
		super.activate(gd);
		ArrayList<Brick> brickCopy = gd.turnBricksIntoCement();
		if(brickCopy != null) originalBricks = brickCopy;
	}
	@Override
	public void revertChanges(GameDelegate gd) {
		gd.revertBricksToNormal(originalBricks);
	}

}
