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
import service.FluegeService;
import java.util.Date;
import java.util.List;

public class StornierungController {
    org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();

    private FlugGesManager manager;

    @FXML
    private Button btnStornieren;

    @FXML
    private Button btnGewinn;

    @FXML
    private Button btnAbbrechen;

    @FXML
    private TextField id_tf;

    @FXML
    private TextField firstname_tf;

    @FXML
    private TextField lastname_tf;


    @FXML
    private TextField gesellschaft;

    @FXML
    private TableView<FlugeView> tableStornierung;

    @FXML
    private TableColumn<FlugeView, Integer> tc1;//idflug

    @FXML
    private TableColumn<FlugeView, String> tc2;//Flugzeug

    @FXML
    private TableColumn<FlugeView, Date> tc3;//Date

    @FXML
    private TableColumn<FlugeView, String> tc4;//Start

    @FXML
    private TableColumn<FlugeView, String> tc5;//End

    @FXML
    private TableColumn<FlugeView, Double> tc6;//Gewinn

    @FXML
    private TableColumn<FlugeView, Integer> tc7;//Sold Eco seats

    @FXML
    private TableColumn<FlugeView, Integer> tc8;// Sold Busseats



    @FXML
    private TableView<BuchungView> tableBuchungen;

    @FXML
    private TableColumn<BuchungView, Integer> tcol1;//idkunde

    @FXML
    private TableColumn<BuchungView, String> tcol2;//firstname

    @FXML
    private TableColumn<BuchungView, String> tcol3;//lastname

    @FXML
    private TableColumn<BuchungView, Integer> tcol4;//Buchungnummer

    @FXML
    private TableColumn<BuchungView, Integer> tcol5;//N°Ecoseats

    @FXML
    private TableColumn<BuchungView, Integer> tcol6;//N°Businessseats

    @FXML
    private TableColumn<BuchungView, Double> tcol7;//Betrag sin Mehrsteuer

