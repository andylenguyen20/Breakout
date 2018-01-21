package game_aln20;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.concurrent.CopyOnWriteArrayList;

public class Breakout extends Application implements GameDelegate{
	public static final Paint BACKGROUND = Color.AZURE;
	public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final String GAME_TITLE = "Breakout Pong Game";
	public static final String START_TITLE = "Breakout Pong Rules";
	public static final String SUBMIT_MSG = "PLAY!";
	public static final String RULES_FILE_NAME = "rules.txt";
	public static final String END_GAME_TITLE = "GAME OVER";
	
	//String path = new File("src/audio/Arcade_Funk.mp3").getAbsolutePath();
	
	//public final Media transition_song = new Media(getClass().getResource("Arcade_Funk.mp3").toExternalForm());
	//new MediaPlayer(sound_3).play();
	
	private Stage myStage;
	private Scene myScene;
	private Group root;
	public static final int SCREEN_WIDTH = 850;
	public static final int SCREEN_HEIGHT = 500;
	
	private Player[] players;
	private Player player1, player2;
	private Player recentlyHit, recentlyCemented;
	private CopyOnWriteArrayList<Ball> balls;
	private PowerUp[] onScreenPowerUps;
	private PowerUp currentlyActivePowerUp;
	
	private boolean cementOn;
	private int level = 1;
	
	private Text scorePanel;
	private Text livesPanel;
	
