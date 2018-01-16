package game_aln20;

import java.awt.geom.Point2D.Double;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Breakout extends Application{
	public static final Paint BACKGROUND = Color.AZURE;
	public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final int SCREEN_WIDTH = 850;
	public static final int SCREEN_HEIGHT = 500;
	public static final String TITLE = "Breakout Pong";
	
	private Scene myScene;
	private Group root;
	
	private Player player1, player2;
	private Paddle paddle1, paddle2;
	private Ball ball;
	private ArrayList<Brick> bricks;
	private int level = 1;
	
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		FileInputStream fis = new FileInputStream("images/paddle.gif");
		Image paddleImage = new Image(fis);
		paddle1 = new Paddle(paddleImage);
		paddle2 = new Paddle(paddleImage);
		player1 = new Player(0,3, paddle1);
		player2 = new Player(0,3, paddle2);
		setUpLevel(1);
		myScene = setUpGame(SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        
        
        // attach "game loop" to timeline to play it
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
	}
	private Scene setUpGame(int width, int height, Paint background) throws FileNotFoundException{
        root = new Group();
        Scene scene = new Scene(root, width, height, background);
        // make some shapes and set their properties
        updateRoot();
        // respond to input
        scene.setOnKeyPressed(e -> {
			try {
				handleKeyInput(e.getCode());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        return scene;
	}
	private void updateRoot(){
		root.getChildren().clear();
		root.getChildren().add(player1.getPaddle());
        root.getChildren().add(player2.getPaddle());
        for(int i = 0; i < bricks.size(); i++){
        	root.getChildren().add(bricks.get(i));
        }
        root.getChildren().add(ball);
	}
	private void setUpLevel(int level) throws FileNotFoundException{
		// file-reading information taken from http://www2.lawrence.edu/fast/GREGGJ/CMSC150/031Files/031Files.html
        String fileName = "levels/level_" + level + ".txt";
        Scanner input = null;
        try {
            input = new Scanner(new File(fileName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		paddle1.setPosition(input.nextInt(), input.nextInt());
		paddle2.setPosition(input.nextInt(), input.nextInt());
		FileInputStream fis = new FileInputStream("images/brick1.gif");
        bricks = new ArrayList<Brick>();
        while(input.hasNextLine()) {
            int x = input.nextInt();
            int y = input.nextInt();
            fis = new FileInputStream("images/brick1.gif");
            Brick brick = new Brick(new Image(fis));
            brick.setX(x);
            brick.setY(y);
          //TODO: make this more elegant
            brick.setCenter(x + brick.getBoundsInLocal().getWidth()/2, y + brick.getBoundsInLocal().getHeight()/2);
            bricks.add(brick);
        }
        fis = new FileInputStream("images/ball.gif");
        ball = new Ball(new Image(fis));
        ball.setStartingPosition(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
        ball.resetPosition();
        Double rnDir = ball.getRandomNormalizedDirection();
        ball.setDirection(rnDir.getX(), rnDir.getY());
	}
	private void step (double elapsedTime) {
		ball.update(myScene,elapsedTime);
		paddle1.update(myScene,elapsedTime);
        paddle2.update(myScene,elapsedTime);
        
        for(int i = 0; i < bricks.size(); i++){
        	bricks.get(i).collide(ball);
        }
        paddle1.redirectBall(ball);
        paddle2.redirectBall(ball);
        ball.redirectOffScreen(myScene);
        if(ball.isOffscreen(myScene)){
        	System.out.println("lost life");
        }
        
    }
	private void drawScreenObjects(){
		
	}
	private void handleKeyInput(KeyCode code) throws FileNotFoundException{
		if (code == KeyCode.UP) {
            player2.getPaddle().setDirection(0,-1);
        }
        else if (code == KeyCode.DOWN) {
        	player2.getPaddle().setDirection(0,1);
        }
        else if (code == KeyCode.W) {
        	player1.getPaddle().setDirection(0,-1);
        }
        else if (code == KeyCode.S) {
        	player1.getPaddle().setDirection(0,1);
        }
        else if (code == KeyCode.DIGIT1) {
        	root.getChildren().clear();
        	setUpLevel(1);
        	updateRoot();
        }
        else if (code == KeyCode.DIGIT2) {
        	root.getChildren().clear();
        	setUpLevel(2);
        	updateRoot();
        }
        else if (code == KeyCode.DIGIT3) {
        	root.getChildren().clear();
        	setUpLevel(3);
        	updateRoot();
        }
	}
	public static void main (String[] args) {
        launch(args);
    }
}
