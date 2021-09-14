package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.*;
import org.hibernate.Query;
import utils.WindowUtil;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import static com.itextpdf.text.Font.FontFamily.COURIER;


public class FlugübersichtController {

    @FXML
    private Button btnAbbrechen;

    @FXML
    private TableView<FlugeView> tableFluglinie;

    @FXML
    private TableColumn<FlugeView, Integer> id_Flug;

    @FXML
    private TableColumn<FlugeView, Date> abfahrtdate;

    @FXML
    private TableColumn<FlugeView, Integer> id_fluglinie;

    @FXML
    private TableColumn<FlugeView, String> startairport;

    @FXML
    private TableColumn<FlugeView, String> endairport;

    @FXML
    private TableColumn<FlugeView, Integer> nr_eco;

    @FXML
    private TableColumn<FlugeView, Integer> bus_seats;

    @FXML
    private TableColumn<FlugeView, String> model;

    @FXML
    private TableColumn<FlugeView, Double> preis_Eco;

    @FXML
    private TableColumn<FlugeView, Double> preis_Bus;

    @FXML
    private Button btn_pdfExport;

    Stage stage00 = new Stage();

    @FXML
    void abbrechen(ActionEvent event) {

        Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {

        try {
            org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
            int flight_search = AppSession.getIntance().getId_gesellschaft();

            String query = "SELECT flug.idflug ,flug.abfahrtdate, fluglinieAnl.startname, fluglinieAnl.zielname, fluglinieAnl.ecopreis, fluglinieAnl.busspreis," +
                    "fluglinieAnl.model, fluglinieAnl.id_fluglinie, flug.ecoseatact, flug.busseatact " +
                    "FROM Fluge flug, FluglinieAnlegen fluglinieAnl where flug.idfluglinie = fluglinieAnl.id_fluglinie and " +
                    "fluglinieAnl.fluggesellschaft =: allFlug";


            Query query1 = session.createQuery(query);
            query1.setParameter("allFlug", flight_search);
            List<Object[]> allList = query1.list();

            if (allList != null && allList.size() > 0) {

            }
            ObservableList<FlugeView> flugeViews = FXCollections.observableArrayList();
            for (Object[] obj : allList) {
                int id = Integer.parseInt(obj[0].toString());
                java.sql.Date date = (java.sql.Date) obj[1];
                String from = obj[2].toString();
                String to = obj[3].toString();
                double ecopreis = Double.parseDouble(obj[4].toString());
                double buspreis = Double.parseDouble(obj[5].toString());
                String modell = obj[6].toString();
                int idFluglinie = Integer.parseInt(obj[7].toString());
                int ecoSeatNr = Integer.parseInt(obj[8].toString());
                int busSeatNr = Integer.parseInt(obj[9].toString());

                FlugeView InstantierteFluge = new FlugeView();
                InstantierteFluge.setIdfluge(id);
                InstantierteFluge.setStartdate(date);
                InstantierteFluge.setStartname(from);
                InstantierteFluge.setZielname(to);
                InstantierteFluge.setEcopreis(ecopreis);
                InstantierteFluge.setBusspreis(buspreis);
                InstantierteFluge.setModel(modell);
                InstantierteFluge.setId_fluglinie(idFluglinie);
                InstantierteFluge.setEcoseat(ecoSeatNr);
                InstantierteFluge.setBusseat(busSeatNr);
                flugeViews.add(InstantierteFluge);

            }

            id_Flug.setCellValueFactory(new PropertyValueFactory<>("idfluge"));
            id_fluglinie.setCellValueFactory(new PropertyValueFactory<>("id_fluglinie"));
            abfahrtdate.setCellValueFactory(new PropertyValueFactory<>("startdate"));
            nr_eco.setCellValueFactory(new PropertyValueFactory<>("ecoseat"));
            bus_seats.setCellValueFactory(new PropertyValueFactory<>("busseat"));
            startairport.setCellValueFactory(new PropertyValueFactory<>("startname"));
            endairport.setCellValueFactory(new PropertyValueFactory<>("zielname"));
            model.setCellValueFactory(new PropertyValueFactory<>("model"));
            preis_Bus.setCellValueFactory(new PropertyValueFactory<>("busspreis"));
            preis_Eco.setCellValueFactory(new PropertyValueFactory<>("ecopreis"));


            for (FlugeView all : flugeViews) {
                tableFluglinie.getItems().add(all);
            }

            // Continuation: Iteration3 Map

           tableFluglinie.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                    -> {
                if (tableFluglinie.getSelectionModel().getSelectedItem() != null) {


                    int fluglinieID = tableFluglinie.getSelectionModel().getSelectedItem().getId_fluglinie();
                    Query<FluglinieAnlegen>query2 = session.createQuery("FROM FluglinieAnlegen f WHERE f.id_fluglinie =: fluglinieID");
                    query2.setParameter("fluglinieID", fluglinieID );
                    List<FluglinieAnlegen> fluglinList = query2.list();
                    FluglinieAnlegen fluglinieREC = fluglinList.get(0);


                    try {
                        AppSession.getIntance().setDatei(fluglinieREC);
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/AirportsMap.fxml"));
                        Parent root = fxmlLoader.load();
                        stage00.setTitle("Route between Start and End Airports");
                        stage00.setScene(new Scene(root));
                        stage00.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @FXML
    void pdfExportieren(ActionEvent event) {

        Document document = new Document();
        String home = System.getProperty("user.home");
        String fileName = home+"/Downloads/Buchungsübersicht.pdf";
        org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();

        Query<Fluge> query2 = session1.createQuery("FROM Fluge");

        List<Fluge> FList = query2.list();

        try {

            if (FList != null) {
                PdfWriter.getInstance(document, new FileOutputStream(fileName));
                document.open();

                // creating my table
                Font tableFont = new Font(COURIER, 10, Font.NORMAL);
                PdfPTable table = new PdfPTable(5);

                float[] columnWidths = {0.1f, 0.1f, 0.1f, 0.1f, 0.1f};
                table.setWidths(columnWidths);
                table.setWidthPercentage(100);
                table.setHorizontalAlignment(0);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);

                // creating cells

                PdfPCell cell1 = new PdfPCell(new Phrase("Flug_ID", tableFont));
                table.addCell(cell1);

                PdfPCell cell2 = new PdfPCell(new Phrase("Abfahrt_Datum", tableFont));
                table.addCell(cell2);

                PdfPCell cell3 = new PdfPCell(new Phrase("Fluglinie_ID", tableFont));
                table.addCell(cell3);

                PdfPCell cell4 = new PdfPCell(new Paragraph("Nr_Economy", tableFont));
                table.addCell(cell4);

                PdfPCell cell5 = new PdfPCell(new Phrase("Nr_Business", tableFont));
                table.addCell(cell5);

                table.setHeaderRows(1);

                for (int i = 0; i < FList.size(); i++) {


                    table.addCell(new Phrase(Integer.toString(FList.get(i).getIdflug()), tableFont));
                    table.addCell(new Phrase(String.valueOf(FList.get(i).getAbfahrtdate()), tableFont));
                    table.addCell(new Phrase(Integer.toString(FList.get(i).getIdfluglinie()), tableFont));
                    table.addCell(new Phrase(Integer.toString(FList.get(i).getEcoseatact()), tableFont));
                    table.addCell(new Phrase(Integer.toString(FList.get(i).getBusseatact()), tableFont));
                }

                document.add(table);

                document.close();
                System.out.println("PDF Created");
                WindowUtil.alertWindow("Done!", Alert.AlertType.CONFIRMATION, "Your PDF has been successfully saved in your Downloads Folder");


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}