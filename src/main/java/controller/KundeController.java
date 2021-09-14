package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class KundeController {

    @FXML
    private Button btnAusgang;

    @FXML
    private Button btnÄndern;

    @FXML
    private Button btnlöschen;

    @FXML
    private Button FlügeBuchen;

    @FXML
    private Button btnFlugkilometerkonto;

    @FXML
    private Button btnBuchungsübersicht;

    @FXML
    private Button sp_reserv_btn;

    @FXML
    private Button rec_message;

    @FXML
    void SPReservieren(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/SitzreservierungAuswahl.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Sitze Reservieren");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); //java.io.IOException
        }
    }

    @FXML
    void Ausgang(ActionEvent event) {

        Stage stage= (Stage) btnAusgang.getScene().getWindow();
        stage.close();

    }

    @FXML
    void Buchungsübersicht(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Buchungsübersicht.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Buchungsübersicht");
            stage.setScene(new Scene(root));
            stage.show();
            // window is closed
            Stage stage2= (Stage) btnÄndern.getScene().getWindow();
            //stage2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void FlügeBuchen(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/BuchungOverview.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Flug Buchung");
            stage.setScene(new Scene(root));
            stage.show();
            Stage stage2= (Stage) btnÄndern.getScene().getWindow();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnFlugkilometerkonto(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/KilometerKonto.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Kilometerkonto");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); //java.io.IOException
        }
    }

    @FXML
    void löschen(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/KundendatenLöschen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Löschung der Kundendaten");
            stage.setScene(new Scene(root));
            stage.show();
            // window is closed
            Stage stage2= (Stage) btnÄndern.getScene().getWindow();
            //stage2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void Ändern(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Kundendatenänderung.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Kundendatenänderung");
            stage.setScene(new Scene(root));
            stage.show();
            // window is closed
            Stage stage2= (Stage) btnÄndern.getScene().getWindow();
            //stage2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void receiveMessage(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/KundePostfach.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Mein Postfach");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
