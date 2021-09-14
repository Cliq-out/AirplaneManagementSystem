package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.AppSession;
import models.Postfach;
import models.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.sql.Timestamp;
import java.util.List;

    public class KundePostfachController {


        @FXML
        private TableView<Postfach> all_messages;

        @FXML
        private TableColumn<Postfach, Timestamp> dateTime;

        @FXML
        private TableColumn<Postfach, String> subject;


        @FXML
        private Button btnZurück;

        @FXML
        private TextArea messageView;

        @FXML
        void Zurück(ActionEvent event) {

            Stage stage = (Stage) btnZurück.getScene().getWindow();
            stage.close();
        }

    @FXML
    public void initialize() {

        dateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        all_messages.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                -> { if (all_messages.getSelectionModel().getSelectedItem() != null) {

                     int CustomerUName = all_messages.getSelectionModel().getSelectedItem().getId_postfach();

                     org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();
                     Query<Postfach> query01 = session1.createQuery("FROM Postfach k WHERE k.id_postfach =:CusUN");
                     query01.setParameter("CusUN",CustomerUName);

                     List<Postfach> cusList = query01.list();
                     Postfach rec = cusList.get(0);

                subject.setText(rec.getSubject());
                dateTime.setText(String.valueOf(rec.getDateTime()));
                messageView.setText(rec.getMessage());
            }
             });

        addAllMessages();

    }

        public void addAllMessages(){

           Session CustomerSession = SessionFactoryUtil.getInstance().getSession();
            String CustomerUN = AppSession.getIntance().getCustomerUsername();
            Query<Postfach> query1 = CustomerSession.createQuery("FROM Postfach k WHERE k.username =:CusUN");
            query1.setParameter("CusUN", CustomerUN);
            List<Postfach> cusList = query1.list();

            for (Postfach customerList : cusList) {
                all_messages.getItems().add(customerList);


            }

            }
    }


