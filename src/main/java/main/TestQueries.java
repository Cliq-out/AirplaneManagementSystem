package main;

import java.sql.Date;
import java.text.SimpleDateFormat;

import models.Buchungen;
import models.FluglinieAnlegen;
import models.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class TestQueries {
    public static void main(String[] args) throws Exception {

        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();

        int idflugli =4392;
        List <Integer> dataBuchungen = new ArrayList<Integer>();

        Query<Buchungen> query0 = session.createQuery("FROM Buchungen b where b.id_fluglinie =: suche");
        query0.setParameter("suche", idflugli);
        List<Buchungen> buchungenList = query0.list();

        int sum = 0;

        for (Buchungen b : buchungenList){
            sum = sum +b.getAnzahl_eco();
        }

        System.out.println(sum);

    }
}
