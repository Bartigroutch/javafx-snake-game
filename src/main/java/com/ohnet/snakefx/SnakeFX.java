package com.ohnet.snakefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeFX extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(SnakeFX.class.getResource("snake-view.fxml"));
        //BoardFX board = new BoardFX();
        Pane pane = new Pane();

        var canvas = new Canvas(300, 300);
        var gc = canvas.getGraphicsContext2D();
        drawLines(gc);

        pane.getChildren().add(canvas);

        Scene scene = new Scene(pane, 320, 240);
        stage.setTitle("Snake FX!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void drawLines(GraphicsContext gc) {
        gc.beginPath();
        gc.moveTo(30.5, 30.5);
        gc.lineTo(150.5, 30.5);
        gc.lineTo(150.5, 150.5);
        gc.lineTo(30.5, 30.5);
        gc.stroke();

        
    }


}
