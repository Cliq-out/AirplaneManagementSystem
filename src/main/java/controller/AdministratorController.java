package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.WindowUtil;

import java.io.IOException;

public class AdministratorController {

    @FXML
    private Button btnAusgang;

    @FXML
    private Button btnAnlegen;

    @FXML
    private Button btnAendern;

    @FXML
    private Button btnLoeschen;

    @FXML
    private Button btnFlugzeugeListe;

    @FXML
    private Button btnFlughafenListe;


    @FXML
    void Anlegen(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/FlugGesManagerAnlegen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Manager anlegen");
            stage.setScene(new Scene(root));
            stage.show();
            // window is closed
            Stage stage2= (Stage) btnAnlegen.getScene().getWindow();
            //stage2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

      /*
        try{
            WindowUtil.loadWindow("Manager Anlegen", "FlugGesManagerAnlegen.fxml", getClass());
            // get the window and close the window
            Stage stage2= (Stage) btnAnlegen.getScene().getWindow();
            stage2.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        */
        System.out.print("It works");
    }

    @FXML
    void Aendern(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/FlugGesManagerAendern.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Manager ändern");
            stage.setScene(new Scene(root));
            stage.show();
            // window is closed
            Stage stage2= (Stage) btnAnlegen.getScene().getWindow();
            //stage2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Loeschen(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/FlugGesManagerLoeschen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Manager löschen");
            stage.setScene(new Scene(root));
            stage.show();
            // window is closed
            Stage stage2= (Stage) btnAnlegen.getScene().getWindow();
            //stage2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void flughafenListe(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/FlughaefenZeigen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Flughäfen Liste");
            stage.setScene(new Scene(root));
            stage.show();
            // window is closed
            Stage stage2= (Stage) btnAnlegen.getScene().getWindow();
            //stage2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void flugzeugeListe(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/FlugzeugeAnsehen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Flugzeuge Liste");
            stage.setScene(new Scene(root));
            stage.show();
            // window is closed
            Stage stage2= (Stage) btnAnlegen.getScene().getWindow();
            //stage2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    void Ausgang(ActionEvent event) {
        Stage stage= (Stage) btnAusgang.getScene().getWindow();
        stage.close();
    }


}
