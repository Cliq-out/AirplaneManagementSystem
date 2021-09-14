package controller;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.WindowUtil;

import java.util.List;

import static models.AppSession.*;

public class KundendatenänderungController{

    @FXML
    private TextField cus_id;

    @FXML
    private TextField firstname_tf;

    @FXML
    private TextField lastname_tf;

    @FXML
    private Button btnAendern;

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
    private PasswordField altpassword_pf;

    @FXML
    private PasswordField neupassword_pf;

    @FXML
    void abbrechen(ActionEvent event) {

        Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();

    }

    @FXML
    void Aendern(ActionEvent event) {

        org.hibernate.Session changeCustomerDataSession = SessionFactoryUtil.getInstance().getSession();
        changeCustomerDataSession.beginTransaction();

        try {

            String customer_UN = username_tf.getText();


            Query<Kunde> myQuery = changeCustomerDataSession.createQuery("FROM Kunde k WHERE k.username = : customerUN");


            myQuery.setParameter("customerUN", customer_UN);

            List<Kunde> customerList = myQuery.list();
            Kunde record = customerList.get(0); // my chosen ID

            record.setVorname(firstname_tf.getText());
            record.setNachname(lastname_tf.getText());
            record.setAdresse(adresse_tf.getText());
            record.setPostfach(postfach_tf.getText());
            record.setTelefonnummer(telefonNr_tf.getText());
            record.setUsername(username_tf.getText());
            record.setPasswort(neupassword_pf.getText());



            changeCustomerDataSession.update(record);

            //To update password with hash and salt class

            LoginModel customerLogin = new LoginModel();
            customerLogin.update(username_tf.getText(), altpassword_pf.getText(), neupassword_pf.getText());
            changeCustomerDataSession.getTransaction().commit();
            // Show my correct alert
            WindowUtil.alertWindow("Perfect!", Alert.AlertType.CONFIRMATION, "Your data has been successfully changed");



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
        cus_id.setDisable(true);
        firstname_tf.setText(Firstname);
        lastname_tf.setText(Lastname);
        adresse_tf.setText(Adress);
        postfach_tf.setText(postfach);
        telefonNr_tf.setText(TelNr);
        username_tf.setText(Username);
        username_tf.setDisable(true);
        altpassword_pf.setText(Password);



    }

    }




