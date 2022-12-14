package com.example.tp4_rpg_javafx_version.isep.rpg;

import java.util.Scanner;

public class Food extends Consumable {
    public Food(String name, String etat) {
        super(name, etat);
    }

    public int compteurRagout = 0;
    public int compteurBento = 1;
    public int compteurNukaCola = 3;

    public int puissanceRagout = 30;
    public int puissanceBento = 20;
    public int puissanceNukaCola = 10;


    public void rechargeNukaCola(int f) {
        if (f == 0 ){
            return;
        }
        compteurNukaCola += f;
        System.out.println("Vous récupérez " + f + " Nuka-Cola ! ");
        userDelay();}
    public void rechargeRagout(int f) {
        if (f == 0 ){
            return;
        }
        compteurRagout += f ;
        System.out.println("Vous récupérez " + f + " Ragout ! ");
        userDelay();}
    public void rechargeBento(int f) {
        if (f == 0 ){
            return;
        }
        compteurBento += f ;
        System.out.println("Vous récupérez " + f + " Bento ! ");
        userDelay();}



    public void useBento(Combattant combattant) {
        System.out.println(combattant.getName() + " utilise un Bento +" + puissanceBento + " PV et récupère de la vie");
        compteurBento -=1;
        combattant.loose(-(puissanceBento+combattant.soinBonus));
    }
    public void useNukaCola(Combattant combattant) {
        System.out.println(combattant.getName() + " utilise un NukaCola +" + puissanceNukaCola + " PV et récupère de la vie");
        compteurNukaCola -=1;
        combattant.loose(-(puissanceNukaCola+combattant.soinBonus));
    }
    public void useRagout(Combattant combattant) {
        System.out.println(combattant.getName() + " utilise un Ragoût +" + puissanceRagout + " PV et récupère de la vie");
        compteurRagout -=1;
        combattant.loose(-(puissanceRagout+combattant.soinBonus));
    }

    private static void userDelay() {
        System.out.println("\n" +
                "v       PRESS ENTER TO SKIP");
        Scanner scan = new Scanner(System.in);
        String delay = scan.nextLine();
    }

}