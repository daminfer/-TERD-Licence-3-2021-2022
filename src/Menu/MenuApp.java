package Menu;

import LecteurRepEsp.HelloController;
import Ressources.RepEspacee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuApp extends Application {
    MenuController mc = new MenuController();
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Menu.MenuApp.class.getResource("Menu.fxml"));
        fxmlLoader.setController(mc);
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("EditeurCartes/ED.css");
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
    }
    public RepEspacee retourmenu (Stage stage , RepEspacee rep) throws IOException {
        mc.chargerprofil(rep);
        FXMLLoader fxmlLoader = new FXMLLoader(MenuApp.class.getResource("Menu.fxml"));
        fxmlLoader.setController(mc);
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
        return rep;
    }

    public static void main(String[] args) {
        launch();
    }
}