	@Override
	public void start(Stage stage) {
		myStage = stage;
		//Media background_song = new Media(getClass().getResource("Arcade_Funk.wav").toExternalForm());
		//background_song = new Media(getClass().getResource("Arcade_Funk.mp3").toExternalForm());
		// TODO Auto-generated method stub
		players = new Player[2];
		players[0] = new Player();
		players[1] = new Player();
		player1 = players[0];
		player2 = players[1];
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		balls = new CopyOnWriteArrayList<Ball>();
		livesPanel = new Text(400, 10, player1.getLives() + "|Lives|" + player2.getLives());
		scorePanel = new Text(400, 25, player1.getScore() + "|Score|" + player2.getScore());
		root = new Group();
		setUpLevel(1);
		VBox vbox = new VBox();
	
		Scanner input = null;
        try {
            input = new Scanner(new File(RULES_FILE_NAME));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
 
        while(input.hasNextLine()){
            vbox.getChildren().add(new Text(input.nextLine()));
        }
    	Button button = new Button(SUBMIT_MSG);
    	button.setOnAction(action -> {
			startGameFromScene();
    	});
    	vbox.getChildren().add(button);
    	myStage.setTitle(START_TITLE);
    	myScene = new Scene(vbox, 500, 150);
        myStage.setScene(myScene);
        myStage.show();
	}
	private void startGameFromScene(){
		myScene = setUpGame(SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND);
		myStage.setScene(myScene);
        myStage.setTitle(GAME_TITLE);
        myStage.show();
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
	}
	private Scene setUpGame(int width, int height, Paint background){
        Scene scene = new Scene(root, width, height, background);
        // make some shapes and set their properties
        refreshRoot();
        // respond to input
        scene.setOnKeyPressed(e -> {
				handleKeyInput(e.getCode());
		});
        return scene;
	}
	private void endGameScreen(){
		VBox vbox = new VBox();
		Text text;
		if(player1.getScore() > player2.getScore()){
			text = new Text("Player 1 wins!");
		}else if(player2.getScore() > player1.getScore()){
			text = new Text("Player 2 wins!");
		}else{
			text = new Text("It's a Draw!");
		}
        vbox.getChildren().add(text);
    	myStage.setTitle(END_GAME_TITLE);
    	myScene = new Scene(vbox, 500, 150);
        myStage.setScene(myScene);
        myStage.show();
	}
	private void refreshRoot(){
		root.getChildren().clear();
		root.getChildren().add(player1.getPaddle());
        root.getChildren().add(player2.getPaddle());
        for(Player player : players){
        	for(Brick brick : player.getBricks()){
        		root.getChildren().add(brick);
        	}
        }
        for(Ball ball : balls){
        	root.getChildren().add(ball);
        }
        for(PowerUp powerup : onScreenPowerUps){
        	root.getChildren().add(powerup);
        }
        root.getChildren().add(scorePanel);
        root.getChildren().add(livesPanel);
	}
	private void setUpLevel(int level){
		//new MediaPlayer(background_song).play();
		/*
		timeline = new Timeline(new KeyFrame(Duration.seconds(powerUpDelay), ev -> {
			PowerUp powerUp = generateRandomPowerUp();
            powerUp.spawnInRandomLocation(SCREEN_WIDTH, SCREEN_HEIGHT);
            root.getChildren().add(powerUp);
            powerUps.add(powerUp);
	    }));
	    */
		onScreenPowerUps = new PowerUp[level];
		for(int i = 0; i < onScreenPowerUps.length; i++){
			onScreenPowerUps[i] = generateRandomPowerUp();
			onScreenPowerUps[i].spawnInRandomLocation(SCREEN_WIDTH, SCREEN_HEIGHT);
            root.getChildren().add(onScreenPowerUps[i]);
		}
		/*
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	    */
		// file-reading information taken from http://www2.lawrence.edu/fast/GREGGJ/CMSC150/031Files/031Files.html
		this.level = level;
		String fileName = "levels/level_" + level + ".txt";
        Scanner input = null;
        try {
            input = new Scanner(new File(fileName));
            setPositionsFromFile(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Ball startingBall = new Ball();
        startingBall.setStartingPosition(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
        startingBall.setStartingSpeed(startingBall.getCurrentSpeed() + level*20);
        startingBall.reset();
        //TODO: make this cleaner
        balls.clear();
        balls.add(startingBall);
        
        for(Player player : players){
        	player.resetLives();
        	player.getPaddle().shrink(level);
        }
		recentlyHit = new Random().nextBoolean() ? player1 : player2;
        refreshRoot();
	}
	
	private void setPositionsFromFile(Scanner input){
		for(Player player: players){
			player.getPaddle().setStartingPosition(input.nextInt(), input.nextInt());
			player.getPaddle().reset(level);
			player.getBricks().clear();
		}
        while(input.hasNextLine()) {
            int x = input.nextInt();
            int y = input.nextInt();
            Brick brick;
            String type = input.next();
            if(type.equals("multihit")){
            	brick = generateRandomMultiHitBrick();
            }else if(type.equals("randpwr")){
            	brick = new RandomPwrBrick();
            }else if(type.equals("extralife")){
            	brick = new ExtraLifeBrick();
            }else{
            	brick = new CementBrick();
            }
            brick.setPosition(x, y);
            String player = input.next();
            if(player.equals("p1")){
            	player1.getBricks().add(brick);
            }else if(player.equals("p2")){
            	player2.getBricks().add(brick);
            }
        }
	}
	private void step (double elapsedTime) {
		updateMovingObjects(elapsedTime);
		checkCollisions();
		checkOffscreen();
		checkLives();
        checkPowerUps();
        adjustPaddlesIfNeeded();
    }
	
	private void updateMovingObjects(double elapsedTime){
		for(Ball ball : balls){
			ball.update(myScene,elapsedTime);
		}
		for(Player player : players){
			player.getPaddle().update(myScene,elapsedTime);
		}
	}
	private void checkCollisions(){
		for(Ball ball : balls){
			for(Player player : players){
				for(Brick brick : player.getBricks()){
					boolean collision = brick.collide(ball);
		        	if(collision && brick instanceof RandomPwrBrick){
		        		((RandomPwrBrick) brick).activateRandomPowerUp(this);
		        		currentlyActivePowerUp = null;
		        	}
		        	if(collision && brick instanceof ExtraLifeBrick){
		        		((ExtraLifeBrick) brick).awardExtraLife(this);
		        		currentlyActivePowerUp = null;
		        	}
				}
				if(player.getPaddle().redirectBall(ball)){
					recentlyHit = player;
				}
			}
		}
	}
	private void checkOffscreen(){
		for(Ball ball : balls){
			ball.redirectOffScreen(myScene);
	        if(ball.checkOffScreen(myScene) == -1){
	        	player1.loseLife();
	        	if(balls.size() > 1){
	        		balls.remove(ball);
	        		root.getChildren().remove(ball);
	        	}else{
	        		ball.reset();
	        	}
	        }else if(ball.checkOffScreen(myScene) == 1){
	        	player2.loseLife();
	        	if(balls.size() > 1){
	        		balls.remove(ball);
	        		root.getChildren().remove(ball);
	        	}else{
	        		ball.reset();
	        	}
	        }
		}
	}
	private void checkLives(){
		//http://www.java2s.com/Code/Java/JavaFX/UsingLabeltodisplayText.htm
		livesPanel.setText(player1.getLives() + "|Lives|" + player2.getLives());
		scorePanel.setText(player1.getScore() + "|Score|" + player2.getScore());
		for(Player player : players){
			if(player.getLives() == 0){
				player.getOpponent().incrementScore();
				root.getChildren().clear();
				if(level == 3){
					endGameScreen();
				}else{
					setUpLevel(++level);
				}
				refreshRoot();
			}
		}
	}
	
	private void adjustPaddlesIfNeeded(){
		for(Player player : players){
			if(player.getLives() == 1 && !player.getPaddle().abilityOn()){
				System.out.println("hi");
				int rand = new Random().nextInt(3);
				switch(rand){
				case 0: player.getPaddle().setFitHeight(player.getPaddle().getFitHeight() * 1.5);
					player.getPaddle().setAbilityOn();
					break;
				case 1: player.getOpponent().getPaddle().setFitHeight(player.getOpponent().getPaddle().getFitHeight() / 1.5);
					player.getOpponent().getPaddle().setAbilityOn();
					break;
				case 2: player.getPaddle().activateSticky();
					player.getPaddle().setAbilityOn();
					break;
				}
			}
		}
	}
	private void checkPowerUps(){
		for(Ball ball : balls){
			for(int i = 0; i < onScreenPowerUps.length; i++){
				PowerUp powerUp = onScreenPowerUps[i];
				if(ball.intersects(powerUp) && powerUp.isVisible()){
					if(currentlyActivePowerUp != null){
						currentlyActivePowerUp.disable(this);
					}
					powerUp.setVisible(false);
					powerUp.activate(this);
					root.getChildren().remove(powerUp);
					currentlyActivePowerUp = powerUp;
					onScreenPowerUps[i] = generateRandomPowerUp();
					onScreenPowerUps[i].spawnInRandomLocation(SCREEN_WIDTH, SCREEN_HEIGHT);
		            root.getChildren().add(onScreenPowerUps[i]);
				}
			}
		}
	}
	
	private void handleKeyInput(KeyCode code){
		switch(code){
			case UP: player2.getPaddle().setDirection(0,-1);
				break;
			case DOWN: player2.getPaddle().setDirection(0,1);
				break;
			case W: player1.getPaddle().setDirection(0,-1);
				break;
			case S: player1.getPaddle().setDirection(0,1);
				break;
			case DIGIT1: setUpLevel(1);
				break;
			case DIGIT2: setUpLevel(2);
				break;
			case DIGIT3: setUpLevel(3);
				break;
			case L: player1.addLife();
				break;
			case F: player2.addLife();
				break;
			case R: player1.getPaddle().reset();
				player2.getPaddle().reset();
				for(Ball ball : balls){
					ball.reset();
				}
				break;
			case D: player1.getPaddle().launchBall(this);
				break;
			case LEFT: player2.getPaddle().launchBall(this);
				break;
			case J: player1.loseLife();
				break;
			case K: player2.loseLife();
				break;
			case H: new BrickCementer().activate(this);
				break;
		default:
			break;
		}
	}
    private Brick generateRandomMultiHitBrick(){
    	Brick[] options = new Brick[]
                {new MultiHitBrick(1), 
                		new MultiHitBrick(2),
                        new MultiHitBrick(3)};
        return options[(int)(Math.random() * options.length)];
    }
    private PowerUp generateRandomPowerUp(){
    	PowerUp[] options = new PowerUp[]
                { new PaddleSpeedAdjuster(1.5), new PaddleSpeedAdjuster(0.75),
                	new BallSpeedAdjuster(1.5), new BallSpeedAdjuster(0.75),
                	new BallCloner(), new BrickCementer()};
        return options[(int)(Math.random() * options.length)];
    }
    
	@Override
	public void changeBallSpeed(double multiplier) {
		for(Ball ball: balls){
			ball.setCurrentSpeed(ball.getCurrentSpeed() * multiplier);
		}
	}
	@Override
	public void changePaddleSpeed(double multiplier) {
		recentlyHit.getPaddle().setCurrentSpeed(recentlyHit.getPaddle().getCurrentSpeed() * multiplier);
	}
	@Override
	public void cloneBall() {
		Ball referenceBall = balls.get(0);
		Ball clone = new Ball();
		clone.setStartingPosition(referenceBall.getX(), referenceBall.getY());
		clone.setCurrentSpeed(referenceBall.getStartingSpeed());
		clone.reset();
		balls.add(clone);
		root.getChildren().add(clone);
	}
	@Override
	public void removeBall(){
		if(balls.size()==1) return;
		Ball toBeRemoved = balls.remove(balls.size() - 1);
		root.getChildren().remove(toBeRemoved);
	}
	@Override
	public void activateRandomPowerUp(){
		currentlyActivePowerUp = generateRandomPowerUp();
		currentlyActivePowerUp.activate(this);
	}
	public static void main (String[] args) {
        launch(args);
    }
	@Override
	public void changePaddleSize(double multiplier) {
		player1.getPaddle().setFitHeight(player1.getPaddle().getFitHeight() * multiplier);
	}
	@Override
	public void launchBallFromStickyPaddle(Ball ball, Paddle paddle){
		if(paddle == player1.getPaddle()){
			ball.setStartingPosition(paddle.getRight() + 2 * ball.getRadius(), paddle.getCenter().getY());
		}else if(paddle == player2.getPaddle()){
			ball.setStartingPosition(paddle.getLeft() - 2 * ball.getRadius(), paddle.getCenter().getY());
		}
		ball.reset();
	}
	@Override
	public void awardExtraLife(){
		for(Player player : players){
			if(recentlyHit == player) player.addLife();
		}
	}
	@Override
	public ArrayList<Brick> turnBricksIntoCement(){
		if(cementOn) return null;
		ArrayList<Brick> copy = new ArrayList<Brick>();
		for(Brick brick : recentlyHit.getBricks()){
			if(brick instanceof MultiHitBrick){
				if (((MultiHitBrick) brick).isActive()){
					copy.add(brick);
					CementBrick cb = new CementBrick();
					cb.setPosition(brick.getX(), brick.getY());
					root.getChildren().add(cb);
					root.getChildren().remove(brick);
					recentlyHit.getBricks().remove(brick);
					recentlyHit.getBricks().add(cb);
					recentlyCemented = recentlyHit;
				}
			}
		}
		return copy;
	}
	@Override
	public void revertBricksToNormal(ArrayList<Brick> copy){
		root.getChildren().removeAll(recentlyCemented.getBricks());
		recentlyCemented.getBricks().clear();
		recentlyCemented.getBricks().addAll(copy);
		root.getChildren().addAll(copy);
	}

}
