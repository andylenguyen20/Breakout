package game_aln20;

import javafx.scene.image.Image;

public class BallCloner extends PowerUp{
	public static final String IMAGE_NAME = "extra_ball_pwr.gif";
	public BallCloner() {
		super();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
	}
	/*
	 * has the GameDelegate add another ball to the game
	 * also @see game_aln20.PowerUp#activate(game_aln20.GameDelegate)
	 */
	public void activate(GameDelegate gd) {
		super.activate(gd);
		gd.cloneBall();
	}
	/*
	 * has the GameDelegate remove the ball from the game
	 * also @see game_aln20.PowerUp#revertChanges(game_aln20.GameDelegate)
	 */
	@Override
	public void revertChanges(GameDelegate gd) {
		gd.removeBall();
	}
}
