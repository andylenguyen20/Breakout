package game_aln20;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class RandomPwrBrick extends MultiHitBrick{
	public static final String IMAGE = "images/random_brick.gif";
	public RandomPwrBrick() {
		super(1);
		try {
			setImage(new Image(new FileInputStream(IMAGE)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public boolean collide(Ball ball){
		if(super.collide(ball)){
			//TODO
			System.out.println("random power up!");
			return true;
		}
		return false;
	}
	
}
