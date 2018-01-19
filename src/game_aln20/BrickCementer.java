package game_aln20;

import java.util.ArrayList;

public class BrickCementer extends PowerUp{
	private ArrayList<Brick> originalBricks;
	public void activate(GameDelegate gd){
		super.activate(gd);
		originalBricks = gd.turnBricksIntoCement();
	}
	@Override
	public void revertChanges(GameDelegate gd) {
		// TODO Auto-generated method stub
		gd.revertBricksToNormal(originalBricks);
	}

}
