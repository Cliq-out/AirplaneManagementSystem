package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.*;
import org.hibernate.query.Query;
import utils.UtilApp;
import utils.WindowUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FluglinieInstanceController {

    @FXML
    private Button btnAbbrechen;

    @FXML
    private TextField id_tf;

    @FXML
    private TextField userid_tf;

    @FXML
    private TableView<FluglinieAnlegen> tableFluglinie;


    @FXML
    private TableColumn<FluglinieAnlegen, Integer> fluglinie;

    @FXML
    private TableColumn<FluglinieAnlegen, String> start;

    @FXML
    private TableColumn<FluglinieAnlegen, String> Ziel;

    @FXML
    private TableColumn<FluglinieAnlegen, String> interval;

    @FXML
    private TableColumn<FluglinieAnlegen, Date> start_id;

    @FXML
    private TableColumn<FluglinieAnlegen, Integer> nr_eco;

    @FXML
    private TableColumn<FluglinieAnlegen, Double> pr_eco;

    @FXML
    private TableColumn<FluglinieAnlegen, Integer> nr_bus;

    @FXML
    private TableColumn<FluglinieAnlegen, Double> pr_bus;

    @FXML
    private TableColumn<FluglinieAnlegen, String> Model;

    @FXML
    void abbrechen(ActionEvent event) {
        Stage stage= (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    @FXML
    void instanziieren (ActionEvent event) {
        int idRuta = 0;
        List<Fluge> lFluge = new ArrayList<>();
        FluglinieAnlegen fluglinieAnlegen = null;

        //1.Selected iten idfluglinie
        idRuta= tableFluglinie.getSelectionModel().getSelectedItem().getId_fluglinie();
        //2.Select idroute and find out its data
        org.hibernate.Session session2 = SessionFactoryUtil.getInstance().getSession();

        Query<FluglinieAnlegen> query02 = session2.createQuery("FROM FluglinieAnlegen fl WHERE fl.id_fluglinie = :idRoute");
        query02.setParameter("idRoute",idRuta);

        List<FluglinieAnlegen> fluglinieList = query02.list();
        fluglinieAnlegen =fluglinieList.get(0);

        //3. create flights
        generateFluge(fluglinieAnlegen, lFluge);//3
        //4 .insert flights db
        if(saveFluge(lFluge)){
            WindowUtil.alertWindow("Richtig", Alert.AlertType.CONFIRMATION, "Flights were instanced!!!");
            refreshTable();

        }else{
            WindowUtil.alertWindow("Falsch", Alert.AlertType.ERROR, "A Problem occurred, try it later!!!");

        }
    }

    public void generateFluge(FluglinieAnlegen fluglinieAnlegen, List<Fluge> datas) {
        //1) every day, 2) every three days, 3) once a week
        Date startdate = fluglinieAnlegen.getStartdatum();
        Date enddate = UtilApp.sumMonthsDate(startdate, 6);
        Calendar today = Calendar.getInstance();

        if (fluglinieAnlegen.getInterval().equals("täglich")) {
            while (startdate.before(enddate)) {
                Fluge fluge = new Fluge();
                fluge.setAbfahrtdate(new java.sql.Date(startdate.getTime()));
                fluge.setIdfluglinie(fluglinieAnlegen.getId_fluglinie());

                fluge.setEcoseatact(fluglinieAnlegen.getEcoseats());
                fluge.setBusseatact(fluglinieAnlegen.getBussseats());
                fluge.setInstancedate(today);
                fluge.setStatus("aktiv");
                fluge.setGewinn(0.00);//Evita excepciones
                fluge.setEcosold(0);
                fluge.setBussold(0);
                datas.add(fluge);
                startdate = UtilApp.sumDaysDate(startdate, 1);
            }
        } else if (fluglinieAnlegen.getInterval().equals("wöchentlich")) {
            while (startdate.before(enddate)) {
                Fluge fluge = new Fluge();
                fluge.setAbfahrtdate(new java.sql.Date(startdate.getTime()));
                fluge.setIdfluglinie(fluglinieAnlegen.getId_fluglinie());

                fluge.setEcoseatact(fluglinieAnlegen.getEcoseats());
                fluge.setBusseatact(fluglinieAnlegen.getBussseats());
                fluge.setInstancedate(today);
                fluge.setStatus("aktiv");
                datas.add(fluge);
                startdate = UtilApp.sumDaysDate(startdate, 7);
            }
        } else if (fluglinieAnlegen.getInterval().equals("alle drei Tage")) {
            while (startdate.before(enddate)) {
                Fluge fluge = new Fluge();
                fluge.setAbfahrtdate(new java.sql.Date(startdate.getTime()));
                fluge.setIdfluglinie(fluglinieAnlegen.getId_fluglinie());

                fluge.setEcoseatact(fluglinieAnlegen.getEcoseats());
                fluge.setBusseatact(fluglinieAnlegen.getBussseats());
                fluge.setInstancedate(today);
                fluge.setStatus("aktiv");
                datas.add(fluge);
                startdate = UtilApp.sumDaysDate(startdate, 3);
            }
        }
    }

    public boolean saveFluge(List<Fluge> datas)
    {
        org.hibernate.Session session =null;
        try{
            session = SessionFactoryUtil.getInstance().getSession();
            session.beginTransaction();
            for (Fluge fluge:datas
            ) {
                session.persist(fluge);
            }

            int idflin = tableFluglinie.getSelectionModel().getSelectedItem().getId_fluglinie();

            Query<FluglinieAnlegen> query02 = session.createQuery("FROM FluglinieAnlegen fl WHERE fl.id_fluglinie = :idflin");
            query02.setParameter("idflin",idflin);
            List<FluglinieAnlegen> fluglinieList = query02.list();
            FluglinieAnlegen fluglinieAnlegen =fluglinieList.get(0);
            fluglinieAnlegen.setInstance("instanced");//***
            session.persist(fluglinieAnlegen);

            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    public void initialize() {
        try {
            int idmanager = AppSession.getIntance().getId_manager();
            String username = AppSession.getIntance().getUsername();

            id_tf.setText(Integer.toString(idmanager));
            id_tf.setDisable(true);
            userid_tf.setText(username);
            userid_tf.setDisable(true);
            refreshTable();

            tableFluglinie.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                    -> {

                if (tableFluglinie.getSelectionModel().getSelectedItem() != null)
                {    int id_fl = tableFluglinie.getSelectionModel().getSelectedItem().getId_fluglinie();//1
                    Date startD= tableFluglinie.getSelectionModel().getSelectedItem().getStartdatum();//2
                    String interval= tableFluglinie.getSelectionModel().getSelectedItem().getInterval();//3
                    Double ecoP = tableFluglinie.getSelectionModel().getSelectedItem().getEcopreis();//4
                    Double busP = tableFluglinie.getSelectionModel().getSelectedItem().getBusspreis();//5
                    int ecoS = tableFluglinie.getSelectionModel().getSelectedItem().getEcoseats();//6
                    int busS = tableFluglinie.getSelectionModel().getSelectedItem().getBussseats();//7
                    int flugG = tableFluglinie.getSelectionModel().getSelectedItem().getFluggesellschaft();//8
                    String startN= tableFluglinie.getSelectionModel().getSelectedItem().getStartname();//9
                    String zielN= tableFluglinie.getSelectionModel().getSelectedItem().getZielname();//10
                    String mod= tableFluglinie.getSelectionModel().getSelectedItem().getModel();//11
                    int dist = tableFluglinie.getSelectionModel().getSelectedItem().getDistance();//12
                    String ins =tableFluglinie.getSelectionModel().getSelectedItem().getInstance();//13
                    Date endD =tableFluglinie.getSelectionModel().getSelectedItem().getEnddatum();//14
/*
                 fluglinieAnlegen.setId_fluglinie(id_fl);//1
                 ........
 */
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Boolean refreshTable(){
        Boolean result=false;
        try{
            tableFluglinie.getItems().clear();
            org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();

            int fluggessuche = AppSession.getIntance().getId_gesellschaft();

            //Bitte nicht ändern
            Query<FluglinieAnlegen> query01 = session1.createQuery("FROM FluglinieAnlegen fm WHERE fm.fluggesellschaft = :fluggesID and fm.instance =''");
            //*** Kommentar von Brenda: Das soll nicht geändert werden, habe ich schon mit den Doktoranden validiert

            query01.setParameter("fluggesID", fluggessuche);
            List<FluglinieAnlegen> fluglinieList = query01.list();

            fluglinie.setCellValueFactory(new PropertyValueFactory<>("id_fluglinie"));
            start.setCellValueFactory(new PropertyValueFactory<>("startname"));
            Ziel.setCellValueFactory(new PropertyValueFactory<>("zielname"));
            interval.setCellValueFactory(new PropertyValueFactory<>("interval"));
            start_id.setCellValueFactory(new PropertyValueFactory<>("startdatum"));
            nr_eco.setCellValueFactory(new PropertyValueFactory<>("ecoseats"));
            pr_eco.setCellValueFactory(new PropertyValueFactory<>("ecopreis"));
            nr_bus.setCellValueFactory(new PropertyValueFactory<>("bussseats"));
            pr_bus.setCellValueFactory(new PropertyValueFactory<>("busspreis"));
            Model.setCellValueFactory(new PropertyValueFactory<>("model"));

            for (FluglinieAnlegen fl : fluglinieList) {
                tableFluglinie.getItems().add(fl);
            }
            result= true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}