package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML
    private Button btnAusgang;

    @FXML
    private Button btnAnlegen;

    @FXML
    private Button btnAendern;

    @FXML
    private Button btnLoeschen;

    @FXML
    private BorderPane rootPane;

    @FXML
    void managerAnlegen(ActionEvent event) {
        try {
            clearButtons();
            /*btnAnlegen.getStyleClass().add("UsedButton");
            loadMainWindow("AdminManagerAnlegen");
            */
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FlugGesManagerAnlegen.fxml"));
            Scene scene = new Scene(root);
            Stage stage=(Stage)btnAnlegen.getScene().getWindow();
            stage.setTitle("Fluggesellschaftmanager anlegen");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearButtons() {

        btnAnlegen.getStyleClass().remove("UsedButton");
        btnAnlegen.getStyleClass().add("button");

        btnAendern.getStyleClass().remove("UsedButton");
        btnAendern.getStyleClass().add("button");

        btnLoeschen.getStyleClass().remove("UsedButton");
        btnLoeschen.getStyleClass().add("button");

    }

    @FXML
    void managerAendern(ActionEvent event) {
        try {
            clearButtons();
           /* btnAendern.getStyleClass().add("UsedButton");
            loadMainWindow("FlugGesManagerAendern");*/
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FlugGesManagerAendern.fxml"));
            Scene scene = new Scene(root);
            Stage stage=(Stage)btnAendern.getScene().getWindow();
            stage.setTitle("Fluggesellschaftmanager ändern");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void managerLoeschen(ActionEvent event) {
        try {
            clearButtons();
            /*btnLoeschen.getStyleClass().add("UsedButton");
            loadMainWindow("AdminManagerLoeschen");*/

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FlugGesManagerLoeschen.fxml"));
            Scene scene = new Scene(root);
            Stage stage=(Stage)btnAendern.getScene().getWindow();
            stage.setTitle("Fluggesellschaftmanager löschen");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void logout(ActionEvent event) {
        try {
            clear();
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/Login.fxml"));
            rootPane.setRight(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clear(){
        rootPane.setCenter(null);
        rootPane.setLeft(null);
    }

    private void loadMainWindow (String s) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/" + s + ".fxml"));
        rootPane.setCenter(parent);

    }




}
