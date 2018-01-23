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
	
	/*
	 * has the GameDelegate create a copy of the bricks currently owned by the 
	 * affected player as well as turn all of these bricks into cement
	 * also @see game_aln20.PowerUp#activate(game_aln20.GameDelegate)
	 */
	public void activate(GameDelegate gd){
		super.activate(gd);
		CopyOnWriteArrayList<Brick> brickCopy = gd.turnBricksIntoCement();
		if(brickCopy != null) bricksClone = brickCopy;
	}
	
	/*
	 * has the GameDelegate set the affected player's bricks back to normal
	 * also @see game_aln20.PowerUp#revertChanges(game_aln20.GameDelegate)
	 */
	@Override
	public void revertChanges(GameDelegate gd) {
		gd.revertBricksToNormal(bricksClone);
	}

}
