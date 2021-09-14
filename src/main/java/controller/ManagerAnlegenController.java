package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.FlugGesManager;
import models.LoginModel;
import models.SessionFactoryUtil;
import utils.WindowUtil;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class ManagerAnlegenController {

    @FXML
    private TextField firstname_tf;

    @FXML
    private TextField lastname_tf;

    @FXML
    private TextField username_tf;

    @FXML
    private PasswordField password_pf;

    @FXML
    private Button btnEingabe;

    @FXML
    private Button btnAbbrechen;

    @FXML
    private ChoiceBox<String> geschlect_cb;

    @FXML
    void abbrechen(ActionEvent event) {
        Stage stage= (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    @FXML
    void weiter(ActionEvent event) {

        FlugGesManager fm = new FlugGesManager();
        fm.setVorname(firstname_tf.getText());
        fm.setNachname(lastname_tf.getText());
        fm.setUsername(username_tf.getText());


        String xx = "";
       for (int i=0; i<=(password_pf.getText()).length(); i++){
           xx=xx+"*";
        }

       fm.setPasswort(xx);

        //Dateien validieren
        String message = dataValidate(fm);

        if(!"".equals(message)){
            //Alert zeigen
            WindowUtil.alertWindow("Error", Alert.AlertType.ERROR, message);
        }else{
            org.hibernate.Session session = SessionFactoryUtil.getInstance().getSessionFactory().openSession();
            session.beginTransaction();
            // here hash and salt

            if (geschlect_cb.getValue()=="Männlich") {
                fm.setGeschlecht("männlich");
            } else
            {
                fm.setGeschlecht("weiblich");
            }

            session.save( fm);
            session.getTransaction().commit();
            session.close();

            //*Hash and Salt ---Rachid Methode nutzen!!!
            LoginModel logDat = new LoginModel();
            try {
               logDat.
                       insert(username_tf.getText(),password_pf.getText(),"Manager");//
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
            //End Hash and Salt

            WindowUtil.alertWindow("Richtig", Alert.AlertType.CONFIRMATION, "Dateien korrekt eingegeben!");
            dataClean();
        }
    }

    private void dataClean() {
        firstname_tf.setText("");
        lastname_tf.setText("");
        username_tf.setText("");
        password_pf.setText("");
    }

    private String dataValidate(FlugGesManager fm) {
        StringBuilder sb= new StringBuilder();
        sb.append("");
        if(fm.getVorname()==null || "".equals(fm.getVorname())){
            sb.append("Vorname muss eingeben werden\n");
        }
        if(fm.getNachname()==null || "".equals(fm.getNachname())){
            sb.append("Nachname muss eigegeben werden\n");
        }
        if(fm.getUsername()==null || "".equals(fm.getUsername())){
            sb.append("Username muss eingegeben werden\n");
        }
        if(fm.getPasswort()==null || "".equals(fm.getPasswort())){
            sb.append("Passwort muss eingegeben werden\n");
        }
        return sb.toString();
    }


    @FXML
    public void initialize() {
        geschlect_cb.setItems(FXCollections.observableArrayList(
                "Männlich", "Weiblich"));
        geschlect_cb.setValue("Weiblich");
    }
}