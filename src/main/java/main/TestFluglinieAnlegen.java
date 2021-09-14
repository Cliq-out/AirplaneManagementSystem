package main;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class TestFluglinieAnlegen extends Application {

    @Override
    public void start(Stage stage) throws Exception {


       //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/Buchungsübersicht.fxml"));
       // Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/EndAirport.fxml"));

       Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/Stornierungen.fxml"));

        // Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ManagerMenu.fxml"));

      //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SitzplatzReservierung.fxml"));


        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FluglinieInstance.fxml"));
     //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FluggesellschaftAnlegen.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Mis pruebas");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}