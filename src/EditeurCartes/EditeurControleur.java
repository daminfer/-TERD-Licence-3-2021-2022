package EditeurCartes;

import LecteurRepEsp.HelloApplication;
import Menu.MenuApp;
import Ressources.*;
import groovy.text.markup.MarkupTemplateEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class EditeurControleur extends PathHolder {
    @FXML
    public VBox sceneDeck;
    @FXML
    public VBox sceneCarte;
    @FXML
    public VBox sceneQCM;
    public int Iteration = 1; // Variable utiliser pour compter le nombre de carte d'un deck.
    public Deck deck = new Deck();


    @FXML
    public Button ButtonNewDeck = new Button();
    @FXML
    public TextField NameDeck = new TextField();
    @FXML
    public Button NewDeck = new Button();

    private void affichage_button() {
        Cartes.getChildren().clear();
        Cartes.getChildren().add(CarteDebut);
        Iteration = 1 ;
        if(this.deck.getCartes() != null){

            for(Carte x : this.deck.getCartes()) {
                Button carte = new Button();
                carte.setPrefHeight(CarteDebut.getPrefHeight());
                carte.setPrefWidth(CarteDebut.getPrefWidth());
                carte.setLayoutX((CarteDebut.getPrefWidth() + CarteDebut.getLayoutX() ) * Iteration + 8 )  ;
                carte.setLayoutY(CarteDebut.getLayoutY());
                carte.setText(x.getNom());
                carte.setOnMouseClicked(e -> {
                    NameCarte.setText(x.getNom());
                    Question.setText(x.getFaceRecto().getText());
                    Reponse.setText(x.getFaceVerso().getText());
                    sceneDeck.setVisible(false);
                    sceneCarte.setVisible(false);
                    sceneQCM.setVisible(true);
                    affiche_edit_carte(x.getNom(), x.getFaceRecto().getText(), x.getFaceVerso().getText());

                });

                Cartes.getChildren().add(carte);
                Iteration++;
                Cartes.setPrefWidth((CarteDebut.getPrefWidth() + CarteDebut.getLayoutX()) * Iteration);

            }



            }
        }



    @FXML
    public void RechercheDeckW() {
        // Description du bouton: ?
        FileChooser fileChooser = new FileChooser();
        File dossier = new File(getPathDeck());
        FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("Decks", "*.txt");
        fileChooser.getExtensionFilters().add(ef);
        fileChooser.setSelectedExtensionFilter(ef);
        fileChooser.setInitialDirectory(dossier);
        File f = fileChooser.showOpenDialog(new Stage());
        Deck d = new Deck().initDeck(f.getName().split(".txt")[0].trim());
        this.deck = d;
        sceneDeck.setVisible(false);
        sceneCarte.setVisible(true);
        sceneQCM.setVisible(false);
        rechercheNomDeck.setText(d.getNom());
        affichage_button();

    }
    @FXML
    public void NewDeckCreat() {
        // Description du bouton: ?
        ArrayList <Carte>  lc = new ArrayList<>();
        Deck d = new Deck();
        if (NameDeck.getText() == "" ){

        }else {
            d.creatDeck(NameDeck.getText(),lc);
            this.deck = d;
            sceneDeck.setVisible(false);
            sceneCarte.setVisible(true);
            sceneQCM.setVisible(false);
            rechercheNomDeck.setText(NameDeck.getText());
            affichage_button();
        }

    }
    @FXML
    public void RetourMenu() throws IOException {
        Stage s = (Stage) sceneDeck.getScene().getWindow();
        MenuApp ma = new MenuApp();
        ma.start(s);
    }

    @FXML
    public Button CarteDebut = new Button();
    @FXML
    AnchorPane Cartes = new AnchorPane();
    @FXML
    TextField rechercheNomDeck = new TextField();
    @FXML
    Button carteR = new Button();
    @FXML
    Button ModifeDeck = new Button();



    @FXML
    protected void NewCarteCreat(){
        // Description du bouton: ?
        /*
        Cree une nouvelle carte cree par le button Nouvelle Carte. qui met une carte a la suite et la modifie directement en allant a E2
        */
        sceneDeck.setVisible(false);
        sceneCarte.setVisible(false);
        sceneQCM.setVisible(true);
        affiche_edit_carte ("Nouvelle_Carte" ,"Question", "Reponse" );

    }
    @FXML
    public void RechercheCarte() {
        // Description du bouton: ?
        /*
        Recherche une carte dans le systeme de Windose. Crée un button.
        */

        try {
            FileChooser fileChooser = new FileChooser();
            File dossier = new File(getPathCarte());
            FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("Cartes", "*.txt");
            fileChooser.getExtensionFilters().add(ef);
            fileChooser.setSelectedExtensionFilter(ef);
            fileChooser.setInitialDirectory(dossier);
            File f = fileChooser.showOpenDialog(new Stage());

            if(!f.equals(null)){
                // Création des boutons pour chaque carte du deck:

            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    public void RetourE0() {

        sceneDeck.setVisible(true);
        sceneCarte.setVisible(false);
        sceneCarte.setVisible(false);

    }

    @FXML
    TextArea Reponse = new TextArea();
    @FXML
    TextArea Question = new TextArea();
    @FXML
    TextField NameCarte = new TextField();
    @FXML
    Button ModifeCarte = new Button();
    @FXML
    public Button retourE_1 = new Button();

    @FXML
    public Button bouton_supprimer = new Button();


    public void RetourE1() {

        sceneDeck.setVisible(false);
        sceneCarte.setVisible(true);
        sceneQCM.setVisible(false);
        affichage_button();
        Question.setText("");
        Reponse.setText("");
        NameCarte.setText("");



    }
    public void ModificationDesCartes() {
        if (ModifeCarte.getText().equals("Modifier la carte") ){
            for (Carte c : deck.getCartes()){
                if(c.getNom().equals(NameCarte.getText())){
                    c.getFaceRecto().setText(Question.getText());
                    c.getFaceVerso().setText(Reponse.getText());
                }
            }
        }

        else {
            Carte c = new Carte();
            c.creatCarte(NameCarte.getText(),Question.getText(),Reponse.getText() );
            deck.addCarte(c);

        }
        RetourE1();
    }

    public void supprimer_carte() {
        for (Carte c : deck.getCartes()) {
            if (c.getNom().equals(NameCarte.getText())) {
                deck.removeCarte(c);c.delete_carte();
                break;
            }
        }
        RetourE1();
    }
    private void affiche_edit_carte(String c, String q, String r) {
       if (c.equals("Nouvelle_Carte") ){
           bouton_supprimer.setVisible(false);
           NameCarte.setEditable(true);
           Question.setPromptText(q);
           Reponse.setPromptText(r);
           NameCarte.setPromptText(c);
           ModifeCarte.setText("Crée la carte");
       }else {
           bouton_supprimer.setVisible(true);
           NameCarte.setEditable(false);
           Question.setText(q);
           Reponse.setText(r);
           NameCarte.setText(c);
           ModifeCarte.setText("Modifier la carte");
       }

    }
}