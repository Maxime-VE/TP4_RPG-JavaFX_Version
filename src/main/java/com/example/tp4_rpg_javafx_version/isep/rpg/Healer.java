package com.example.tp4_rpg_javafx_version.isep.rpg;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Healer extends SpellCaster{
    public Healer(String n, int h, int d, boolean def, int r, int m) {super(n, h, d, def, r, m);}


    public void special(Combattant combattant) {
        if (getMana() < coutSoin){
            System.out.println(getName() + " n'a plus assez de mana, " + getName() + " medite pendant ce tour et reçoit +5 Mana");
            setMana(5);
        }else{
            int soin = heal-degatSpecial;
            System.out.println(combattant.getName() + " reçoit +" + Math.abs(soin) + "PV");
            combattant.loose(soin);
            setMana(-coutSoin);
            System.out.println("Il reste " + getMana() + " Mana a " + getName());
        }
    }


    @Override
    public void fight(Combattant combattant) {
        System.out.println(getName() + " lance une attaque !");
        System.out.println(getName() + " inflige " + degatTotal + " points de degat a " + combattant.getName());
        combattant.loose(degatTotal);
    }

    public void sayAction() {
        int soin = heal-degatSpecial;
        System.out.println("1- Attaque \n" +
                "2- Soin : +" + (int) Math.abs(soin) + "PV (coute "+ coutSoin + " Mana)\n" +
                "3- Protection \n" +
                "4- Objet \n" +
                "Mana actuel : " + mana);
    }
    @Override
    public void actualStatus() {
        System.out.println(getName() + " : " + getHealthPoint() + " PV  ,  " + degatTotal + " ATK  ,  " + getResistance() + " DEF  ,  " + getMana() + " Mana" );
    }
    public void sayUpgrade() {
        System.out.println("Veuillez choisir la recompense de " + getName());
        userDelay();
        actualStatus();
        System.out.println("""
                1- Amelioration des degats\s
                2- Amelioration de l'efficacite de la capacite speciale\s
                3- Amelioration de la defense\s
                4- Amelioration de l'efficacite des objets\s
                5- Reduction du coup en mana de la capacite speciale""");
        Scanner scanChoix = new Scanner(System.in);
        int choix = scanChoix.nextInt();
        switch (choix) {
            case 1:
                degat += 2;
                degatTotal = degat + currentWeaponList.get(0).getDamagePoints();
                System.out.println(getName() + " se sent plus fort !");
                break;
            case 2:
                degatSpecial += 3;
                System.out.println(getName() + " maitrise mieux sa capacite speciale !");
                break;
            case 3:
                addResistance(2);
                System.out.println(getName() + " se sent plus resistant !");
                break;
            case 4:
                bonusVie += 2;
                soinBonus = bonusVie;
                System.out.println(getName() + " est plus receptif aux effets des objets !");
                break;
            case 5:
                coutSoin -= 2;
                System.out.println(getName() + " demande maintenant moins de mana pour effectuer \"Soin\" !");
                break;
        }

    }

    public void protection() {
        System.out.println(getName() + " se protege !");
        isProtected = true;
    }

    @Override
    public void take(Item item) {
        if (item instanceof Weapon) {
            weapon = (Weapon) item;
            System.out.println(getName() + " se voit confier l'arme " + item.getName() + " (+" + ((Weapon) item).getDamagePoints() + " degats)");
            degatTotal = getDegat() + ((Weapon) item).getDamagePoints();
            currentWeaponList.add(weapon);
        } else {
            System.out.println("Oups ! " + item.getName() + " ne convient pas aux Healer !");
        }
    }

    @Override
    public void changeWeapon(Weapon item) {
        System.out.println(getName() + " recupere " + item.getName()+ " (+" + ((Weapon) item).getDamagePoints() + " degats)");
        if (currentWeaponList.size()== 0) {
            take(item);
        } else {
            Weapon currentWeapon = currentWeaponList.get(0);
            System.out.println("Mais " + getName() + " possede deja " + currentWeapon.getName() + " (+" + currentWeapon.getDamagePoints() + " degats)" );
            System.out.println("Souhaitez-vous changer l'equipement de " + getName() + " ? [y/n]");
            Scanner scanChoixWeapon = new Scanner(System.in);
            String choixWeapon = scanChoixWeapon.nextLine();
            if (Objects.equals(choixWeapon, "y")) {
                currentWeaponList.remove(0);
                take(item);
            } else if (Objects.equals(choixWeapon, "n")) {
                System.out.println(getName() + " laisse " + item.getName());
                userDelay();
            }else{
                changeWeapon(item);
            }
        }
    }
    private static void userDelay() {
        System.out.println("\n" +
                "v       PRESS ENTER TO SKIP");
        Scanner scan = new Scanner(System.in);
        String delay = scan.nextLine();
    }
    public ArrayList<Weapon> currentWeaponList = new ArrayList<>();
    public int degatTotal = degat;
    private Weapon weapon;
    int heal = -20;
    public int coutSoin = 25;
    public int bonusVie = 0;
    public int degatSpecial = 0;
}
