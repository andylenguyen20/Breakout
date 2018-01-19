package game_aln20;

import javafx.scene.image.Image;

public class CementBrick extends Brick{
	public static final String IMAGE = "cement_brick.gif";
	public CementBrick() {
		super();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE));
		setImage(image);
	}
}
