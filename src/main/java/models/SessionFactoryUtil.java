package models;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

public class SessionFactoryUtil {
    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;
    private Session session;
    private static SessionFactoryUtil sessionFactoryUtil;

    private SessionFactoryUtil(){

    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session getSession(){
        if(session == null){
            session = sessionFactory.openSession();//first time
        }else if(!session.isOpen()){
            session = sessionFactory.openSession();// if is closed, open sesion
        }
        return session;
    }

    public void closeSession(){
        session.close();
        //sessionFactory.close();
        //sessionFactoryUtil=null;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static SessionFactoryUtil getInstance(){
        if(sessionFactoryUtil==null){
            sessionFactoryUtil = new SessionFactoryUtil();
            try {
                sessionFactoryUtil.setUp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactoryUtil;
    }


    private void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
            e.printStackTrace();
        }
    }
}
