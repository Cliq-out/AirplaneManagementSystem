package main;

import models.Flughaefen;
import models.Flugzeuge;
import models.SessionFactoryUtil;
import org.hibernate.query.Query;

public class FabianQueryTestReichweite {

        public static void main(String[] args) {
            org.hibernate.Session session = SessionFactoryUtil.getInstance().getSessionFactory().openSession();
            session.beginTransaction();
            String target = "A319"; //expected 6900
            Query<Flugzeuge> query = session.createQuery("FROM Flugzeuge fz WHERE fz.flugzeugtyp = '" + target + "'");
            Flugzeuge fz = query.list().get(0);
            System.out.println(fz.getReichweite());
            target = "480"; //expected: AAT
            Query<Flughaefen> query1 = session.createQuery("FROM Flughaefen fh WHERE fh.int_flughaefen = " + target);
            Flughaefen fh = query1.list().get(0);
            System.out.println(fh.getCode());
            target = "475"; //expected: AAL
            Query<Flughaefen> query2 = session.createQuery("FROM Flughaefen fh1 WHERE fh1.int_flughaefen = " + target);
            Flughaefen fh1 = query2.list().get(0);
            System.out.println(fh1.getCode());
            session.close();
        }
}
