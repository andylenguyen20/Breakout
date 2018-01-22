package game_aln20;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.geom.Point2D.Double;

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
	private Player player1, player2, recentlyHit;
	private CopyOnWriteArrayList<Ball> balls;
	private PowerUp[] onScreenPowerUps;
	private PowerUp currentlyActivePowerUp;
	
	private Level myLevel;
	private Label statusDisplay;
	
	@Override
	public void start(Stage stage) {
		myStage = stage;
		root = new Group();
		initializeScreenObjects();
		startUpSplashScreen();
		statusDisplay = new Label();
		statusDisplay.setTranslateX(SCREEN_WIDTH/2);
	}
	private void setScene(String title){
		myStage.setScene(myScene);
        myStage.setTitle(title);
        myStage.show();
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
    	myScene = new Scene(vbox, 500, 150);
    	setScene(START_TITLE);
	}
	private void startUpGameScreen(){
		myScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND);
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
			text = new Text("Player 1 wins!");
		}else if(player2.getScore() > player1.getScore()){
			text = new Text("Player 2 wins!");
		}else{
			text = new Text("It's a Draw!");
		}
        hbox.getChildren().add(text);
        hbox.setStyle("-fx-background: #FF3F3F;");
    	myScene = new Scene(hbox, 500, 150);
        setScene(END_GAME_TITLE);
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
		if(onScreenPowerUps != null){
			for(PowerUp powerUp : onScreenPowerUps) if(powerUp != null) powerUp.disable(this);
		}
		for(Player player : players){
        	player.getBricks().clear();
        }
		balls.clear();
		setUpFromLevel(level);
		for(int i = 0; i < onScreenPowerUps.length; i++){
			onScreenPowerUps[i] = generateRandomPowerUp();
			onScreenPowerUps[i].spawnInRandomLocation(SCREEN_WIDTH, SCREEN_HEIGHT);
		}
		recentlyHit = new Random().nextBoolean() ? player1 : player2;
		currentlyActivePowerUp = null;
		for(Player player : players){
			player.resetLives();
			player.getPaddle().reset();
		}
		for(Ball ball : balls){
			ball.setStartingPosition(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
			ball.reset();
		}
		refreshRoot();
	}
	private void setUpFromLevel(int level){
		myLevel = new Level(level);
		onScreenPowerUps = myLevel.getFreshPowerUpsArray();
		player1.getBricks().addAll(myLevel.getBricks("p1"));
		player2.getBricks().addAll(myLevel.getBricks("p2"));
		Double[] paddlePositions = myLevel.getPaddlePositions(players.length);
		for(int i = 0; i < players.length; i++){
			players[i].getPaddle().setStartingPosition(paddlePositions[i].getX(), paddlePositions[i].getY());
			players[i].getPaddle().setStartingHeight(players[i].getPaddle().getFitHeight() + myLevel.getPaddleSpeedOffset());
		}
		Ball startingBall = new Ball();
        startingBall.setStartingSpeed(startingBall.getCurrentSpeed() + myLevel.getBallSpeedOffset());
        balls.add(startingBall);
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
	        	player1.loseLife();
	        	resetBallIfNeeded(ball);
	        }else if(offScreenStatus == 1){
	        	player2.loseLife();
	        	resetBallIfNeeded(ball);
	        }
		}
	}

	private void resetBallIfNeeded(Ball ball){
		if(balls.size() == 1){
			ball.reset();
    	}else{
    		balls.remove(ball);
    		root.getChildren().remove(ball);
    	}
	}
	
	private void checkLives(){
		for(Player player : players){
			if(player.getLives() == 0){
				player.getOpponent().incrementScore();
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
					boolean shouldDisableEarly = currentlyActivePowerUp != null && !currentlyActivePowerUp.isDisabled();
					if(shouldDisableEarly){
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
			case UP: player2.getPaddle().setDirection(0,-1); break;
			case DOWN: player2.getPaddle().setDirection(0,1); break;
			case W: player1.getPaddle().setDirection(0,-1); break;
			case S: player1.getPaddle().setDirection(0,1); break;
			case DIGIT1: startNewLevel(1); break;
			case DIGIT2: startNewLevel(2); break;
			case DIGIT3: startNewLevel(3); break;
			case L: player1.addLife(); break;
			case F: player2.addLife(); break;
			case R: player1.getPaddle().reset();
				player2.getPaddle().reset();
				for(Ball ball : balls) ball.reset();
				break;
			case D: player1.getPaddle().launchBall(this); break;
			case LEFT: player2.getPaddle().launchBall(this); break;
			case J: player1.loseLife(); break;
			case K: player2.loseLife(); break;
			case H: new BrickCementer().activate(this); break;
		default: break;
		}
	}
    private PowerUp generateRandomPowerUp(){
    	PowerUp[] options = new PowerUp[]
                { new PaddleSpeedAdjuster(SpeedAdjuster.FASTER_SPEED_MULTIPLIER), 
                	new PaddleSpeedAdjuster(SpeedAdjuster.SLOWER_SPEED_MULTIPLIER),
                	new BallSpeedAdjuster(SpeedAdjuster.FASTER_SPEED_MULTIPLIER),
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
	public void changePaddleSpeed(double multiplier) {
		recentlyHit.getPaddle().setCurrentSpeed(recentlyHit.getPaddle().getCurrentSpeed() * multiplier);
	}
	@Override
	public void cloneBall() {
		Ball referenceBall = balls.get(0);
		Ball clone = new Ball();
		clone.setStartingPosition(referenceBall.getX(), referenceBall.getY());
		clone.setStartingSpeed(referenceBall.getStartingSpeed());
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
		if(currentlyActivePowerUp != null && currentlyActivePowerUp.isDisabled()){
			currentlyActivePowerUp.disable(this);
		}
		currentlyActivePowerUp = generateRandomPowerUp();
		currentlyActivePowerUp.activate(this);
	}
	@Override
	public void changePaddleSize(double multiplier) {
		player1.getPaddle().setFitHeight(player1.getPaddle().getFitHeight() * multiplier);
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
			if(recentlyHit == player) player.addLife();
		}
	}
	@Override
	public CopyOnWriteArrayList<Brick> turnBricksIntoCement(){
		if(recentlyHit.isCemented()) return null;
		recentlyHit.setCemented(true);
		CopyOnWriteArrayList<Brick> copy = new CopyOnWriteArrayList<Brick>();
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
				}
			}
		}
		return copy;
	}
	@Override
	public void revertBricksToNormal(CopyOnWriteArrayList<Brick> copy){
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
