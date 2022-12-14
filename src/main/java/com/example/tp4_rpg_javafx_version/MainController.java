package com.example.tp4_rpg_javafx_version;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

import com.example.tp4_rpg_javafx_version.isep.rpg.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import static com.example.tp4_rpg_javafx_version.CharSelectionController.heros;


public class MainController {

    @FXML
    private TextArea console;
    @FXML
    private TextField textField;
    public static PrintStream ps ;

    public void initialize() {
        ps = new PrintStream(new Console(console)) ;
    }


    //VARIABLE INITIALES
    Potion potion = new Potion("Stock de potions", "plein");
    Food food = new Food("Stock de nourriture", "plein");
    static ArrayList<Ennemy> manche1 = new ArrayList<>();
    static ArrayList<Ennemy> manche2 = new ArrayList<>();
    static ArrayList<Ennemy> manche3 = new ArrayList<>();
    static ArrayList<Ennemy> manche4 = new ArrayList<>();
    static ArrayList<Ennemy> manche5 = new ArrayList<>();
    static ArrayList<ArrayList<Ennemy>> enemiesList = new ArrayList<>();
    ArrayList<Ennemy> enemies;
    int idHero = 0;
    int idEnemy = 0;
    int manche = 0;
    int choixAction;
    int typeConsommable;
    Combattant cibleWeapons;
    Weapon weapons;

    public static void displayprintln(String s) {
        System.setOut(ps);
        System.setErr(ps);
        System.out.println(s);
    }
    public static void display(String s) {
        System.setOut(ps);
        System.setErr(ps);
        System.out.print(s);
    }


