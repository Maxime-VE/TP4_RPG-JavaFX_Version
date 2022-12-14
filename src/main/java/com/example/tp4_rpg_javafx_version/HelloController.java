package com.example.tp4_rpg_javafx_version;

import com.example.tp4_rpg_javafx_version.isep.rpg.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label Label;
    public static int displayMode;

    @FXML
    protected void onPlayButtonClick() throws IOException
    {
        displayMode=1;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("introduction-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
        HelloApplication.currentStage.setScene(scene);
        HelloApplication.currentStage.show();
    }
    @FXML
    void onConsoleButtonClick(MouseEvent event) {
        displayMode=0;
        Game.game();
    }
}