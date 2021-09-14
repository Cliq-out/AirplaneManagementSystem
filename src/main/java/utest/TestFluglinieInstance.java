package utest;


import models.SessionFactoryUtil;
import org.hibernate.query.Query;
import org.junit.Test;
import utils.UtilApp;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestFluglinieInstance {

    @Test
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
        java.sql.Date dat3 = java.sql.Date.valueOf("2020-01-09");


        Date datDaysTest = UtilApp.sumMonthsDate(dat2,1);
        System.out.println("JUnit Test für die Summe von Monaten zu einem Datum");
        assertEquals(dat3,datDaysTest);
    }

    @Test
    public void testSession(){
        String expected="qvqtgufm";
        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
        Query<String> query02 = session.createSQLQuery("select current_database()");
        String result=query02.getSingleResult();
        System.out.println("JUnit Test Überprüfung der Datenbank");
        assertEquals(expected,result);
    }

    @Test
    public void testTestFluglinieAnlegen() {
        Integer expected=1;
        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
        Query<Integer> query02 = session.createQuery("select 1 from FluglinieAnlegen");
        Integer tableNameResult = query02.getSingleResult();
        System.out.println("JUnit Test Überprüft ob die Tabelle vorhanden ist");
        assertEquals(expected,tableNameResult);
    }
}
