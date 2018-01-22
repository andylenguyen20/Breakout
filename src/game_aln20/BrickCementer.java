package game_aln20;

import java.util.concurrent.CopyOnWriteArrayList;

import javafx.scene.image.Image;

public class BrickCementer extends PowerUp{
	private CopyOnWriteArrayList<Brick> bricksClone;
	public static final String IMAGE_NAME = "brick_cementer_pwr.gif";
	
	public BrickCementer(){
		super();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
	}
	
	public void activate(GameDelegate gd){
		super.activate(gd);
		CopyOnWriteArrayList<Brick> brickCopy = gd.turnBricksIntoCement();
		if(brickCopy != null) bricksClone = brickCopy;
	}
	@Override
	public void revertChanges(GameDelegate gd) {
		gd.revertBricksToNormal(bricksClone);
	}

}
