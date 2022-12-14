package com.example.tp4_rpg_javafx_version.isep.rpg;

import java.util.Scanner;

public class Potion extends Consumable {
    public Potion(String name, String etat) {
        super(name, etat);
    }


    public int compteurMaxiPotion = 1;
    public int compteurPotion = 2;
    public int compteurMiniPotion = 1;

    public int puissanceMaxiPotion = 30;
    public int puissancePotion = 20;
    public int puissanceMiniPotion = 10;



    public void rechargeMiniPotion(int p) {
        if (p == 0 ){
            return;
        }
        compteurMiniPotion += p ;
        System.out.println("Vous récupérez " + p + " Mini Potion ! ");
        userDelay();}
    public void rechargeMaxiPotion(int p) {
        if (p == 0 ){
            return;
        }
        compteurMaxiPotion += p ;
        System.out.println("Vous récupérez " + p + " Maxi Potion ! ");
        userDelay();}
    public void rechargePotion(int p) {
        if (p == 0 ){
            return;
        }
        compteurPotion += p ;
        System.out.println("Vous récupérez " + p + " Potion ! ");
        userDelay();}


    public void usePotion(SpellCaster combattant) {
            System.out.println(combattant.getName() + " utilise une Potion +20 Mana et récupère du mana");
            compteurPotion -=1;
            combattant.setMana(puissancePotion+combattant.soinBonus);
    }

    public void useMiniPotion(SpellCaster combattant) {
        System.out.println(combattant.getName() + " utilise une mini Potion +10 Mana et récupère du mana");
        compteurMiniPotion -=1;
        combattant.setMana(puissanceMiniPotion+combattant.soinBonus);
    }

    public void useMaxiPotion(SpellCaster combattant) {
        System.out.println(combattant.getName() + " utilise une maxi Potion +30 Mana et récupère du mana");
        compteurMaxiPotion -=1;
        combattant.setMana(puissanceMaxiPotion+combattant.soinBonus);
    }

    private static void userDelay() {
        System.out.println("\n" +
                "v       PRESS ENTER TO SKIP");
        Scanner scan = new Scanner(System.in);
        String delay = scan.nextLine();
    }
}