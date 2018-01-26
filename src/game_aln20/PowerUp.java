package game_aln20;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/*
 * Purpose: The goal was to create an abstract PowerUp class that had attributes that specific PowerUps like BallCloner had in common.
 * Every PowerUp needed to be able to position itself randomly on a screen, as well as have qualities of an ImageView that enable it to
 * detect collisions and be drawn to the screen. A PowerUp also needed to be able to activate for a certain period of time as well as deactivate.
 * 
 * Why I think it is well designed: I think I made this class as compact as it needed to be in terms of what attributes it needed to have. For instance,
 * I could have easily added an intersects method that checks whether the ball has collided with the PowerUp. However, by deciding to have the PowerUp
 * extend ImageView, I didn't need to write this method since the ImageView method has an intersects method that performs the same function. Extending ImageView
 * also allowed me to give this class the inherited abilities of being able to be drawn to the screen and have a position on the screen. I'm also proud of the similar
 * PowerUp attributes that I chose when considering all PowerUp subclasses. I think the attributes that I chose: similar image dimensions, deactivation status, 
 * spawning in a random location, activation, and deactivation were all the qualities that the PowerUp subclasses needed to function, with the only differences between 
 * these subclasses being in their implementations of the activate(GameDelegate gd) and deactivate(GameDelegate gd) methods. I'm especially proud of those last two methods
 * because every PowerUp needs some sort of external class information in order to perform a power up ability. Refer to @GameDelegate.java for information on this.
 * 
 * What this shows I have learned: I think this shows that I understand the concept of inheritance in that I found traits in this abstract PowerUp class that classes
 * like BrickCementer or BallCloner would end up using. I also demonstrated concise coding by being able to keep this class small and compact by avoiding extra method implementations
 * such as setX or setY in having this class extend ImageView.
 */

public abstract class PowerUp extends ImageView{
	public static final double DEFAULT_RADIUS = 30.0;
	public static final int ACTIVATION_DURATION = 3;
	private boolean deactivated;
	
	/*
	 * Sets the image as well as imageWidth and imageHeight
	 */
	public PowerUp(String imageName) {
		super(imageName);
		super.setFitWidth(DEFAULT_RADIUS);
		super.setFitHeight(DEFAULT_RADIUS);
	}
	
	/*
	 * Returns: A boolean that indicates whether this PowerUp has been deactivated
	 */
	public boolean isDeactivated(){
		return deactivated;
	}
	
	/*
	 * Spawns itself in a random location on the screen given the dimensions of the screen
	 */
	public void spawnInRandomLocation(int screenWidth, int screenHeight){
		Random random = new Random();
		double x = random.nextDouble() * (screenWidth - 2*super.getFitWidth()) + super.getFitWidth();
		double y = random.nextDouble() * (screenHeight - 2*super.getFitHeight()) + super.getFitHeight();
		super.setX(x);
		super.setY(y);
	}
	
	/*
	 * Activates the PowerUp. If the powerup isn't deactivated by 5 seconds, the powerup
	 * will deactivate on its own. Method is meant to be added to given the type of the powerup
	 * that is extending this class
	 */
	public void activate(GameDelegate gd){
		deactivated = false;
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(ACTIVATION_DURATION), ev -> {
			if(!deactivated) {
				deactivate(gd);
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
	public void deactivate(GameDelegate gd){
		if(deactivated) return;
		deactivated = true;
	}
}