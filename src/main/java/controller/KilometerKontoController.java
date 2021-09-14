package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.*;
import org.hibernate.query.Query;

import java.util.List;

public class KilometerKontoController {

    @FXML
    private Label kontoLabel;

    @FXML
    private Button btnAbbrechen;

    @FXML
    private Label kilometerLabel;

    @FXML
    private Label CO2Label;

    @FXML
    void abbrechen(ActionEvent event) {
        Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        org.hibernate.Session session = models.SessionFactoryUtil.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query<Kunde> query1 = session.createQuery("FROM Kunde k WHERE k.username = '" + AppSession.getIntance().getCustomerUsername() + "'");
        Kunde kunde = query1.list().get(0);
        //Brenda - Nur Buchungen die aktiv sind -
        Query<Buchungen> query = session.createQuery("FROM Buchungen b  WHERE b.status = 'aktiv'" +
                " and b.id_kunde =" + kunde.getId_kunde());
        List<Buchungen> buchungen = query.list();
        session.close();

        int kilometer = 0;
        int CO2 = 0;
        for (int i = 0; i < buchungen.size(); i++) {
            double distance = this.calcDistanceAirports(buchungen.get(i).getStart(), buchungen.get(i).getEnde());
            kilometer += distance;
            CO2 += distance * 0.0571 * (buchungen.get(i).getAnzahl_eco() + buchungen.get(i).getAnzahl_bus());
        }
        kilometerLabel.setText(kilometerLabel.getText() + kilometer + "Km");
        CO2Label.setText(CO2Label.getText() + CO2 + "Kg CO2");
    }

    private double calcDistanceAirports(String start, String end){ //copy paste aus FluglinieAnlegenController
        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
        //from
        String query4 = "FROM Flughaefen f where f.name =: startName";
        Query<Flughaefen> query04 = session.createQuery(query4);
        query04.setParameter("startName", start);
        List<Flughaefen> data4 = query04.list();
        Flughaefen rec= data4.get(0);
        //to
        String query5 = "FROM Flughaefen f where f.name =: endName";
        Query<Flughaefen> query05 = session.createQuery(query5);
        query05.setParameter("endName", end);
        List<Flughaefen> data5 = query05.list();
        Flughaefen rec1= data5.get(0);
        session.close();

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
        return dist;
    }
}
