package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.scene.control.Alert;
import models.Buchungen;
import models.SessionFactoryUtil;
import org.hibernate.query.Query;
import utils.WindowUtil;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class BuchungenPDF {
    org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
    int id_kunde = 1;
    String firstname = "Beto";
    String lastname = "Ortiz";


    public void TextData() {

        Document document = new Document();
        String pdfData = "Buchungen.pdf";
        Query<Buchungen> query = session.createQuery("FROM Buchungen b where b.id_kunde =: kundeid");//where...
        query.setParameter("kundeid", id_kunde);
        java.util.List<Buchungen> books = query.list();


        try {
            if (books != null) {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfData));
                document.open();

                PdfPTable table = new PdfPTable(8); // 8 columns.
                table.setWidthPercentage(100); //Width 100%
                table.setSpacingBefore(10f); //Space before table
                table.setSpacingAfter(10f); //Space after table

                //Set Column widths
                float[] columnWidths = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
                table.setWidths(columnWidths);

                PdfPCell cell1 = new PdfPCell(new Paragraph("Id_Buchung"));
                cell1.setPaddingLeft(10);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell2 = new PdfPCell(new Paragraph("Start"));
                cell2.setPaddingLeft(10);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell3 = new PdfPCell(new Paragraph("End"));
                cell3.setPaddingLeft(10);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell4 = new PdfPCell(new Paragraph("Flugzeug"));
                cell3.setPaddingLeft(10);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell5 = new PdfPCell(new Paragraph("N°Sitzplätze"));
                cell3.setPaddingLeft(10);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell6 = new PdfPCell(new Paragraph("Anzahl Sitzplätze"));
                cell3.setPaddingLeft(10);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell7 = new PdfPCell(new Paragraph("Betrag"));
                cell3.setPaddingLeft(10);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell8 = new PdfPCell(new Paragraph("Date"));
                cell3.setPaddingLeft(10);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                String id_k = Integer.toString(id_kunde);
                Paragraph pa1 = new Paragraph(id_k);
                Paragraph pa2 = new Paragraph(firstname);
                Paragraph pa3 = new Paragraph(lastname);

                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                table.addCell(cell5);
                table.addCell(cell6);
                table.addCell(cell7);
                table.addCell(cell8);


                for (int i = 0; i < books.size(); i++) {
                    table.addCell(Integer.toString(books.get(i).getId_buchung()));//1.Id_Buchung
                    table.addCell((books.get(i).getStart()));//2.start
                    table.addCell(books.get(i).getEnde());//3.end
                    table.addCell(books.get(i).getEnde());//4.Flugzeug
                    table.addCell(Integer.toString(books.get(i).getAnzahl_eco()));//5.AnzahlEco
                    table.addCell(Double.toString(books.get(i).getAnzahl_eco()));//6.Betrag
                    table.addCell(Double.toString(books.get(i).getBetrag()));//7.Betrag

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//create format
                    String dat = sdf.format(books.get(i).getDate());//convert date of list to format
                    table.addCell(dat);//date

                    String betr = Double.toString(books.get(i).getBetrag());
                    table.addCell(betr);//betrag
                }
                document.add(pa1);
                document.add(pa2);
                document.add(pa3);
                document.add(table);

                document.close();

                writer.close();
                //WindowUtil.alertWindow("Richtig", Alert.AlertType.CONFIRMATION, "Buchungen Report ist fertig!");//Erro
                System.out.println("Report wurde erstellt!!!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}