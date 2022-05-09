package Menu;

import EditeurCartes.EditeurApp;
import Edt.EmploisDuTemps;
import LecteurRepEsp.HelloApplication;
import Ressources.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MenuController extends PathHolder{
    ArrayList<Integer> listNivplanning = new ArrayList<>();
    RepEspacee rep;

    public Button Repesp;

    public Label Welcometext;
    public Label profiltext;


    public VBox Acceuil;
    public VBox sceneProfil;


    public Pane infoprofil;
    public Pane paneCreeProfil;

    public TextField nomProfil = new TextField();
    public TextField nbNivProfil = new TextField();
    public TextField nbCartesProfil = new TextField();

    @FXML
    public void RepEspClick() throws IOException {

        if (rep != null){
            rep.initProfil(rep.getNomProfil());
        }
        Stage s = (Stage) Repesp.getScene().getWindow();
        HelloApplication re = new HelloApplication() ;
        rep = re.start(s,rep,listNivplanning);


    }
    public void onEditeurClick() throws IOException {
        Stage s = (Stage) Repesp.getScene().getWindow();
        EditeurApp ea = new EditeurApp() ;
        ea.start(s);


    }
    public void onEDTClick(){
        Stage s = (Stage) Repesp.getScene().getWindow();
        EmploisDuTemps edt = new EmploisDuTemps() ;
        edt.start(s,listNivplanning , rep);

    }
    @FXML
    public void onOptionClick() {
        sceneProfil.setVisible(true);
        Acceuil.setVisible(false);
    }


    @FXML
    public void onNewProfilClick() {
        paneCreeProfil.setVisible(true);
        infoprofil.setVisible(false);

    }
    @FXML
    public void onCreeProfilClick() {
        try {
            if (!this.nomProfil.getText().equals("")) {
                rep = new RepEspacee();
                rep.createProfil(nomProfil.getText(), Integer.parseInt(nbNivProfil.getText().trim()), Integer.parseInt(nbCartesProfil.getText().trim()), 0, false);
                rep.initProfil(nomProfil.getText().trim());
                profiltext.setText(nomProfil.getText().trim() + "\nNombre de niveaux: " + String.valueOf(rep.getNbNiveau()) + "\nNombre de carte par jours: " + String.valueOf(rep.getNbCartesAjouter()));
                infoprofil.setVisible(true);
                paneCreeProfil.setVisible(false);
                listNivplanning = (ArrayList<Integer>) rep.getPlanning();

            }
        } catch (NumberFormatException e) {
        }
    }
    @FXML
    public void onChooseProfile(){

        System.out.println(this.rep);
        paneCreeProfil.setVisible(false);
        FileChooser fileChooser = new FileChooser();
        File dossier = new File(getPathProfils());
        FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("profils" ,"*.txt");
        fileChooser.getExtensionFilters().add(ef);
        fileChooser.setSelectedExtensionFilter(ef);
        fileChooser.setInitialDirectory(dossier);


        System.out.println(fileChooser.getInitialDirectory());
        File file = fileChooser.showOpenDialog(new Stage());
        System.out.println(file.getAbsolutePath());

        this.rep = new RepEspacee().initProfil(file.getName().replaceAll(".txt" , "").trim());
        profiltext.setText(file.getName().replaceAll(".txt" ,"")+ "\nNombre de niveaux: " + String.valueOf(rep.getNbNiveau()) + "\nNombre de carte par jours: " + String.valueOf(rep.getNbCartesAjouter()));
        infoprofil.setVisible(true);
        this.listNivplanning = (ArrayList<Integer>) rep.getPlanning();
        System.out.println(this.rep);

    }


    @FXML
    public void okchooseprofil() {
        sceneProfil.setVisible(false);
        Acceuil.setVisible(true);
        Welcometext.setText("voici le planning du jour: Niveaux "+ listNivplanning );
    }
    public void chargerprofil (RepEspacee currep){
        this.rep = currep;
    }

}
