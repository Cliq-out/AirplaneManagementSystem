package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.FlugGesManager;
import models.SessionFactoryUtil;
import org.hibernate.query.Query;
import utils.WindowUtil;

import java.util.List;

public class
ManagerLoeschenController {

    @FXML
    private TextField vorname_tf;

    @FXML
    private Button btnLoeschen;

    @FXML
    private Button btnAbbrechen;

    @FXML
    private TextField nachname_tf;

    @FXML
    private TableView<FlugGesManager> tableManager;

    @FXML
    private TableColumn<FlugGesManager, Integer> idcol1;

    @FXML
    private TableColumn<FlugGesManager, String> idcol2;

    @FXML
    private TableColumn<FlugGesManager, String> idcol3;

    @FXML
    private TextField idmanager_tf;

    @FXML
    void Abbrechen(ActionEvent event) {
        Stage stage= (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    @FXML
    void Loeschen(ActionEvent event) {
        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
        session.beginTransaction();

        try {

            int id_man = Integer.parseInt(idmanager_tf.getText());
            Query<FlugGesManager> query02 = session.createQuery("FROM FlugGesManager fm WHERE fm.id_manager = :managerid");
            query02.setParameter("managerid", id_man);

            List<FlugGesManager> flugGesManagerList = query02.list();

            FlugGesManager fm = flugGesManagerList.get(0);
            session.delete(fm);
           // session.flush();//unwichtig
            WindowUtil.alertWindow("Richtig", Alert.AlertType.CONFIRMATION, "Dateien gelöscht!");
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
    }


    @FXML
    public void initialize() {
        idmanager_tf.setDisable(true);
        idcol1.setCellValueFactory(new PropertyValueFactory<>("id_manager"));
        idcol2.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        idcol3.setCellValueFactory(new PropertyValueFactory<>("nachname"));
        
        tableManager.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                -> {
            if (tableManager.getSelectionModel().getSelectedItem() != null) {
                int id_man = tableManager.getSelectionModel().getSelectedItem().getId_manager();

                org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();
                Query<FlugGesManager> query01 = session1.createQuery("FROM FlugGesManager fm WHERE fm.id_manager = :managerid");
                query01.setParameter("managerid", id_man);

                List<FlugGesManager> fluggesmanagerList = query01.list();
                FlugGesManager flugesmanager_rec = fluggesmanagerList.get(0);//what we´ve selected

                String idm = Integer.toString(flugesmanager_rec.getId_manager());
                idmanager_tf.setText(idm);
                vorname_tf.setText(flugesmanager_rec.getVorname());
                nachname_tf.setText(flugesmanager_rec.getNachname());
            }
        });


        fillTableFlugGesManager();
    }

    public void fillTableFlugGesManager() {
        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
        Query<FlugGesManager> query = session.createQuery("FROM FlugGesManager");
        List<FlugGesManager> flugGesManagerList = query.list();
        tableManager.getItems().clear();//clean if exists data

        for (FlugGesManager fm : flugGesManagerList) {
            tableManager.getItems().add(fm);
        }
    }
}