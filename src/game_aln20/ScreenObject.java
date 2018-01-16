package game_aln20;

import java.awt.geom.Point2D.Double;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class ScreenObject extends ImageView{
	private Double center;
	public ScreenObject(Image img){
		super(img);
		center = new Double();
	}
	public Double getCenter(){
		return center;
	}
	public void setCenter(double x, double y){
		center.setLocation(x, y);
	}
	public double getBottom(){
        return getCenter().y + getBoundsInLocal().getHeight() / 2;
    }
	public double getTop(){
        return getCenter().y - getBoundsInLocal().getHeight() / 2;
    }
	public double getLeft(){
        return getCenter().x - getBoundsInLocal().getWidth() / 2;
    }
	public double getRight(){
        return getCenter().x + getBoundsInLocal().getWidth() / 2;
    }
	public double distanceFromCenter(Double point){
		return Math.sqrt(Math.pow(center.getX() - point.getX(), 2) + Math.pow(center.getY() - point.getY(), 2));
	}
}