    public class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char)b));
        }

    }

    public int etat = 0;    //etat: [0=Démarrage, 1=Début nouvelle manche, 2=Attaque Ennemie, 3=Attaque Alliées,
                                //   4=Choix Action, 5=Attaque Normale, 6=Attaque Spe, 7=Accès Inventaire, 8=cibleConsommable
                                //   9=Récompenses Automatiques, 10=]
    @FXML
    void onActionClick(MouseEvent event) throws IOException {
//        if(enemiesList.size() == 0){
//            On part sur la fenêtre victoire
//        }
        switch(etat) {
            case 0:
                int nombreEnnemy = (int) ((heros.size() / 2) + 1);
                Game.initialisationEnnemy(nombreEnnemy, enemiesList, manche1, manche2, manche3, manche4, manche5);
                displayprintln(" Debut de la partie ");
                etat = 1;
                break;
            case 1:
                enemies = enemiesList.get(0);
                manche += 1; // Compteur de manche
                displayprintln("C'est le debut de la manche numero : " + manche + " et vous allez affronter ");
                if (enemies.size() != 1) {
                    for (Ennemy e : enemies) {
                        display(e.getName() + ", ");
                    }
                    displayprintln("");
                } else {
                    displayprintln(enemies.get(idEnemy).getName());
                }

                System.out.println("Il s'agit d'un ennemi de type :  " + enemies.get(idEnemy).getType());
                Game.displayStatus(heros, enemies);
                Random random = new Random();
                int int_random = random.nextInt(2);
                if (int_random == 1) {
                    etat = 2;
                } else {
                    etat = 3;
                    System.out.println("\n" + "C'est au tour des héros d'attaquer");
                }
                break;
            case 2:
                displayprintln(" ");
                if (enemies.size() == 0) {
                    enemiesList.remove(0);
                    idHero = 0;
                    if (enemiesList.size() == 0 ){
                        //TODO ramener vers écran de victoire
                        break;
                    }
                    System.out.println("Vous tombez sur un tresor cache proche du lieu de votre precedent combat ");
                    //TODO ramener vers les récompenses
                    etat=1;
                    break;
                }
                Game.attaqueEnnemie(heros, enemies);
                if (heros.size() == 0) {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("defaite-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
                    HelloApplication.currentStage.setScene(scene);
                    HelloApplication.currentStage.show();
                }
                Game.displayStatus(heros, enemies);
                Game.finProtection(heros);
                etat = 3;
                System.out.println("\n" + "C'est au tour des heros d'attaquer");
                break;
            case 3:
                if (enemies.size() == 0) {
                    enemiesList.remove(0);
                    idHero = 0;
                    if (enemiesList.size() == 0 ){
                        //TODO ramener vers écran de victoire
                        break;
                    }
                    System.out.println("Vous tombez sur un tresor cache proche du lieu de votre precedent combat ");
                    etat=9;
                    //TODO ramener vers les récompenses
                    break;
                }
                Combattant goodOne = heros.get(idHero);
                System.out.println(" Que va faire " + goodOne.getName() + "?");
                goodOne.sayAction();
                displayprintln("");
                etat = 4;
                break;
            case 4:
                goodOne = heros.get(idHero);
                choixAction = Integer.parseInt(textField.getText());
                System.out.println("Joueur : " + choixAction + "\n");

                switch (choixAction) {
                    case 1:
                        int compteurId = 1;
                        System.out.println("Qui souhaitez-vous attaquer ?");
                        for (Combattant combattant : enemies) {
                            System.out.println(compteurId + "- " + combattant.getName() + " : " + combattant.getHealthPoint() + " PV");
                            compteurId++;
                        }
                        displayprintln("");
                        etat = 5;
                        break;
                    case 2:
                        if (goodOne instanceof Healer) {
                            System.out.println("Qui est-ce que " + goodOne.getName() + " souhaite soigner ?");
                            int compteurid = 1;
                            for (Combattant ally : heros) {
                                System.out.println(compteurid + "- " + ally.getName() + " : " + ally.getHealthPoint() + "PV");
                                compteurid++;
                            }
                            displayprintln("");
                        } else {
                            compteurId = 1;
                            System.out.println("Qui souhaitez-vous attaquer ?");
                            for (Combattant combattant : enemies) {
                                System.out.println(compteurId + "- " + combattant.getName() + " : " + combattant.getHealthPoint() + " PV");
                                compteurId++;
                            }
                            displayprintln("");
                        }
                        etat = 6;
                        break;
                    case 3:
                        goodOne.protection();
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        break;
                    case 4:
                        System.out.println("Quel objet souhaitez-vous consommer ? \n" +
                                "0- Retour \n" +
                                "Nourriture : \n" +
                                "1- Nuka-Cola : + " + food.puissanceNukaCola + " PV (" + food.compteurNukaCola + " en stock) \n" +
                                "2- Bento : + " + food.puissanceBento + " PV (" + food.compteurBento + " en stock) \n" +
                                "3- Ragoût : + " + food.puissanceRagout + " PV (" + food.compteurRagout + " en stock) \n" +
                                "Potion : (Uniquement pour des utilisateurs de sort : Mage & Healer) \n" +
                                "4- Mini Potion : +" + potion.puissanceMiniPotion + "Mana (" + potion.compteurMiniPotion + " en stock)\n" +
                                "5- Potion : +" + potion.puissancePotion + "Mana (" + potion.compteurPotion + " en stock) \n" +
                                "6- Maxi Potion : +" + potion.puissanceMaxiPotion + "Mana (" + potion.compteurMaxiPotion + " en stock)");
                        etat = 7;
                        break;
                }
                break;
            case 5:
                goodOne = heros.get(idHero);
                int choixCible = Integer.parseInt(textField.getText());
                System.out.println("Joueur : " + choixCible + "\n");
                Ennemy ennemy = enemies.get((choixCible - 1));                        //ATTAQUE SIMPLE SUR LA CIBLE
                goodOne.fight(ennemy);
                if (ennemy.getHealthPoint() <= 0) {
                    enemies.remove((choixCible - 1));
                    System.out.println("\n" + "Les Héros ont vaincu " + ennemy.getName() + " !");
                }
                if (enemies.size() == 0) {
                    etat=3;
                    break;
                }
                Game.displayStatus(heros, enemies);
                if (idHero == (heros.size() - 1)) {
                    idHero = 0;
                    etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                } else {
                    idHero++;
                    etat = 3;
                }
                break;

            case 6:
                goodOne = heros.get(idHero);
                choixCible = Integer.parseInt(textField.getText());
                System.out.println("Joueur : " + choixCible + "\n");
                if (goodOne instanceof Healer) {
                    goodOne.special(heros.get((choixCible - 1)));
                } else {                                                                  //ATTAQUE SPECIALE SUR LA CIBLE

                    ennemy = enemies.get((choixCible - 1));
                    goodOne.special(ennemy);
                    if (ennemy.getHealthPoint() <= 0) {
                        enemies.remove((choixCible - 1));
                        System.out.println("\n" + " Les Héros ont vaincu " + ennemy.getName() + " !");
                    }
                }
                if (enemies.size() == 0) {
                    etat=3;
                    break;
                }
                Game.displayStatus(heros, enemies);
                if (idHero == (heros.size() - 1)) {
                    idHero = 0;
                    etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                } else {
                    idHero++;
                    etat = 3;
                }
                break;
            case 7:
                goodOne = heros.get(idHero);
                int choixObjet = Integer.parseInt(textField.getText());
                System.out.println("Joueur : " + choixObjet + "\n");
                switch (choixObjet) {
                    case 0:
                        etat=3;
                        break;
                    case 1:
                        if (food.compteurNukaCola > 0) {
                            System.out.println("\n" +
                                    "Sur qui est-ce que " + goodOne.getName() + " souhaite utiliser l'objet Nuka-Cola ?");
                            int compteurid = 1;
                            for (Combattant ally : heros) {
                                System.out.println(compteurid + "- " + ally.getName() + " : " + ally.getHealthPoint() + " PV");
                                compteurid++;
                            }
                            typeConsommable=1;
                            etat=8;
                        } else {
                            System.out.println("Vous n'avez plus de Nuka-Cola");
                            displayprintln(" ");
                            etat = 3;
                            break;
                        }
                        break;
                    case 2:
                        if (food.compteurBento > 0) {
                            System.out.println("\n" +
                                    "Sur qui est-ce que " + goodOne.getName() + " souhaite utiliser l'objet Bento ?");
                            int compteurid = 1;
                            for (Combattant ally : heros) {
                                System.out.println(compteurid + "- " + ally.getName() + " : " + ally.getHealthPoint() + " PV");
                                compteurid++;
                            }
                            typeConsommable=2;
                            etat=8;
                        } else {
                            System.out.println("Vous n'avez plus de Bento");
                            displayprintln(" ");
                            etat = 3;
                            break;
                        }
                        break;
                    case 3:
                        if (food.compteurRagout > 0) {
                            System.out.println("\n" +
                                    "Sur qui est-ce que " + goodOne.getName() + " souhaite utiliser l'objet Ragout ?");
                            int compteurid = 1;
                            for (Combattant ally : heros) {
                                System.out.println(compteurid + "- " + ally.getName() + " : " + ally.getHealthPoint() + " PV");
                                compteurid++;
                            }
                            typeConsommable=3;
                            etat=8;
                        } else {
                            System.out.println("Vous n'avez plus de Ragout");

                            etat = 3;
                            break;
                        }
                        break;
                    case 4:
                        if(potion.compteurMiniPotion > 0) {
                            System.out.println("\n" +
                                    "Sur qui est-ce que " + goodOne.getName() + " souhaite utiliser l'objet Mini Potion ?");
                            int compteurid = 1;
                            ArrayList<Combattant> ciblePotion = new ArrayList<>();
                            for(Combattant ally :heros) {
                                if (ally instanceof SpellCaster) {
                                    ciblePotion.add(ally);
                                    System.out.println(compteurid + "- " + ally.getName() + " : " + ((SpellCaster) ally).getMana() + " Mana");
                                    compteurid++;
                                }
                            }
                            if (ciblePotion.size() == 0 ) {
                                System.out.println("Aucun héro ne peut recevoir de potion !");
                                displayprintln("");
                                etat=3;
                                break;
                            }
                            typeConsommable=4;
                            etat=8;
                            break;
                        } else{
                            System.out.println("Vous n'avez plus de MiniPotion");
                            displayprintln(" ");
                            etat = 3;
                            break;
                        }
                    case 5:
                        if(potion.compteurPotion > 0) {
                            System.out.println("\n" +
                                    "Sur qui est-ce que " + goodOne.getName() + " souhaite utiliser l'objet Potion ?");
                            int compteurid = 1;
                            ArrayList<Combattant> ciblePotion = new ArrayList<>();
                            for(Combattant ally :heros) {
                                if (ally instanceof SpellCaster) {
                                    ciblePotion.add(ally);
                                    System.out.println(compteurid + "- " + ally.getName() + " : " + ((SpellCaster) ally).getMana() + " Mana");
                                    compteurid++;
                                }
                            }
                            if (ciblePotion.size() == 0 ) {
                                System.out.println("Aucun héro ne peut recevoir de potion !");
                                displayprintln("");
                                etat=3;
                                break;
                            }
                            typeConsommable=5;
                            etat=8;
                            break;
                        } else{
                            System.out.println("Vous n'avez plus de Potion");
                            displayprintln(" ");
                            etat = 3;
                            break;
                        }

                    case 6:
                        if(potion.compteurMaxiPotion > 0) {
                            System.out.println("\n" +
                                    "Sur qui est-ce que " + goodOne.getName() + " souhaite utiliser l'objet Maxi Potion ?");
                            int compteurid = 1;
                            ArrayList<Combattant> ciblePotion = new ArrayList<>();
                            for(Combattant ally :heros) {
                                if (ally instanceof SpellCaster) {
                                    ciblePotion.add(ally);
                                    System.out.println(compteurid + "- " + ally.getName() + " : " + ((SpellCaster) ally).getMana() + " Mana");
                                    compteurid++;
                                }
                            }
                            if (ciblePotion.size() == 0 ) {
                                System.out.println("Aucun héro ne peut recevoir de potion !");
                                displayprintln("");
                                etat=3;
                                break;
                            }
                            typeConsommable=6;
                            etat=8;
                            break;
                        } else{
                            System.out.println("Vous n'avez plus de MaxiPotion");
                            displayprintln(" ");
                            etat = 3;
                            break;
                        }

                }break;
            case 8:
                int choixCibleConsumable = Integer.parseInt(textField.getText());
                System.out.println("Joueur : " + choixCibleConsumable + "\n");
                ArrayList<Combattant> ciblePotion = new ArrayList<>();
                for(Combattant ally :heros) {
                    if (ally instanceof SpellCaster) {
                        ciblePotion.add(ally);
                    }}
                switch (typeConsommable){
                    case 1:
                        food.useNukaCola(heros.get((choixCibleConsumable-1)));
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        break;

                    case 2:
                        food.useBento(heros.get((choixCibleConsumable-1)));
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        break;
                    case 3:
                        food.useRagout(heros.get((choixCibleConsumable-1)));
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        break;
                    case 4:
                        potion.useMiniPotion((SpellCaster) ciblePotion.get((choixCibleConsumable-1)));
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        break;
                    case 5:
                        potion.usePotion((SpellCaster) ciblePotion.get((choixCibleConsumable-1)));
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        break;
                    case 6:
                        potion.useMaxiPotion((SpellCaster) ciblePotion.get((choixCibleConsumable-1)));
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        break;
                }break;
            case 9: //TODO verifier les problèmes de freeze dans la page.
                System.out.println(" ");
                Random randomObjet = new Random();
                int [] heroPresent = {0,0,0,0};
                for (Combattant c : heros){
                    if(c instanceof Warrior){
                        heroPresent[0] = 1;
                    } else if (c instanceof Hunter hunter){
                        heroPresent[1] = 1;
                        int flecheReward = randomObjet.nextInt(4,7);
                        hunter.compteurFleche +=flecheReward;
                        System.out.println(hunter.getName() + " recoit " + flecheReward + " fleches.\n");
                    } else if (c instanceof Mage){
                        heroPresent[2] = 1;
                    } else {
                        heroPresent[3] = 1;
                    }
                }


                //INITIALISATION NOMBRE DE RECOMPENSE

                int maxLvl3Consumable;
                int maxLvl2Consumable;
                int maxLvl1Consumable;
                if (manche <= 2) {
                    maxLvl3Consumable = 1;
                    maxLvl2Consumable = 3;
                    maxLvl1Consumable = 4;
                } else {
                    maxLvl3Consumable = (int) ((heros.size()+manche-2)/2);
                    maxLvl2Consumable = (int) ((heros.size()+manche)/2);
                    maxLvl1Consumable = (int) ((heros.size()+manche+2)/2);
                }

                //RECOMPENSE POTION

                int miniPotionReward = randomObjet.nextInt(1,maxLvl1Consumable);
                potion.compteurMiniPotion += miniPotionReward;
                System.out.println("Vous avez recu " + miniPotionReward + " MiniPotions.\n" +
                        "");
                int PotionReward = randomObjet.nextInt(0,maxLvl2Consumable);
                if (PotionReward != 0){
                    potion.compteurPotion += PotionReward;
                    System.out.println("Vous avez recu " + PotionReward + " Potions.\n" +
                            "");
                }
                int maxiPotionReward = randomObjet.nextInt(0,maxLvl3Consumable);
                if (maxiPotionReward != 0) {
                    potion.compteurMaxiPotion += maxiPotionReward;
                    System.out.println("Vous avez recu " + maxiPotionReward + " MaxiPotions.\n" +
                            "");
                }

                //RECOMPENSE FOOD
                int nukaColaReward = randomObjet.nextInt(1,maxLvl1Consumable);
                food.compteurNukaCola += nukaColaReward;
                System.out.println("Vous avez recu " + nukaColaReward + " Nuka-Cola.\n" +
                        "");
                int bentoReward = randomObjet.nextInt(0,maxLvl2Consumable);
                if (bentoReward != 0){
                    food.compteurBento += bentoReward;
                    System.out.println("Vous avez recu " + bentoReward + " Bento.\n" +
                            "");
                }
                int ragoutReward = randomObjet.nextInt(0,maxLvl3Consumable);
                if (ragoutReward != 0){
                    food.compteurRagout += ragoutReward;
                    System.out.println("Vous avez recu " + ragoutReward + " Ragout.\n" +
                            "");
                }


                System.out.println("\n" +
                        "Vous venez de trouver une arme au sol, a qui souhaitez-vous la donner ?");
                int compteurid = 1;
                for (Combattant ally : heros) {
                    System.out.println(compteurid + "- " + ally.getName() + " : " + ally.getHealthPoint() + " PV  " + ally.getDegat() + " ATK" );
                    compteurid++;
                }
                etat=10;
                break;
            case 10:
                int choixCibleWeapons = Integer.parseInt(textField.getText());
                System.out.println("Joueur : " + choixCibleWeapons +"\n");
                cibleWeapons = heros.get(choixCibleWeapons-1);
                randomObjet = new Random();
                if(cibleWeapons instanceof Warrior){
                    String weaponName;
                    int degatCommonWeapon;
                    String description;
                    int rareteWeapon = randomObjet.nextInt(101);
                    if (rareteWeapon <= 46) {
                        weaponName = Game.nommageWeapon(Game.weaponList[0], 0);
                        degatCommonWeapon = randomObjet.nextInt(4,8);
                        description = "Commun";
                    } else if (rareteWeapon > 46 && rareteWeapon <= 75) {
                        weaponName = Game.nommageWeapon(Game.weaponList[0], 1);
                        degatCommonWeapon = randomObjet.nextInt(6, 10);
                        description = "Rare";
                    } else if (rareteWeapon > 75  && rareteWeapon <= 92) {
                        weaponName = Game.nommageWeapon(Game.weaponList[0], 2);
                        degatCommonWeapon = randomObjet.nextInt(9, 13);
                        description = "Epique";
                    } else {
                        weaponName = Game.nommageWeapon(Game.weaponList[0], 3);
                        degatCommonWeapon = randomObjet.nextInt(13, 19);
                        description = "Legendaire";
                    }
                    weapons = new Weapon(weaponName, description, degatCommonWeapon);
                    System.out.println("Vous venez de trouver " + weapons.getName() + " +" + weapons.getDamagePoints() + " ATK ("+ weapons.getDescription()+ ") !\n" +
                            "Mais vous possedez deja " + ((Warrior) cibleWeapons).currentWeaponList.get(0).getName() + " (" +  ((Warrior) cibleWeapons).currentWeaponList.get(0).getDamagePoints() + " ATK )\n" +
                            "Souhaitez-vous changer d'arme ? [y/n]");
                    etat=11;
                    break;

                } else if (cibleWeapons instanceof Hunter) {
                    String weaponName;
                    int degatCommonWeapon;
                    String description;
                    int rareteWeapon = randomObjet.nextInt(101);
                    if (rareteWeapon <= 46) {
                        weaponName = Game.nommageWeapon(Game.weaponList[1], 0);
                        degatCommonWeapon = randomObjet.nextInt(4,8);
                        description = "Commun";
                    } else if (rareteWeapon > 46 && rareteWeapon <= 75) {
                        weaponName = Game.nommageWeapon(Game.weaponList[1], 1);
                        degatCommonWeapon = randomObjet.nextInt(6, 10);
                        description = "Rare";
                    } else if (rareteWeapon > 75  && rareteWeapon <= 92) {
                        weaponName = Game.nommageWeapon(Game.weaponList[1], 2);
                        degatCommonWeapon = randomObjet.nextInt(9, 13);
                        description = "Epique";
                    } else {
                        weaponName = Game.nommageWeapon(Game.weaponList[1], 3);
                        degatCommonWeapon = randomObjet.nextInt(13, 19);
                        description = "Legendaire";
                    }
                    weapons = new Weapon(weaponName, description, degatCommonWeapon);
                    System.out.println("Vous venez de trouver " + weapons.getName() + " +" + weapons.getDamagePoints() + " ATK ("+ weapons.getDescription()+ ") !\n" +
                            "Mais vous possedez deja " + ((Hunter) cibleWeapons).currentWeaponList.get(0).getName() + " (" +  ((Hunter) cibleWeapons).currentWeaponList.get(0).getDamagePoints() + " ATK )\n" +
                            "Souhaitez-vous changer d'arme ? [y/n]");
                    etat=11;
                    break;

                }else if (cibleWeapons instanceof Mage) {
                    String weaponName;
                    int degatCommonWeapon;
                    String description;
                    int rareteWeapon = randomObjet.nextInt(101);
                    if (rareteWeapon <= 46) {
                        weaponName = Game.nommageWeapon(Game.weaponList[2], 0);
                        degatCommonWeapon = randomObjet.nextInt(4,8);
                        description = "Commun";
                    } else if (rareteWeapon > 46 && rareteWeapon <= 75) {
                        weaponName = Game.nommageWeapon(Game.weaponList[2], 1);
                        degatCommonWeapon = randomObjet.nextInt(6, 10);
                        description = "Rare";
                    } else if (rareteWeapon > 75  && rareteWeapon <= 92) {
                        weaponName = Game.nommageWeapon(Game.weaponList[2], 2);
                        degatCommonWeapon = randomObjet.nextInt(9, 13);
                        description = "Epique";
                    } else {
                        weaponName = Game.nommageWeapon(Game.weaponList[2], 3);
                        degatCommonWeapon = randomObjet.nextInt(13, 19);
                        description = "Legendaire";
                    }
                    weapons = new Weapon(weaponName, description, degatCommonWeapon);
                    System.out.println("Vous venez de trouver " + weapons.getName() + " +" + weapons.getDamagePoints() + " ATK ("+ weapons.getDescription()+ ") !\n" +
                            "Mais vous possedez deja " + ((Mage) cibleWeapons).currentWeaponList.get(0).getName() + " (" +  ((Mage) cibleWeapons).currentWeaponList.get(0).getDamagePoints() + " ATK )\n" +
                            "Souhaitez-vous changer d'arme ? [y/n]");
                    etat=11;
                    break;

                }else if (cibleWeapons instanceof Healer) {
                    String weaponName;
                    int degatCommonWeapon;
                    String description;
                    int rareteWeapon = randomObjet.nextInt(101);
                    if (rareteWeapon <= 46) {
                        weaponName = Game.nommageWeapon(Game.weaponList[0], 0);
                        degatCommonWeapon = randomObjet.nextInt(4,8);
                        description = "Commun";
                    } else if (rareteWeapon > 46 && rareteWeapon <= 75) {
                        weaponName = Game.nommageWeapon(Game.weaponList[3], 1);
                        degatCommonWeapon = randomObjet.nextInt(6, 10);
                        description = "Rare";
                    } else if (rareteWeapon > 75  && rareteWeapon <= 92) {
                        weaponName = Game.nommageWeapon(Game.weaponList[3], 2);
                        degatCommonWeapon = randomObjet.nextInt(9, 13);
                        description = "Epique";
                    } else {
                        weaponName = Game.nommageWeapon(Game.weaponList[3], 3);
                        degatCommonWeapon = randomObjet.nextInt(13, 19);
                        description = "Legendaire";
                    }
                    weapons = new Weapon(weaponName, description, degatCommonWeapon);
                    System.out.println("Vous venez de trouver " + weapons.getName() + " +" + weapons.getDamagePoints() + " ATK ("+ weapons.getDescription()+ ") !\n" +
                            "Mais vous possedez deja " + ((Healer) cibleWeapons).currentWeaponList.get(0).getName() + " (" +  ((Healer) cibleWeapons).currentWeaponList.get(0).getDamagePoints() + " ATK )\n" +
                            "Souhaitez-vous changer d'arme ? [y/n]");
                    etat=11;
                    break;
                }
                etat=1;
                break;
            case 11:
                String choixChangementWeapon = textField.getText();
                if (choixChangementWeapon.equals("y")){
                    if(cibleWeapons instanceof Warrior){
                        ((Warrior)cibleWeapons).currentWeaponList.remove(0);
                        ((Warrior) cibleWeapons).take(weapons);
                        System.out.println(" ");
                        etat=1;
                        break;
                    }else if(cibleWeapons instanceof Hunter){
                        ((Hunter)cibleWeapons).currentWeaponList.remove(0);
                        ((Hunter) cibleWeapons).take(weapons);
                        System.out.println(" ");
                        etat=1;
                        break;
                    }else if(cibleWeapons instanceof Mage){
                        ((Mage)cibleWeapons).currentWeaponList.remove(0);
                        ((Mage) cibleWeapons).take(weapons);
                        System.out.println(" ");
                        etat=1;
                        break;
                    }else if(cibleWeapons instanceof Healer){
                        ((Healer)cibleWeapons).currentWeaponList.remove(0);
                        ((Healer) cibleWeapons).take(weapons);
                        System.out.println(" ");
                        etat=1;
                        break;
                    }
                }else{
                    System.out.println("Vous laissez l'arme sur place et partez vers la suite de votre aventure...\n");
                    etat=1;
                    break;
                }


        }
    }
}