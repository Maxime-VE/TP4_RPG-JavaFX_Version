package com.example.tp4_rpg_javafx_version.isep.rpg;

public class Goblin extends Ennemy{
    public Goblin(String n, int h, int d, boolean def, int r, String t) {

        super(n, h, d, def, r, t);
    }

    @Override
    public void fight(Combattant combattant) {
        System.out.println(getName() + " lance une attaque !");
        int degatInfliges = (getDegat()-combattant.getResistance());
        if (degatInfliges < 0) {
            degatInfliges=0;
        }
        if (combattant.getProtection()) {
            System.out.println(combattant.getName() + " est protege !");
            int attack = (int) (degatInfliges/1.6);
            combattant.loose(attack);
            System.out.println("Il inflige " + attack + " points de degat");
        }else {
            combattant.loose(degatInfliges);
            System.out.println("Il inflige " + degatInfliges + " points de degat");
        }
    }
    public void sayAction() {
        System.out.print("");
    }
    public void sayUpgrade() {
        System.out.print("");
    }
    public void special(Combattant combattant) {
        System.out.print("");
    }

    @Override
    public void protection() {    }
    @Override
    public void actualStatus() {    }

}