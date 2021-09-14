package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.*;
import org.hibernate.query.Query;
import utils.WindowUtil;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BuchungOverviewController {

    private Buchungen buchungen_act;
    private Kunde kunde;
    private Fluge fluge_act;
    private boolean aktuelleSucheIstMultistop = false;
    private List<Integer[]> multiStopRouten;

    @FXML
    private Button btnAbbrechen;

    @FXML
    private Button btnBuchen;

    @FXML
    private Button btnSuchen;

    @FXML
    private TableView<FlugeView> tableBuchung;

    @FXML
    private TableColumn<FlugeView, String> tcol11;//idflug
    //änderung von Fabian, das war ein Integer, jetz is das ein String, integer können auf String ge typecastet werden, daher geht Direktflug nicht kaput
    //String war nötig um beim Multiflug mehrere Ids anzeigen zu können, Modell war schon String daher kein problem da

    @FXML
    private TableColumn<FlugeView, String> tcol1;//startairport

    @FXML
    private TableColumn<FlugeView, String> tcol2;//endairport

    @FXML
    private TableColumn<FlugeView, String> tcol3;//Flugzeug

    @FXML
    private TableColumn<FlugeView, Double> tcol21;//Preis Eco

    @FXML
    private TableColumn<FlugeView,Double> tcol211;//Preis Business

    @FXML
    private TableColumn<FlugeView, Date> tcol31;//Date

    @FXML
    private TableColumn<FlugeView, Integer> tcol4;//Multistop-Stops von Fabian


    @FXML
    private ComboBox<String> from;

    @FXML
    private ComboBox<String> to;

    @FXML
    private DatePicker date;

    @FXML
    private ChoiceBox<String> multistopp;

    @FXML
    private TextField zwischenStopZahl;

    @FXML
    private TextField firstname_tf;

    @FXML
    private TextField lastname_tf;

    @FXML
    private TextField id_tf;

    @FXML
    private TextField ecoseat_tf;

    @FXML
    private TextField busseat_tf;

    @FXML
    private TextField betrag;

    @FXML
    void Abbrechen(ActionEvent event) {
        Stage stage= (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    private String dataValidate(){
        StringBuilder sb= new StringBuilder();
        sb.append("");
        if("".equals(ecoseat_tf.getText())){
            sb.append("N° Economy Seats is required\n");
        }
        if("".equals(busseat_tf.getText())){
            sb.append("N° Bussiness Seats is required\n");
        }
        if(from.getValue() == null){
            sb.append("Start Airport is required\n");
        }
        if(to.getValue() == null){
            sb.append("End Airport is required\n");
        }
        if(date.getValue() == null){
            sb.append("Departure date is required\n");
        }
        //falls multistop ausgewählt ist muss da ne Zahl stehen; Fabian
        if(!zwischenStopZahl.isDisabled() && zwischenStopZahl.getText().equals("")) {
            sb.append("Please enter a Maximum Stop-count\n");
        }

        return sb.toString();
    }

    @FXML
    void Suchen(ActionEvent event) {
        String message = dataValidate();

        tableBuchung.getItems().clear();
        if(!"".equals(message)){
            //alerta
            WindowUtil.alertWindow("Error", Alert.AlertType.ERROR, message);
        }else {
            if (zwischenStopZahl.isDisabled()) {//Direktflug, unverändert nach der erstem Zeile, das hat Brenda oder Pearl gemacht
                aktuelleSucheIstMultistop = false;
                
                //from
                String start = from.getValue();
                String end = to.getValue();

                //StartDatum
                Date dateStart = Date.valueOf(date.getValue());

                org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();

                String query3 = "SELECT fg.idflug ,fg.abfahrtdate, fa.startname, fa.zielname, fa.ecopreis, fa.busspreis, " +
                        "fa.model, fa.id_fluglinie, fg.ecoseatact, fg.busseatact " +
                        "FROM Fluge fg, FluglinieAnlegen fa where fg.idfluglinie =fa.id_fluglinie and " +
                        "fa.startname =: startname and fa.zielname =: zielname"; /*and fg.abfahrtdate =: abfahrtDate";*/ //date fällt weg, date wird manuel aussortiert weil SQL nicht Date mit BETWEEN kann (Fabian)
                Query query03 = session.createQuery(query3);
                query03.setParameter("startname", start);
                query03.setParameter("zielname", end);
                //query03.setParameter("abfahrtDate", dateStart);

                List<Object[]> flugeList = query03.list();

                //(Komment.Brenda) Erweiterung: Ursprünglich sollte man nur einen Tag suchen,
                // jetzt such man nach verfügbare Flüge innerhalb eines Intervalls (+/-3 Tage)

                //*Fabian*
                //3 tage range check, von fabian, sortiert alle aus die nicht im 3 tage bereich sind
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateStart);
                cal.add(Calendar.DATE, -3);
                Date minDate = new Date(cal.getTimeInMillis());
                cal.setTime(dateStart);
                cal.add(Calendar.DATE, 3);
                Date maxDate = new Date(cal.getTimeInMillis());
                System.out.println(maxDate + "   " + minDate);
                for (int i = 0; i < flugeList.size(); i++) {
                    Date flugdate = (Date) flugeList.get(i)[1];
                    if (flugdate.after(maxDate) || flugdate.before(minDate)) {
                        flugeList.remove(i);
                        i--;
                    }
                }//Fabian ende

                //...
                if (flugeList != null && flugeList.size() > 0) {

                    ObservableList<FlugeView> flugeViews = FXCollections.observableArrayList();

                    for (Object[] obj : flugeList) {
                        int id = Integer.parseInt(obj[0].toString());
                        Date dat = (Date) obj[1];
                        String startnam = obj[2].toString();
                        String zielnam = obj[3].toString();
                        double ecop = Double.parseDouble(obj[4].toString());
                        double busp = Double.parseDouble(obj[5].toString());
                        String modell = obj[6].toString();
                        int idFluglinie = Integer.parseInt(obj[7].toString());
                        int ecoSeatN = Integer.parseInt(obj[8].toString());
                        int busSeatN = Integer.parseInt(obj[9].toString());

                        FlugeView flugeV = new FlugeView();
                        flugeV.setIdfluge(id);
                        flugeV.setStartdate(dat);
                        flugeV.setStartname(startnam);
                        flugeV.setZielname(zielnam);
                        flugeV.setEcopreis(ecop);
                        flugeV.setBusspreis(busp);
                        flugeV.setModel(modell);
                        flugeV.setId_fluglinie(idFluglinie);
                        flugeV.setEcoseat(ecoSeatN);
                        flugeV.setBusseat(busSeatN);
                        flugeViews.add(flugeV);
                    }

                    tcol11.setCellValueFactory(new PropertyValueFactory<>("idfluge"));//idflug
                    tcol1.setCellValueFactory(new PropertyValueFactory<>("startname"));//start
                    tcol2.setCellValueFactory(new PropertyValueFactory<>("zielname"));//end
                    tcol3.setCellValueFactory(new PropertyValueFactory<>("model"));//model Flugzeug
                    tcol21.setCellValueFactory(new PropertyValueFactory<>("ecopreis"));//Economy Price
                    tcol211.setCellValueFactory(new PropertyValueFactory<>("busspreis"));//Business Price
                    tcol31.setCellValueFactory(new PropertyValueFactory<>("startdate"));//Date

                    for (FlugeView fv : flugeViews) {
                          tableBuchung.getItems().add(fv);
                    }

                } else {
                    WindowUtil.alertWindow("Error", Alert.AlertType.ERROR, "Sorry, we cannot find any suitable flight");
                }
                //... End validate if you find the flight

            } else {//Multistop suche, von Fabian
                aktuelleSucheIstMultistop = true;
                
                String start = from.getValue();
                String end = to.getValue();
                Date searchdate = Date.valueOf(date.getValue());

                org.hibernate.Session session = models.SessionFactoryUtil.getInstance().getSessionFactory().openSession();
                session.beginTransaction();
                Query<Object[]> query = session.createQuery("SELECT fg.idflug ,fg.abfahrtdate, fa.startname, fa.zielname, fa.ecopreis, " +
                        "fa.busspreis, fa.model, fa.id_fluglinie, fg.ecoseatact, fg.busseatact " +
                        "FROM Fluge fg, FluglinieAnlegen fa where fg.idfluglinie =fa.id_fluglinie");
                List<Object[]> fluge = query.list();
                session.close();

                multiStopRouten = new ArrayList<>();//arraylist weil List und Observable list sind abstract

                Calendar cal = Calendar.getInstance();
                cal.setTime(searchdate);
                cal.add(Calendar.DATE, 3);
                Date maxDate = new Date(cal.getTimeInMillis());
                cal.setTime(searchdate);
                cal.add(Calendar.DATE, -3);
                Date minDate = new Date(cal.getTimeInMillis());
                System.out.println(maxDate + "   " + minDate);
                for (int i = 0; i < fluge.size(); i++) {
                    Date flugdate = (Date) fluge.get(i)[1];
                    if (flugdate.after(maxDate) || flugdate.before(minDate)) {
                        fluge.remove(i);
                        i--;
                    }
                }
                System.out.println("nach aussortierung " + fluge.size());
                Date tempdate = minDate;
                for (int i = 0; i < 7; i++) {
                    this.recursiveFlightSearch(tempdate, start, end, fluge, Integer.parseInt(zwischenStopZahl.getText()), new ArrayList<Integer>());
                    cal.add(Calendar.DATE, 1);
                    tempdate = (new Date(cal.getTimeInMillis()));
                }
                //jetzt enthällt die Liste multiStopRouten Integer[] mit den indexen er Fluege aus den 7 tagen die infrage kommen...
                System.out.println("routen gefunden " + multiStopRouten.size());
                if(multiStopRouten.size() > 0) {
                    //jetz muss ich irgendwie fake flüge erzeugen, umm die multistops als einen flug in der liste aber mehreren in den buchungen anzuzeigen...
                    ObservableList<FlugeView> fakeFlugView = FXCollections.observableArrayList();//bitte fragt mich nicht was diese zeile hier macht, ich hab die von oben(brenda oder pearl) kopiert und flugeviewmitmultistopview ausgetauscht; also ich weiß was die macht, das is ne liste für die flugobjekte die später in der tabelle landen, aber ich habe keine ahnung was FXCollections.observableArrayList() macht / warum das hier benutzt wird!
                    for (int i = 0; i < multiStopRouten.size(); i++) {
                        Integer[] currentRoute = multiStopRouten.get(i);
                        String ids = "";
                        String models = "";
                        double ecoPreis = 0;
                        double busiPreis = 0;
                        for (int ii = 0; ii < currentRoute.length; ii++) {
                            ids += fluge.get(currentRoute[ii])[0].toString();
                            models += fluge.get(currentRoute[ii])[6].toString();
                            if (ii != currentRoute.length - 1) {
                                ids += ", ";
                                models += ", ";
                            }
                            ecoPreis += Double.parseDouble(fluge.get(currentRoute[ii])[4].toString());
                            busiPreis += Double.parseDouble(fluge.get(currentRoute[ii])[5].toString());
                        }
                        Date date = (Date) fluge.get(currentRoute[0])[1]; //bei allen fluegen einer Route gleich, Ich gehe davon aus, dass man Alle multistopfuege an einem tag zurück legt. Im gesammten Sytsem/den anforderungen ist nirgendwo die rede von uhrzeiten/flugdauern. Selbst wenn wird mit dieser zeile der erste Flug genommen => Abflugdatum ist richtig, ankunftsdatum wäre dann evtl anders
                        int stopCount = currentRoute.length - 1;

                        FlugeView fakeFlug = new FlugeView();
                        fakeFlug.setMultiStopId(ids);
                        fakeFlug.setStartdate(date);
                        fakeFlug.setStartname(start);
                        fakeFlug.setZielname(end);
                        fakeFlug.setEcopreis(ecoPreis);
                        fakeFlug.setBusspreis(busiPreis);
                        fakeFlug.setModel(models);
                        fakeFlug.setStopCount(stopCount);
                        fakeFlugView.add(fakeFlug);
                    }

                    tcol11.setCellValueFactory(new PropertyValueFactory<>("multiStopId"));//wichtiger unterschied hier, idfluge ist nen integer aus brendas klasse, idfluge2 ist ein string aus meiner klasse
                    tcol1.setCellValueFactory(new PropertyValueFactory<>("startname"));//
                    tcol2.setCellValueFactory(new PropertyValueFactory<>("zielname"));
                    tcol3.setCellValueFactory(new PropertyValueFactory<>("model"));
                    tcol21.setCellValueFactory(new PropertyValueFactory<>("ecopreis"));
                    tcol211.setCellValueFactory(new PropertyValueFactory<>("busspreis"));
                    tcol31.setCellValueFactory(new PropertyValueFactory<>("startdate"));
                    tcol4.setCellValueFactory(new PropertyValueFactory<>("stopCount"));

                    for (FlugeView fv : fakeFlugView) {//der bolck war auch von oben kopiert, das is ne for all / for each der einfach nur für jedes element der multistopview ein element in der tabelle anzeigt/einfügt
                        tableBuchung.getItems().add(fv);
                    }
                } else {//kopiert von oben
                    WindowUtil.alertWindow("Error", Alert.AlertType.ERROR, "Sorry, we cannot find any suitable flight");
                }
            }
        }
    }

    private void recursiveFlightSearch(Date date, String from, String to, List<Object[]> fluge, int stoppCount, List<Integer> flightsTaken) {
        if (from.equals(to)) {
            //route war erfolgreich
            System.out.println("flug gefunden: stopcount " + stoppCount + "; flightstaken " + flightsTaken.size());
            for (int i = 0; i < flightsTaken.size(); i++) {
                System.out.println("flug " + i + ": von " + fluge.get(flightsTaken.get(i))[2] + " nach " + fluge.get(flightsTaken.get(i))[3]);
            }
            System.out.println("===============================");

            Integer[] route = new Integer[flightsTaken.size()];
            for (int i = 0; i  < route.length; i++) {
                route[i] = flightsTaken.get(i);
            }
            multiStopRouten.add(route);
        } else {
            if (stoppCount > 0) {
                //route sucht weiter
                for (int i = 0; i < fluge.size(); i++) {
                    if (fluge.get(i)[2].toString().equals(from)) {
                        if (date.equals(fluge.get(i)[1])) {
                            boolean beenThere = false;
                            for (int ii = 0; ii < flightsTaken.size(); ii++) {
                                if (fluge.get(i)[3].toString().equals(fluge.get(flightsTaken.get(ii))[2])) {
                                    beenThere = true;
                                    break;
                                }
                            }
                            if (!beenThere) {
                                flightsTaken.add(i);
                                this.recursiveFlightSearch(date, fluge.get(i)[3].toString(), to, fluge, stoppCount - 1, flightsTaken);
                                flightsTaken.remove(flightsTaken.size() - 1);
                            }
                            //der flug führt zu einem flughaven, an dem wir schon waren...
                        }
                        //der flug aus der liste war nicht an dem aktuell überprüften Datum
                    }
                    //der flug ging nicht vom aktuellen knoten ab
                }
            }
            //route hat weitergesucht, erfolg ungewiss
        }
        //route war erfolglos
    }
    //multistoppsuche von Fabian Ende

    @FXML
    void Buchen(ActionEvent event) {
        if(!aktuelleSucheIstMultistop) {//unverändert, von pearl oder brenda
            if (buchungen_act != null) {
                int idf = tableBuchung.getSelectionModel().getSelectedItem().getIdfluge();
                String query4 = "FROM Fluge fg WHERE fg.idflug =: idf";


                org.hibernate.Session session = SessionFactoryUtil.getInstance().getSessionFactory().openSession();
                Query<Fluge> query04 = session.createQuery(query4);
                query04.setParameter("idf", idf);
                List<Fluge> flugList = query04.list();
                Fluge fluge_act = flugList.get(0);
                int newEco = fluge_act.getEcoseatact() - buchungen_act.getAnzahl_eco();
                int newBus = fluge_act.getBusseatact() - buchungen_act.getAnzahl_bus();

                if (newEco < 0 || newBus < 0) {
                    WindowUtil.alertWindow("Error", Alert.AlertType.ERROR, "There are not enough places in this flight!!!");
                } else {
                    fluge_act.setEcoseatact(newEco);
                    fluge_act.setBusseatact(newBus);

                    session.beginTransaction();
                    buchungen_act.setArt("direkt");//bugfix von fabian
                    session.save(buchungen_act);
                    session.update(fluge_act);
                    session.getTransaction().commit();
                    WindowUtil.alertWindow("Richtig", Alert.AlertType.CONFIRMATION, "Your flight was booked!!!");
                    dataClean();
                }

            }
        } else {//multistop buchung, von Fabian
            if (tableBuchung.getSelectionModel().getSelectedItem() != null) {
                String idsAusTabelle = tableBuchung.getSelectionModel().getSelectedItem().getMultiStopId();
                String[] ids = idsAusTabelle.split(", ");
                List<Fluge> fluge = new ArrayList<>();//arraylist weil List und Observable list sind abstract
                org.hibernate.Session session = models.SessionFactoryUtil.getInstance().getSessionFactory().openSession();
                session.beginTransaction();;
                for (int i = 0; i < ids.length; i++) {
                    Query<Fluge> query = session.createQuery("FROM Fluge fg WHERE fg.idflug = " + ids[i]);
                    fluge.add(query.list().get(0));
                }
                session.getTransaction().commit();

                boolean platzfrei = true;
                for (int i = 0; i < fluge.size(); i++) {
                    if (fluge.get(i).getEcoseatact() - Integer.parseInt(ecoseat_tf.getText()) < 0 || fluge.get(i).getBusseatact() - Integer.parseInt(busseat_tf.getText()) < 0) {
                        platzfrei = false;
                        break;
                    }
                }
                if (!platzfrei) {
                    session.close();
                    WindowUtil.alertWindow("Error", Alert.AlertType.ERROR, "There are not enough places in this flight!!!");
                } else {
                    for (int i = 0; i < fluge.size(); i++) {
                        session.beginTransaction();
                        fluge.get(i).setEcoseatact(fluge.get(i).getEcoseatact() - Integer.parseInt(ecoseat_tf.getText()));
                        fluge.get(i).setBusseatact(fluge.get(i).getBusseatact() - Integer.parseInt(busseat_tf.getText()));
                        session.update(fluge.get(i));
                        session.getTransaction().commit();
                    }
                    for (int i = 0; i < fluge.size(); i++) {
                        session.beginTransaction();
                        Query<FluglinieAnlegen> query = session.createQuery("FROM FluglinieAnlegen fl WHERE fl.id_fluglinie = " + fluge.get(i).getIdfluglinie());
                        FluglinieAnlegen fluglinie = (query.list().get(0));
                        Buchungen buchung = new Buchungen();
                        buchung.setStart(fluglinie.getStartname());
                        buchung.setEnde(fluglinie.getZielname());
                        buchung.setId_fluglinie(fluglinie.getId_fluglinie());
                        buchung.setFlugzeugtyp(fluglinie.getModel());
                        //der block hier ist von oben (direktflug von brenda/pearl) kopiert:
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar hoy = Calendar.getInstance();
                        String today = dateFormat.format(hoy.getTime());
                        java.sql.Date dat = java.sql.Date.valueOf(today);
                        buchung.setDate(dat);
                        //kopie ende
                        buchung.setAnzahl_eco(Integer.parseInt(ecoseat_tf.getText()));
                        buchung.setAnzahl_bus(Integer.parseInt(busseat_tf.getText()));
                        buchung.setBetrag(1.19 * ((Integer.parseInt(ecoseat_tf.getText()) * fluglinie.getEcopreis())
                                + (Integer.parseInt(busseat_tf.getText()) * fluglinie.getBusspreis())));
                        buchung.setDate_buchung(tableBuchung.getSelectionModel().getSelectedItem().getStartdate());
                        buchung.setArt("multi-stopp");
                        buchung.setId_kunde(kunde.getId_kunde());
                        buchung.setIdflug(Integer.parseInt(ids[i]));
                        buchung.setStatus("aktiv");//hat was mit stornierung zu tun, fragt mich bitte nichts dazu, mir wurde gesagt das muss da so hin
                        session.save(buchung);
                        session.getTransaction().commit();
                    }
                    session.close();
                    WindowUtil.alertWindow("Richtig", Alert.AlertType.CONFIRMATION, "Your flight was booked!!!");
                    dataClean();
                }
            }
        }
    }

    public void initialize() {
        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
        //Info des Kunden
        String query0 = "FROM Kunde k where k.username =: kundeUserName";

        String kundeUserName= AppSession.getIntance().getCustomerUsername();//Geloggte Kunde
        //String kundeUserName ="pearlly123";//Nur zum Testen
        Query<Kunde> query00 = session.createQuery(query0);
        query00.setParameter("kundeUserName", kundeUserName);
        List<Kunde> kundeList = query00.list();
        kunde = kundeList.get(0);
        int idkund = kunde.getId_kunde();
        String vornam = kunde.getVorname();
        String nachnam = kunde.getNachname();

        id_tf.setText(Integer.toString(idkund));
        firstname_tf.setText(vornam);
        lastname_tf.setText(nachnam);

        betrag.setDisable(true);

        //Lists Flughaefen -start und end
        String query1 = "SELECT f.name FROM Flughaefen f where f.name <>''";
        List<String> data1 = session.createQuery(query1).list();

        ObservableList<String> airportsStart = FXCollections.observableArrayList();
        ObservableList<String> airportsEnd = FXCollections.observableArrayList();

        airportsStart.addAll(data1);
        airportsEnd.addAll(data1);

        from.setItems(airportsStart);
        to.setItems(airportsStart);

        //Multistopp choicebox
        multistopp.setItems(FXCollections.observableArrayList(
                "direkt", "multi-stopp"));
        multistopp.setValue("direkt");
        //Textfeld zur angabe der maximalen zwischenstops, Fabian
        zwischenStopZahl.setDisable(true);
        multistopp.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.equals("direkt")) {
                zwischenStopZahl.setDisable(true);
                zwischenStopZahl.setText("");
            } else {
                zwischenStopZahl.setDisable(false);
            }
        });

        //from
       from.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                -> {
            //VAL
        }  );

        //to
        to.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                -> {
            //VAL
        }  );


        tableBuchung.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)

                -> {
            buchungen_act=null;
            fluge_act=null;
            if (tableBuchung.getSelectionModel().getSelectedItem() != null) {
                String startA = tableBuchung.getSelectionModel().getSelectedItem().getStartname();
                String endA = tableBuchung.getSelectionModel().getSelectedItem().getZielname();
                int flugL = tableBuchung.getSelectionModel().getSelectedItem().getId_fluglinie();

                String flugzeugTyp = tableBuchung.getSelectionModel().getSelectedItem().getModel();
                double ecoPreis = tableBuchung.getSelectionModel().getSelectedItem().getEcopreis();
                double busPreis = tableBuchung.getSelectionModel().getSelectedItem().getBusspreis();

                java.util.Date abfahrtDate = tableBuchung.getSelectionModel().getSelectedItem().getStartdate();

                int anzahlEco = Integer.parseInt(ecoseat_tf.getText());
                int anzahlBus = Integer.parseInt(busseat_tf.getText());

                double betragTot = 1.19*((anzahlEco * ecoPreis) + (anzahlBus * busPreis));

                betrag.setText(Double.toString(betragTot));

                String mult_direkt = multistopp.getValue();

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar hoy = Calendar.getInstance();
                String today = dateFormat.format(hoy.getTime());
                java.sql.Date dat = java.sql.Date.valueOf(today);

                buchungen_act = new Buchungen();
                buchungen_act.setStart(startA);
                buchungen_act.setEnde(endA);
                buchungen_act.setId_fluglinie(flugL);
                buchungen_act.setFlugzeugtyp(flugzeugTyp);
                buchungen_act.setDate(abfahrtDate);
                buchungen_act.setAnzahl_eco(anzahlEco);
                buchungen_act.setAnzahl_bus(anzahlBus);
                buchungen_act.setBetrag(betragTot);
                buchungen_act.setDate_buchung(dat);
                buchungen_act.setArt(mult_direkt);
                buchungen_act.setId_kunde(kunde.getId_kunde());
                buchungen_act.setIdflug(tableBuchung.getSelectionModel().getSelectedItem().getIdfluge());
                buchungen_act.setStatus("aktiv");//OJO Brenda
            }
        });
    }


    private void dataClean() {
        from.getSelectionModel().clearSelection();
        to.getSelectionModel().clearSelection();
        date.setValue(null);
        //multistopp.getSelectionModel().clearSelection();
        ecoseat_tf.setText("");
        busseat_tf.setText("");
        betrag.setText("");
        zwischenStopZahl.setText("");
        tableBuchung.getItems().clear();
    }

}