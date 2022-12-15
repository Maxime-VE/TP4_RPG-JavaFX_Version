package com.example.tp4_rpg_javafx_version;

import com.example.tp4_rpg_javafx_version.isep.rpg.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.tp4_rpg_javafx_version.HelloApplication.sound;

public class CharSelectionController {
    @FXML
    private ImageView offButton;

    @FXML
    private ImageView onButton;
    @FXML
    private ImageView imageArcher;

    @FXML
    private ImageView imageHealer;

    @FXML
    private ImageView imageMage;

    @FXML
    private ImageView imageWarrior;

    @FXML
    private Label label1;

    @FXML
    private Label label12;

    @FXML
    private Label label13;

    @FXML
    private Label label2;

    @FXML
    private Label label21;

    @FXML
    private Label label22;

    @FXML
    private Label label3;

    @FXML
    private Label label31;

    @FXML
    private Label label32;

    @FXML
    private Label label4;

    @FXML
    private Label label41;

    @FXML
    private Label label42;

    @FXML
    private Label label43;

    @FXML
    private Label labelHealer;

    @FXML
    private Label labelHunter;

    @FXML
    private Label labelMage;

    @FXML
    private Label labelWarrior;

    @FXML
    private ImageView acceptButtonImage;

    @FXML
    private Label acceptLabel;

    @FXML
    private ImageView playButtonImage;

    @FXML
    private Label playLabel;
    @FXML
    private TextField textField;
    public int charType;    // 0=Warrior , 1=Archer , 2=Mage , 3=Healer
    public static List<Combattant> heros = new ArrayList<>();

    @FXML
    void handleMouseClicked(MouseEvent event) {
        acceptButtonImage.setVisible(true);
        acceptLabel.setVisible(true);
        textField.setVisible(true);
        imageWarrior.setVisible(true);
        imageArcher.setVisible(false);
        imageHealer.setVisible(false);
        imageMage.setVisible(false);
        textField.setText("");
        charType=0;
    }

    @FXML
    void handleMouseClicked2(MouseEvent event) {
        acceptButtonImage.setVisible(true);
        acceptLabel.setVisible(true);
        textField.setVisible(true);
        imageArcher.setVisible(true);
        imageWarrior.setVisible(false);
        imageHealer.setVisible(false);
        imageMage.setVisible(false);
        textField.setText("");
        charType=1;
    }

    @FXML
    void handleMouseClicked3(MouseEvent event) {
        acceptButtonImage.setVisible(true);
        acceptLabel.setVisible(true);
        textField.setVisible(true);
        imageMage.setVisible(true);
        imageWarrior.setVisible(false);
        imageHealer.setVisible(false);
        imageArcher.setVisible(false);
        textField.setText("");
        charType=2;
    }

    @FXML
    void handleMouseClicked4(MouseEvent event) {
        acceptButtonImage.setVisible(true);
        acceptLabel.setVisible(true);
        textField.setVisible(true);
        imageHealer.setVisible(true);
        imageWarrior.setVisible(false);
        imageArcher.setVisible(false);
        imageMage.setVisible(false);
        textField.setText("");
        charType=3;
    }

    @FXML
    void handleMouseEntered(MouseEvent event) {
        labelWarrior.setVisible(false);
        label1.setVisible(true);
        label12.setVisible(true);
        label13.setVisible(true);
    }

    @FXML
    void handleMouseEntered2(MouseEvent event) {
        labelHunter.setVisible(false);
        label2.setVisible(true);
        label21.setVisible(true);
        label22.setVisible(true);
    }

    @FXML
    void handleMouseEntered3(MouseEvent event) {
        labelMage.setVisible(false);
        label3.setVisible(true);
        label31.setVisible(true);
        label32.setVisible(true);
    }
    @FXML
    void handleMouseEntered4(MouseEvent event) {
        labelHealer.setVisible(false);
        label4.setVisible(true);
        label41.setVisible(true);
        label42.setVisible(true);
        label43.setVisible(true);
    }
    @FXML
    void handleMouseExited(MouseEvent event) {
        labelWarrior.setVisible(true);
        label1.setVisible(false);
        label12.setVisible(false);
        label13.setVisible(false);
    }

    @FXML
    void handleMouseExited2(MouseEvent event) {
        labelHunter.setVisible(true);
        label2.setVisible(false);
        label21.setVisible(false);
        label22.setVisible(false);
    }

    @FXML
    void handleMouseExited3(MouseEvent event) {
        labelMage.setVisible(true);
        label3.setVisible(false);
        label31.setVisible(false);
        label32.setVisible(false);
    }
    @FXML
    void handleMouseExited4(MouseEvent event) {
        labelHealer.setVisible(true);
        label4.setVisible(false);
        label41.setVisible(false);
        label42.setVisible(false);
        label43.setVisible(false);
    }

    @FXML
    void onAcceptButtonClick(MouseEvent event) {
        //RECUPERER LE NOM DU HERO
        textField.setVisible(false);
        acceptButtonImage.setVisible(false);
        acceptLabel.setVisible(false);
        imageWarrior.setVisible(false);
        imageArcher.setVisible(false);
        imageHealer.setVisible(false);
        imageMage.setVisible(false);
        playButtonImage.setVisible(true);
        playLabel.setVisible(true);
        switch (charType) {
            case 0 -> {
                Warrior w = new Warrior(textField.getText(), 2, 13, false, 4);
                w.take(new Weapon("Couteau", "Commun", 1));
                heros.add(w);
            }
            case 1 -> {
                Hunter hu = new Hunter(textField.getText(), 37, 11, false, 3);
                hu.take(new Weapon("Arc", "Commun", 1));
                heros.add(hu);
            }
            case 2 -> {
                Mage m = new Mage(textField.getText(), 34, 8, false, 3, 65);
                m.take(new Weapon("Baguette d'apprenti", "Commun", 1));
                heros.add(m);
            }
            case 3 -> {
                Healer h = new Healer(textField.getText(), 36, 7, false, 2, 55);
                h.take(new Weapon("Bracelet de renforcement", "Commun", 1));
                heros.add(h);
            }
        }


    }

    @FXML
    void onPlayButtonClick(MouseEvent event)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
        HelloApplication.currentStage.setScene(scene);
        HelloApplication.currentStage.show();
        //Game.main();
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
