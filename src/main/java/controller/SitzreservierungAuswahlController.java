package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.AppSession;
import models.Buchungen;
import models.Kunde;
import models.SessionFactoryUtil;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SitzreservierungAuswahlController {

        @FXML
        private Button btnAbbrechen;

        @FXML
        private TableView<Buchungen> tableFluglinie;

        @FXML
        private TableColumn<Buchungen, Integer> id_buchung;

        @FXML
        private TableColumn<Buchungen, Integer> id_kunde;

        @FXML
        private TableColumn<Buchungen, Integer> id_fluglinie;

        @FXML
        private TableColumn<Buchungen, Date> date;

        @FXML
        private TableColumn<Buchungen, Integer> nr_Eco;

        @FXML
        private TableColumn<Buchungen, Integer> nr_bus;

        @FXML
        private TableColumn<Buchungen, Double> betrag;

        @FXML
        private TableColumn<Buchungen, String> start;

        @FXML
        private TableColumn<Buchungen, String> end;

        @FXML
        private TableColumn<Buchungen, Date> buchungsdatum;

        @FXML
        private Button btn_reservieren;

        @FXML
        void abbrechen(ActionEvent event) {
            Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
            stage.close();
        }

        @FXML
        public void initialize() {
            //der teil hier wurde kopiert, den teil hier hat Brenda in BuchungsübersichtController gemacht,
            //ich habe den teil nur kopiert und "AND hatsitzereserviert = false" hinzugefügt, weil ich ursprünmglich geplant hatte, eine sitzplatzreservierung aufzuzwingen
            //es aber nun optional ist, daher brauche ich eine tabelle mit den gebuchten flügen die noch keine sitze reserviert haben.
            //(fragt mich bitte nichts hierzu, besonders nicht was die for schleife da unten macht...
            //(ich weiß WAS sie macht, aber "Buchungen allbookings: bookings" ist syntax die ich nicht kannte / die ich nicht recherschiert habe)
            org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();
            String customerUN = AppSession.getIntance().getCustomerUsername();
            Query<Kunde> query1 = session1.createQuery("FROM Kunde k WHERE k.username = : customerUN");
            query1.setParameter("customerUN", customerUN);
            List<Kunde> UNList = query1.list();
            Kunde UNRec = UNList.get(0);
            int UNRecord = UNRec.getId_kunde();
            Query<Buchungen> query2 = session1.createQuery("FROM Buchungen b  WHERE b.id_kunde=: id_kunde AND hatsitzereserviert = false");
            query2.setParameter("id_kunde",UNRecord);
            List<Buchungen> bookings = query2.list();

            id_buchung.setCellValueFactory(new PropertyValueFactory<>("id_buchung"));
            id_kunde.setCellValueFactory(new PropertyValueFactory<>("id_kunde"));
            id_fluglinie.setCellValueFactory(new PropertyValueFactory<>("id_fluglinie"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            nr_Eco.setCellValueFactory(new PropertyValueFactory<>("anzahl_eco"));
            nr_bus.setCellValueFactory(new PropertyValueFactory<>("anzahl_bus"));
            betrag.setCellValueFactory(new PropertyValueFactory<>("betrag"));
            start.setCellValueFactory(new PropertyValueFactory<>("start"));
            end.setCellValueFactory(new PropertyValueFactory<>("ende"));
            buchungsdatum.setCellValueFactory(new PropertyValueFactory<>("date_buchung"));

            for(Buchungen allbookings: bookings){
                tableFluglinie.getItems().add(allbookings);
            }
        }

        @FXML
        void reservieren() {
            AppSession.getIntance().setId_flug(id_buchung.getTableView().getSelectionModel().getSelectedItem().getIdflug());
            AppSession.getIntance().setAnazahlZuBuchenerEcoSitzplaetze(id_buchung.getTableView().getSelectionModel().getSelectedItem().getAnzahl_eco());
            AppSession.getIntance().setAnzahlZuBuchenderBusiSitzplaetze(id_buchung.getTableView().getSelectionModel().getSelectedItem().getAnzahl_bus());
            AppSession.getIntance().setId_buchung(id_buchung.getTableView().getSelectionModel().getSelectedItem().getId_buchung());
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/SitzplatzReservierung.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
                stage.setTitle("Sitzauswahl");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace(); //java.io.IOException
            }
        }

}
