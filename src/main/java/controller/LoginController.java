package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.*;
import org.hibernate.query.Query;
import utils.WindowUtil;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.List;

public class LoginController {

    private LoginModel app = new LoginModel();;

    @FXML
    private Label txtStatus;

    @FXML

    private TextField txtBN;

    @FXML
    private PasswordField psfPW;

    @FXML
    private BorderPane rootPane;

    @FXML
    public void login(ActionEvent actionEvent) throws IOException {

        if(txtBN.getText().equals(null)){
            txtStatus.setText("Bitte geben Sie Ihren Benutzernamen ein.");
            return;
        }else if(psfPW.getText().equals(null)){
            txtStatus.setText("Bitte geben Sie Ihr Passwort ein.");
            return;
        }

        if (LoginModel.getLogin(txtBN.getText(), psfPW.getText())){

            if(LoginModel.getStatus(txtBN.getText(), psfPW.getText()).equals("Manager")) {
                org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();
                Query<FlugGesManager> query01 = session1.createQuery("FROM FlugGesManager fm WHERE fm.username = :username");
                query01.setParameter("username", txtBN.getText());

                List<FlugGesManager> fluggesmanagerList = query01.list();
                FlugGesManager flugesmanager_rec = fluggesmanagerList.get(0);

                Query<Fluggesellschaft> query02 = session1.createQuery("FROM Fluggesellschaft a WHERE a.id_manager = :id_manager");
                query02.setParameter("id_manager", flugesmanager_rec.getId_manager());

                List<Fluggesellschaft> FluggesellschaftList = query02.list();
                //Condition for Managers without Fluggesellschaft - Brenda



                if (FluggesellschaftList!=null && FluggesellschaftList.size()>0){
                    Fluggesellschaft Fluggesellschaft_rec = FluggesellschaftList.get(0);
                    AppSession.getIntance().setUsername(txtBN.getText());
                    AppSession.getIntance().setId_manager(flugesmanager_rec.getId_manager());
                    AppSession.getIntance().setGesellshaftname(Fluggesellschaft_rec.getName());
                    AppSession.getIntance().setId_gesellschaft(Fluggesellschaft_rec.getId_fluggesellschaft());
                }
                else{
                    AppSession.getIntance().setUsername(txtBN.getText());
                }

                //Up to here


                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ManagerMenu.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage(); //(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Managermenü");
                stage.setScene(scene);
                stage.show();
                this.clearInput();

            }    // My Customer Session

               else if(LoginModel.getStatus(txtBN.getText(), psfPW.getText()).equals("Kunde")) {
                org.hibernate.Session sessionKunde = SessionFactoryUtil.getInstance().getSession();

                    Query <Kunde> query3 = sessionKunde.createQuery("FROM Kunde k WHERE k.username = : username");
                    query3.setParameter("username", txtBN.getText());
                    AppSession.getIntance().setCustomerUsername(txtBN.getText());

                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/KundeMenu.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage(); //(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Kundenmenü");
                stage.setScene(scene);
                stage.show();
                this.clearInput();
            }
                else{
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AdministratorMenu.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage(); //(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Adminmenü");
                stage.setScene(scene);
                stage.show();
                this.clearInput();
            }
        }else{
            WindowUtil.alertWindow("Falsches Passwort", Alert.AlertType.CONFIRMATION, "Sie haben einen flaschen Benutzername oder Passwort eingegeben. /n Falls Sie ihr Passwort vergessen haben wenden Sie sich an ihren Administrator.");
        }

    }

    @FXML
    void register(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/KundeAnlegen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage(); //(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Registrierung");
        stage.setScene(scene);
        stage.show();

    }

    private void clearInput() {
        txtBN.setText("");
        psfPW.setText("");
    }
}


