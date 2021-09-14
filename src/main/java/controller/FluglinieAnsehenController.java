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
import models.FluglinieAnlegen;
import models.SessionFactoryUtil;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class FluglinieAnsehenController {

    @FXML
    private Button btnAbbrechen;

    @FXML
    private TableView<FluglinieAnlegen> tableFluglinie;


    @FXML
    private TableColumn<FluglinieAnlegen, Integer> flights;

    @FXML
    private TableColumn<FluglinieAnlegen, String> start;

    @FXML
    private TableColumn<FluglinieAnlegen, String> Ziel;

    @FXML
    private TableColumn<FluglinieAnlegen, String> interval;

    @FXML
    private TableColumn<FluglinieAnlegen, Date> start_id;

    @FXML
    private TableColumn<FluglinieAnlegen, Integer> nr_eco;

    @FXML
    private TableColumn<FluglinieAnlegen, Double> pr_eco;

    @FXML
    private TableColumn<FluglinieAnlegen, Integer> nr_bus;

    @FXML
    private TableColumn<FluglinieAnlegen, Double> pr_bus;

    @FXML
    private TableColumn<FluglinieAnlegen, String> Model;

    Stage stage00 = new Stage();


    @FXML
    void abbrechen(ActionEvent event) {
        Stage stage= (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void initialize() {
        try{
            org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();

            int fluggessuche = AppSession.getIntance().getId_gesellschaft();

            Query<FluglinieAnlegen> query01 = session1.createQuery("FROM FluglinieAnlegen fm WHERE fm.fluggesellschaft = :fluggesID");

            query01.setParameter("fluggesID",fluggessuche );
            List<FluglinieAnlegen> fluglinieList = query01.list();

            flights.setCellValueFactory(new PropertyValueFactory<>("id_fluglinie"));
            start.setCellValueFactory(new PropertyValueFactory<>("startname"));
            Ziel.setCellValueFactory(new PropertyValueFactory<>("zielname"));
            interval.setCellValueFactory(new PropertyValueFactory<>("interval"));
            start_id.setCellValueFactory(new PropertyValueFactory<>("startdatum"));
            nr_eco.setCellValueFactory(new PropertyValueFactory<>("ecoseats"));
            pr_eco.setCellValueFactory(new PropertyValueFactory<>("ecopreis"));
            nr_bus.setCellValueFactory(new PropertyValueFactory<>("bussseats"));
            pr_bus.setCellValueFactory(new PropertyValueFactory<>("busspreis"));
            Model.setCellValueFactory(new PropertyValueFactory<>("model"));

            for (FluglinieAnlegen fl : fluglinieList) {
                tableFluglinie.getItems().add(fl);
            }
            // Continuation: Iteration3 Map
            tableFluglinie.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                    -> {
                if (tableFluglinie.getSelectionModel().getSelectedItem() != null) {

                    int fluglinieID = tableFluglinie.getSelectionModel().getSelectedItem().getId_fluglinie();
                    Query<FluglinieAnlegen>query1 = session1.createQuery("FROM FluglinieAnlegen f WHERE f.id_fluglinie =: fluglinieID");
                    query1.setParameter("fluglinieID", fluglinieID );
                    List<FluglinieAnlegen> fluglinList = query1.list();
                    FluglinieAnlegen fluglinieREC = fluglinList.get(0);


                    try {
                        AppSession.getIntance().setDatei(fluglinieREC);
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/AirportsMap.fxml"));
                        Parent root = fxmlLoader.load();
                        stage00.setTitle("Route between Start and Endairport");
                        stage00.setScene(new Scene(root));
                        stage00.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}