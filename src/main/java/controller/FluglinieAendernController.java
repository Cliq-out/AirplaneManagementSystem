package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.WindowUtil;

import java.util.List;

public class FluglinieAendernController {

    private int distanceAirports;

    @FXML
    private TextField fluggesellschaft_tf;

    @FXML
    private TextField endairport_tf;

    @FXML
    private TextField Startairport_tf;

    @FXML
    private ChoiceBox<Integer> Idfluglinie_cb;

    @FXML
    private ChoiceBox<String> interval;

    @FXML
    private ComboBox<String> flugzeug;

    @FXML
    private TextField fluglinieID;

    @FXML
    private TextField totalseats;

    @FXML
    private TextField e_sitNr11;

    @FXML
    private TextField b_sitNr;

    @FXML
    private TextField e_price;

    @FXML
    private TextField b_price;

    @FXML
    private Button btnAbbrechen;

    @FXML
    private Button btnAendern;



    @FXML
    void abbrechen(ActionEvent event) {
        Stage stage= (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    @FXML
    void showFlugzeugOptions(ActionEvent event) { }

    @FXML
    void weiter(ActionEvent event) {
        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
        session.beginTransaction();

        int id_fl = Idfluglinie_cb.getValue();
        Query<FluglinieAnlegen> query02 = session.createQuery("FROM FluglinieAnlegen f WHERE f.id_fluglinie = :flugID");

        query02.setParameter("flugID",id_fl);

        List<FluglinieAnlegen> fluglinieList = query02.list();
        FluglinieAnlegen flightChange = fluglinieList.get(0);//selected ID

        flightChange.setEcoseats(Integer.parseInt(e_sitNr11.getText()));
        flightChange.setBussseats(Integer.parseInt(b_sitNr.getText()));
        flightChange.setEcopreis(Double.parseDouble(e_price.getText()));
        flightChange.setBusspreis(Double.parseDouble(b_price.getText()));
        // here my choicebox definition

        if (interval.getValue() == "täglich") {
            flightChange.setInterval("täglich");

        } else if (interval.getValue() == "alle drei Tage") {
            flightChange.setInterval("alle drei Tage");

        } else {
            flightChange.setInterval("wöchentlich");
        }

        flightChange.setInterval(interval.getValue());

        int eco = Integer.parseInt(e_sitNr11.getText());
        flightChange.setEcoseats(eco);
        int tot = Integer.parseInt(totalseats.getText());
        int bus = Integer.parseInt(b_sitNr.getText());

        session.update(flightChange);
        session.getTransaction().commit();
        session.close();

        if((eco/(eco+bus))>0.875){
            WindowUtil.alertWindow("Falsch", Alert.AlertType.ERROR, "The number of ecoseats has to be minimum 87.5% of the total!");}
        else {
            WindowUtil.alertWindow("Richtig", Alert.AlertType.CONFIRMATION, "Data was inserted");
            //dataClean();
        }
    }


    @FXML
    void calculateSeats(ActionEvent event) {
        int numTotalSeats =Integer.parseInt(totalseats.getText());
        int numEconSeats =Integer.parseInt(e_sitNr11.getText());
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
    public void  initialize(){

        //Geloggte Manager bzw. Gesellschaft
        fluggesellschaft_tf.setDisable(true);
        b_sitNr.setDisable(true);
        Startairport_tf.setDisable(true);
        endairport_tf.setDisable(true);
        fluggesellschaft_tf.setText(AppSession.getIntance().getGesellshaftname());
        int fluggessuche = AppSession.getIntance().getId_gesellschaft();

        //Lists Flughafen und Flugzeuge
        Idfluglinie_cb.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                -> { if (Idfluglinie_cb.getSelectionModel().getSelectedItem() != null)
        {     int id_fl = Idfluglinie_cb.getSelectionModel().getSelectedItem();

            loadFluglinie(id_fl);

        }
        });

        flugzeug.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                -> {
            if (flugzeug.getSelectionModel().getSelectedItem() != null) {
                String flugzeugName = flugzeug.getSelectionModel().getSelectedItem();

                org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();
                Query<Flugzeuge> query01 = session1.createQuery("FROM Flugzeuge f WHERE f.flugzeugtyp= :flugzeugeName");
                query01.setParameter("flugzeugeName", flugzeugName);

                List<Flugzeuge> flugzeugeList = query01.list();
                Flugzeuge rec = flugzeugeList.get(0);//what we´ve selected
                if(distanceAirports <= rec.getReichweite()){
                    totalseats.setText(Integer.toString(rec.getAnzahl_Sitzplaetze()));
                    e_sitNr11.setText("");
                    b_sitNr.setText("");
                    e_price.setText("");
                    b_price.setText("");
                }
                else{
                    WindowUtil.alertWindow("Falsch", Alert.AlertType.ERROR, "Die Rechweite ist kleiner als den Abstand zwischen Flughäfen!!");
                    int id_fl = Idfluglinie_cb.getSelectionModel().getSelectedItem();
                    loadFluglinie(id_fl);
                }
                //totalseats;

            }
        });



        //1) Fluglinie ID
        Session session = SessionFactoryUtil.getInstance().getSession();

        Query<Integer> query06 = session.createQuery("SELECT ff.id_fluglinie FROM FluglinieAnlegen ff where ff.fluggesellschaft =: fluggesellschaft");
        query06.setParameter("fluggesellschaft",fluggessuche);
        //Solo las rutas de mi aerolinea
        List<Integer> fluglinieList = query06.list();


        //Flugzeugetypen
        String query2= "SELECT a.flugzeugtyp FROM Flugzeuge a";
        List<String> data2 = session.createQuery(query2).list();

        ObservableList<Integer> fluglinieId  = FXCollections.observableArrayList();
        ObservableList<String> flugzeugeObsList  = FXCollections.observableArrayList();


        fluglinieId.addAll(fluglinieList);
        flugzeugeObsList.addAll(data2);
        Idfluglinie_cb.setItems(fluglinieId);
        flugzeug.setItems(flugzeugeObsList);

        //2) Interval Choicebox

        interval.setItems(FXCollections.observableArrayList(
                "täglich", "alle drei Tage", "wöchentlich"));
        interval.setValue("täglich");

        totalseats.setDisable(true);
    }

    private void loadFluglinie(int id_fl){
        org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();
        Query<FluglinieAnlegen> query01 = session1.createQuery("FROM FluglinieAnlegen fm WHERE fm.id_fluglinie = :fluglinieID");
        query01.setParameter("fluglinieID",id_fl);

        List<FluglinieAnlegen> fluglinieList = query01.list();
        FluglinieAnlegen rec  = fluglinieList.get(0);//what we´ve selected

        distanceAirports = rec.getDistance();
        //Actualize saved information

        Startairport_tf.setText(rec.getStartname());
        endairport_tf.setText(rec.getZielname());

        interval.setValue(rec.getInterval());
        flugzeug.setValue(rec.getModel());

        //totalseats;
        e_sitNr11.setText(Integer.toString(rec.getEcoseats()));
        b_sitNr.setText(Integer.toString(rec.getBussseats()));
        totalseats.setText(Integer.toString(rec.getBussseats()+rec.getEcoseats()));
        e_price.setText(Double.toString(rec.getEcopreis()));
        b_price.setText(Double.toString(rec.getBusspreis()));
    }
}
