package com.example.tp4_rpg_javafx_version;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class IntroductionController {


    @FXML
    void onNextButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("char-selection-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
        HelloApplication.currentStage.setScene(scene);
        HelloApplication.currentStage.show();
    }

}
