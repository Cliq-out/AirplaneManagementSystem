package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.AppSession;
import models.Kunde;
import models.LoginModel;
import models.SessionFactoryUtil;
import org.hibernate.query.Query;
import utils.WindowUtil;

import java.util.List;

public class KundendatenLoeschenController {

    @FXML
    private TextField firstname_tf;

    @FXML
    private TextField lastname_tf;

    @FXML
    private PasswordField password_pf;

    @FXML
    private Button btnLoeschen;

    @FXML
    private Button btnAbbrechen;

    @FXML
    private TextField adresse_tf;

    @FXML
    private TextField postfach_tf;

    @FXML
    private TextField telefonNr_tf;

    @FXML
    private TextField username_tf;

    @FXML
    private TextField cus_id;

    @FXML
    void abbrechen(ActionEvent event) {

        Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();

    }

    @FXML
    void Loeschen(ActionEvent event) {

        org.hibernate.Session changeCustomerDataSession = SessionFactoryUtil.getInstance().getSession();
        changeCustomerDataSession.beginTransaction();

        try {

            String customer_UN = username_tf.getText();
            Query<Kunde> myQuery = changeCustomerDataSession.createQuery("FROM Kunde k WHERE k.username = : customerUN");


            myQuery.setParameter("customerUN", customer_UN);

            List<Kunde> customerList = myQuery.list();
            Kunde record = customerList.get(0);

            record.setVorname(firstname_tf.getText());
            record.setNachname(lastname_tf.getText());
            record.setAdresse(adresse_tf.getText());
            record.setPostfach(postfach_tf.getText());
            record.setTelefonnummer(telefonNr_tf.getText());
            record.setUsername(username_tf.getText());


            WindowUtil.alertWindow("Warning", Alert.AlertType.WARNING,"Are you sure you want to delete your personal data?");

            changeCustomerDataSession.delete(record);

            //To update password with hash and salt class

            LoginModel customerLogin = new LoginModel();
            customerLogin.insert(username_tf.getText(), password_pf.getText(), "Kunde");
            changeCustomerDataSession.getTransaction().commit();
            // Show my correct alert
            WindowUtil.alertWindow("Perfect!", Alert.AlertType.CONFIRMATION, "Your data has been successfully deleted");
            clearInfo();


            if (changeCustomerDataSession.getTransaction().isActive()) {
                changeCustomerDataSession.getTransaction().commit();
            }

        } catch (Exception e) {
            try {
                changeCustomerDataSession.getTransaction().rollback();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();

        } finally {
            changeCustomerDataSession.close();

        }
    }


    @FXML
    public void  initialize(){

        org.hibernate.Session CustomerSession = SessionFactoryUtil.getInstance().getSession();
        String CustomerUN = AppSession.getIntance().getCustomerUsername();

        Query<Kunde> query1 = CustomerSession.createQuery("FROM Kunde k WHERE k.username =:CusUN");
        query1.setParameter("CusUN", CustomerUN);
        List<Kunde> cusList = query1.list();
        Kunde rec = cusList.get(0);

        int idCus = rec.getId_kunde();
        String Firstname = rec.getVorname();
        String Lastname = rec.getNachname();
        String Username = rec.getUsername();
        String Adress = rec.getAdresse();
        String postfach = rec.getPostfach();
        String TelNr = rec.getTelefonnummer();
        String Password = rec.getPasswort();

        cus_id.setText(Integer.toString(idCus));
        firstname_tf.setText(Firstname);
        lastname_tf.setText(Lastname);
        adresse_tf.setText(Adress);
        postfach_tf.setText(postfach);
        telefonNr_tf.setText(TelNr);
        username_tf.setText(Username);
        password_pf.setText(Password);


    }

    private void clearInfo() {
        firstname_tf.setText("");
        lastname_tf.setText("");
        adresse_tf.setText("");
        postfach_tf.setText("");
        telefonNr_tf.setText("");
        username_tf.setText("");
        password_pf.setText("");
        cus_id.setText("");
    }

}



