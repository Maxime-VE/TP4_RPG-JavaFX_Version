package com.example.tp4_rpg_javafx_version;

import com.example.tp4_rpg_javafx_version.isep.rpg.Combattant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class VictoryController {

    @FXML
    void exitButton(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void restartButton(MouseEvent event) throws IOException {
        if (CharSelectionController.heros.size() > 0) {
            CharSelectionController.heros.subList(0, CharSelectionController.heros.size()).clear();
        }
        HelloApplication.mediaPlayer.stop();
        HelloApplication.playMainSound();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
        HelloApplication.currentStage.setScene(scene);
        HelloApplication.currentStage.show();
    }

}