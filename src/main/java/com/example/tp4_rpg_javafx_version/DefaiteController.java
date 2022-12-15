package com.example.tp4_rpg_javafx_version;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class DefaiteController {

    @FXML
    void exitButton(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void restartButton(MouseEvent event) throws IOException {
        if (MainController.enemiesList.size() > 0) {
            MainController.enemiesList.subList(0, MainController.enemiesList.size()).clear();
        }
        HelloApplication.mediaPlayer.stop();
        HelloApplication.playMainSound();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
        HelloApplication.currentStage.setScene(scene);
        HelloApplication.currentStage.show();
    }

}
