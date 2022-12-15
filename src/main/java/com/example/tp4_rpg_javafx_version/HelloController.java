package com.example.tp4_rpg_javafx_version;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.application.HostServices;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.example.tp4_rpg_javafx_version.HelloApplication.sound;

public class HelloController {
    @FXML
    private Label Label;
    public static int displayMode;
    @FXML
    private ImageView offButton;

    @FXML
    private AnchorPane popUp;
    @FXML
    private ImageView onButton;

    int about=0;

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
    void onConsoleButtonClick(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("redirection-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
        HelloApplication.currentStage.setScene(scene);
        HelloApplication.currentStage.show();
        displayMode=0;
    }

    @FXML
    void aboutButtonClicked(MouseEvent event) {
        if (about==0){
            popUp.setVisible(true);
            about=1;
        }else {
            popUp.setVisible(false);
            about=0;
        }
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