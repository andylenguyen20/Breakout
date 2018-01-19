package game_aln20;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


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
		new Timer().schedule(new TimerTask() {
	        @Override
	        public void run() {
	        	revertChanges(gd);
	        }
	    }, 10000);
	}
	public abstract void revertChanges(GameDelegate gd);
}
