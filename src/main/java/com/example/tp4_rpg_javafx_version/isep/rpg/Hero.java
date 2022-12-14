package com.example.tp4_rpg_javafx_version.isep.rpg;

public abstract class Hero extends Combattant{
    private String name;
    private int healthPoint;

    public Hero(String n, int h, int d, boolean def, int r) {super(n, h, d, def, r);    }


    // Abstrait car n'importe quel hero peut prendre un objet mais son
    // utilisation dépend du type du héro (Epée -> Warrior  Baton -> Mage)
    public abstract void take(Item item);

    public abstract void changeWeapon(Weapon item);


}
