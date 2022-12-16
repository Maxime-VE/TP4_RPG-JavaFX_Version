package com.example.tp4_rpg_javafx_version.isep.rpg;

public class Weapon extends Item {

    public Weapon(String name, String description, int damagePoints) {
        super(name, description);
        this.damagePoints = damagePoints;
    }

    public int getDamagePoints() {
        return damagePoints;
    }


    private int damagePoints;
}