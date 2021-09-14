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

public class ManagerController {

    @FXML
    private Button btnFlgGeselAnlegen;

    @FXML
    private Button btnFlgLinAnsehen;

    @FXML
    private Button btnFlgLinAnlegen;

    @FXML
    private Button btnFlgLinAendern;

    @FXML
    private Button btnFlugHfZeigen;

    @FXML
    private Button btnFlugZgZeigen;

    @FXML
    private BorderPane rootPane;



    @FXML
    void FlgLinAnsehen(ActionEvent event) {
        try {
            clearButtons();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FluglinieAnsehen.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            //Stage stage=(Stage)btnFlgLinAnsehen.getScene().getWindow();
            stage.setTitle("Fluglinie Ansehen");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void GesAnlegen(ActionEvent event) {
        try {
            clearButtons();
            btnFlgGeselAnlegen.getStyleClass().add("UsedButton");
            loadMainWindow("ManagerFluggesellschaftAnlegen");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void clearButtons() {

        btnFlgGeselAnlegen.getStyleClass().remove("UsedButton");
        btnFlgGeselAnlegen.getStyleClass().add("button");

        btnFlgLinAnlegen.getStyleClass().remove("UsedButton");
        btnFlgLinAnlegen.getStyleClass().add("button");

        btnFlgLinAendern.getStyleClass().remove("UsedButton");
        btnFlgLinAendern.getStyleClass().add("button");

        btnFlugHfZeigen.getStyleClass().remove("UsedButton");
        btnFlugHfZeigen.getStyleClass().add("button");

        btnFlugZgZeigen.getStyleClass().remove("UsedButton");
        btnFlugZgZeigen.getStyleClass().add("button");
    }

    @FXML
    void FlugHfZeigen(ActionEvent event) {
        try {
            clearButtons();
            /*btnFlugHfZeigen.getStyleClass().add("UsedButton");
            loadMainWindow("FlughaefenZeigen");*/

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FlughaefenZeigen.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            //Stage stage=(Stage)btnFlugHfZeigen.getScene().getWindow();
            stage.setTitle("Flugzeuge Zeigen");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void FlglinAnlegen(ActionEvent event) {
        try {
            clearButtons();
            //btnFlgLinAnlegen.getStyleClass().add("UsedButton");
            //loadMainWindow("fluglinieAnlegen");
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/fluglinieAnlegen.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            //Stage stage=(Stage)btnFlgLinAnlegen.getScene().getWindow();
            stage.setTitle("Fluggesellschaftanlegen");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void FlgLinAendern(ActionEvent event) {
        try {
            clearButtons();
            /*
            btnFlgLinAendern.getStyleClass().add("UsedButton");
            loadMainWindow("ManagerFluglinieAendern");*/
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/fluglinieAendern.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            //Stage stage=(Stage)btnFlgLinAnlegen.getScene().getWindow();
            stage.setTitle("FluggesellschaftÄndern");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void FlugZgZeigen(ActionEvent event) {
        try {
            clearButtons();
          /*  btnFlugZgZeigen.getStyleClass().add("UsedButton");
            loadMainWindow("FlugzeugeZeigen");*/

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FlugzeugeZeigen.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            //Stage stage=(Stage)btnFlugZgZeigen.getScene().getWindow();
            stage.setTitle("Flugzeuge Zeigen");
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

    private void clear() {
        rootPane.setCenter(null);
        rootPane.setLeft(null);
    }

    private void loadMainWindow(String s) throws IOException {
        String name="fxml/" + s + ".fxml";
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/fluglinieAnlegen.fxml"));
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/" + s + ".fxml"));
        rootPane.setCenter(parent);

    }
    /*

    private boolean checkForGesellschaft() {
        org.hibernate.Session session = models.SessionFactoryUtil.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        String id_manager = ""; //hier muss die manmager ID die angemeldet ist rein
        if(( (Integer) session.createQuery("select count(*) from fluggesellschaft where id_fluggesellschaft = " + id_manager).iterate().next() ).intValue() > 0) {
            session.close();
            return true;
        }
        session.close();
        return false;
    }
    */
}

