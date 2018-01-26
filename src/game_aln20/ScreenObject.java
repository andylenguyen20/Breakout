package game_aln20;

import java.awt.geom.Point2D.Double;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class ScreenObject extends ImageView{
	private Double center;
	public ScreenObject(String imageName){
		super();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		setImage(image);
		center = new Double();
	}
	
	/*
	 * returns the center coordinate of this ScreenObject in the form of a Point2D.Double Object
	 */
	public Double getCenter(){
		return center;
	}
	
	/*
	 * sets the image coordinate (double x, double y) of this ScreenObject and sets the 
	 * center coordinate of this ScreenObject given the image coordinate
	 */
	public void setPosition(double x, double y){
		setX(x);
		setY(y);
		center.setLocation(x + getBoundsInLocal().getWidth()/2, y + getBoundsInLocal().getHeight()/2);
	}
	
	/*
	 * returns the y coordinate of the bottom of the image
	 */
	public double getBottom(){
        return getCenter().y + getBoundsInLocal().getHeight() / 2;
    }
	
	/*
	 * returns the y coordinate of the top of the image
	 */
	public double getTop(){
        return getCenter().y - getBoundsInLocal().getHeight() / 2;
    }
	
	/*
	 * returns the x coordinate of the left of the image
	 */
	public double getLeft(){
        return getCenter().x - getBoundsInLocal().getWidth() / 2;
    }
	
	/*
	 * returns the x coordinate of the right of the image
	 */
	public double getRight(){
        return getCenter().x + getBoundsInLocal().getWidth() / 2;
    }
}
