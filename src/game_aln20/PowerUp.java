package game_aln20;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;


public abstract class PowerUp extends ScreenObject{
	public static final int RADIUS = 30;
	private boolean active;
	public PowerUp() {
		super();
		setFitWidth(RADIUS);
		setFitHeight(RADIUS);
		
		// TODO Auto-generated constructor stub
	}
	
	public void disable(){
		this.setVisible(false);
		active = false;
	}
	public void spawnInRandomLocation(int xMax, int yMax){
		active = true;
		Random random = new Random();
		double x = random.nextDouble() * (xMax - 2*RADIUS) + RADIUS;
		double y = random.nextDouble() * (yMax - 2*RADIUS) + RADIUS;
		setX(x);
		setY(y);
	}
	public boolean isActive(){
		return active;
	}
	public void activate(GameDelegate gd){
		/*
		new Timer().schedule(new TimerTask() {
	        @Override
	        public void run() {
	        	revertChanges(gd);
	        }
	    }, 10000);
	    */
		/*
		new Timer().schedule(new TimerTask() {
	        @Override
	        public void run() {
	        	Platform.runLater(new Runnable() {
	                 @Override public void run() {
	                     revertChanges(gd);
	                 }
	             });
	        	revertChanges(gd);
	        }
	    }, 1000);
	    */
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
			revertChanges(gd);
	    }));
	    timeline.setCycleCount(1);
	    timeline.play();
	}
	public abstract void revertChanges(GameDelegate gd);
}
