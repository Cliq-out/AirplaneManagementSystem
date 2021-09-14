package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.AppSession;
import models.Flughaefen;
import models.FluglinieAnlegen;
import models.SessionFactoryUtil;
import org.hibernate.query.Query;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AirportsMapController {

    @FXML
    private Button btnAusgang;


    @FXML
    private Button map_btn;

    @FXML
    private WebView webView_wb;

    @FXML
    private TextField startName_tf;

    @FXML
    private TextField endName_tf;


    Double lat1 ;
    Double lon1 ;
    Double lat2 ;
    Double lon2 ;


    public void initialize() {

        org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();

        FluglinieAnlegen fluglinie = (FluglinieAnlegen) AppSession.getIntance().getDatei();
        String start = fluglinie.getStartname();
        String ende = fluglinie.getZielname();

        startName_tf.setText(start);
        startName_tf.setDisable(true);

        endName_tf.setText(ende);
        endName_tf.setDisable(true);


        Query<Flughaefen> query1 = session1.createQuery("FROM Flughaefen a WHERE a.name =: startName");
        query1.setParameter("startName", start);
        List<Flughaefen> airportList = query1.list();
        Flughaefen startairportRec = airportList.get(0);

        lat1 = startairportRec.getLat();
        lon1 = startairportRec.getLon();


        Query<Flughaefen>query2= session1.createQuery("FROM Flughaefen a WHERE a.name =: endName");
        query2.setParameter("endName", ende);
        List<Flughaefen> airportList2 = query2.list();
        Flughaefen endairportRec = airportList2.get(0);

        lat2 = endairportRec.getLat();
        lon2 = endairportRec.getLon();


    }

    @FXML
    void Ausgang(ActionEvent event) {
        Stage stage = (Stage) btnAusgang.getScene().getWindow();
        stage.close();
    }

    @FXML
    void map(ActionEvent event) {
        try {

            String filename1=new File(".").getCanonicalPath()+"\\htmls\\MapWithPolylines.html?";

            filename1 +="lat1="+lat1;
            filename1 +="&lng1="+lon1;
            filename1 +="&lat2="+lat2;
            filename1 +="&lng2="+lon2;
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