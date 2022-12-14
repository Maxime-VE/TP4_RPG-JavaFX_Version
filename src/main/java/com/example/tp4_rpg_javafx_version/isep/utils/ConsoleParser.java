package com.example.tp4_rpg_javafx_version.isep.utils;

import com.example.tp4_rpg_javafx_version.HelloController;
import com.example.tp4_rpg_javafx_version.MainController;

public class ConsoleParser {

    public static void displayMethod(String s){
        if (HelloController.displayMode == 0) {
            System.out.println(s);
        } else {
            MainController.displayprintln(s);
        }
    }
}
