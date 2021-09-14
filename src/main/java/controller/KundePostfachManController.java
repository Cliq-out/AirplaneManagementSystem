package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Kunde;
import models.Postfach;
import models.SessionFactoryUtil;
import org.hibernate.Query;
import utils.WindowUtil;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class KundePostfachManController {

    @FXML
    private TextField username_tf;

    @FXML
    private ComboBox<Integer> customer_id;

    @FXML
    private Button btnZurück;

    @FXML
    private Button sendenbtn;

    @FXML
    private TextArea message;

    @FXML
    private TextField subject;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    void Zurück(ActionEvent event) {

        Stage stage = (Stage) btnZurück.getScene().getWindow();
        stage.close();

    }

    @FXML
    void sendMessage(ActionEvent event) {

        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

        Postfach postfach = new Postfach();

        postfach.setUsername(username_tf.getText());

        postfach.setId_kunde(customer_id.getValue());

        postfach.setSubject(subject.getText());
        postfach.setMessage(message.getText());
        postfach.setDateTime(currentTimestamp);

        String infoMessage = validationCheck(postfach);

        if (!"".equals(infoMessage)) {
            // Show my error alert
            WindowUtil.alertWindow("Error", Alert.AlertType.ERROR, infoMessage);

        } else {

            try {

                org.hibernate.Session postfachSession = SessionFactoryUtil.getInstance().getSessionFactory().openSession();
                postfachSession.beginTransaction();

                postfachSession.save(postfach);
                postfachSession.getTransaction().commit();
                postfachSession.close();


            } catch (Exception e) {

                e.printStackTrace();
            }
        }
            // Show my correct alert
            WindowUtil.alertWindow("Perfect!", Alert.AlertType.CONFIRMATION, "Your message has been successfully sent");
        }

    @FXML
    void showCustomerID(ActionEvent event) {

    }

    private String validationCheck( Postfach postfach) {

        StringBuilder string_builder = new StringBuilder();

        if (postfach.getSubject() == null || "".equals(postfach.getSubject())) {
            string_builder.append("You must give in the subject of your subject\n");
        }
        if (postfach.getMessage() == null || "".equals(postfach.getMessage())) {
            string_builder.append("You must give in your message\n");
        }
        if (postfach.getUsername() == null || "".equals(postfach.getUsername())) {
            string_builder.append("You must give in your Username\n");
        }

        if (postfach.getId_kunde() == 0 || "".equals(postfach.getId_kunde())) {
            string_builder.append("You must give in customer ID\n");
        }
        return string_builder.toString();

    }

    @FXML
    public void initialize() {

        username_tf.setDisable(true);
        firstname.setDisable(true);
        lastname.setDisable(true);

        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
        String query1 = "SELECT p.id_kunde FROM Kunde p";
        List<Integer> cusList= session.createQuery(query1).list();

        ObservableList<Integer> customerIdList = FXCollections.observableArrayList();

        customerIdList.addAll(cusList);
        customer_id.setItems(customerIdList);

        customer_id.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                -> {
            if (customer_id.getSelectionModel().getSelectedItem() != null) {
                int cusID = customer_id.getValue();
                String query2 = "FROM Kunde p where p.id_kunde =: idKunde";
                Query<Kunde> query02 = session.createQuery(query2);
                query02.setParameter("idKunde", cusID);
                List<Kunde> data = query02.list();
                Kunde rec = data.get(0);

                String username = rec.getUsername();
                String vorname = rec.getVorname();
                String nachname = rec.getNachname();

                username_tf.setText(username);
                firstname.setText(vorname);
                lastname.setText(nachname);

            }
        } );

    }
}




