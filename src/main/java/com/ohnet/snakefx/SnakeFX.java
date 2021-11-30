package com.ohnet.snakefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;

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

    private Timer timer;

    private Image ball;
    private Image apple;
    private Image head;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Canvas canvas = new Canvas();
        canvas.setHeight(B_WIDTH);
        canvas.setWidth(B_HEIGHT);

        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();

        loadImages();
        initGame();

        graphicsContext2D.drawImage(apple,  200, 200);

        for (int z = 0; z < dots; z++) {
            if (z == 0) {
                graphicsContext2D.drawImage(head, x[z], y[z]);
            } else {
                graphicsContext2D.drawImage(ball, x[z], y[z]);
            }
        }
        
        VBox vbox = new VBox(canvas);
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    @Override
//    public void start(Stage stage) throws IOException {
//        primaryStage = stage;
//
//        SnakeBoardFX fxBoard = new SnakeBoardFX(primaryStage);
//    }

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
        }

        locateApple();

        // old java.swing.Timer
//        timer = new Timer(DELAY, this);
//        timer.start();

        /**
         * new code
         */
//        Timeline timeline = new Timeline(
//                new KeyFrame(
//                        Duration.ZERO,
//                        actionEvent -> dateLabel.set(dateFormat.format(new Date()))
//                ),
//                new KeyFrame(
//                        Duration.seconds(1)
//                )
//        );
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
    }

    private void locateApple() {

        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

}
