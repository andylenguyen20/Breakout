package game_aln20;

import java.io.File;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.geom.Point2D.Double;
import java.net.URL;

public class Breakout extends Application implements GameDelegate{
	public static final Paint BACKGROUND_COLOR = Color.AZURE;
	public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final int TRANSITION_MUSIC_DURATION = 3;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final String GAME_TITLE = "Breakout Pong Game";
	public static final String START_TITLE = "Breakout Pong Rules";
	public static final String SUBMIT_MSG = "PLAY!";
	public static final String RULES_FILE_NAME = "rules.txt";
	public static final String END_GAME_TITLE = "GAME OVER";
	public static final String DRAW_MSG = "It's a draw!";
	public static final String PLAYER1_TAG = "Player1";
	public static final String PLAYER2_TAG = "Player2";
	public static final String INTRO_SONG_FILE_PATH = "audio/Fast_Ace.wav";
	public static final String GAME_SONG_FILE_PATH = "audio/Arcade_Funk.wav";
	
	private Stage myStage;
	private Scene myScene;
	private Label statusDisplay;
	private Group root;
	private AudioClip clip;
	public static final int GAME_SCREEN_WIDTH = 850;
	public static final int GAME_SCREEN_HEIGHT = 500;
	public static final int SPLASH_SCREEN_WIDTH = 825;
	public static final int SPLASH_SCREEN_HEIGHT = 500;
	public static final int END_GAME_SCREEN_WIDTH = 500;
	public static final int END_GAME_SCREEN_HEIGHT = 150;
	
	private Player[] players;
	private Player player1, player2, recentlyHit;
	private CopyOnWriteArrayList<Ball> balls;
	private PowerUp[] onScreenPowerUps;
	private PowerUp currentlyActivePowerUp;
	private Level myLevel;
	
