package controller;


import com.itextpdf.text.Document;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.*;
import org.hibernate.query.Query;

import java.io.File;
import java.io.FileOutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import utils.WindowUtil;


import javax.swing.*;
import java.util.Date;
import java.util.List;

import static com.itextpdf.text.Font.FontFamily.COURIER;

public class BuchungsübersichtController {

    Stage stage00 = new Stage();


    @FXML
    private Button btnAbbrechen;

    @FXML
    private Button btnRoute;

    @FXML
    private Button btn_pdfExport;


    @FXML
    private TableView<Buchungen> tableFluglinie;

    @FXML
    private TableColumn<Buchungen, Integer> id_buchung;

    @FXML
    private TableColumn<Buchungen, Integer> id_kunde;

    @FXML
    private TableColumn<Buchungen, Integer> id_fluglinie;

    @FXML
    private TableColumn<Buchungen, Date> date;

    @FXML
    private TableColumn<Buchungen, Integer> nr_Eco;

    @FXML
    private TableColumn<Buchungen, Integer> nr_bus;

    @FXML
    private TableColumn<Buchungen, Double> betrag;

    @FXML
    private TableColumn<Buchungen, String> start;

    @FXML
    private TableColumn<Buchungen, String> end;

    @FXML
    private TableColumn<Buchungen, String> flugzeugstyp;

    @FXML
    private TableColumn<Buchungen, Date> buchungsdatum;

    @FXML
    private TableColumn<Buchungen, String> art;

    @FXML
    private TableColumn<Buchungen, String> res_status = new TableColumn<>("Reservation Status");


    @FXML
    void abbrechen(ActionEvent event) {
        Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    @FXML
    void route(ActionEvent event) {
        AppSession.getIntance().setId_buchung(id_buchung.getTableView().getSelectionModel().getSelectedItem().getId_buchung());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Web.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Route zum Flughafen");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); //java.io.IOException
        }
    }

