package game_aln20;

import javafx.scene.image.Image;

public class CementBrick extends Brick{
	public static final String IMAGE_NAME = "cement_brick.gif";
	public CementBrick() {
		super(IMAGE_NAME);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_NAME));
		setImage(image);
	}
}
