package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.AppSession;
import models.Buchungen;
import models.Flughaefen;
import models.Kunde;
import org.hibernate.query.Query;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStreamWriter;

public class WebController {

    private String start, ziel;
    private boolean mitAuto = false;
    private WebEngine engine;

    @FXML
    private WebView webView;

    @FXML
    private Button btnSwitch;

    @FXML
    private Button btnAbbrechen;

    @FXML
    void abbrechen(ActionEvent event) {
        Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void switchMode() {
        mitAuto = !mitAuto;
        if (mitAuto) {
            btnSwitch.setText("Zu Route mit öffentlichen Verkehrsmitteln wechseln");
            engine.load("http://www.google.com/maps/dir/?api=1&origin=" + start + "&destination=" + ziel + "&travelmode=driving");
        } else {
            btnSwitch.setText("Zu Route mit dem Auto wechseln");
            engine.load("http://www.google.com/maps/dir/?api=1&origin=" + start + "&destination=" + ziel + "&travelmode=transit");
        }
    }

    @FXML
    private void initialize ()
    {
        org.hibernate.Session session = models.SessionFactoryUtil.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query<Kunde> query1 = session.createQuery("FROM Kunde k WHERE k.username = '" + AppSession.getIntance().getCustomerUsername() + "'");
        Kunde kunde = query1.list().get(0);
        Query<Buchungen> query2 = session.createQuery("FROM Buchungen b WHERE b.id_buchung = " + AppSession.getIntance().getId_buchung());
        Buchungen buchung = query2.list().get(0);
        Query<Flughaefen> query3 = session.createQuery("FROM Flughaefen f WHERE f.name = '" + buchung.getStart() + "'");
        Flughaefen flughafen = query3.list().get(0);
        session.close();

        this.start = kunde.getAdresse();
        this.ziel = flughafen.getName();
        this.engine = webView.getEngine();

        /*engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            Document doc = engine.getDocument();
                            try {
                                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                                transformer.transform(new DOMSource(doc),
                                        new StreamResult(new OutputStreamWriter(System.out, "UTF-8")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });*/
        //das hier war nen versuch die seite auseinander zunehmen in html und dann nur die instructionbox anzuzeigen...
        //ich kann das fast alles erklären wenn wer will

        this.checkAdresse();
    }

    private void checkAdresse() {
        String[] adresse = start.split(" ");
        boolean numbersFound = false;
        boolean restIsLetters = true;
        for (int i = 0; i < adresse.length; i++) {
            if (!numbersFound) {
                if (adresse[i].matches("\\d.*")) {
                    i++;
                    if (adresse[i].matches("\\d+")) {
                        numbersFound = true;
                    }
                }
            } else {
                if (adresse[i].matches(".*\\d.*")) {
                    restIsLetters = false;
                }
            }
        }
        if (!numbersFound || !restIsLetters) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Adresse im falschen Format");
            alert.setHeaderText(null);
            alert.setContentText("Die von ihnen im System hinterlegte Adresse ist nicht im optimalen Format gespeichert.\n" +
                    "Die von Ihnen angegebene Adresse:\n" +
                    start + "\nUm Fehlerhafte Routen oder verwechslungen zu vermeiden, verwenden sie bitte volgendes Format:\n" +
                    "Straßenname Hausnummer[optional mit a,b,c...], Postleitzahl Stadtname\n" +
                    "Hier ein Beispiel:\n" +
                    "Universitätsstraße 2, 45141 Essen \n" +
                    "Bitte korrigieren Sie dies und kommen dann wieder, es sollte keine 2 Minuten dauern :)");
            //bugfix speziel für Linux mit JavaFX 10 und 11 (upgrade zu JavaFX12 würde das auch beheben), sonst reicht der code hierdrüber + show();
            //Quellen: https://stackoverflow.com/questions/55190380/javafx-creates-alert-dialog-which-is-too-small
            //und: https://stackoverflow.com/questions/30643146/javafx-dialog-doesnt-show-all-content-text
                alert.getDialogPane().setContent(new Label("Die von ihnen im System hinterlegte Adresse ist nicht im optimalen Format gespeichert.\n" +
                    "Die von Ihnen angegebene Adresse:\n" +
                    start + "\nUm Fehlerhafte Routen oder verwechslungen zu vermeiden, verwenden sie bitte volgendes Format:\n" +
                    "Straßenname Hausnummer[optional mit a,b,c...], Postleitzahl Stadtname\n" +
                    "Hier ein Beispiel:\n" +
                    "Universitätsstraße 2, 45141 Essen\n" +
                    "Bitte korrigieren Sie dies und kommen dann wieder, es sollte keine 2 Minuten dauern :)"));
                alert.setResizable(true);
                alert.onShownProperty().addListener(e -> {
                    Platform.runLater(() -> alert.setResizable(false));
                });
            //Bugfix ende
            alert.show();
            Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
            stage.close();//diese zeile wird nie erreicht:
            //hehe zeile 151 wirft actually ne load exception weil der btn noch nicht richtig geladen ist und lässt das fenster abschmieren...
            //ich meine das is das gleiche ergebnis wie nen close() also was solls XD
        } else {
            if (!ziel.contains("Airport")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Fehlerhafter Name im System");
                alert.setHeaderText(null);
                alert.setContentText("Es wurde ein Fehler im System bezüglich des Namens ihres gewünschten Flughafens erkannt\n" +
                        "Es wurde ein automatischer Behebungsversuch gestartet. Es ist nicht garantiert, dass GoogleMaps Ihren Flughafen findet.\n" +
                        "Sollte Ihr Flughafen nicht gefunden werden, wenden Sie sich bitte an einen Systemadministrator!");
                //bugfix speziel für Linux mit JavaFX 10 und 11 (upgrade zu JavaFX12 würde das auch beheben), sonst reicht der code hierdrüber + show();
                //Quellen: https://stackoverflow.com/questions/55190380/javafx-creates-alert-dialog-which-is-too-small
                //und: https://stackoverflow.com/questions/30643146/javafx-dialog-doesnt-show-all-content-text
                    alert.getDialogPane().setContent(new Label("Es wurde ein Fehler im System bezüglich des Namens ihres gewünschten Flughafens erkannt\\n\" +\n" +
                            "Es wurde ein automatischer Behebungsversuch gestartet. Es ist nicht garantiert, dass GoogleMaps Ihren Flughafen findet.\\n\" +\n" +
                            "Sollte Ihr Flughafen nicht gefunden werden, wenden Sie sich bitte an einen Systemadministrator!"));
                    alert.setResizable(true);
                    alert.onShownProperty().addListener(e -> {
                        Platform.runLater(() -> alert.setResizable(false));
                    });
                //Bugfix ende
                alert.show();
                ziel += " Airport";
            }
            this.switchMode();
        }
    }
}