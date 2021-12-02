package com.ohnet.snakefx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class to show the GameOver - Layer
 */
public class GameOverFX {

    private final Stage stage;

    public GameOverFX(Stage stage) {
        this.stage = stage;
    }

    private void createScene() {
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

    public GameOverFX build() {
        createScene();
        return new GameOverFX(stage);
    }
}
