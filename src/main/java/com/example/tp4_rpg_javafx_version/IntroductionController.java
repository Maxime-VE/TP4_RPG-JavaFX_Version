package com.example.tp4_rpg_javafx_version;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static com.example.tp4_rpg_javafx_version.HelloApplication.sound;

public class IntroductionController {

    @FXML
    private ImageView offButton;

    @FXML
    private ImageView onButton;


    @FXML
    void onNextButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("char-selection-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
        HelloApplication.currentStage.setScene(scene);
        HelloApplication.currentStage.show();
    }



    @FXML
    void onSoundButtonClick(MouseEvent event) {
        if(sound==0){
            HelloApplication.mediaPlayer.pause();
            onButton.setVisible(false);
            offButton.setVisible(true);
            sound=1;
        }else{
            HelloApplication.mediaPlayer.play();
            onButton.setVisible(true);
            offButton.setVisible(false);
            sound=0;
        }
    }

    @FXML
    void soundButtonVisibility(MouseEvent event) {
        if(sound==0){
            onButton.setVisible(true);
            offButton.setVisible(false);
        }else{
            onButton.setVisible(false);
            offButton.setVisible(true);
        }
    }

}
