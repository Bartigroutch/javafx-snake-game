package com.ohnet.snakefx;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SnakeBoardFX extends Pane {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = false;

    // draws a sample canvas
    Canvas canvas = new Canvas(200, 200);

    //private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public SnakeBoardFX(Stage primaryStage) throws FileNotFoundException {
        loadImages();
        initGame();
        //doDrawing(primaryStage);
        gameIn(primaryStage, canvas.getGraphicsContext2D());
        //super.getChildren().add(canvas);
        //gameOverFX(primaryStage);
    }

    public void doDrawing(Stage stage) {
        var graphicsContext2D = canvas.getGraphicsContext2D();
        // draw snake or game over
        doDrawing(stage, canvas.getGraphicsContext2D());

        //super.getChildren().add(canvas);
    }

    private void drawLines(GraphicsContext gc) {
        gc.beginPath();
        gc.moveTo(30.5, 30.5);
        gc.lineTo(150.5, 30.5);
        gc.lineTo(150.5, 150.5);
        gc.lineTo(30.5, 30.5);
        gc.stroke();
    }

    private void initGame() {
        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();
    }

    private void doDrawing(Stage stage, GraphicsContext gc) {
        if (inGame) {
            //gc.drawImage(apple, apple_x, apple_y, this);
            gc.drawImage(apple,  apple_x, apple_y);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    gc.drawImage(head, x[z], y[z]);
                } else {
                    gc.drawImage(ball, x[z], y[z]);
                }
            }

           // Toolkit.getDefaultToolkit().sync();
            gc.stroke();
            this.setStyle("-fx-background-color: red");
            Scene scene = new Scene(this, 320, 240);
            stage.setScene(scene);
            //stage.show();
        } else {
            gameOverFX(stage);
        }
    }

    /**
     * loading images for snake
     */
    private void loadImages() throws FileNotFoundException {
        InputStream stream = new FileInputStream("/Users/danielbeyer/01_Entwicklung/devProjects/snake-for-nik/javafx-snake-game/src/main/resources/pictures/apple.png");
        apple = new Image(stream);
        //ball =new Image("file:/src/main/resources/pictures/dot.png");
        //apple = new Image("file:/src/main/resources/pictures/apple.png");
        //head = new Image("file:/src/main/resources/pictures/head.png");
    }

    /**
     * postion randomly
     */
    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }


    private void gameIn(Stage stage, GraphicsContext gc) throws FileNotFoundException {
        if(stage!=null && gc != null) {

            //gc.drawImage(apple, apple_x, apple_y);

            //gc.stroke();
            canvas.widthProperty().bind(this.widthProperty());
            canvas.heightProperty().bind(this.heightProperty());
            //gc.drawImage(apple, 10, 10, canvas.getWidth(), canvas.getHeight());
            gc.fillOval(10, 10, 10 * 2, 10 * 2);

            super.getChildren().add(canvas);

            /*try{
                Thread.sleep(10000);
            } catch(InterruptedException e){}*/

            this.setStyle("-fx-background-color: white");
            stage.setTitle("Snake FX!");
            stage.setResizable(false);

            stage.sizeToScene();

//            InputStream stream = new FileInputStream("/Users/danielbeyer/01_Entwicklung/devProjects/snake-for-nik/javafx-snake-game/src/main/resources/pictures/apple.png");
//            Image image = new Image(stream);
//            //Creating the image view
//            ImageView imageView = new ImageView();
//            //Setting image to the image view
//            imageView.setImage(image);
//            //Setting the image view parameters
//            imageView.setX(10);
//            imageView.setY(10);
//            imageView.setFitWidth(575);
//            imageView.setPreserveRatio(true);
//            //Setting the Scene object
//            Group root = new Group(imageView);

            Scene scene = new Scene(this, 595, 370);
            stage.setTitle("Displaying Image");
            stage.setScene(scene);
            stage.show();
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
}
