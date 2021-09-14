package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Kunde;
import models.LoginModel;
import models.SessionFactoryUtil;
import utils.WindowUtil;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class KundeAnlegenController {

    @FXML
    private TextField firstname_tf;

    @FXML
    private TextField lastname_tf;

    @FXML
    private PasswordField password_pf;

    @FXML
    private Button btnEingabe;

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
    void abbrechen(ActionEvent event) {

        Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();

    }

    @FXML
    void weiter(ActionEvent event) {

        Kunde kunde = new Kunde();

        kunde.setVorname(firstname_tf.getText());
        kunde.setNachname(lastname_tf.getText());
        kunde.setAdresse(adresse_tf.getText());
        kunde.setPostfach(postfach_tf.getText());
        kunde.setTelefonnummer(telefonNr_tf.getText());
        kunde.setUsername(username_tf.getText());

        String fillingSymbol = "";
        for (int i = 0; i <= (password_pf.getText()).length(); i++) {
            fillingSymbol = fillingSymbol + "*";
        }

        kunde.setPasswort(fillingSymbol);

        //Validate the given data
        String infoMessage = validationCheck(kunde);

        if (!"".equals(infoMessage)) {
            // Show my error alert
            WindowUtil.alertWindow("Error", Alert.AlertType.ERROR, infoMessage);

        } else {
            org.hibernate.Session kundeLoginSession = SessionFactoryUtil.getInstance().getSessionFactory().openSession();
            kundeLoginSession.beginTransaction();

            kundeLoginSession.save(kunde);
            kundeLoginSession.getTransaction().commit();
            kundeLoginSession.close();

            // password hash and salt

            LoginModel kundeLogin = new LoginModel();

            try {
                kundeLogin.insert(username_tf.getText(), password_pf.getText(), "Kunde");

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
            // Show my correct alert
            WindowUtil.alertWindow("Perfect!", Alert.AlertType.CONFIRMATION, "Your data has been successfully saved");
            clearInfo();
        }

    }

    private void clearInfo() {
        firstname_tf.setText("");
        lastname_tf.setText("");
        adresse_tf.setText("");
        postfach_tf.setText("");
        telefonNr_tf.setText("");
        username_tf.setText("");
        password_pf.setText("");
    }

    private String validationCheck(Kunde kunde) {

        StringBuilder string_builder = new StringBuilder();
        string_builder.append("");

        if (kunde.getVorname() == null || "".equals(kunde.getVorname())) {
            string_builder.append("You must give in your Firstname\n");
        }
        if (kunde.getNachname() == null || "".equals(kunde.getNachname())) {
            string_builder.append("You must give in your Lastname\n");
        }
        if (kunde.getAdresse() == null || "".equals(kunde.getAdresse())) {
            string_builder.append("You must give in your Address\n");
        }
        if (kunde.getPostfach() == null || "".equals(kunde.getPostfach())) {
            string_builder.append("You must give in your Postcode\n");
        }
        if (kunde.getTelefonnummer() == null || "".equals(kunde.getTelefonnummer())) {
            string_builder.append("You must give in your Telephone number\n");
        }
        if (kunde.getUsername() == null || "".equals(kunde.getUsername())) {
            string_builder.append("You must give in your Username\n");
        }
        if (kunde.getPasswort() == null || "".equals(kunde.getPasswort())) {
            string_builder.append("You must give in your Password\n");
        }

        return string_builder.toString();

    }

    @FXML
    public void initialize() {

    }
}


