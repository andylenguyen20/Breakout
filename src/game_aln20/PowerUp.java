package game_aln20;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class PowerUp extends ScreenObject{
	public static final int DEFAULT_RADIUS = 30;
	public static final int ACTIVATION_DURATION = 3;
	private boolean deactivated;
	public PowerUp() {
		super();
		setFitWidth(DEFAULT_RADIUS);
		setFitHeight(DEFAULT_RADIUS);
	}
	
	/*
	 * disables the powerup by setting it to deactivated status and reverting its changes
	 */
	public void disable(GameDelegate gd){
		deactivated = true;
		revertChanges(gd);
	}
	
	/*
	 * returns a boolean that indicates whether this powerup has been deactivated
	 */
	public boolean isDeactivated(){
		return deactivated;
	}
	
	/*
	 * spawns the powerup in random location given an xmax and ymax of the screen
	 */
	public void spawnInRandomLocation(int xMax, int yMax){
		Random random = new Random();
		double x = random.nextDouble() * (xMax - 2*DEFAULT_RADIUS) + DEFAULT_RADIUS;
		double y = random.nextDouble() * (yMax - 2*DEFAULT_RADIUS) + DEFAULT_RADIUS;
		setPosition(x,y);
	}
	
	/*
	 * activates the powerup. If the powerup isn't deactivated by 5 seconds, the powerup
	 * will deactivate on its own. Method is meant to be added to given the type of the powerup
	 * that is extending this class
	 */
	public void activate(GameDelegate gd){
		deactivated = false;
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(ACTIVATION_DURATION), ev -> {
			if(!deactivated) {
				disable(gd);
			}
	    }));
	    timeline.setCycleCount(1);
	    timeline.play();
	}
	
	/*
	 * implemented by subclasses. Calls upon the GameDelegate to reverts changes of the powerup activation. 
	 * Each powerup will have a different implementation of the revertChanges method in order to revert 
	 * their specific powerup action.
	 */
	protected abstract void revertChanges(GameDelegate gd);
}