    @FXML
    public void initialize() {

        int idmanager = AppSession.getIntance().getId_manager();
        //int idmanager = 7517;//S.Probe con Fluggesell

        String gesell = AppSession.getIntance().getGesellshaftname();
        //Si es nuevo manager OJO, especificar este caso//Falta
        //int int idmanager = 7984;//S.Probe sin Fluggesell -Senior Miprueba Manager OJO
        String query0 = "FROM FlugGesManager m where m.id_manager =: id_man";

        Query<FlugGesManager> query00 = session.createQuery(query0);
        query00.setParameter("id_man", idmanager);
        List<FlugGesManager> managerList = query00.list();
        manager = managerList.get(0);
        String vornam = manager.getVorname();
        String nachnam = manager.getNachname();

        id_tf.setText(Integer.toString(idmanager));
        id_tf.setDisable(true);
        firstname_tf.setText(vornam);
        firstname_tf.setDisable(true);

        lastname_tf.setText(nachnam);
        lastname_tf.setDisable(true);

        gesellschaft.setText(gesell);
        gesellschaft.setDisable(true);

        tableStornierung.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                -> {
            if (tableStornierung.getSelectionModel().getSelectedItem() != null) {
                fillTableBuchungen();
            }
        });
        refreshTableFluge();
    }


    @FXML
    void gewinn(ActionEvent event) {
        FluegeService service = new FluegeService();
        service.calculateWinn();
        service.calculateBookings();
        refreshTableFluge();
        System.out.println("Es läuft!");
    }

    public void fillTableBuchungen() {

        int id_flug = tableStornierung.getSelectionModel().getSelectedItem().getIdfluge();

        String query2 = "SELECT b.id_kunde ,k.vorname,k.nachname,b.id_buchung," +
                " b.anzahl_eco, b.anzahl_bus, b.betrag" +
                " FROM Buchungen b, Kunde k " +
                "where b.idflug = :flugID and b.id_kunde = k.id_kunde" +
                " and b.status != 'inaktiv'";

        Query query02 = session.createQuery(query2);
        query02.setParameter("flugID", id_flug);
        List<Object[]> buchungenList = query02.list();

        ObservableList<BuchungView> buchungViews = FXCollections.observableArrayList();

        tableBuchungen.getItems().clear();

        tcol1.setCellValueFactory(new PropertyValueFactory<>("idkunde"));//idkunde
        tcol2.setCellValueFactory(new PropertyValueFactory<>("vorname"));//vorname
        tcol3.setCellValueFactory(new PropertyValueFactory<>("nachname"));//nachname
        tcol4.setCellValueFactory(new PropertyValueFactory<>("buchungnr"));//buchungnr
        tcol5.setCellValueFactory(new PropertyValueFactory<>("ecoseats"));//ecoseats
        tcol6.setCellValueFactory(new PropertyValueFactory<>("busseats"));//busseats
        tcol7.setCellValueFactory(new PropertyValueFactory<>("betrag"));//betrag


        for (Object[] obj : buchungenList) {
            int idk = (int) obj[0];
            String fn = obj[1].toString();
            String ln = obj[2].toString();
            int bnr = (int) obj[3];
            int ecos = (int) obj[4];
            int buss = (int) obj[5];
            double bet = (double) obj[6];

            BuchungView buchungView = new BuchungView();
            buchungView.setIdkunde(idk);
            buchungView.setVorname(fn);
            buchungView.setNachname(ln);
            buchungView.setBuchungnr(bnr);
            buchungView.setEcoseats(ecos);
            buchungView.setBusseats(buss);
            buchungView.setBetrag(bet);
            //betrag ohne Mehrsteuer?
            buchungViews.add(buchungView);
        }

        tableBuchungen.getItems().addAll(buchungViews);
    }

    @FXML
    void Stornieren(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are  you sure to cancel this flight?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();//muestra ventanita y espera accion

        if (alert.getResult() == ButtonType.YES) {
            int idflug = tableStornierung.getSelectionModel().getSelectedItem().getIdfluge();
            FluegeService service = new FluegeService();
            Fluge fluge = new Fluge();
            fluge.setIdflug(idflug);
            service.cancelFluge(fluge);
            refreshTableFluge();
        }
    }

    @FXML
    void Abbrechen(ActionEvent event) {
        Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    void refreshTableFluge() {
        String query1 = "SELECT fg.idflug ,fa.model, fg.abfahrtdate, fa.startname," +
                "fa.zielname, fa.id_fluglinie, fg.gewinn, fg.ecosold, fg.bussold " +
                "FROM Fluge fg, FluglinieAnlegen fa " +
                "where fg.idfluglinie =fa.id_fluglinie" +
                " and fg.status!='inaktiv'";

        Query query01 = session.createQuery(query1);
        List<Object[]> flugeList = query01.list();

        tableStornierung.getItems().clear();
        tableBuchungen.getItems().clear();
        if (flugeList != null && flugeList.size() > 0) {

            ObservableList<FlugeView> flugeViews = FXCollections.observableArrayList();

            for (Object[] obj : flugeList) {
                int id = (int) obj[0];
                String modell = obj[1].toString();
                java.sql.Date dat = (java.sql.Date) obj[2];
                String startnam = obj[3].toString();
                String zielnam = obj[4].toString();
                int idfluglin =(int)obj[5];
                double gew = (double) obj[6];
                int ecosl =(int)obj[7];
                int bussl =(int)obj[8];


                FlugeView flugeV = new FlugeView();
                flugeV.setIdfluge(id);
                flugeV.setModel(modell);
                flugeV.setStartdate(dat);
                flugeV.setStartname(startnam);
                flugeV.setZielname(zielnam);
                flugeV.setGewinn(gew);
                flugeV.setEcosold(ecosl);
                flugeV.setBussold(bussl);

                flugeViews.add(flugeV);
            }

            tc1.setCellValueFactory(new PropertyValueFactory<>("idfluge"));//id_flug
            tc2.setCellValueFactory(new PropertyValueFactory<>("model"));//flugzeug
            tc3.setCellValueFactory(new PropertyValueFactory<>("startdate"));//date
            tc4.setCellValueFactory(new PropertyValueFactory<>("startname"));//start
            tc5.setCellValueFactory(new PropertyValueFactory<>("zielname"));//end
            tc6.setCellValueFactory(new PropertyValueFactory<>("gewinn"));//gewinn
            tc7.setCellValueFactory(new PropertyValueFactory<>("ecosold"));//anzahl -OJO-Sold EcoSeats
            tc8.setCellValueFactory(new PropertyValueFactory<>("bussold"));//anzahl2 -OJO-Sold BusSeats

            for (FlugeView f : flugeViews) {
                tableStornierung.getItems().add(f);
            }
        }
    }
}