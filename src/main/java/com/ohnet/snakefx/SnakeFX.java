package com.ohnet.snakefx;

import com.ohnet.snakefx.util.AnimationTimerExtension;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SnakeFX extends Application {

    private final int ALL_DOTS = 900;
    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int RAND_POS = 29;
    private final int DELAY = 140;


    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private int dots;
    private int apple_x;
    private int apple_y;

    private AnimationTimerExtension timer;
    // time to wait to move the snake - as higher the value as slower the worm...
    private static final int TIME_TO_WAIT_MS = 1_50;

    private Image ball;
    private Image apple;
    private Image head;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Pane root = new Pane();

        StackPane holder = new StackPane();
        holder.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        Canvas canvas = new Canvas(B_WIDTH, B_HEIGHT);

        holder.getChildren().add(canvas);
        root.getChildren().add(holder);

        Scene scene = new Scene(root, B_WIDTH, B_HEIGHT);

        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        loadImages();
        initGame();

        graphicsContext2D.drawImage(apple,  apple_x, apple_y);

        for (int z = 0; z < dots; z++) {
            if (z == 0) {
                graphicsContext2D.drawImage(head, x[z], y[z]);
            } else {
                graphicsContext2D.drawImage(ball, x[z], y[z]);
            }
        }

        // needs to be set to have a key-listener
        canvas.setFocusTraversable(true);
        // reacts onKeyEvent and moves snake
        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
               switch (keyEvent.getCode()) {
                        case UP:    leftDirection  = false;
                                    rightDirection = false;
                                    upDirection = true;
                                    downDirection = false;
                                    break;
                        case DOWN:  leftDirection  = false;
                                    rightDirection = false;
                                    upDirection = false;
                                    downDirection = true;
                                    break;
                        case LEFT:  leftDirection  = true;
                                    rightDirection = false;
                                    upDirection = false;
                                    downDirection = false;
                                    break;
                        case RIGHT: leftDirection  = false;
                                    rightDirection = true;
                                    upDirection = false;
                                    downDirection = false;
                                    break;
               }
               move();
            }

        });

        //You can add a specific action when each frame is started.
        timer = new AnimationTimerExtension(TIME_TO_WAIT_MS) {
            @Override
            public void handle() {

                // clear the panel to see motion
                graphicsContext2D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                checkApple();
                checkCollision();
                move();
                doDrawing(primaryStage, graphicsContext2D);
            }

        };

        timer.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Sanke FX");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        timer.stop();
    }

    /**
     * loading images for snake
     */
    private void loadImages() throws FileNotFoundException {
        InputStream stream1 = new FileInputStream("/Users/danielbeyer/01_Entwicklung/devProjects/snake-for-nik/javafx-snake-game/src/main/resources/pictures/apple.png");
        apple = new Image(stream1);
        InputStream stream2 = new FileInputStream("/Users/danielbeyer/01_Entwicklung/devProjects/snake-for-nik/javafx-snake-game/src/main/resources/pictures/dot.png");
        ball =new Image(stream2);
        InputStream stream3 = new FileInputStream("/Users/danielbeyer/01_Entwicklung/devProjects/snake-for-nik/javafx-snake-game/src/main/resources/pictures/head.png");
        head = new Image(stream3);
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void initGame() {
        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
            System.out.println("dot: " + dots + " , x: " + x.length + " , y: "+ y.length);
        }

        locateApple();
    }

    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    private void doDrawing(Stage stage, GraphicsContext g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z]);
                } else {
                    g.drawImage(ball, x[z], y[z]);
                }
            }
            //Toolkit.getDefaultToolkit().sync();
        } else {
            new GameOverFX(stage).build();
        }
    }

    /**
     * checks if the "worm" is inside the game.
     */
    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            locateApple();
        }
    }

}
