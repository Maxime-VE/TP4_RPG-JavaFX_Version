package com.example.tp4_rpg_javafx_version.isep.rpg;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Warrior extends Hero{

    public Warrior(String n, int h, int d, boolean def, int r) {
        super(n, h, d, def, r);
    }

    @Override
    public void fight(Combattant combattant) {
        System.out.println(getName() + " lance une attaque !");
        System.out.println(getName() + " inflige " + degatTotal + " points de degat a " + combattant.getName());
        combattant.loose(degatTotal);
    }
    public void sayAction() {
        System.out.println("""
                1- Attaque\s
                2- Attaque Speciale (Chance d'infliger une attaque entre 0,5 et 1,5 fois plus forte que Attaque)\s
                3- Protection\s
                4- Objet""");
    }


    public void sayUpgrade() {
        System.out.println("Veuillez choisir la recompense de " + getName());
        userDelay();
        actualStatus();
        System.out.println("""
                1- Amelioration des degats\s
                2- Amelioration de l'efficacite de l'attaque speciale\s
                3- Amelioration de la defense\s
                4- Amelioration de l'efficacite des objets""");
        Scanner scanChoix = new Scanner(System.in);
        int choix = scanChoix.nextInt();
        switch (choix) {
            case 1:
                degat += 3;
                degatTotal = degat + currentWeaponList.get(0).getDamagePoints();
                System.out.println(getName() + " se sent plus fort !");
                break;
            case 2:
                degatSpecial += 4;
                System.out.println(getName() + " maitrise mieux son attaque speciale !");
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
        }

    }

    @Override
    public void actualStatus() {
        System.out.println(getName() + " : " + getHealthPoint() + " PV  ,  " + degatTotal + " ATK  ,  " + getResistance() + " DEF");
    }

    public void protection() {
        System.out.println(getName() + " se protege !");
        isProtected = true;
    }

    public void special(Combattant combattant) {
        Random random = new Random();
        float randomForce = random.nextFloat();
        randomForce += 0.5;
        System.out.println(getName() + " lance une attaque speciale !");
        int attack = (int) ((degatTotal*randomForce)+degatSpecial);
        System.out.println(getName() + " inflige " + attack + " points de degat a " + combattant.getName());
        combattant.loose(attack);
    }

    // Implémentation de la méthode abstraite "take" par le Warrior :
    //   Le guerrier ne peut utiliser que les objets de type "Weapon"
    @Override
    public void take(Item item) {
        if (item instanceof Weapon) {
            weapon = (Weapon) item;
            System.out.println(getName() + " se voit confier l'arme " + item.getName() + " (+" + ((Weapon) item).getDamagePoints() + " degats)");
            degatTotal = getDegat() + ((Weapon) item).getDamagePoints();
            currentWeaponList.add(weapon);
        } else {
            System.out.println("Oups ! " + item.getName() + " ne convient pas aux Warrior !");
        }
    }

    @Override
    public void changeWeapon(Weapon item) {
        System.out.println(getName() + " recupere " + item.getName()+ " (+" + ((Weapon) item).getDamagePoints() + " degats)");
        if (currentWeaponList.size() == 0) {
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
    public int degatSpecial = 0;
    private int bonusVie = 0;
    private Weapon weapon;
}
