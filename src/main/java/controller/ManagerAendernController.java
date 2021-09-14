package  controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.FlugGesManager;
import models.LoginModel;
import models.SessionFactoryUtil;
import org.hibernate.query.Query;
import utils.WindowUtil;

import java.util.List;

public class ManagerAendernController  {

    @FXML
    private TextField idmanager_tf;

    @FXML
    private TextField vorname_tf;

    @FXML
    private TextField nachname_tf;

    @FXML
    private TextField username_tf;

    @FXML
    private ChoiceBox<String> geschlecht_cb;

    @FXML
    private PasswordField altpassword_pf;

    @FXML
    private PasswordField neupassword_pf;


    @FXML
    private Button btnAendern;

    @FXML
    private Button btnAbbrechen;

    @FXML
    private TableView<FlugGesManager> tableManager;

    @FXML
    private TableColumn<FlugGesManager, Integer> idcol1;//id_manager

    @FXML
    private TableColumn<FlugGesManager, String> idcol2;//vorname

    @FXML
    private TableColumn<FlugGesManager, String> idcol3;//nachname


    @FXML
    void Abbrechen(ActionEvent event) {
        Stage stage= (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    @FXML
    void Aendern(ActionEvent event) {

        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
        session.beginTransaction();
        try {

            int id_man = Integer.parseInt(idmanager_tf.getText());
            Query<FlugGesManager> query02 = session.createQuery("FROM FlugGesManager fm WHERE fm.id_manager = :managerid");

            query02.setParameter("managerid",id_man);

            List<FlugGesManager> flugGesManagerList = query02.list();
            FlugGesManager fm = flugGesManagerList.get(0);//selected ID

            String oldPass = fm.getPasswort();


            fm.setVorname(vorname_tf.getText());
            fm.setNachname(nachname_tf.getText());
            fm.setGeschlecht(geschlecht_cb.getValue());
            fm.setGeschlecht(geschlecht_cb.getValue());

            session.update(fm);
            //funktioniert noch nicht

            //Hash und Salt abrufen
            //
            LoginModel loginModel = new LoginModel();
            loginModel.update(username_tf.getText(), altpassword_pf.getText(),neupassword_pf.getText());
            session.getTransaction().commit();
            //-------------------------

            WindowUtil.alertWindow("Richtig", Alert.AlertType.CONFIRMATION, "Dateien wurden geändert!");
            dataClean();


            if (session.getTransaction().isActive()) {
                session.getTransaction().commit();
            }

            fillTableFlugGesManager();
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            //SessionFactoryUtil.getInstance().closeSession();
        }
    }


    private void dataClean() {
        idmanager_tf.setText("");
        vorname_tf.setText("");
        nachname_tf.setText("");
        username_tf.setText("");
        altpassword_pf.setText("");
        neupassword_pf.setText("");
    }



    @FXML
    public void initialize() {
        geschlecht_cb.setItems(FXCollections.observableArrayList(
                "männlich", "weiblich"));

        idcol1.setCellValueFactory(new PropertyValueFactory<>("id_manager"));
        idcol2.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        idcol3.setCellValueFactory(new PropertyValueFactory<>("nachname"));


        tableManager.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                -> { if (tableManager.getSelectionModel().getSelectedItem() != null)
        {     int id_man = tableManager.getSelectionModel().getSelectedItem().getId_manager();

            org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();
            Query<FlugGesManager> query01 = session1.createQuery("FROM FlugGesManager fm WHERE fm.id_manager = :managerid");
            query01.setParameter("managerid",id_man);

            List<FlugGesManager> fluggesmanagerList = query01.list();
            FlugGesManager flugesmanager_rec  = fluggesmanagerList.get(0);//what we´ve selected

            String idm =  Integer.toString(flugesmanager_rec.getId_manager());
            idmanager_tf.setText(idm);
            vorname_tf.setText(flugesmanager_rec.getVorname());
            nachname_tf.setText(flugesmanager_rec.getNachname());
            username_tf.setText(flugesmanager_rec.getUsername());
            geschlecht_cb.setValue(flugesmanager_rec.getGeschlecht());

        }
        });

        fillTableFlugGesManager();
    }


    public void fillTableFlugGesManager() {
        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
        Query<FlugGesManager> query = session.createQuery("FROM FlugGesManager");
        List<FlugGesManager> flugGesManagerList = query.list();
        tableManager.getItems().clear();//clean if the table is full

        for (FlugGesManager fm : flugGesManagerList) {
            tableManager.getItems().add(fm);
        }
    }
}