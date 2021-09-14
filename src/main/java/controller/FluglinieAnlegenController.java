package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.WindowUtil;

import java.sql.Date;

public class FluglinieAnlegenController {

    private int distanceAirports;
    private int id_co;

    @FXML
    private ComboBox<String> from;

    @FXML
    private ComboBox<String> to;

    @FXML
    private DatePicker date;

    @FXML
    private ChoiceBox<String> interval;

    @FXML
    private ComboBox<String> flugzeug;

    @FXML
    private TextField Fluggesellschaft;

    @FXML
    private TextField e_sitNr1;

    @FXML
    private TextField b_sitNr;

    @FXML
    private TextField e_price;

    @FXML
    private TextField b_price;

    @FXML
    private TextField seatTotal_tf;


    @FXML
    private Button btnAnlegen;

    @FXML
    private Button btnAbbrechen;



    @FXML
    void abbrechen(ActionEvent event) {
        Stage stage= (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    @FXML
    void showFlugzeugOptions(ActionEvent event) {}

    @FXML
    void showOptionsFrom(ActionEvent event) {}

    @FXML
    void showOptionsTo(ActionEvent event) {}

    @FXML
    void calculateSeats(ActionEvent event) {
        int numTotalSeats =Integer.parseInt(seatTotal_tf.getText());
        int numEconSeats =Integer.parseInt(e_sitNr1.getText());
        int numBussinesSeats =0;

        numBussinesSeats=(numTotalSeats-numEconSeats)/2;
        b_sitNr.setText("");

        if(numBussinesSeats >= 0.125*numTotalSeats){
            WindowUtil.alertWindow("Falsch", Alert.AlertType.ERROR, "The number of ecoseats has to be minimum 87.5% of the total!");
        }else{
            b_sitNr.setText("" + numBussinesSeats);
        }
    }

    @FXML
    void weiter(ActionEvent event) {

        String mens=validateFields();
        if(!"".equals(mens)){

            WindowUtil.alertWindow("Falsch", Alert.AlertType.ERROR, "Insert all fields. "+mens);
        }
        else {
            Session session = SessionFactoryUtil.getInstance().getSessionFactory().openSession();
            session.beginTransaction();
            FluglinieAnlegen fl = new FluglinieAnlegen();

            // here my choicebox definition
            if (interval.getValue() == "täglich") {
                fl.setInterval("täglich");

            } else if (interval.getValue() == "alle drei Tage") {
                fl.setInterval("alle drei Tage");

            } else {
                fl.setInterval("wöchentlich");
            }

            // here fluglinie id

            fl.setStartname(from.getValue());
            fl.setZielname(to.getValue());
            fl.setInterval(interval.getValue());
            fl.setModel(flugzeug.getValue());

            //StartDatum
            Date start = Date.valueOf(date.getValue());
            fl.setStartdatum(start);//Muss noch gestestet werden

            //Sitzplaetze-Combobox Flugzeug

            int eco = Integer.parseInt(e_sitNr1.getText());
            fl.setEcoseats(eco);
            int tot = Integer.parseInt(seatTotal_tf.getText());
            int bus = tot - eco;
            //int bus = Integer.parseInt(b_sitNr.getText());

            fl.setBussseats(bus);
            fl.setEcopreis(Double.parseDouble(e_price.getText()));
            fl.setBusspreis(Double.parseDouble(b_price.getText()));
            fl.setFluggesellschaft(id_co);
            fl.setDistance(distanceAirports);
            fl.setInstance("");//OJO

            session.save(fl);
            session.getTransaction().commit();
            session.close();
            WindowUtil.alertWindow("Richtig", Alert.AlertType.CONFIRMATION, "Data was inserted");
            dataClean();
        }
    }
    private String validateFields(){
        String msg ="";
        if(from.getSelectionModel().getSelectedItem() == null){
            msg+= "\n Von is required\n";
        }
        if(to.getSelectionModel().getSelectedItem() == null){
            msg+= " Nach is required\n";
        }
        if(date.getValue() == null){
            msg+= " Ab is required\n";
        }
        if(interval.getSelectionModel().getSelectedItem() == null){
            msg+= " interval is required\n";
        }
        if(flugzeug.getSelectionModel().getSelectedItem() == null){
            msg+= " flugzeug is required\n";
        }
        if("".equals(e_sitNr1.getText())){
            msg+= " No.Economy Seats is required\n";
        }
        if("".equals(e_price.getText())){
            msg+= " Economy Seats Price is required\n";
        }
        if("".equals(b_price.getText())){
            msg+= " Bussiness Seats Price is required\n";
        }
        return msg;
    }

    @FXML
    public void initialize() {
        //Geloggte Manager bzw. Gesellschaft
        Fluggesellschaft.setDisable(true);
        Fluggesellschaft.setText(AppSession.getIntance().getGesellshaftname());


        //Lists Flughafen und Flugzeuge
        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();

        String query1 = "SELECT f.name FROM Flughaefen f where f.name <>''";
        List<String> data1 = session.createQuery(query1).list();

        //  Hier soll flugzeugtyp von flugges-flugmanager
        //String query2 = "SELECT a.flugzeugtyp FROM Flugzeuge a";
        id_co = AppSession.getIntance().getId_gesellschaft();
        String query2 = "SELECT flugzeugtyp FROM gekauftesFlugzeug gkf where gkf.id_fluggesellschaft =: id_co";

        Query<String> query = session.createQuery(query2);
        query.setParameter("id_co",id_co);
        List<String> data2 = query.list();
        // String query02= "SELECT a.flugzeugtyp FROM Flugzeuge a"; where Gesellschaft =Gess

        ObservableList<String> airportsStart = FXCollections.observableArrayList();
        ObservableList<String> airportsEnd = FXCollections.observableArrayList();
        //  Flugzeuge
        ObservableList<String> flugzeugeObsList = FXCollections.observableArrayList();


        airportsStart.addAll(data1);
        airportsEnd.addAll(data1);
        flugzeugeObsList.addAll(data2);

        from.setItems(airportsStart);
        to.setItems(airportsStart);
        flugzeug.setItems(flugzeugeObsList);

        //Interval Choicebox

        interval.setItems(FXCollections.observableArrayList(
                "täglich", "alle drei Tage", "wöchentlich"));
        interval.setValue("täglich");

        seatTotal_tf.setDisable(true);
        b_sitNr.setDisable(true);


        from.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                -> {
            calcDistanceAirports();
        }  );


        to.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                -> {
            calcDistanceAirports();
            System.out.println("Distance  "+distanceAirports);

        }  );


        flugzeug.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                -> {
            if (flugzeug.getSelectionModel().getSelectedItem() != null) {
                String flugzeugTYP = flugzeug.getValue();
                String query6 =       "FROM Flugzeuge p where p.flugzeugtyp =: flugzeugTYPE";
                Query<Flugzeuge> query06 = session.createQuery(query6);
                query06.setParameter("flugzeugTYPE", flugzeugTYP);
                List<Flugzeuge> data5 = query06.list();
                Flugzeuge rec= data5.get(0);
                Integer rechw = rec.getReichweite();//a
                Integer numberseats = rec.getAnzahl_Sitzplaetze();
                if(rechw < distanceAirports){
                    //lipiar #sits,,,
                    seatTotal_tf.setText("");
                    e_sitNr1.setText("");
                    b_sitNr.setText("");
                    e_price.setText("");
                    b_price.setText("");
                    WindowUtil.alertWindow("Falsch", Alert.AlertType.ERROR, "Die Rechweite ist kleiner als den Abstand zwischen Flughäfen!!");
                }else{
                    seatTotal_tf.setText(Integer.toString(numberseats));
                }
            }
        } );

    }

    private void calcDistanceAirports(){
        if (to.getSelectionModel().getSelectedItem() != null && from.getSelectionModel().getSelectedItem() != null) {
            org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
            //from
            String start = from.getValue();
            String query4 = "FROM Flughaefen f where f.name =: startName";
            Query<Flughaefen> query04 = session.createQuery(query4);
            query04.setParameter("startName", start);
            List<Flughaefen> data4 = query04.list();
            Flughaefen rec= data4.get(0);
            //to
            String end = to.getValue();
            String query5 = "FROM Flughaefen f where f.name =: endName";
            Query<Flughaefen> query05 = session.createQuery(query5);
            query05.setParameter("endName", end);
            List<Flughaefen> data5 = query05.list();
            Flughaefen rec1= data5.get(0);

            //(alte Formel war fehlerhaft/gab NaN aus)
            //Formel für Distanz zwischen 2 Koordinaten, Quelle: https://www.geodatasource.com/developers/java
            double lat1 = rec.getLat();
            double lon1 = rec.getLon();
            double lat2 = rec1.getLat();
            double lon2 = rec1.getLon();
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1))
                    * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            distanceAirports = (int)dist;
        }
    }


    private void dataClean() {
        from.getSelectionModel().clearSelection();
        to.getSelectionModel().clearSelection();
        date.setValue(null);
        interval.getSelectionModel().clearSelection();
        flugzeug.getSelectionModel().clearSelection();;
        e_sitNr1.setText("");
        b_sitNr.setText("");
        e_price.setText("");
        b_price.setText("");
        seatTotal_tf.setText("");
    }
}