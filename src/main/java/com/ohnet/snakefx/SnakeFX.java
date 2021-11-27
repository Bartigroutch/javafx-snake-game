package com.ohnet.snakefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeFX extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(SnakeFX.class.getResource("snake-view.fxml"));
        BoardFX board = new BoardFX();
        Scene scene = new Scene(board, 320, 240);
        stage.setTitle("Snake FX!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
