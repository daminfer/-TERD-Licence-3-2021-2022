package LecteurRepEsp;
import Ressources.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication {
    HelloController hc = new HelloController();
    public RepEspacee start(Stage stage , RepEspacee rep , ArrayList<Integer> l ) throws IOException {
        hc.chargerprofil(rep , l );

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        fxmlLoader.setController(hc);
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("EditeurCartes/ED.css");
        stage.setTitle("Répétition Espacée");
        stage.setScene(scene);
        stage.show();

        return rep;
    }




    public static void main(String[] args){
    }
}