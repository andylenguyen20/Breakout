package game_aln20;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.geom.Point2D.Double;

public class Level {
	private int level;
	private GameDelegate context;
	public Level(int level){
		this.level = level;
	}
	public int getLevel(){
		return level;
	}
	public double getBallSpeedOffset(){
		return level * 25;
	}
	public double getPaddleSizeOffset(){
		return -(level * 12.5);
	}
	public PowerUp[] getFreshPowerUpsArray(){
		return new PowerUp[level];
	}
	public Double[] getPaddlePositions(int numPaddles){
        Scanner input = getInput("levels/level_paddlepos" + level + ".txt");
        Double[] positions = new Double[numPaddles];
        for(int i = 0; i < numPaddles; i++){
        	positions[i] = new Double(input.nextInt(), input.nextInt());
        }
        return positions;
	}
	public ArrayList<Brick> getBricks(String playerTag){
        Scanner input = getInput("levels/level_bricks" + level + ".txt");
        ArrayList<Brick> bricks = new ArrayList<Brick>();
		while(input.hasNextLine()) {
            int x = input.nextInt();
            int y = input.nextInt();
            Brick brick = null;
            String type = input.next();
            switch(type){
            case "multihit": brick = generateRandomMultiHitBrick(); break;
            case "randpwr": brick = new RandomPwrBrick(); break;
            case "extralife": brick = new ExtraLifeBrick(); break;
            case "cement": brick = new CementBrick(); break;
            default: break;
            }
            if(input.next().equals(playerTag) && brick != null){
            	brick.setPosition(x, y);
                bricks.add(brick);
            }
        }
		return bricks;
	}
    
	/*
	 * HELPERS
	 */
	// file-reading information taken from http://www2.lawrence.edu/fast/GREGGJ/CMSC150/031Files/031Files.html
	private Scanner getInput(String fileName){
		Scanner input = null;
        try {
            input = new Scanner(new File(fileName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return input;
	}
	private Brick generateRandomMultiHitBrick(){
	    	Brick[] options = new Brick[]
	                {new MultiHitBrick(1), 
	                		new MultiHitBrick(2),
	                        new MultiHitBrick(3)};
	        return options[(int)(Math.random() * options.length)];
	}
}
