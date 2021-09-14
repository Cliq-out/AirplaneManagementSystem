package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.AppSession;
import models.Buchungen;
import models.Flughaefen;
import models.Kunde;
import org.hibernate.query.Query;

import com.lynden.gmapsfx.service.directions.*;

public class RoutenController implements MapComponentInitializedListener, DirectionsServiceCallback{

    private final String apikey = "AIzaSyDPJcwp49e_bgBIYexs7Y8-t3heLKG5fSI"; //von pearl

    private boolean mitAuto = true;
    private Flughaefen flughafen;
    private String start, ziel;

    @FXML
    private GoogleMapView mapView;
    private DirectionsService directionsService;
    private DirectionsPane directionsPane;

    @FXML
    private Label Route;

    @FXML
    private Button btnSwitch;

    @FXML
    private Button btnAbbrechen;

    @FXML
    private TextField fromTextField;

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
        } else {
            btnSwitch.setText("Zu Route mit dem Auto wechseln");
        }
        this.updateRoute();
    }

    private void updateRoute() {
        DirectionsRequest route;
        if (mitAuto) {
            route = new DirectionsRequest(start, ziel, TravelModes.DRIVING);
        } else {
            route = new DirectionsRequest(start, ziel, TravelModes.TRANSIT);
        }
        directionsService.getRoute(route, this, new DirectionsRenderer(true, mapView.getMap(), directionsPane));
    }

    @Override
    public void directionsReceived(DirectionsResult directionsResult, DirectionStatus directionStatus) {

    }

    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        options.center(new LatLong(flughafen.getLat(),flughafen.getLon()))
                .zoomControl(true)
                .zoom(12)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);
        GoogleMap map = mapView.createMap(options);
        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();
    }

    @FXML
    public void initialize() {
        from.bindBidirectional(fromTextField.textProperty());

        org.hibernate.Session session = models.SessionFactoryUtil.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query<Kunde> query1 = session.createQuery("FROM Kunde k WHERE k.username = '" + AppSession.getIntance().getCustomerUsername() + "'");
        Kunde kunde = query1.list().get(0);
        Query<Buchungen> query2 = session.createQuery("FROM Buchungen b WHERE b.id_buchung = " + AppSession.getIntance().getId_buchung());
        Buchungen buchung = query2.list().get(0);
        Query<Flughaefen> query3 = session.createQuery("FROM Flughaefen f WHERE f.name = '" + buchung.getStart() + "'");
        this.flughafen = query3.list().get(0);
        session.close();

        mapView.setKey(apikey);
        mapView.addMapInializedListener(this);

        this.start = kunde.getAdresse();
        this.ziel = flughafen.getName();
        //this.updateRoute();
    }

    protected StringProperty from = new SimpleStringProperty();

    public void toTextFieldAction(ActionEvent actionEvent) {
        DirectionsRequest request = new DirectionsRequest(from.get(),flughafen.getName(), TravelModes.DRIVING);
        directionsService.getRoute(request, this, new DirectionsRenderer(true, mapView.getMap(), directionsPane));
    }
}
