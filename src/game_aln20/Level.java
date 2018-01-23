package game_aln20;

import java.io.File;

import java.util.ArrayList;
import java.util.Scanner;
import java.awt.geom.Point2D.Double;

public class Level {
	public static final double levelBallSpeedMultiplier = 25;
	public static final double levelPaddleSizeMultiplier = 12.5;
	
	private int level;
	
	public Level(int level){
		this.level = level;
	}
	
	/*
	 * returns the current level
	 */
	public int getLevel(){
		return level;
	}
	
	/*
	 * returns this level's additional ball speed
	 */
	public double getBallSpeedOffset(){
		return level * levelBallSpeedMultiplier;
	}
	
	/*
	 * returns this level's additional paddle size
	 */
	public double getPaddleSizeOffset(){
		return -(level * levelPaddleSizeMultiplier);
	}
	
	/*
	 * returns an empty PowerUp array for the new level
	 */
	public PowerUp[] getFreshPowerUpsArray(){
		return new PowerUp[level];
	}
	
	/*
	 * reads paddle positions from a file specific to this level
	 * and returns the coordinates in a Point2D.Double array
	 */
	public Double[] getPaddlePositions(int numPaddles){
        Scanner input = getInput("levels/level_paddlepos" + level + ".txt");
        Double[] positions = new Double[numPaddles];
        for(int i = 0; i < numPaddles; i++){
        	positions[i] = new Double(input.nextInt(), input.nextInt());
        }
        return positions;
	}
	
	/*
	 * reads a given player's brick positions from a file specific to this level
	 * and returns an ArrayList of bricks to be used for this level
	 */
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
	 * returns a Scanner object for a given file
	 */
	private Scanner getInput(String fileName){
		Scanner input = null;
        try {
            input = new Scanner(new File(fileName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return input;
	}
	
	/*
	 * generates a random MultiHitBrick
	 */
	private Brick generateRandomMultiHitBrick(){
	    	Brick[] options = new Brick[]
	                {new MultiHitBrick(1), 
	                		new MultiHitBrick(2),
	                        new MultiHitBrick(3)};
	        return options[(int)(Math.random() * options.length)];
	}
}
