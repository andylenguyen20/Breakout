package game_aln20;

import java.awt.geom.Point2D.Double;
import javafx.scene.image.ImageView;

public abstract class ScreenObject extends ImageView{
	private Double center;
	public ScreenObject(){
		super();
		center = new Double();
	}
	public Double getCenter(){
		return center;
	}
	
	public void setPosition(double x, double y){
		setX(x);
		setY(y);
		center.setLocation(x + getBoundsInLocal().getWidth()/2, y + + getBoundsInLocal().getHeight()/2);
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
}
