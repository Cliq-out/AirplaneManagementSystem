package service;

import javafx.scene.control.Alert;
import models.*;
import org.hibernate.query.Query;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.WindowUtil;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FluegeService {
    org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
    List<FluglinieAnlegen> fluglinieListe;
    List<Buchungen> buchungList;
    List<Flughaefen> airportList;
    List<Fluge> flugs;
    List<Postfach>p;

    public String cancelFluge(Fluge fluge){
        final String INACTIVE ="inaktiv";
        String antwort="OK";
        //load objects
        Query<Buchungen> query0 = session.createQuery("FROM Buchungen m where m.idflug =:idflug");
        query0.setParameter("idflug",fluge.getIdflug());
        List<Buchungen> buchungens =query0.list();
        buchungens.forEach(t->t.setStatus(INACTIVE));//toda la lista
        //set Fluge - Fluge -inaktiv
        Query<Fluge> query1 = session.createQuery("FROM Fluge f where f.idflug =:idflug");
        query1.setParameter("idflug",fluge.getIdflug());
        Fluge fluge1=query1.getSingleResult();
        fluge1.setStatus(INACTIVE);

        //Create Stornierungen
        Calendar today= Calendar.getInstance();
        Stornierungen storn = new Stornierungen();
        storn.setIdflug(fluge.getIdflug());
        storn.setDatestorn(today);
        //Brenda Weber- erste Ende


        //Pearl
        // Sending Message to Postfach

        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

        Postfach post_fach = new Postfach();

        final String newline = System.getProperty("line.separator");
        final String msgPostfach = new StringBuilder().append("Dear Customer,").append(newline).append(newline).append("We’re sorry to inform you that your booked flight has just been cancelled.").append(newline).append("We sincerely apologies for any inconveniences caused. Please contact our Customer Service Team on +492740847438 to request your payment refund or to book an alternative available flight on a special discount price. ").append(newline).append(newline).append("Yours Sincerely, ").append(newline).append("SuperAirlines Manager.").toString();
        final String msgSubject = "Flight Cancellation Notification" ;
        Query<Buchungen> query4 = session.createQuery("FROM Buchungen b where b.idflug =:idflug");
        query4.setParameter("idflug",fluge.getIdflug());

        List<Buchungen> allbookings = query4.list();

        if (allbookings !=null && allbookings.size() !=0){//Brenda
        Buchungen cusID = allbookings.get(0);
        int customer = cusID.getId_kunde();
        Query<Postfach> query5 = session.createQuery("FROM Postfach p where p.id_kunde =:idkunde");
        query5.setParameter("idkunde", customer);
        List<Postfach> postfach =query5.list();

        Postfach rec = postfach.get(0);

        post_fach.setId_kunde(rec.getId_kunde());
        post_fach.setId_postfach(rec.getId_postfach());
        post_fach.setUsername(rec.getUsername());
        post_fach.setDateTime(currentTimestamp);
        post_fach.setSubject(msgSubject);
        post_fach.setMessage(msgPostfach);


        //Sending Message to Telegram

        Query<Kunde> query6 = session.createQuery("FROM Kunde k where k.id_kunde =:idkunde");
        query6.setParameter("idkunde", customer);
        List<Kunde> chatID =query6.list();

        Kunde record = chatID.get(0);

        String findChatID = record.getId_chat();
        String myUniqueToken = "1048068166:AAG147JOrbWtVuGORkBz-6UtTIobdlQ7-sQ";
        String message = "Dear Customer," + newline + newline + "We’re sorry to inform you that your booked flight has just been cancelled." + newline + "We sincerely apologies for any inconveniences caused. Please contact our Customer Service Team on +492740847438 to request your payment refund or to book an alternative available flight on a special discount price. " + newline + newline + "Yours Sincerely, " + newline + "SuperAirlines Manager.";
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/{token}/sendMessage")
                .queryParam("chat_id", findChatID)
                .queryParam("text", message);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + myUniqueToken))
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(response.statusCode());
        System.out.println(response.body());
        }

        // Automessage (For my telegram auto message)

        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            botsApi.registerBot(new SuperAirlineBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        //Brenda-if


        //save in db
        session.beginTransaction();
        buchungens.forEach(t->{
            session.update(t);
        });

        session.update(fluge1);
        session.save(storn);
        if (allbookings !=null && allbookings.size() !=0){//Brenda
            session.save(post_fach);
        }

        //Pearl Ende

        session.getTransaction().commit();
        WindowUtil.alertWindow("Richtig", Alert.AlertType.CONFIRMATION, "Der Flug wurde erfolgreich storniert!!!");

        return antwort;
    }
    //Brenda Weber-Beginn
    public List<Integer> getLaenge (Fluge fluge){
        List <Integer> dataLaenge = new ArrayList<Integer>();

        int fluglinieId = fluge.getIdfluglinie();
        //Data aus Flughaefen
        FluglinieAnlegen fluglinieRec = (FluglinieAnlegen)getElementFromList(2,fluge.getIdfluglinie(),"");

        String start1 =fluglinieRec.getStartname();
        String end1 = fluglinieRec.getZielname();

        Flughaefen airport= (Flughaefen)getElementFromList(4,0,start1);
        int langeS= airport.getRunway_length();

        airport= (Flughaefen)getElementFromList(4,0,end1);
        int langeE= airport.getRunway_length();

        dataLaenge.add(0, langeS);
        dataLaenge.add(1,langeE);
        return dataLaenge;
    }
    //Data aus Buchungen
    public List <Integer> getDataBuchungen (Fluge fluge){

        int flugId = fluge.getIdflug();

        List <Integer> dataBuchungen = new ArrayList<Integer>();

        //Buchungen
        List<Buchungen> buchungList1 = (List<Buchungen>)getElementFromList(3,flugId,"");
        int sum = 0;
        int sum1 =0;

        for (Buchungen b : buchungList1){
            sum = sum +b.getAnzahl_eco();//cant pasajes comprados por vuelo eco
            sum1 = sum1 +b.getAnzahl_bus();
        }

       dataBuchungen.add(0, sum);
       dataBuchungen.add(1, sum1);

        return dataBuchungen;
    }

    //Data O. Fluglinie
    public List<Object> getDataFluglinie (Fluge fluge){

        List <Object> dataFluglinie = new ArrayList<Object>();

        int fluglinieId = fluge.getIdfluglinie();

        //Fluglinie
        if(fluglinieListe!=null && fluglinieListe.size()>0) {//OJO revisar
            FluglinieAnlegen fluglinieRec = (FluglinieAnlegen)getElementFromList(2,fluge.getIdfluglinie(),"");

            String startName = fluglinieRec.getStartname();//0
            String zielName = fluglinieRec.getZielname();//1
            int abstand = fluglinieRec.getDistance();//2
            int ecoSeats = fluglinieRec.getEcoseats();//3
            int busSeats = fluglinieRec.getBussseats();//4
            double ecoPreis=fluglinieRec.getEcopreis();//5
            double busPreis=fluglinieRec.getBusspreis();//6

            dataFluglinie.add(0, startName);
            dataFluglinie.add(1, zielName);
            dataFluglinie.add(2, abstand);
            dataFluglinie.add(3,ecoSeats);
            dataFluglinie.add(4,busSeats);
            dataFluglinie.add(5,ecoPreis);
            dataFluglinie.add(6,busPreis);
        }
        return dataFluglinie;
    }

    //Gewinn
    public double gewinnFluege (Fluge fluge) {

       double gewinn =0;

       int abstand = (int) getDataFluglinie(fluge).get(2);//Abstand de FLuglinie
       int anzahlEco = (int)getDataFluglinie(fluge).get(3); //N° Economy Seats
       int anzahlBus = (int) getDataFluglinie(fluge).get(4); //N° Business Seats

       int anzahlSitze=anzahlEco+2*anzahlBus;//N° Total Anzahl Seats der Fluglinie

       int anzahlEcoSeatsBuch= getDataBuchungen(fluge).get(0); //N° Anzahl Sitze  Buchungen
       double preisEcoSeatsBuch= (double) getDataFluglinie(fluge).get(5);// Preis Eco Fluglinie

       int anzahlBusSeatBuch=getDataBuchungen(fluge).get(1); //N° Anzahl Sitze  Buchungen
       double preisBusSeatBuch= (double) getDataFluglinie(fluge).get(6);//Preis  Bus Fluglinie

       int langeStart=getLaenge(fluge).get(0); // de Flughafen segun start
       int langeEnd=getLaenge(fluge).get(1);//   de Flughafen segun endname

       double kerosinkosten= 3.58 * (abstand /100) * anzahlSitze ;

       //SitzeBuchung*PreisKlasse ->Buchungen
       double einnahmen= (anzahlEcoSeatsBuch* preisEcoSeatsBuch)+(anzahlBusSeatBuch*preisBusSeatBuch);

       double flughafenpauschale=0.05*(langeStart+langeEnd);

       gewinn = einnahmen -kerosinkosten-flughafenpauschale;

       return gewinn;
    }
    //Select list
    public  Object getElementFromList(Integer tipo,Integer id, String name){
        Object result=null;
        switch (tipo){
            case 1:{
                for (Fluge fluge: flugs
                     ) {
                    if(fluge.getIdflug()==id){
                        result=fluge;
                        break;
                    }
                }
                break;
            }
            case 2:{
                for (FluglinieAnlegen item:fluglinieListe
                ) {
                    if(item.getId_fluglinie()==id){
                        result=item;
                        break;
                    }
                }
                break;
            }
            case 3:{
                List <Buchungen> r=new ArrayList<>();
                for (Buchungen item:buchungList
                ) {
                    if(item.getIdflug()==id){
                        r.add(item);
                    }
                }
                result=r;
                break;
            }
            case 4:{
                for (Flughaefen item:airportList
                ) {
                    if(item.getName().equals(name)){
                        result=item;
                        break;
                    }
                }
                break;
            }
        }
        return result;
    }

    public  int loadDataFluge(){
        try {
            Query<FluglinieAnlegen> query = session.createQuery("FROM FluglinieAnlegen fl ");
            fluglinieListe = query.list();

            Query<Buchungen> query0 = session.createQuery("FROM Buchungen b");
            buchungList = query0.list();

            Query<Flughaefen> query1 = session.createQuery("FROM Flughaefen a ");
            airportList = query1.list();

            String query2 = "FROM Fluge f";
            Query query02 = session.createQuery(query2);
            flugs = query02.list();
        }catch(Exception e){
            return 0;
        }
        return 1;
    }

    //Im Controller anwenden
    public void calculateWinn(){
        loadDataFluge();
        if(flugs !=null) {
            session.beginTransaction();
            Double winn=0.0;
            for (Fluge f : flugs) {
                winn=gewinnFluege(f);
                System.out.println(winn);//print als Probe
                f.setGewinn(winn);
                session.persist(f);
            }
            session.getTransaction().commit();;
        }

    }

    //Im Controller anwenden
    public void calculateBookings(){
        loadDataFluge();
        if(flugs !=null) {
            session.beginTransaction();
            Integer ecos=0;
            Integer buss=0;
            for (Fluge f : flugs) {
                ecos=getDataBuchungen(f).get(0);
                buss=getDataBuchungen(f).get(1);
                System.out.println(+ecos+" "+buss);//print als Probe
               
                f.setEcosold(ecos);
                f.setBussold(buss);
                session.persist(f);
            }
            session.getTransaction().commit();
        }
    }

    public static void main(String[] args){
        //in controller
        Fluge fluge = new Fluge();
        fluge.setIdflug(4676);
        fluge.setIdfluglinie(4406);
        FluegeService service = new FluegeService();

       //System.out.println(service.gewinnFluege(fluge));

       // service.calculateBookings();
        service.calculateWinn();
       //service.cancelFluge(fluge);
      //System.out.println(service.getLaenge(fluge));//por separado no
       //System.out.println(service.getDataBuchungen(fluge));
       //System.out.println(service.getDataFluglinie(fluge));
        //Brenda Weber-End

        // Pearl (For my telegram auto message)
/*        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {

            botsApi.registerBot(new SuperAirlineBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
    }
}