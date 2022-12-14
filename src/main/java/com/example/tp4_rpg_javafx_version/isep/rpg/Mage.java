package com.example.tp4_rpg_javafx_version.isep.rpg;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Mage extends SpellCaster{

    public Mage(String n, int h, int d, boolean def, int r, int m) {super(n, h, d, def, r, m);}

    public void special(Combattant combattant) {
        if (getMana() < coutSort){
            System.out.println(getName() + " n'a plus assez de mana, " + getName() + " médite pendant ce tour et reçoit +5 Mana");
            setMana(5);
        }else{
            System.out.println(getName() + " lance un sort !");
            int attack = (int) ((degatTotal*2.4)+degatSpecial);
            System.out.println(getName() + " inflige " + attack + " points de degat à " + combattant.getName());
            combattant.loose(attack);
            setMana(-coutSort);
            System.out.println("Il reste " + getMana() + " Mana à " + getName());
        }


    }

    @Override
    public void fight(Combattant combattant) {
        System.out.println(getName() + " lance une attaque !");
        System.out.println(getName() + " inflige " + degatTotal + " points de degat à " + combattant.getName());
        combattant.loose(degatTotal);
    }
    public void sayAction() {
        System.out.println("1- Attaque \n" +
                "2- Sortilège (coûte " + coutSort +  " Mana) \n" +
                "3- Protection \n" +
                "4- Objet\n" +
                "Mana actuel : " + getMana());
    }
    @Override
    public void actualStatus() {
        System.out.println(getName() + " : " + getHealthPoint() + " PV  ,  " + degatTotal + " ATK  ,  " + getResistance() + " DEF  ,  " + getMana() + " Mana" );
    }

    public void sayUpgrade() {
        System.out.println("Veuillez choisir la récompense de " + getName());
        userDelay();
        actualStatus();
        System.out.println("""
                1- Amélioration des dégats\s
                2- Amélioration de l'efficacité de l'attaque spéciale\s
                3- Amélioration de la défense\s
                4- Amélioration de l'efficacité des objets\s
                5- Réduction du coup en mana de l'attaque spéciale""");
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
                System.out.println(getName() + " maîtrise mieux son attaque spéciale !");
                break;
            case 3:
                addResistance(2);
                System.out.println(getName() + " se sent plus résistant !");
                break;
            case 4:
                bonusVie += 2;
                soinBonus = bonusVie;
                System.out.println(getName() + " est plus réceptif aux effets des objets !");
                break;
            case 5:
                coutSort -= 2;
                System.out.println(getName() + " demande maintenant moins de mana pour effectuer \"Sortilège\" !");
                break;
        }

    }

    public void protection() {
        System.out.println(getName() + " se protège !");
        isProtected = true;
    }

    // Implémentation de la méthode abstraite "take" par le Mage :
    //   Le guerrier ne peut utiliser que les objets de type "Weapon"
    @Override
    public void take(Item item) {
        if (item instanceof Weapon) {
            weapon = (Weapon) item;
            System.out.println(getName() + " se voit confier l'arme " + item.getName() + " (+" + ((Weapon) item).getDamagePoints() + " dégats)");
            degatTotal = getDegat() + ((Weapon) item).getDamagePoints();
            currentWeaponList.add(weapon);
        } else {
            System.out.println("Oups ! " + item.getName() + " ne convient pas aux Mage !");
        }
    }

    @Override
    public void changeWeapon(Weapon item) {
        System.out.println(getName() + " récupère " + item.getName()+ " (+" + ((Weapon) item).getDamagePoints() + " dégats)");
        if (currentWeaponList.size() == 0) {
            take(item);
        } else {
            Weapon currentWeapon = currentWeaponList.get(0);
            System.out.println("Mais " + getName() + " possède déjà " + currentWeapon.getName() + " (+" + currentWeapon.getDamagePoints() + " dégats)" );
            System.out.println("Souhaitez-vous changer l'équipement de " + getName() + " ? [y/n]");
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
    ArrayList<Weapon> currentWeaponList = new ArrayList<>();
    public int degatTotal = degat;
    private Weapon weapon;
    int coutSort = 25;
    public int bonusVie = 0;
    public int degatSpecial = 0;
}
