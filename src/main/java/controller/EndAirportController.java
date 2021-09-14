package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.AppSession;
import models.Flughaefen;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EndAirportController {

    @FXML
    private Button btnAusgang;

    @FXML
    private Button interest_btn;

    @FXML
    private Button fotos_btn;

    @FXML
    private Button map_btn;

    @FXML
    private WebView webView_wb;

    @FXML
    private TextField endName_tf;

    String endName ="Al Gaidah Airport";//Nur Probe
    Double lat =16.1947 ;///Nur Probe
    Double lon = 52.1694;///Nur Probe

    public void initialize() {
        Flughaefen flughaefen=(Flughaefen)AppSession.getIntance().getDatei();
        String endName =flughaefen.getName();
        endName_tf.setText(endName);
        endName_tf.setDisable(true);
    }

    @FXML
    void Ausgang(ActionEvent event) {
        Stage stage = (Stage) btnAusgang.getScene().getWindow();
        stage.close();
    }

    @FXML
    void map(ActionEvent event) {
        try {
            //String filename1 = getClass().getResource("/htmls/MapWithMarker.html").getPath()+"?";
            String filename1=new File(".").getCanonicalPath()+"\\htmls\\MapWithMarker.html?";
            Flughaefen flughaefen=(Flughaefen)AppSession.getIntance().getDatei();
            filename1 +="lat="+flughaefen.getLat();
            filename1 +="&lng="+flughaefen.getLon();
            File file = new File(filename1);
            URL url = file.toURL();

            System.out.println("Test Url: " + url.toString());
            webView_wb.getEngine().load(url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}