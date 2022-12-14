package com.example.tp4_rpg_javafx_version;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.example.tp4_rpg_javafx_version.isep.rpg.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    public void button(ActionEvent event) {
        Game.startConsole();
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
                                //   9=Vérification Dernier joueur de la liste, 10=]
    @FXML
    void onActionClick(ActionEvent event) throws IOException {
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
                }
                break;
            case 2:
                displayprintln(" ");
                if (enemies.size() == 0) {
                    //TODO ramener vers les récompenses puis recharger les nouveaux ennemis (etat=1)
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
                break;
            case 3:
                if (enemies.size() == 0) {
                    //TODO Same
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
                        etat=9;
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
                Ennemy ennemy = enemies.get((choixCible - 1));                        //ATTAQUE SIMPLE SUR LA CIBLE
                goodOne.fight(ennemy);
                if (ennemy.getHealthPoint() <= 0) {
                    enemies.remove((choixCible - 1));
                    System.out.println("Les Héros ont vaincu " + ennemy.getName() + " !");
                }
                Game.displayStatus(heros, enemies);
                etat=9;
                break;
            case 6:
                goodOne = heros.get(idHero);
                choixCible = Integer.parseInt(textField.getText());
                if (goodOne instanceof Healer) {
                    goodOne.special(heros.get((choixCible - 1)));
                } else {                                                                  //ATTAQUE SPECIALE SUR LA CIBLE

                    ennemy = enemies.get((choixCible - 1));
                    goodOne.special(ennemy);
                    if (ennemy.getHealthPoint() <= 0) {
                        enemies.remove((choixCible - 1));
                        System.out.println("Les Héros ont vaincu " + ennemy.getName() + " !");
                    }
                }
                Game.displayStatus(heros, enemies);
                etat=9;
                break;
            case 7:
                goodOne = heros.get(idHero);
                int choixObjet = Integer.parseInt(textField.getText());
                switch (choixObjet) {
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
                ArrayList<Combattant> ciblePotion = new ArrayList<>();
                for(Combattant ally :heros) {
                    if (ally instanceof SpellCaster) {
                        ciblePotion.add(ally);
                    }}
                switch (typeConsommable){
                    case 1:
                        food.useNukaCola(heros.get((choixCibleConsumable-1)));
                        etat=9;
                        break;
                    case 2:
                        food.useBento(heros.get((choixCibleConsumable-1)));
                        etat=9;
                        break;
                    case 3:
                        food.useRagout(heros.get((choixCibleConsumable-1)));
                        etat=9;
                        break;
                    case 4:
                        potion.useMiniPotion((SpellCaster) ciblePotion.get((choixCibleConsumable-1)));
                        etat=9;
                        break;
                    case 5:
                        potion.usePotion((SpellCaster) ciblePotion.get((choixCibleConsumable-1)));
                        etat=9;
                        break;
                    case 6:
                        potion.useMaxiPotion((SpellCaster) ciblePotion.get((choixCibleConsumable-1)));
                        etat=9;
                        break;
                }break;
            case 9:
                if (idHero == (heros.size() - 1)) {
                    idHero = 0;
                    etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                } else {
                    idHero++;
                    etat = 3;
                }
                break;
        }
    }
}