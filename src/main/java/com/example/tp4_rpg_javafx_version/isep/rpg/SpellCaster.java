package com.example.tp4_rpg_javafx_version.isep.rpg;

public abstract class SpellCaster extends Hero {

    public SpellCaster(String n, int h, int d, boolean def, int r, int m) {

        super(n, h, d, def, r);
        mana = m;
    }

    public int mana;

    public int getMana() {
        return mana;
    }

    public void setMana(int recharge) {
        mana += recharge;
    }

}