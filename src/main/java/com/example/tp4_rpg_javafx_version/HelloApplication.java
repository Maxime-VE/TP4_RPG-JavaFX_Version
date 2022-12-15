package com.example.tp4_rpg_javafx_version;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class HelloApplication extends Application {

    public static Stage currentStage;


    @Override
    public void start(Stage stage) throws IOException {
        playMainSound();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        currentStage = stage;

        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
        stage.setTitle("TP4-RPG");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static MediaPlayer mediaPlayer;
    public static int sound=0;
    public static void playMainSound(){
        String s = "src/main/resources/com/example/tp4_rpg_javafx_version/music/mainAmbiance.mp3";
        Media media = new Media(Paths.get(s).toUri().toString());
        mediaPlayer= new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void playVictorySound(){
        String s = "src/main/resources/com/example/tp4_rpg_javafx_version/music/victoryMusic.mp3";
        Media media = new Media(Paths.get(s).toUri().toString());
        mediaPlayer= new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void playDefeatSound(){
        String s = "src/main/resources/com/example/tp4_rpg_javafx_version/music/gameOverMusic.mp3";
        Media media = new Media(Paths.get(s).toUri().toString());
        mediaPlayer= new MediaPlayer(media);
        mediaPlayer.play();
    }

}