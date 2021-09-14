package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.AppSession;
import models.Buchungen;
import models.Fluge;
import org.hibernate.query.Query;

import java.util.ArrayList;

/* Wichtig!
 * Wannimmer dieses Fenster aufgerufen wird, muss der AppSession vorher die FlugID mitgegeben werden, damit das hier weiß um welchen flug es sich überhaupt handelt!!!!!
 * (das muss in den "sitzplatzreservieren knopf")
 */

public class SitzplatzReservierungController {

    private int id_flug;
    private int ecoSeat;
    private int busiSeat;
    private ArrayList<Rectangle> seats;
    private boolean[] modified;
    private boolean[] sitzreservierung;
    private GraphicsContext gc;
    private int zuBuchendeEcoSitze;
    private int zuBuchendeBusiSite;

    @FXML
    private Button btnAbbrechen;

    @FXML
    private Button btnBuchen;

    @FXML
    private Canvas canvas;

    @FXML
    private Label busilabel;

    @FXML
    private Label ecolabel;

    @FXML
    void abbrechen(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/SitzreservierungAuswahl.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
            stage.setTitle("Sitze Reservieren");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); //java.io.IOException
        }
    }

    @FXML
    void buchen(ActionEvent event) {
        //speichern im system hier mit checks ob alles richtig is

        //check ob die richtige anzahl sitze ausgewaelt wurde
        int selectedBusi = 0;
        for (int i = 0; i < busiSeat; i++) {
            if (modified[i]) {
                selectedBusi++;
            }
        }
        int selectedEco = 0;
        for (int i = busiSeat; i < ecoSeat + busiSeat; i++) {
            if (modified[i]) {
                selectedEco++;
            }
        }
        if (selectedBusi == zuBuchendeBusiSite && selectedEco == zuBuchendeEcoSitze) {
            //sucess --------- EVTL GUCKEN OB IN DER ZWISHCENZEIT ÜBERSCHRIEBEN WURDE
            //transatctoin fix mit datenbank (kann ich nicht am laptop machen weil linux und pgadmin sich nicht mögen + muss brendas klasse anpassen

            org.hibernate.Session session = models.SessionFactoryUtil.getInstance().getSessionFactory().openSession();
            session.beginTransaction();
            Query<Fluge> query = session.createQuery("FROM Fluge fg WHERE fg.idflug = " + id_flug);
            Fluge flug = query.list().get(0);

            for (int i = 0; i < sitzreservierung.length; i++) {
                if (modified[i]) {
                    sitzreservierung[i] = true;
                }
            }
            flug.setReservierungen(sitzreservierung); //muss noch in flug klasse geändert werden
            session.update(flug);
            session.getTransaction().commit();
            session.beginTransaction();
            Query<Buchungen> query1 = session.createQuery("FROM Buchungen b  WHERE b.id_buchung = " + AppSession.getIntance().getId_buchung());
            Buchungen buchung = query1.list().get(0);
            buchung.setHatSitzeReserviert(true);
            session.update(buchung);
            session.getTransaction().commit();
            session.close();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sitzplaatzreservierung erfolgreich");
            alert.setHeaderText(null);
            alert.setContentText("Sitze erfolgreich reserviert.");
                //bugfix speziel für Linux mit JavaFX 10 und 11 (upgrade zu JavaFX12 würde das auch beheben), sonst reicht der code hierdrüber + show();
                //Quellen: https://stackoverflow.com/questions/55190380/javafx-creates-alert-dialog-which-is-too-small
                //und: https://stackoverflow.com/questions/30643146/javafx-dialog-doesnt-show-all-content-text
                alert.getDialogPane().setContent(new Label("Sitze erfolgreich reserviert."));
                alert.setResizable(true);
                alert.onShownProperty().addListener(e -> {
                    Platform.runLater(() -> alert.setResizable(false));
                });
                //Bugfix ende
            alert.show();
            //Stage stage = (Stage)btnBuchen.getScene().getWindow();
            //stage.close();
            //hier kann noch in der appsession eine flag gesetzt werden auf die das hintergrund/buchungsfenster wartet, um dann weiteruzmachen
            //edit: hat sich erledigt, ich überschreib das fenster einfach
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/SitzreservierungAuswahl.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
                stage.setTitle("Sitze Reservieren");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace(); //java.io.IOException
            }
        } else {
            //error / falsche sizte
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText(null);
            alert.setContentText("Sie haben nicht die richtige Anzahl an Sitzplaetzen ausgewählt:\nSie haben " + selectedBusi + " Busines-Sitze und " + selectedEco
                    + " Economy-Sitze ausgewählt.\nSie muessen " + zuBuchendeBusiSite + " Busines-Sitze und " + zuBuchendeEcoSitze + " Economy-Sitze auswählen.");
                //bugfix speziel für Linux mit JavaFX 10 und 11 (upgrade zu JavaFX12 würde das auch beheben), sonst reicht der code hierdrüber + show();
                //Quellen: https://stackoverflow.com/questions/55190380/javafx-creates-alert-dialog-which-is-too-small
                //und: https://stackoverflow.com/questions/30643146/javafx-dialog-doesnt-show-all-content-text
                alert.getDialogPane().setContent(new Label("Sie haben nicht die richtige Anzahl an Sitzplaetzen ausgewählt:\nSie haben " + selectedBusi + " Busines-Sitze und " + selectedEco
                        + " Economy-Sitze ausgewählt.\nSie muessen " + zuBuchendeBusiSite + " Busines-Sitze und " + zuBuchendeEcoSitze + " Economy-Sitze auswählen."));
                alert.setResizable(true);
                alert.onShownProperty().addListener(e -> {
                    Platform.runLater(() -> alert.setResizable(false));
                });
                //Bugfix ende
            alert.show();
        }
    }

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMouseClicked(e -> this.click(e.getX(), e.getY()));
        seats = new ArrayList<>();

        //Flug info laden aus datenbank
        id_flug = AppSession.getIntance().getId_flug();
        zuBuchendeBusiSite = AppSession.getIntance().getAnzahlZuBuchenderBusiSitzplaetze();
        zuBuchendeEcoSitze = AppSession.getIntance().getAnazahlZuBuchenerEcoSitzplaetze();
        busilabel.setText(busilabel.getText() + ": " + zuBuchendeBusiSite);
        ecolabel.setText(ecolabel.getText() + ": " + zuBuchendeEcoSitze);

        org.hibernate.Session session = models.SessionFactoryUtil.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query<Fluge> query = session.createQuery("FROM Fluge fg WHERE fg.idflug = " + id_flug);
        Fluge flug = query.list().get(0);
        session.close();
        ecoSeat = flug.getEcoseatact();
        busiSeat = flug.getBusseatact();
        sitzreservierung = flug.getReservierungen();
        if (sitzreservierung == null) {
            //falls flug noch keine reservierungen hat wird hier kein array sein (weil das bei der instanziierung nicht angelegt wird...)
            //könnte man da machen, aber ich machs hier damit ich nicht an anderer leute klassen was ändern muss
            sitzreservierung = new boolean[busiSeat+ecoSeat];
        }
        modified = new boolean[sitzreservierung.length];
        /*sitzreservierung = new boolean[853];
        sitzreservierung[6] = true;
        sitzreservierung[400] = true;
        busiSeat = 200;
        ecoSeat = 600;
        TEST WERTE*/

        /* Mögliche anzahl:
         * 853 750 555 440 420 400
         * 280 262 239 236 220 200
         * 189 180 142 120 100
         * 86 50 43 35 31 27 20 19 16 12
         * 6 5 4 3 2 */

        int markerX = 50, markerY = 50;
        int seatsize = 50;
        for (int i = 0; i < busiSeat; i++) {
            if(sitzreservierung[i]) {
                gc.strokeRect(markerX, markerY, seatsize, seatsize);
                gc.setFill(Color.RED);
                gc.fillRect(markerX+1, markerY+1, seatsize-2, seatsize-2);
                gc.setFill(Color.WHITE);
                seats.add(new Rectangle(markerX, markerY, 0, 0));
            } else {
                gc.strokeRect(markerX, markerY, seatsize, seatsize);
                seats.add(new Rectangle(markerX, markerY, seatsize, seatsize));
            }

            markerY += seatsize;
            if (markerY > 700) {
                markerY = 50;
                markerX += seatsize;
            }
        }
        markerX += seatsize;
        markerY = 50;
        seatsize = 30;
        for (int i = busiSeat; i < ecoSeat + busiSeat; i++) {
            if(sitzreservierung[i]) {
                gc.strokeRect(markerX, markerY, seatsize, seatsize);
                gc.setFill(Color.RED);
                gc.fillRect(markerX+1, markerY+1, seatsize-2, seatsize-2);
                gc.setFill(Color.WHITE);
                seats.add(new Rectangle(markerX, markerY, 0, 0));
            } else {
                gc.strokeRect(markerX, markerY, seatsize, seatsize);
                seats.add(new Rectangle(markerX, markerY, seatsize, seatsize));
            }

            markerY += seatsize;
            if (markerY > 700) {
                markerY = 50;
                markerX += seatsize;
            }
        }
    }

    private void click(double x, double y) {
        //hier werden clicks gehandlet, vergleich zu rectangles hier
        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i).contains(x, y)) {
                if (modified[i]) {
                    gc.setFill(Color.WHITE);
                } else {
                    gc.setFill(Color.GREEN);
                }
                gc.fillRect(seats.get(i).getX() + 1, seats.get(i).getY() + 1, seats.get(i).getHeight() - 2, seats.get(i).getWidth() - 2);
                modified[i] = !modified[i];
                break;
            }
        }
    }

    //binary decoder oder boolean array
}
