package game_aln20;

/*
 * Purpose: The purpose of this class is to create an extension of PowerUp that can be instantiated and adds 
 * another ball to the game/can remove the ball from the game once it needs to be deactivated.
 * 
 * Why I think it is well-designed: I think that this class is well-designed because of its simplicity. The only difference
 * between a BallCloner PowerUp and any other extensions of PowerUp should be in what it does when it activates/deactivates.
 * Even with the different implementations of the activate(GameDelegate gd) and deactivate(GameDelegate gd) methods between PowerUp
 * subclasses, I still think I did a good job at keeping the differences very concise. I think the implementations of these two methods
 * is well-designed in that it uses delegation. Rather than requesting unneccesary information or being too involved in other classes, all
 * the PowerUp has to do in its perspective is ask the GameDelegate to cloneBall() for it. In similarity, during deactivation it can simply
 * ask the GameDelegate to removeBall() for it. Calling upon a delegator makes sense because a PowerUp shouldn't be given too much awareness about 
 * the Breakout class or any other classes that it is contained in; calling upon a delegate makes it easier for this PowerUp to choose what it wants
 * to do as well as minimize the amount of information it has access to.
 * 
 * What this shows I have learned: I think this class demonstrates my understanding of inheritance in that this class inherits all the abilities of a PowerUp,
 * such as being drawable onto a screen, and also that it can be activated and deactivated. I think I also showed my skills with inheritance by being able to
 * keep the same higher-level implementation for the activate(GameDelegate gd) and deactivate(GameDelegate gd) methods--using the super.activate(gd) and 
 * super.deactivate(gd) calls--while also adding extensions that make this BallCloner class unique: calling gd.cloneBall() and gd.removeBall(). I think the
 * gd.cloneBall() and gd.removeBall() method calls demonstrates my ability to incorporate delegation and encapsulation in my programming. This is because
 * I used an interface to do the work for the PowerUp, which a) made it easier for the BallCloner to obtain/alter specific game information that it didn't have access to
 * before and b) still minimize the amount of information given to this BallCloner PowerUp.
 */
public class BallCloner extends PowerUp{
	public static final String IMAGE_NAME = "extra_ball_pwr.gif";
	
	/*
	 * Creates a new instance of the BallCloner class with the given image file name
	 */
	public BallCloner() {
		super(IMAGE_NAME);
	}
	
	/*
	 * Calls upon the GameDelegate to add another ball to the game
	 * @see PowerUp activate(GameDelegate gd) method
	 * also @see GameDelegate removeBall() method
	 */
	@Override
	public void activate(GameDelegate gd) {
		super.activate(gd);
		gd.cloneBall();
	}
	
	/*
	 * Calls upon the GameDelegate to remove the ball from the game
	 * @see PowerUp deactivate(GameDelegate gd) method
	 * also @see GameDelegate removeBall() method
	 */
	@Override
	public void deactivate(GameDelegate gd) {
		super.deactivate(gd);
		gd.removeBall();
	}
}