	@Override
	public void start(Stage stage) {
		myStage = stage;
		root = new Group();
		initializeScreenObjects();
		startUpSplashScreen();
		statusDisplay = new Label();
		statusDisplay.setTranslateX(GAME_SCREEN_WIDTH/2);
	}
	private void initializeScreenObjects(){
		players = new Player[2];
		players[0] = new Player();
		players[1] = new Player();
		player1 = players[0];
		player2 = players[1];
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		balls = new CopyOnWriteArrayList<Ball>();
	}
	private void startUpSplashScreen(){
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
			startUpGameScreen();
			startNewLevel(1);
    	});
    	vbox.getChildren().add(button);
    	myScene = new Scene(vbox, SPLASH_SCREEN_WIDTH, SPLASH_SCREEN_HEIGHT);
    	setScene(START_TITLE);
	}
	private void startUpGameScreen(){
		myScene = new Scene(root, GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT, BACKGROUND_COLOR);
		myScene.setOnKeyPressed(e -> {
			handleKeyInput(e.getCode());
		});
		setScene(GAME_TITLE);
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
	}
	private void startUpEndGameScreen(){
		HBox hbox = new HBox();
		Text text;
		if(player1.getScore() > player2.getScore()){
			text = new Text(PLAYER1_TAG + "wins");
		}else if(player2.getScore() > player1.getScore()){
			text = new Text(PLAYER2_TAG + "wins");
		}else{
			text = new Text(DRAW_MSG);
		}
        hbox.getChildren().add(text);
    	myScene = new Scene(hbox, END_GAME_SCREEN_WIDTH, END_GAME_SCREEN_HEIGHT);
        setScene(END_GAME_TITLE);
	}
	private void setScene(String title){
		myStage.setScene(myScene);
        myStage.setTitle(title);
        myStage.show();
	}
	private void playMusicFromFile(String filename){
		if(clip != null) {
			clip.stop();
		}
		URL url = null;
		try {
			File file = new File(filename);
			if (file.canRead()) url = file.toURI().toURL();
		}
		catch (MalformedURLException e) { e.printStackTrace(); }
		if (url == null) throw new RuntimeException("audio " + filename + " not found");
		clip = new AudioClip(url.toString());
		clip.setCycleCount(Timeline.INDEFINITE);
		clip.play();
	}
	private void playMusicAtLevelStart(){
		playMusicFromFile(INTRO_SONG_FILE_PATH);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(TRANSITION_MUSIC_DURATION), ev -> {
			playMusicFromFile(GAME_SONG_FILE_PATH);
	    }));
	    timeline.setCycleCount(1);
	    timeline.play();
	}
	
	private void refreshRoot(){
		root.getChildren().clear();
        for(Player player : players){
        	root.getChildren().add(player.getPaddle());
        	root.getChildren().addAll(player.getBricks());
        }
        for(Ball ball : balls){
        	root.getChildren().add(ball);
        }
        for(PowerUp powerup : onScreenPowerUps){
        	root.getChildren().add(powerup);
        }
        root.getChildren().add(statusDisplay);
	}
	private void startNewLevel(int level){
		disableAndClearScreenObjects();
		setUpGameAttributesFromLevel(level);
		resetGameComponents();
		refreshRoot();
		playMusicAtLevelStart();
	}
	private void disableAndClearScreenObjects(){
		if(currentlyActivePowerUp != null && !currentlyActivePowerUp.isDeactivated()){
			currentlyActivePowerUp.deactivate(this);
		}
		for(Player player : players){
        	player.getBricks().clear();
        }
		balls.clear();
	}
	private void setUpGameAttributesFromLevel(int level){
		myLevel = new Level(level);
		onScreenPowerUps = myLevel.getFreshPowerUpsArray();
		player1.getBricks().addAll(myLevel.getBricks(PLAYER1_TAG));
		player2.getBricks().addAll(myLevel.getBricks(PLAYER2_TAG));
		Double[] paddlePositions = myLevel.getPaddlePositions(players.length);
		for(int i = 0; i < players.length; i++){
			players[i].getPaddle().setStartingPosition(paddlePositions[i].getX(), paddlePositions[i].getY());
			players[i].getPaddle().setStartingHeight(Paddle.DEFAULT_HEIGHT + myLevel.getPaddleSizeOffset());
		}
		Ball startingBall = new Ball();
        startingBall.setStartingSpeed(Ball.DEFAULT_SPEED + myLevel.getBallSpeedOffset());
        balls.add(startingBall);
	}
	private void resetGameComponents(){
		for(int i = 0; i < onScreenPowerUps.length; i++){
			onScreenPowerUps[i] = generateRandomPowerUp();
			onScreenPowerUps[i].spawnInRandomLocation(GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT);
		}
		recentlyHit = new Random().nextBoolean() ? player1 : player2;
		currentlyActivePowerUp = null;
		for(Player player : players){
			player.resetLives();
			player.getPaddle().reset();
		}
		for(Ball ball : balls){
			ball.setStartingPosition(GAME_SCREEN_WIDTH/2, GAME_SCREEN_HEIGHT/2);
			ball.reset();
		}
	}

	private void updateStatusDisplay(){
		statusDisplay.setText(player1.getLives() + "|Lives|" + player2.getLives() + "\n" + player1.getScore() + "|Score|" + player2.getScore() + "\nLevel: " + myLevel.getLevel());
		if(currentlyActivePowerUp != null){
			statusDisplay.setGraphic(new ImageView(currentlyActivePowerUp.getImage()));
		}else{
			statusDisplay.setGraphic(null);
		}
	}

	private void step (double elapsedTime) {
		updateMovingObjects(elapsedTime);
		checkCollisions();
		checkOffscreen();
		checkLives();
        checkPowerUps();
        updateStatusDisplay();
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
		        	}
		        	if(collision && brick instanceof ExtraLifeBrick){
		        		((ExtraLifeBrick) brick).awardExtraLife(this);
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
			int offScreenStatus = ball.getOffscreenStatus(myScene);
	        if(offScreenStatus == -1){
	        	player1.setLives(player1.getLives() - 1);
	        	resetBallIfNeeded(ball);
	        }else if(offScreenStatus == 1){
	        	player2.setLives(player2.getLives() - 1);
	        	resetBallIfNeeded(ball);
	        }
		}
	}

	private void resetBallIfNeeded(Ball ball){
		if(balls.size() == 1){
			ball.setStartingPosition(GAME_SCREEN_WIDTH/2, GAME_SCREEN_HEIGHT/2);
			ball.reset();
    	}else{
    		balls.remove(ball);
    		root.getChildren().remove(ball);
    	}
	}
	
	private void checkLives(){
		for(Player player : players){
			if(player.getLives() == 0){
				player.getOpponent().setScore(player.getOpponent().getScore() + 1);;
				if(myLevel.getLevel() == 3){
					startUpEndGameScreen();
				}else{
					startNewLevel(myLevel.getLevel() + 1);
				}
			}else if(player.getLives() == 1){
				setPaddleAbility(player);
			}
		}
	}
	
	private void setPaddleAbility(Player player){
		if(!player.getPaddle().abilityOn()){
			int rand = new Random().nextInt(3);
			switch(rand){
			case 0: player.getPaddle().setAbilityOn();
					player.getPaddle().setFitHeight(player.getPaddle().getFitHeight() * 1.5);
					break;
			case 1: player.getOpponent().getPaddle().setAbilityOn();
					player.getOpponent().getPaddle().setFitHeight(player.getOpponent().getPaddle().getFitHeight() / 1.5);
					break;
			case 2: player.getPaddle().setAbilityOn();
					player.getPaddle().activateSticky();
					break;
			}
		}
	}
	private void checkPowerUps(){
		for(Ball ball : balls){
			for(int i = 0; i < onScreenPowerUps.length; i++){
				PowerUp powerUp = onScreenPowerUps[i];
				if(ball.intersects(powerUp) && powerUp.isVisible()){
					boolean shouldDisableEarly = currentlyActivePowerUp != null && !currentlyActivePowerUp.isDeactivated();
					if(shouldDisableEarly){
						currentlyActivePowerUp.deactivate(this);
					}
					powerUp.setVisible(false);
					powerUp.activate(this);
					root.getChildren().remove(powerUp);
					currentlyActivePowerUp = powerUp;
					onScreenPowerUps[i] = generateRandomPowerUp();
					onScreenPowerUps[i].spawnInRandomLocation(GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT);
		            root.getChildren().add(onScreenPowerUps[i]);
				}
			}
		}
	}
	
	private void handleKeyInput(KeyCode code){
		switch(code){
			case UP: player2.getPaddle().setDirection(0,-1); break;
			case DOWN: player2.getPaddle().setDirection(0,1); break;
			case W: player1.getPaddle().setDirection(0,-1); break;
			case S: player1.getPaddle().setDirection(0,1); break;
			case DIGIT1: startNewLevel(1); break;
			case DIGIT2: startNewLevel(2); break;
			case DIGIT3: startNewLevel(3); break;
			case L: player1.setLives(player1.getLives() + 1); break;
			case F: player2.setLives(player2.getLives() + 1); break;
			case R: player1.getPaddle().reset();
				player2.getPaddle().reset();
				for(Ball ball : balls) ball.reset();
				break;
			case D: player1.getPaddle().launchBall(this); break;
			case LEFT: player2.getPaddle().launchBall(this); break;
		default: break;
		}
	}
    private PowerUp generateRandomPowerUp(){
    	PowerUp[] options = new PowerUp[]
                { new PaddleSpeedAdjuster(SpeedAdjuster.FASTER_SPEED_MULTIPLIER), 
                	new PaddleSpeedAdjuster(SpeedAdjuster.FASTER_SPEED_MULTIPLIER), 
                	new PaddleSpeedAdjuster(SpeedAdjuster.SLOWER_SPEED_MULTIPLIER),
                	new PaddleSpeedAdjuster(SpeedAdjuster.SLOWER_SPEED_MULTIPLIER),
                	new BallSpeedAdjuster(SpeedAdjuster.FASTER_SPEED_MULTIPLIER),
                	new BallSpeedAdjuster(SpeedAdjuster.FASTER_SPEED_MULTIPLIER),
                	new BallSpeedAdjuster(SpeedAdjuster.SLOWER_SPEED_MULTIPLIER),
                	new BallSpeedAdjuster(SpeedAdjuster.SLOWER_SPEED_MULTIPLIER),
                	new BallCloner(), 
                	new BrickCementer()};
        return options[(int)(Math.random() * options.length)];
    }
    
	@Override
	public void changeBallSpeed(double multiplier) {
		for(Ball ball: balls){
			ball.setCurrentSpeed(ball.getCurrentSpeed() * multiplier);
		}
	}
	@Override
	public void revertBallSpeed(){
		for(Ball ball: balls){
			ball.setCurrentSpeed(ball.getStartingSpeed());
		}
	}
	@Override
	public Paddle changePaddleSpeed(double multiplier) {
		Paddle paddleAffected = recentlyHit.getPaddle();
		paddleAffected.setCurrentSpeed(paddleAffected.getCurrentSpeed() * multiplier);
		return paddleAffected;
	}
	@Override
	public void revertPaddleSpeed(Paddle paddleAffected) {
		paddleAffected.setCurrentSpeed(paddleAffected.getStartingSpeed());
	}
	@Override
	public void cloneBall() {
		Ball referenceBall = balls.get(0);
		Ball clone = new Ball();
		clone.setStartingPosition(GAME_SCREEN_WIDTH/2, GAME_SCREEN_HEIGHT/2);
		clone.setStartingSpeed(referenceBall.getStartingSpeed());
		clone.reset();
		clone.setCurrentSpeed(referenceBall.getCurrentSpeed());
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
		if(currentlyActivePowerUp != null && !currentlyActivePowerUp.isDisabled()){
			currentlyActivePowerUp.deactivate(this);
		}
		currentlyActivePowerUp = generateRandomPowerUp();
		currentlyActivePowerUp.activate(this);
	}
	
	@Override
	public void launchBallFromStickyPaddle(Ball ball, Paddle paddle){
		if(paddle == player1.getPaddle()){
			ball.setStartingPosition(paddle.getRight() + 2*ball.getRadius(), paddle.getCenter().getY());
		}else if(paddle == player2.getPaddle()){
			ball.setStartingPosition(paddle.getLeft() - 2*ball.getRadius(), paddle.getCenter().getY());
		}
		ball.reset();
	}
	@Override
	public void awardExtraLife(){
		for(Player player : players){
			if(recentlyHit == player) player.setLives(player.getLives() + 1);;
		}
	}
	@Override
	public ArrayList<Brick> turnBricksIntoCement(){
		if(recentlyHit.isCemented()) return null;
		recentlyHit.setCemented(true);
		ArrayList<Brick> copy = new ArrayList<Brick>();
		for(Brick brick : recentlyHit.getBricks()){
			boolean activeMultiHit = brick instanceof MultiHitBrick && ((MultiHitBrick) brick).isActive();
			boolean cementBrick = brick instanceof CementBrick;
			if (activeMultiHit || cementBrick){
					copy.add(brick);
					CementBrick cb = new CementBrick();
					cb.setPosition(brick.getX(), brick.getY());
					root.getChildren().add(cb);
					root.getChildren().remove(brick);
					recentlyHit.getBricks().remove(brick);
					recentlyHit.getBricks().add(cb);
			}
		}
		return copy;
	}

	@Override
	public void revertBricksToNormal(ArrayList<Brick> copy){
		for(Player player : players){
			if(!player.isCemented()){
				continue;
			}
			root.getChildren().removeAll(player.getBricks());
			player.getBricks().clear();
			player.getBricks().addAll(copy);
			player.setCemented(false);
			root.getChildren().addAll(player.getBricks());
		}
	}
	
	public static void main (String[] args) {
        launch(args);
    }
}
