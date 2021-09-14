package utils;

import controller.FluglinieInstanceController;
import models.Fluge;
import models.FluglinieAnlegen;
import models.SessionFactoryUtil;
import org.hibernate.query.Query;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestUtil {

    @Test
   // @DisplayName("Summe of Day to a date")
    public void summeDateDays() {

        java.sql.Date dat = java.sql.Date.valueOf("2019-12-08");
        java.sql.Date dat1 = java.sql.Date.valueOf("2019-12-11");


       Date datDaysTest = UtilApp.sumDaysDate(dat,3);
       System.out.println("JUnit Test für die Summe von Tagen zu einem Datum");
       assertEquals(dat1,datDaysTest);
    }
    @Test
    public void summeDateMonth() {

        java.sql.Date dat2 = java.sql.Date.valueOf("2019-12-09");
        java.sql.Date dat3 = java.sql.Date.valueOf("2020-01-08");


        Date datDaysTest = UtilApp.sumMonthsDate(dat2,1);
        System.out.println("JUnit Test für die Summe von Monaten zu einem Datum");
        assertEquals(dat3,datDaysTest);
    }

   @Test
    public void routeGenerator() {

       FluglinieAnlegen fluglinieProbe;
       Fluge flugeProbe;
       List<Fluge > probeFlugeList= new ArrayList<>();
       int idRut =4458;//hier ein neues id_fluglinie für den Test erstellen
       org.hibernate.Session session1 = SessionFactoryUtil.getInstance().getSession();

       //Erstelle ein FluglinieAnlegen
       Query<FluglinieAnlegen> query0 = session1.createQuery("FROM FluglinieAnlegen fl WHERE fl.id_fluglinie = :idR");
       query0.setParameter("idR",idRut);

       List<FluglinieAnlegen> fluglinieList = query0.list();
       fluglinieProbe =fluglinieList.get(0);

       FluglinieInstanceController obj1 = new FluglinieInstanceController();
       obj1.generateFluge(fluglinieProbe,probeFlugeList);
       obj1.saveFluge(probeFlugeList);

       Query<Fluge> query1 = session1.createQuery("FROM Fluge f WHERE f.idfluglinie =: idR");
       query1.setParameter("idR",idRut);
       List<Fluge>flugeList =query1.list();
       //Was in Fluegen gespeichert wurde
       flugeProbe = flugeList.get(0);
       int idRutToCompare =flugeProbe.getIdfluglinie();
       assertEquals(idRut,idRutToCompare);
    }
}




