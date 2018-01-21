package game_aln20;

import javafx.scene.image.Image;

public class BallCloner extends PowerUp{
	public static final String IMAGE_NAME = "extra_ball_pwr.gif";
	public BallCloner() {
		super();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
	}
	
	public void activate(GameDelegate gd) {
		super.activate(gd);
		gd.cloneBall();
	}
	@Override
	public void revertChanges(GameDelegate gd) {
		gd.removeBall();
	}

}
