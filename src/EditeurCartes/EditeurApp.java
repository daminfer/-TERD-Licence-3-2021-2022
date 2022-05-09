package EditeurCartes;
import Menu.MenuApp;
import Ressources.RepEspacee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class EditeurApp extends Application {
    EditeurControleur ec = new EditeurControleur();
    public void start(Stage stage) throws IOException {
        /* Initialisation du stage de l'éditeur de code */
        FXMLLoader fxmlLoader = new FXMLLoader(EditeurControleur.class.getResource("Editeur0.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Editeur de Carte"); // Attention, newnom -> Editeur de carte.
        scene.getStylesheets().add("EditeurCartes/ED.css");
        stage.setScene(scene);

        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
