package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ManagerMenuController {

    @FXML
    private Button btnAusgang;

    @FXML
    private Button GesellAnlegen_btn;

    @FXML
    private Button FluglinieAendern_btn;

    @FXML
    private Button FluglinieZeigen_btn;

    @FXML
    private Button btnFlugzeugeListe;

    @FXML
    private Button btnFlughafenListe;

    @FXML
    private Button FluglinieAnlegen_btn;

    @FXML
    private Button btnFlugestornieren;


    @FXML
    private Button btnFlugeinstanziieren;

    @FXML
    private Button btnFluegÜbersicht;

    @FXML
    private Button sendMsg;


    @FXML
    void Ausgang(ActionEvent event) {
        Stage stage= (Stage) btnAusgang.getScene().getWindow();
        stage.close();
    }

    @FXML
    void FluglinieAendern(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/fluglinieAendern.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Fluglinie Ändern");
            stage.setScene(new Scene(root));
            stage.show();
            Stage stage2= (Stage) FluglinieAnlegen_btn.getScene().getWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void FluglinieZeigen(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/FluglinieAnsehen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Fluglinie Zeigen");
            stage.setScene(new Scene(root));
            stage.show();
            Stage stage2= (Stage) FluglinieZeigen_btn.getScene().getWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void FluglinielAnlegen_btn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/fluglinieAnlegen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Fluglinie Anlegen");
            stage.setScene(new Scene(root));
            stage.show();
            Stage stage2= (Stage) FluglinieAnlegen_btn.getScene().getWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void GesellAnlegen(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/FluggesellschaftAnlegen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Fluggesellschaft Anlegen");
            stage.setScene(new Scene(root));
            stage.show();
            Stage stage2= (Stage) GesellAnlegen_btn.getScene().getWindow();
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
            stage.setTitle("Flugzeuge Liste");
            stage.setScene(new Scene(root));
            stage.show();

            Stage stage2= (Stage) btnFlughafenListe.getScene().getWindow();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void flugzeugeListe(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/FlugzeugeZeigen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Flugzeuge Liste");
            stage.setScene(new Scene(root));
            stage.show();

            Stage stage2= (Stage) btnFlugzeugeListe.getScene().getWindow();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void flugeUebersicht(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Flugübersicht.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Flugübersicht");
            stage.setScene(new Scene(root));
            stage.show();

            Stage stage2 = (Stage) btnFlugzeugeListe.getScene().getWindow();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @FXML
    void flugeInstanziieren(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/FluglinieInstance.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Flugzeuge Liste");
            stage.setScene(new Scene(root));
            stage.show();

            Stage stage2= (Stage) btnFlugzeugeListe.getScene().getWindow();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void flugeStornieren(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Stornierungen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Stornierung");
            stage.setScene(new Scene(root));
            stage.show();

            Stage stage2 = (Stage) btnFlugzeugeListe.getScene().getWindow();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(ActionEvent actionEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/KundePostfachMan.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Send Message");
            stage.setScene(new Scene(root));
            stage.show();

            Stage stage2 = (Stage) btnFlugzeugeListe.getScene().getWindow();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
