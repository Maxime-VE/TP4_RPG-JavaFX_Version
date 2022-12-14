package com.example.tp4_rpg_javafx_version.isep.rpg;

public abstract class Ennemy extends Combattant{
    public Ennemy(String n, int h, int d, boolean def, int r, String t) {

        super(n, h, d, def, r);
        type = t;
    }
    private String type;
    public String getType(){ return type;}
}