    @FXML
    public void initialize() {

        try {
            org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();

            String customerUN = AppSession.getIntance().getCustomerUsername();
            //String customerUN ="BBolon";//Nur zum Testen
            Query<Kunde> query1 = session1.createQuery("FROM Kunde k WHERE k.username = : customerUN");
            query1.setParameter("customerUN", customerUN);
            List<Kunde> UNList = query1.list();
            Kunde UNRec = UNList.get(0);

            int UNRecord = UNRec.getId_kunde();

            Query<Buchungen> query2 = session1.createQuery("FROM Buchungen b  WHERE b.id_kunde=: id_kunde");
            query2.setParameter("id_kunde", UNRecord);
            List<Buchungen> bookings = query2.list();

            if (bookings != null) {

                id_buchung.setCellValueFactory(new PropertyValueFactory<>("id_buchung"));

                id_kunde.setCellValueFactory(new PropertyValueFactory<>("id_kunde"));

                id_fluglinie.setCellValueFactory(new PropertyValueFactory<>("id_fluglinie"));

                date.setCellValueFactory(new PropertyValueFactory<>("date"));

                nr_Eco.setCellValueFactory(new PropertyValueFactory<>("anzahl_eco"));

                nr_bus.setCellValueFactory(new PropertyValueFactory<>("anzahl_bus"));

                betrag.setCellValueFactory(new PropertyValueFactory<>("betrag"));

                start.setCellValueFactory(new PropertyValueFactory<>("start"));

                end.setCellValueFactory(new PropertyValueFactory<>("ende"));

                flugzeugstyp.setCellValueFactory(new PropertyValueFactory<>("flugzeugtyp"));

                buchungsdatum.setCellValueFactory(new PropertyValueFactory<>("date_buchung"));

                art.setCellValueFactory(new PropertyValueFactory<>("art"));

                res_status.setCellValueFactory(cellData -> {
                    boolean reserviert = cellData.getValue().isHatSitzeReserviert();
                    String reservationString;
                    if (reserviert == true) {
                        reservationString = "reserviert";
                    } else {
                        reservationString = "nicht reserviert";
                    }
                    return new ReadOnlyStringWrapper(reservationString);

                });

                for (Buchungen allbookings : bookings) {

                    tableFluglinie.getItems().add(allbookings);
                }
                //Brenda-Anfang
                tableFluglinie.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue)
                        -> {
                    if (tableFluglinie.getSelectionModel().getSelectedItem() != null) {
                        String endAirport = tableFluglinie.getSelectionModel().getSelectedItem().getEnde();//findet Zielairport


                        Query<Flughaefen>query3 = session1.createQuery("FROM Flughaefen a WHERE a.name =: endName");
                        query3.setParameter("endName", endAirport);
                        List<Flughaefen> airportList = query3.list();
                        Flughaefen airportRec = airportList.get(0);
                        String endName = airportRec.getName();
                        Double lat = airportRec.getLat();
                        Double lon = airportRec.getLon();
                        System.out.println(endName+" "+lat+"  ,  "+lon);

                        try {
                            AppSession.getIntance().setDatei(airportRec);
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/EndAirport.fxml"));
                            Parent root = fxmlLoader.load();
                            stage00.setTitle("End Airport View");
                            stage00.setScene(new Scene(root));
                            stage00.show();//Cómo hago para cerrarlo nuevamente
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                 //Brenda Ende
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @FXML
    void pdfExportieren(ActionEvent event) {

        Document document = new Document();
        String home = System.getProperty("user.home");
        String fileName = home+"/Downloads/Buchungsübersicht.pdf";

        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
        String customerUN = AppSession.getIntance().getCustomerUsername();
        Query<Kunde> query1 = session.createQuery("FROM Kunde k WHERE k.username = : customerUN");
        query1.setParameter("customerUN", customerUN);
        List<Kunde> UNList = query1.list();
        Kunde UNRec = UNList.get(0);


       // int idKunde = UNRec.getId_kunde();
        String firstname = UNRec.getVorname();
        String lastname = UNRec.getNachname();


        int UNRecord = UNRec.getId_kunde();

        Query<Buchungen> query2 = session.createQuery("FROM Buchungen b  WHERE b.id_kunde=: id_kunde");
        query2.setParameter("id_kunde",UNRecord);
        List<Buchungen> bookings = query2.list();


        try {

            if(bookings!=null) {

                // PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(fileName));
                PdfWriter.getInstance(document, new FileOutputStream(fileName));
                document.open();

                // creating my table
                Font tableFont = new Font(COURIER, 5, Font.NORMAL);
                PdfPTable table = new PdfPTable(13);

                float[] columnWidths = {0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.08f, 0.1f, 0.1f, 0.1f};
                table.setWidths(columnWidths);
                table.setWidthPercentage(100);
                table.setHorizontalAlignment(0);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);


                // creating cells
                PdfPCell cell1 = new PdfPCell(new Phrase("BuchungID", tableFont));
                table.addCell(cell1);

                PdfPCell cell2 = new PdfPCell(new Phrase("KundeID", tableFont));
                table.addCell(cell2);

                PdfPCell cell3 = new PdfPCell(new Phrase("FluglinieID", tableFont));
                table.addCell(cell3);

                PdfPCell cell4 = new PdfPCell(new Phrase("Date", tableFont));
                table.addCell(cell4);

                PdfPCell cell5 = new PdfPCell(new Paragraph("Nr_Economy", tableFont));
                table.addCell(cell5);

                PdfPCell cell6 = new PdfPCell(new Phrase("Nr_Business", tableFont));
                table.addCell(cell6);

                PdfPCell cell7 = new PdfPCell(new Phrase("Betrag", tableFont));
                table.addCell(cell7);

                PdfPCell cell8 = new PdfPCell(new Phrase("Von", tableFont));
                table.addCell(cell8);

                PdfPCell cell9 = new PdfPCell(new Phrase("Nach", tableFont));
                table.addCell(cell9);

                PdfPCell cell10 = new PdfPCell(new Phrase("Flugzeugtyp", tableFont));
                table.addCell(cell10);

                PdfPCell cell11 = new PdfPCell(new Phrase("Buchungsdatum", tableFont));
                table.addCell(cell11);

                PdfPCell cell12 = new PdfPCell(new Phrase("Art", tableFont));
                table.addCell(cell12);

                PdfPCell cell13 = new PdfPCell(new Phrase("Ist_Sitz_Res.", tableFont));
                table.addCell(cell13);

                String info = "Buchungsübersicht für" ;

                Font headingFont=new Font(COURIER,10.0f,Font.UNDERLINE,BaseColor.BLUE);
                Paragraph p1 = new Paragraph(info +" " + firstname+ " " + lastname,headingFont);
                p1.setAlignment(Element.ALIGN_CENTER);

                table.setHeaderRows(1);



              for (int i = 0; i < bookings.size(); i++) {
                    table.addCell(new Phrase(Integer.toString(bookings.get(i).getId_buchung()), tableFont));
                    table.addCell(new Phrase(Integer.toString(bookings.get(i).getId_kunde()), tableFont));
                    table.addCell(new Phrase(Integer.toString(bookings.get(i).getId_fluglinie()), tableFont));
                    table.addCell(new Phrase(String.valueOf(bookings.get(i).getDate()), tableFont));
                    table.addCell(new Phrase(Integer.toString(bookings.get(i).getAnzahl_eco()), tableFont));
                    table.addCell(new Phrase(Double.toString(bookings.get(i).getAnzahl_bus()), tableFont));
                    table.addCell(new Phrase(Double.toString(bookings.get(i).getBetrag()), tableFont));
                    table.addCell(new Phrase(bookings.get(i).getStart(), tableFont));
                    table.addCell(new Phrase(bookings.get(i).getEnde(), tableFont));
                    table.addCell(new Phrase(bookings.get(i).getFlugzeugtyp(), tableFont));
                    table.addCell(new Phrase(String.valueOf(bookings.get(i).getDate_buchung()), tableFont));
                    table.addCell(new Phrase(bookings.get(i).getArt(), tableFont));
                    table.addCell(new Phrase(String.valueOf(bookings.get(i).isHatSitzeReserviert()), tableFont));

                }

                document.add(p1);
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






