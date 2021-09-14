package main;

import controller.SitzplatzReservierungController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.AppSession;

public class FabianTestSitzplatzreservierung extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SitzplatzReservierung.fxml"));
        stage.setTitle("Sitzplatzreservierung");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        AppSession.getIntance().setId_manager(472); //mein test manager
        AppSession.getIntance().setId_gesellschaft(472); //meine test gesellschaft
        AppSession.getIntance().setId_flug(472); //mein test flug
        AppSession.getIntance().setAnazahlZuBuchenerEcoSitzplaetze(5);
        AppSession.getIntance().setAnzahlZuBuchenderBusiSitzplaetze(2);
        launch(args);
    }
}
