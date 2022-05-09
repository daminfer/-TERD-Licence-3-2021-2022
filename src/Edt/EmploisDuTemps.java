package Edt;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import Menu.MenuApp;
import Ressources.RepEspacee;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.FlowPane;

import javafx.stage.Stage;
import javafx.util.Callback;

public class EmploisDuTemps {

    // Factory to create Cell of DatePicker
        private Callback<DatePicker, DateCell> getDayCellFactory(RepEspacee rep) {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setStyle("-fx-background-color: #afa2d7;");
                        setText(item.getDayOfMonth() +"\n" + rep.PlanningEDT(item.getDayOfMonth()));
                    }
                };
            }
        };
        return dayCellFactory;
    }

    public void start(Stage stage, ArrayList<Integer> planning_jour_courrant, RepEspacee rep) {

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());//sert a selectionner le jours courant
        datePicker.setShowWeekNumbers(true);

        // Factory to create Cell of DatePicker
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory(rep);
        datePicker.setDayCellFactory(dayCellFactory);
        FlowPane root = new FlowPane();

        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node content = datePickerSkin.getPopupContent();
        Button retour_Menu = new Button();
        retour_Menu.setText("<");
        root.getChildren().add(retour_Menu);
        root.getChildren().add(content);
        root.setPadding(new Insets(10));

        retour_Menu.setOnAction(e -> {
            MenuApp ma = new MenuApp();
            try {
                ma.start(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        stage.setTitle("Emplois du temps");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}