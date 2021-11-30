package com.ohnet.snakefx;

import com.ohnet.snakefx.util.AnimationTimerExtension;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private static final int  TIME_TO_WAIT_MS = 1_000;

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
        //holder.setStyle("-fx-background-color: white");

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

        Button button = new Button("I'm here...");

        Timeline t = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(button.translateXProperty(), 0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(button.translateXProperty(), 80))
        );
        t.setAutoReverse(true);
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();

        //You can add a specific action when each frame is started.
        timer = new AnimationTimerExtension(TIME_TO_WAIT_MS) {
            @Override
            public void handle() {

                checkApple();

                for (int z = 0; z < dots; z++) {

                    checkCollision();
                    if (z == 0) {
                        move();
                        doDrawing(primaryStage, graphicsContext2D);
                        System.out.println("dot: " + z + " , x: " + x.length + " , y: "+ y.length);
                    } else {
                        move();
                        doDrawing(primaryStage, graphicsContext2D);
                        System.out.println("dot: " + z + " , x: " + x.length + " , y: "+ y.length);
                    }
                }
            }

        };

        timer.start();

        holder.getChildren().add(button);

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
            gameOverFX(stage);
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

    private void gameOverFX(Stage stage) {
        TilePane tilePane = new TilePane();

        Text text1 = new Text("Game Over");
        text1.setFill(Color.BLACK);
        text1.setFont(new Font("Verdana", 32));

        tilePane.getChildren().add(text1);
        tilePane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(tilePane, 320, 240);

        stage.setScene(scene);
        stage.show();
    }

    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            locateApple();
        }
    }

}
