package com.example.tp4_rpg_javafx_version;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;


import com.example.tp4_rpg_javafx_version.isep.rpg.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import static com.example.tp4_rpg_javafx_version.CharSelectionController.heros;
import static com.example.tp4_rpg_javafx_version.HelloApplication.sound;


public class MainController {
    @FXML
    private ImageView demonImage;
    @FXML
    private ImageView dragonImage;
    @FXML
    private ImageView goblinImage;
    @FXML
    private ImageView golemImage;
    @FXML
    private ImageView slimeImage;

    @FXML
    private ImageView healerImage;
    @FXML
    private ImageView hunterImage;
    @FXML
    private ImageView mageImage;
    @FXML
    private ImageView warriorImage;
    @FXML
    private ImageView treasureImage;
    @FXML
    private ImageView offButton;

    @FXML
    private ImageView onButton;

    @FXML
    private Label etatLabel;

    @FXML
    private TextArea console;
    @FXML
    private TextField textField;
    public static PrintStream ps ;

    @FXML
    public void initialize() {
        // Votre code existant
        ps = new PrintStream(new Console(console));

        // Ajouter un gestionnaire d'événements pour le TextField
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    onActionClick(null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
    Combattant cibleUpgrade;

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

    public int etat = 0;        //etat: [0=Démarrage, 1=Début nouvelle manche, 2=Attaque Ennemie, 3=Attaque Alliées,
                                //   4=Choix Action, 5=Attaque Normale, 6=Attaque Spe, 7=Accès Inventaire, 8=cibleConsommable
                                //   9=Récompenses Automatiques, 10=Création de la nouvelle arme, 11=Confirmation changement d'armes,
                                //   12 = Choix amélioration statistique, 13= Affectation de l'amélioration]
    @FXML
    void onActionClick(MouseEvent event) throws IOException {
        switch(etat) {
            case 0:
                int nombreEnnemy = (int) ((heros.size() / 2) + 1);
                Game.initialisationEnnemy(nombreEnnemy, enemiesList, manche1, manche2, manche3, manche4, manche5);
                displayprintln(" Debut de la partie ");
                etat = 1;
                textField.clear();
                break;
            case 1:
                enemies = enemiesList.get(0);
                manche += 1; // Compteur de manche
                switch(manche){
                    case 1:
                        slimeImage.setVisible(true);
                        break;
                    case 2:
                        slimeImage.setVisible(false);
                        golemImage.setVisible(true);
                        break;
                    case 3:
                        golemImage.setVisible(false);
                        goblinImage.setVisible(true);
                        break;
                    case 4:
                        goblinImage.setVisible(false);
                        dragonImage.setVisible(true);
                        break;
                    case 5:
                        dragonImage.setVisible(false);
                        demonImage.setVisible(true);
                        break;
                }
                displayprintln("C'est le debut de la manche numero : " + manche + " et vous allez affronter ");
                if (enemies.size() != 1) {
                    for (Ennemy e : enemies) {
                        display(e.getName() + " ; ");
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
                    etatLabel.setText("Défense!");
                    etat = 2;
                } else {
                    etat = 3;
                    etatLabel.setText("Attaque!");
                    System.out.println("\n" + "C'est au tour des heros d'attaquer");
                }
                textField.clear();
                break;
            case 2:
                displayprintln(" ");
                if (enemies.size() == 0) {
                    slimeImage.setVisible(false);
                    golemImage.setVisible(false);
                    goblinImage.setVisible(false);
                    dragonImage.setVisible(false);
                    dragonImage.setVisible(false);
                    enemiesList.remove(0);
                    idHero = 0;
                    if (enemiesList.size() == 0 ){
                        HelloApplication.mediaPlayer.stop();
                        HelloApplication.playVictorySound();
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("victory-view.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
                        HelloApplication.currentStage.setScene(scene);
                        HelloApplication.currentStage.show();
                        break;
                    }
                    System.out.println("Vous tombez sur un tresor cache proche du lieu de votre precedent combat ");
                    etatLabel.setText("Récompense");
                    treasureImage.setVisible(true);
                    etat=9;
                    break;
                }
                Game.attaqueEnnemie(heros, enemies);
                if (heros.size() == 0) {
                    HelloApplication.mediaPlayer.stop();
                    HelloApplication.playDefeatSound();
                    if (MainController.enemiesList.size() > 0) {
                        MainController.enemiesList.subList(0, MainController.enemiesList.size()).clear();
                        enemies.subList(0, enemies.size()).clear();
                    }
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("defaite-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
                    HelloApplication.currentStage.setScene(scene);
                    HelloApplication.currentStage.show();
                }
                Game.displayStatus(heros, enemies);
                Game.finProtection(heros);
                etat = 3;
                System.out.println("\n" + "C'est au tour des heros d'attaquer");
                etatLabel.setText("Attaque!");
                break;
            case 3:
                if (enemies.size() == 0) {
                    slimeImage.setVisible(false);
                    golemImage.setVisible(false);
                    goblinImage.setVisible(false);
                    dragonImage.setVisible(false);
                    dragonImage.setVisible(false);
                    enemiesList.remove(0);
                    idHero = 0;
                    if (enemiesList.size() == 0 ){
                        HelloApplication.mediaPlayer.stop();
                        HelloApplication.playVictorySound();
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("victory-view.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);
                        HelloApplication.currentStage.setScene(scene);
                        HelloApplication.currentStage.show();
                        break;
                    }
                    System.out.println("Vous tombez sur un tresor cache proche du lieu de votre precedent combat ");
                    etatLabel.setText("Récompense");
                    treasureImage.setVisible(true);
                    etat=9;
                    break;
                }
                Combattant goodOne = heros.get(idHero);
                if(goodOne instanceof Warrior){
                    warriorImage.setVisible(true);
                    mageImage.setVisible(false);
                    hunterImage.setVisible(false);
                    healerImage.setVisible(false);
                }else if(goodOne instanceof Hunter){
                    hunterImage.setVisible(true);
                    healerImage.setVisible(false);
                    mageImage.setVisible(false);
                    warriorImage.setVisible(false);
                }else if(goodOne instanceof Mage){
                    mageImage.setVisible(true);
                    hunterImage.setVisible(false);
                    healerImage.setVisible(false);
                    warriorImage.setVisible(false);
                }else if(goodOne instanceof Healer){
                    healerImage.setVisible(true);
                    mageImage.setVisible(false);
                    warriorImage.setVisible(false);
                    hunterImage.setVisible(false);
                }
                System.out.println(" Que va faire " + goodOne.getName() + "?");
                goodOne.sayAction();
                displayprintln("");
                etat = 4;
                etatLabel.setText("Valider");
                textField.clear();
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
                        etatLabel.setText("Attaquer!");
                        etat = 5;
                        textField.clear();
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
                            etatLabel.setText("Soigner");
                        } else {
                            compteurId = 1;
                            System.out.println("Qui souhaitez-vous attaquer ?");
                            for (Combattant combattant : enemies) {
                                System.out.println(compteurId + "- " + combattant.getName() + " : " + combattant.getHealthPoint() + " PV");
                                compteurId++;
                            }
                            displayprintln("");
                            etatLabel.setText("Attaquer!");
                        }
                        etat = 6;
                        textField.clear();
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
                        textField.clear();
                        break;
                    case 4:
                        System.out.println("Quel objet souhaitez-vous consommer ? \n" +
                                "0- Retour \n" +
                                "Nourriture : \n" +
                                "1- Nuka-Cola : + " + food.puissanceNukaCola + " PV (" + food.compteurNukaCola + " en stock) \n" +
                                "2- Bento : + " + food.puissanceBento + " PV (" + food.compteurBento + " en stock) \n" +
                                "3- Ragout : + " + food.puissanceRagout + " PV (" + food.compteurRagout + " en stock) \n" +
                                "Potion : (Uniquement pour des utilisateurs de sort : Mage & Healer) \n" +
                                "4- Mini Potion : +" + potion.puissanceMiniPotion + "Mana (" + potion.compteurMiniPotion + " en stock)\n" +
                                "5- Potion : +" + potion.puissancePotion + "Mana (" + potion.compteurPotion + " en stock) \n" +
                                "6- Maxi Potion : +" + potion.puissanceMaxiPotion + "Mana (" + potion.compteurMaxiPotion + " en stock)");
                        etat = 7;
                        textField.clear();
                        break;
                }
                textField.clear();
                break;
            case 5:
                goodOne = heros.get(idHero);
                int choixCible = Integer.parseInt(textField.getText());
                System.out.println("Joueur : " + choixCible + "\n");
                Ennemy ennemy = enemies.get((choixCible - 1));                        //ATTAQUE SIMPLE SUR LA CIBLE
                goodOne.fight(ennemy);
                if (ennemy.getHealthPoint() <= 0) {
                    enemies.remove((choixCible - 1));
                    System.out.println("\n" + "Les Heros ont vaincu " + ennemy.getName() + " !");
                }
                if (enemies.size() == 0) {
                    etatLabel.setText("Valider");
                    etat=3;
                    textField.clear();
                    break;
                }
                Game.displayStatus(heros, enemies);
                if (idHero == (heros.size() - 1)) {
                    idHero = 0;

                    etatLabel.setText("Défense");
                    etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                } else {
                    idHero++;
                    etatLabel.setText("Valider");
                    etat = 3;
                }
                textField.clear();
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
                        System.out.println("\n" + " Les Heros ont vaincu " + ennemy.getName() + " !");
                    }
                }
                if (enemies.size() == 0) {
                    etatLabel.setText("Valider");
                    etat=3;
                    textField.clear();
                    break;
                }
                Game.displayStatus(heros, enemies);
                if (idHero == (heros.size() - 1)) {
                    idHero = 0;
                    etatLabel.setText("Défense");
                    etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                } else {
                    etatLabel.setText("Valider");
                    idHero++;
                    etat = 3;
                }
                textField.clear();
                break;
            case 7:
                goodOne = heros.get(idHero);
                int choixObjet = Integer.parseInt(textField.getText());
                System.out.println("Joueur : " + choixObjet + "\n");
                switch (choixObjet) {
                    case 0:
                        etatLabel.setText("Valider");
                        etat=3;
                        textField.clear();
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
                            etatLabel.setText("Valider");
                            etat=8;
                        } else {
                            System.out.println("Vous n'avez plus de Nuka-Cola");
                            displayprintln(" ");
                            etatLabel.setText("Valider");
                            etat = 3;
                            textField.clear();
                            break;
                        }
                        textField.clear();
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
                            etatLabel.setText("Valider");
                            etat=8;
                        } else {
                            System.out.println("Vous n'avez plus de Bento");
                            displayprintln(" ");
                            etatLabel.setText("Valider");
                            etat = 3;
                            textField.clear();
                            break;
                        }
                        textField.clear();
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
                            etatLabel.setText("Valider");
                            etat=8;
                        } else {
                            System.out.println("Vous n'avez plus de Ragout");
                            etatLabel.setText("Valider");
                            etat = 3;
                            textField.clear();
                            break;
                        }
                        textField.clear();
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
                                System.out.println("Aucun hero ne peut recevoir de potion !");
                                displayprintln("");
                                etatLabel.setText("Valider");
                                etat=3;
                                textField.clear();
                                break;
                            }
                            typeConsommable=4;
                            etat=8;
                            textField.clear();
                            break;
                        } else{
                            System.out.println("Vous n'avez plus de MiniPotion");
                            displayprintln(" ");
                            etatLabel.setText("Valider");
                            etat = 3;
                            textField.clear();
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
                                System.out.println("Aucun hero ne peut recevoir de potion !");
                                displayprintln("");
                                etatLabel.setText("Valider");
                                etat=3;
                                textField.clear();
                                break;
                            }
                            typeConsommable=5;
                            etat=8;
                            textField.clear();
                            break;
                        } else{
                            System.out.println("Vous n'avez plus de Potion");
                            displayprintln(" ");
                            etatLabel.setText("Valider");
                            etat = 3;
                            textField.clear();
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
                                System.out.println("Aucun hero ne peut recevoir de potion !");
                                displayprintln("");
                                etatLabel.setText("Valider");
                                etat=3;
                                textField.clear();
                                break;
                            }
                            typeConsommable=6;
                            etat=8;
                            textField.clear();
                            break;
                        } else{
                            System.out.println("Vous n'avez plus de MaxiPotion");
                            displayprintln(" ");
                            etatLabel.setText("Valider");
                            etat = 3;
                            textField.clear();
                            break;
                        }

                }
                textField.clear();
                break;
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
                            etatLabel.setText("Défense");
                            etat = 2;                                                 //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        textField.clear();
                        break;

                    case 2:
                        food.useBento(heros.get((choixCibleConsumable-1)));
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 2;
                            etatLabel.setText("Défense");                                           //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        textField.clear();
                        break;
                    case 3:
                        food.useRagout(heros.get((choixCibleConsumable-1)));
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 2;
                            etatLabel.setText("Défense");                                         //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        textField.clear();
                        break;
                    case 4:
                        potion.useMiniPotion((SpellCaster) ciblePotion.get((choixCibleConsumable-1)));
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 2;
                            etatLabel.setText("Défense");                                          //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        textField.clear();
                        break;
                    case 5:
                        potion.usePotion((SpellCaster) ciblePotion.get((choixCibleConsumable-1)));
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 2;
                            etatLabel.setText("Défense");                                             //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        textField.clear();
                        break;
                    case 6:
                        potion.useMaxiPotion((SpellCaster) ciblePotion.get((choixCibleConsumable-1)));
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 2;
                            etatLabel.setText("Défense");                                            //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 3;
                        }
                        textField.clear();
                        break;
                }
                textField.clear();
                break;
            case 9:
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
                etatLabel.setText("Valider");
                etat=10;
                textField.clear();
                break;
            case 10:
                int choixCibleWeapons = Integer.parseInt(textField.getText());
                System.out.println("Joueur : " + choixCibleWeapons +"\n");
                cibleWeapons = heros.get(choixCibleWeapons-1);
                if(cibleWeapons instanceof Warrior){
                    warriorImage.setVisible(true);
                    mageImage.setVisible(false);
                    hunterImage.setVisible(false);
                    healerImage.setVisible(false);
                }else if(cibleWeapons instanceof Hunter){
                    hunterImage.setVisible(true);
                    healerImage.setVisible(false);
                    mageImage.setVisible(false);
                    warriorImage.setVisible(false);
                }else if(cibleWeapons instanceof Mage){
                    mageImage.setVisible(true);
                    hunterImage.setVisible(false);
                    healerImage.setVisible(false);
                    warriorImage.setVisible(false);
                }else if(cibleWeapons instanceof Healer){
                    healerImage.setVisible(true);
                    mageImage.setVisible(false);
                    warriorImage.setVisible(false);
                    hunterImage.setVisible(false);
                }
                randomObjet = new Random();
                if(cibleWeapons instanceof Warrior){
                    String weaponName;
                    int degatCommonWeapon;
                    String description;
                    int rareteWeapon = randomObjet.nextInt(101);
                    if (rareteWeapon <= 46) {
                        weaponName = Game.nommageWeapon(Game.weaponList[0], 0);
                        degatCommonWeapon = randomObjet.nextInt(2,4);
                        description = "Commun";
                    } else if (rareteWeapon > 46 && rareteWeapon <= 75) {
                        weaponName = Game.nommageWeapon(Game.weaponList[0], 1);
                        degatCommonWeapon = randomObjet.nextInt(4, 7);
                        description = "Rare";
                    } else if (rareteWeapon > 75  && rareteWeapon <= 92) {
                        weaponName = Game.nommageWeapon(Game.weaponList[0], 2);
                        degatCommonWeapon = randomObjet.nextInt(7, 10);
                        description = "Epique";
                    } else {
                        weaponName = Game.nommageWeapon(Game.weaponList[0], 3);
                        degatCommonWeapon = randomObjet.nextInt(9, 12);
                        description = "Legendaire";
                    }
                    weapons = new Weapon(weaponName, description, degatCommonWeapon);
                    System.out.println("Vous venez de trouver " + weapons.getName() + " +" + weapons.getDamagePoints() + " ATK ("+ weapons.getDescription()+ ") !\n" +
                            "Mais vous possedez deja " + ((Warrior) cibleWeapons).currentWeaponList.get(0).getName() + " (" +  ((Warrior) cibleWeapons).currentWeaponList.get(0).getDamagePoints() + " ATK )\n" +
                            "Souhaitez-vous changer d'arme ? [y/n]");
                    etat=11;
                    etatLabel.setText("Valider");
                    textField.clear();
                    break;

                } else if (cibleWeapons instanceof Hunter) {
                    String weaponName;
                    int degatCommonWeapon;
                    String description;
                    int rareteWeapon = randomObjet.nextInt(101);
                    if (rareteWeapon <= 46) {
                        weaponName = Game.nommageWeapon(Game.weaponList[1], 0);
                        degatCommonWeapon = randomObjet.nextInt(2,4);
                        description = "Commun";
                    } else if (rareteWeapon > 46 && rareteWeapon <= 75) {
                        weaponName = Game.nommageWeapon(Game.weaponList[1], 1);
                        degatCommonWeapon = randomObjet.nextInt(4, 7);
                        description = "Rare";
                    } else if (rareteWeapon > 75  && rareteWeapon <= 92) {
                        weaponName = Game.nommageWeapon(Game.weaponList[1], 2);
                        degatCommonWeapon = randomObjet.nextInt(7, 10);
                        description = "Epique";
                    } else {
                        weaponName = Game.nommageWeapon(Game.weaponList[1], 3);
                        degatCommonWeapon = randomObjet.nextInt(9, 12);
                        description = "Legendaire";
                    }
                    weapons = new Weapon(weaponName, description, degatCommonWeapon);
                    System.out.println("Vous venez de trouver " + weapons.getName() + " +" + weapons.getDamagePoints() + " ATK ("+ weapons.getDescription()+ ") !\n" +
                            "Mais vous possedez deja " + ((Hunter) cibleWeapons).currentWeaponList.get(0).getName() + " (" +  ((Hunter) cibleWeapons).currentWeaponList.get(0).getDamagePoints() + " ATK )\n" +
                            "Souhaitez-vous changer d'arme ? [y/n]");
                    etat=11;
                    etatLabel.setText("Valider");
                    textField.clear();
                    break;

                }else if (cibleWeapons instanceof Mage) {
                    String weaponName;
                    int degatCommonWeapon;
                    String description;
                    int rareteWeapon = randomObjet.nextInt(101);
                    if (rareteWeapon <= 46) {
                        weaponName = Game.nommageWeapon(Game.weaponList[2], 0);
                        degatCommonWeapon = randomObjet.nextInt(2,4);
                        description = "Commun";
                    } else if (rareteWeapon > 46 && rareteWeapon <= 75) {
                        weaponName = Game.nommageWeapon(Game.weaponList[2], 1);
                        degatCommonWeapon = randomObjet.nextInt(4,7);
                        description = "Rare";
                    } else if (rareteWeapon > 75  && rareteWeapon <= 92) {
                        weaponName = Game.nommageWeapon(Game.weaponList[2], 2);
                        degatCommonWeapon = randomObjet.nextInt(7, 10);
                        description = "Epique";
                    } else {
                        weaponName = Game.nommageWeapon(Game.weaponList[2], 3);
                        degatCommonWeapon = randomObjet.nextInt(9,12);
                        description = "Legendaire";
                    }
                    weapons = new Weapon(weaponName, description, degatCommonWeapon);
                    System.out.println("Vous venez de trouver " + weapons.getName() + " +" + weapons.getDamagePoints() + " ATK ("+ weapons.getDescription()+ ") !\n" +
                            "Mais vous possedez deja " + ((Mage) cibleWeapons).currentWeaponList.get(0).getName() + " (" +  ((Mage) cibleWeapons).currentWeaponList.get(0).getDamagePoints() + " ATK )\n" +
                            "Souhaitez-vous changer d'arme ? [y/n]");
                    etat=11;
                    etatLabel.setText("Valider");
                    textField.clear();
                    break;

                }else if (cibleWeapons instanceof Healer) {
                    String weaponName;
                    int degatCommonWeapon;
                    String description;
                    int rareteWeapon = randomObjet.nextInt(101);
                    if (rareteWeapon <= 46) {
                        weaponName = Game.nommageWeapon(Game.weaponList[0], 0);
                        degatCommonWeapon = randomObjet.nextInt(2,4);
                        description = "Commun";
                    } else if (rareteWeapon > 46 && rareteWeapon <= 75) {
                        weaponName = Game.nommageWeapon(Game.weaponList[3], 1);
                        degatCommonWeapon = randomObjet.nextInt(4, 7);
                        description = "Rare";
                    } else if (rareteWeapon > 75  && rareteWeapon <= 92) {
                        weaponName = Game.nommageWeapon(Game.weaponList[3], 2);
                        degatCommonWeapon = randomObjet.nextInt(7, 10);
                        description = "Epique";
                    } else {
                        weaponName = Game.nommageWeapon(Game.weaponList[3], 3);
                        degatCommonWeapon = randomObjet.nextInt(9, 12);
                        description = "Legendaire";
                    }
                    weapons = new Weapon(weaponName, description, degatCommonWeapon);
                    System.out.println("Vous venez de trouver " + weapons.getName() + " +" + weapons.getDamagePoints() + " ATK ("+ weapons.getDescription()+ ") !\n" +
                            "Mais vous possedez deja " + ((Healer) cibleWeapons).currentWeaponList.get(0).getName() + " (" +  ((Healer) cibleWeapons).currentWeaponList.get(0).getDamagePoints() + " ATK )\n" +
                            "Souhaitez-vous changer d'arme ? [y/n]");
                    etat=11;
                    etatLabel.setText("Valider");
                    textField.clear();
                    break;
                }
                etat=1;
                idHero=0;
                etatLabel.setText("Continuer");
                textField.clear();
                break;
            case 11:
                String choixChangementWeapon = textField.getText();
                if (choixChangementWeapon.equals("y")){
                    if(cibleWeapons instanceof Warrior){
                        ((Warrior)cibleWeapons).currentWeaponList.remove(0);
                        ((Warrior) cibleWeapons).take(weapons);
                        System.out.println(" ");
                        idHero=0;
                        etat=12;
                        treasureImage.setVisible(false);
                        etatLabel.setText("Continuer");
                        textField.clear();
                        break;
                    }else if(cibleWeapons instanceof Hunter){
                        ((Hunter)cibleWeapons).currentWeaponList.remove(0);
                        ((Hunter) cibleWeapons).take(weapons);
                        System.out.println(" ");
                        idHero=0;
                        etat=12;
                        treasureImage.setVisible(false);
                        etatLabel.setText("Continuer");
                        textField.clear();
                        break;
                    }else if(cibleWeapons instanceof Mage){
                        ((Mage)cibleWeapons).currentWeaponList.remove(0);
                        ((Mage) cibleWeapons).take(weapons);
                        System.out.println(" ");
                        idHero=0;
                        etat=1;
                        treasureImage.setVisible(false);
                        etatLabel.setText("Continuer");
                        textField.clear();
                        break;
                    }else if(cibleWeapons instanceof Healer){
                        ((Healer)cibleWeapons).currentWeaponList.remove(0);
                        ((Healer) cibleWeapons).take(weapons);
                        System.out.println(" ");
                        idHero=0;
                        etat=12;
                        treasureImage.setVisible(false);
                        etatLabel.setText("Continuer");
                        textField.clear();
                        break;
                    }
                }else{
                    System.out.println("Vous laissez l'arme sur place et partez vers la suite de votre aventure...\n");
                    etat=12;
                    idHero=0;
                    treasureImage.setVisible(false);
                    etatLabel.setText("Continuer");
                    textField.clear();
                    break;
                }

            case 12:
                cibleUpgrade = heros.get(idHero);
                if (cibleUpgrade instanceof Warrior) {
                    System.out.println("""
                1- Amelioration des degats\s
                2- Amelioration de l'efficacite de l'attaque speciale\s
                3- Amelioration de la defense\s
                4- Amelioration de l'efficacite des objets""");
                    etat=13;
                    textField.clear();
                    break;
                }else if(cibleUpgrade instanceof Mage){
                    System.out.println("""
                1- Amelioration des degats\s
                2- Amelioration de l'efficacite de l'attaque speciale\s
                3- Amelioration de la defense\s
                4- Amelioration de l'efficacite des objets\s
                5- Reduction du coup en mana de l'attaque speciale""");
                    etat=13;
                    textField.clear();
                    break;
                }else if(cibleUpgrade instanceof Hunter){
                    System.out.println("""
                1- Amelioration des degats\s
                2- Amelioration de l'efficacite de l'attaque speciale\s
                3- Amelioration de la defense\s
                4- Amelioration de l'efficacite des objets""");
                    etat=13;
                    textField.clear();
                    break;
                }else if(cibleUpgrade instanceof Healer){
                    System.out.println("""
                1- Amelioration des degats\s
                2- Amelioration de l'efficacite de la capacite speciale\s
                3- Amelioration de la defense\s
                4- Amelioration de l'efficacite des objets\s
                5- Reduction du coup en mana de la capacite speciale""");
                    etat=13;
                    textField.clear();
                    break;
                }
            case 13:
                int choixUpgrade = Integer.parseInt(textField.getText());
                switch(choixUpgrade){
                    case 1:
                        cibleUpgrade.setDegat(3);
                        if(cibleUpgrade instanceof Warrior){
                            cibleUpgrade.degatTotal=cibleUpgrade.getDegat() + ((Warrior) cibleUpgrade).currentWeaponList.get(0).getDamagePoints();
                        }else if(cibleUpgrade instanceof Mage){
                            cibleUpgrade.degatTotal=cibleUpgrade.getDegat() + ((Mage) cibleUpgrade).currentWeaponList.get(0).getDamagePoints();
                        }else if(cibleUpgrade instanceof Hunter){
                            cibleUpgrade.degatTotal=cibleUpgrade.getDegat() + ((Hunter) cibleUpgrade).currentWeaponList.get(0).getDamagePoints();
                        }else if(cibleUpgrade instanceof Healer){
                            cibleUpgrade.degatTotal=cibleUpgrade.getDegat() + ((Healer) cibleUpgrade).currentWeaponList.get(0).getDamagePoints();
                        }
                        System.out.println(cibleUpgrade.getName() + " se sent plus fort !\n");
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 1;
                            etatLabel.setText("Continuer");                                            //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 12;
                        }
                        textField.clear();
                        break;
                    case 2:
                        if(cibleUpgrade instanceof Warrior){
                            ((Warrior) cibleUpgrade).degatSpecial +=4;
                        }else if(cibleUpgrade instanceof Mage){
                            ((Mage) cibleUpgrade).degatSpecial +=3;
                        }else if(cibleUpgrade instanceof Hunter){
                            ((Hunter) cibleUpgrade).degatSpecial +=3;
                        }else if(cibleUpgrade instanceof Healer){
                            ((Healer) cibleUpgrade).degatSpecial +=3;
                        }
                        System.out.println(cibleUpgrade.getName() + " maitrise mieux sa capacite speciale !\n");
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 1;
                            etatLabel.setText("Continuer");                                            //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 12;
                        }
                        textField.clear();
                        break;
                    case 3:
                        cibleUpgrade.addResistance(2);
                        System.out.println(cibleUpgrade.getName() + " est plus resistant !\n");
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 1;
                            etatLabel.setText("Continuer");                                            //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 12;
                        }
                        textField.clear();
                        break;
                    case 4:
                        if(cibleUpgrade instanceof Warrior){
                            ((Warrior) cibleUpgrade).bonusVie +=3;
                        }else if(cibleUpgrade instanceof Mage){
                            ((Mage) cibleUpgrade).bonusVie +=3;
                        }else if(cibleUpgrade instanceof Hunter){
                            ((Hunter) cibleUpgrade).bonusVie +=3;
                        }else if(cibleUpgrade instanceof Healer){
                            ((Healer) cibleUpgrade).bonusVie +=3;
                        }
                        System.out.println(cibleUpgrade.getName() + " est plus receptif aux objets !\n");
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 1;
                            etatLabel.setText("Continuer");                                            //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 12;
                        }
                        textField.clear();
                        break;
                    case 5:
                        if(cibleUpgrade instanceof Mage){
                            ((Mage) cibleUpgrade).coutSort -= 2;
                        }else if(cibleUpgrade instanceof Healer){
                            ((Healer) cibleUpgrade).coutSoin -= 2;
                        }
                        System.out.println(cibleUpgrade.getName() + " nécessite moins de mana pour utiliser ses sorts !\n");
                        if (idHero == (heros.size() - 1)) {
                            idHero = 0;
                            etat = 1;
                            etatLabel.setText("Continuer");                                            //VERIFICATION DERNIER JOUEUR A JOUER
                        } else {
                            idHero++;
                            etat = 12;
                        }
                        textField.clear();
                        break;

                }
        }
    }

    @FXML
    void onSoundButtonClick(MouseEvent event) {
        if(sound==0){
            HelloApplication.mediaPlayer.pause();
            onButton.setVisible(false);
            offButton.setVisible(true);
            sound=1;
        }else{
            HelloApplication.mediaPlayer.play();
            onButton.setVisible(true);
            offButton.setVisible(false);
            sound=0;
        }
    }

    @FXML
    void soundButtonVisibility(MouseEvent event) {
        if(sound==0){
            onButton.setVisible(true);
            offButton.setVisible(false);
        }else{
            onButton.setVisible(false);
            offButton.setVisible(true);
        }
    }
}