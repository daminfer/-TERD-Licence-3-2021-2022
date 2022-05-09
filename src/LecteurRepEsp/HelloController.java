package LecteurRepEsp;
import Ressources.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HelloController extends PathHolder {
    ArrayList<Integer> listNivplanning = new ArrayList<>();
    Niveau niveaucourant;
    int indicepourniveaucourant; int indicedeckcourant;
    Carte cartecourant;
    Deck deckcourant;
    ArrayList<Carte> listCartecourant = new ArrayList<>();
    List<Deck> listDeckcourant = new ArrayList<>();
    RepEspacee rep ;

    @FXML
    public Label welcomeText;

    public Label nomdeck;
    public Label nomcarte;
    public Label nomniveau;
    public Label QuestionReponse;
    public Label reponsedonnee;

    public VBox Acceuil;

    public VBox StartQuestion;



    public Pane autoEvalPane;
    public Pane entrerReponsePane;

    public TextArea entrerReponse= new TextArea();



    @FXML
    public void onAjoutDeckClick() {
        System.out.println(this.rep);
        if (this.rep != null ) {
            Niveau niv0 = new Niveau().initlvl("Niveau_0");
            FileChooser fileChooser = new FileChooser();
            File file = new File(getPathDeck());
            fileChooser.setInitialDirectory(file);
            List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
            if (files != null) {
                for (File f : files) {
                    String s = f.getName().replaceAll(".txt", "").trim();
                    Deck d = new Deck().initDeck(s);
                    if (!rep.DeckinNiv(d)){
                        niv0.addnewDeck(d);
                    }

                }
            }
        }else
        {
            welcomeText.setText("veuiller creer ou choisir un profil avant d'ajouter un deck.");
        }
    }




    @FXML
    public void onStartClick() {

        if (this.rep != null) {
            if (!rep.getLecturedone()) {
                Acceuil.setVisible(false);
                StartQuestion.setVisible(true);
                autoEvalPane.setVisible(false);
                entrerReponsePane.setVisible(true);
                lectureNiveauSuivant();
            } else {
                welcomeText.setText("Vous avez deja fini votre session revenez demain ou veuillez ajouter des nouveaux decks a memoriser ");
            }
        } else {
            welcomeText.setText("veuiller creer ou choisir un profil avant de démarrer une session");
        }
    }

    public void lectureNiveauSuivant() {
        if (listNivplanning.isEmpty()) {
            Acceuil.setVisible(true);
            StartQuestion.setVisible(false);
            welcomeText.setText("Vous avez fini votre session revenez demain ou veuillez ajouter des nouveaux decks a memoriser ");
            rep.initProfil(rep.getNomProfil());
            

        } else {
            indicepourniveaucourant = listNivplanning.get(0);
            listNivplanning.remove(0);
            niveaucourant = new Niveau().initlvl("Niveau_" + String.valueOf(indicepourniveaucourant));
            listDeckcourant = niveaucourant.getDecks().keySet().stream().toList();
            indicedeckcourant = 0;

            if (listDeckcourant.isEmpty()) {
                lectureNiveauSuivant();
            } else {
                nomniveau.setText(niveaucourant.getNomLvl());
                lectureDeckSuivant();
            }


        }
    }
    public void lectureDeckSuivant(){
        if (indicedeckcourant == listDeckcourant.size()) {
            lectureNiveauSuivant();
        }
        else{
            deckcourant = listDeckcourant.get(indicedeckcourant);
            indicedeckcourant += 1 ;
            listCartecourant = niveaucourant.getDecks().get(deckcourant);
            nomdeck.setText(deckcourant.getNom());
            lectureCarteSuivant();
        }
    }
    public void lectureCarteSuivant(){
        if (listCartecourant.isEmpty()){
            lectureDeckSuivant();
        }else{
            cartecourant = listCartecourant.get(0);
            listCartecourant.remove(0);
            nomcarte.setText(cartecourant.getNom());
            QuestionReponse.setText(cartecourant.getFaceRecto().getText());
        }

    }
    public void Vraibutton() {
        autoEvalPane.setVisible(false);
        entrerReponsePane.setVisible(true);
        String nivsuiv = niveaucourant.getNomLvl().split("_")[0].trim() +'_'+String.valueOf(Integer.valueOf(niveaucourant.getNomLvl().split("_")[1] )+ 1).trim();
        rep.passageCarteAuNivSuivant(cartecourant.getNom(),niveaucourant.getNomLvl(),nivsuiv);
        lectureCarteSuivant();

    }

    public void Fauxbutton() {
        autoEvalPane.setVisible(false);
        entrerReponsePane.setVisible(true);
        rep.passageCarteAuNivSuivant(cartecourant.getNom(),niveaucourant.getNomLvl(),"Niveau_1");
        lectureCarteSuivant();
    }

    public void Validerbutton() {
        autoEvalPane.setVisible(true);
        entrerReponsePane.setVisible(false);
        reponsedonnee.setText(entrerReponse.getText());
        QuestionReponse.setText(cartecourant.getFaceVerso().getText());
    }

    public void retourmenu() throws IOException {
        Stage s = (Stage) welcomeText.getScene().getWindow();
        Menu.MenuApp ma= new Menu.MenuApp();
        ma.start(s);
    }


    public void chargerprofil (RepEspacee currep , ArrayList<Integer> listplan){
        this.rep = currep;
        this.listNivplanning = listplan;
    }
}