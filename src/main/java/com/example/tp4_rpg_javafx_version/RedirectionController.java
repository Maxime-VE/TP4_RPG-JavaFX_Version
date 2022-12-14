package com.example.tp4_rpg_javafx_version;

import com.example.tp4_rpg_javafx_version.isep.rpg.Game;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class RedirectionController {

    @FXML
    void gameLaunch(MouseEvent event) {
        Game.game();
    }

}