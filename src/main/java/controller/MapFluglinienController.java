package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MapFluglinienController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback{

    //DirectionsServiceCallback {

    private DirectionsService directionsService;
    private DirectionsPane directionsPane;

    private StringProperty fromAirport = new SimpleStringProperty();
    private StringProperty toAirport = new SimpleStringProperty();

    @FXML
    private Button button;


    @FXML
    private GoogleMapView mapView; //= new GoogleMapView(null,"AIzaSyDPJcwp49e_bgBIYexs7Y8-t3heLKG5fSI");

    private GoogleMap map;


    @FXML
    private TextField from;

    @FXML
    private TextField to;

    @FXML

    void toAction(ActionEvent event){

        DirectionsRequest request = new DirectionsRequest(fromAirport.get(), toAirport.get(), TravelModes.DRIVING);
        directionsService.getRoute(request, this, new DirectionsRenderer(true, mapView.getMap(), directionsPane));

    }
 

    @Override
    public void initialize(URL url, ResourceBundle rb) {

       //GoogleMapView mapView = new GoogleMapView(null,"AIzaSyDPJcwp49e_bgBIYexs7Y8-t3heLKG5fSI");
        mapView.addMapInializedListener(this);
        toAirport.bindBidirectional(to.textProperty());
        fromAirport.bindBidirectional(from.textProperty());


    }

    @Override
    public void mapInitialized() {

        MapOptions options = new MapOptions();

        options.center(new LatLong(47.606189, -122.335842))
                .zoomControl(true)
                .zoom(12)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);
        //GoogleMap flug_map = mapView.createMap(options);
        map = mapView.createMap(options);

        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();

        ////////////////////////////////////////////////////////////

        LatLong joeSmithLocation = new LatLong(47.6197, -122.3231);
        LatLong joshAndersonLocation = new LatLong(47.6297, -122.3431);
        LatLong bobUnderwoodLocation = new LatLong(47.6397, -122.3031);
        LatLong tomChoiceLocation = new LatLong(47.6497, -122.3325);
        LatLong fredWilkieLocation = new LatLong(47.6597, -122.3357);


        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(47.6097, -122.3331))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(12);

        map = mapView.createMap(mapOptions);
        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();
        map.showDirectionsPane(); //::::::::::::::


        //Add markers to the map
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(joeSmithLocation);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(joshAndersonLocation);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(bobUnderwoodLocation);

        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(tomChoiceLocation);

        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.position(fredWilkieLocation);

        Marker joeSmithMarker = new Marker(markerOptions1);
        Marker joshAndersonMarker = new Marker(markerOptions2);
        Marker bobUnderwoodMarker = new Marker(markerOptions3);
        Marker tomChoiceMarker = new Marker(markerOptions4);
        Marker fredWilkieMarker = new Marker(markerOptions5);

        map.addMarker(joeSmithMarker);
        map.addMarker(joshAndersonMarker);
        map.addMarker(bobUnderwoodMarker);
        map.addMarker(tomChoiceMarker);
        map.addMarker(fredWilkieMarker);

        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<h2>Fred Wilkie</h2>"
                + "Current Location: Safeway<br>"
                + "ETA: 45 minutes");

        InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
        fredWilkeInfoWindow.open(map, fredWilkieMarker);
    }

    @Override
    public void directionsReceived(DirectionsResult directionsResult, DirectionStatus directionStatus) {


    }
}


