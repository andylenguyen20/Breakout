package game_aln20;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class PowerUp extends ScreenObject{
	public static final int RADIUS = 30;
	private boolean deactivated;
	public PowerUp() {
		super();
		setFitWidth(RADIUS);
		setFitHeight(RADIUS);
	}
	public void disable(GameDelegate gd){
		deactivated = true;
		revertChanges(gd);
	}
	public void spawnInRandomLocation(int xMax, int yMax){
		Random random = new Random();
		double x = random.nextDouble() * (xMax - 2*RADIUS) + RADIUS;
		double y = random.nextDouble() * (yMax - 2*RADIUS) + RADIUS;
		setPosition(x,y);
	}
	public void activate(GameDelegate gd){
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
			if(deactivated) {
				return;
			}
			disable(gd);
	    }));
	    timeline.setCycleCount(1);
	    timeline.play();
	}
	public abstract void revertChanges(GameDelegate gd);
}
