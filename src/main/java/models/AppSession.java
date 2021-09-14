package models;

public class AppSession {

    private static AppSession instance;
    private String username;
    private int id_manager;
    private String gesellshaftname;
    private int id_gesellschaft;
    private int id_flug;
    private int anazahlZuBuchenerEcoSitzplaetze;
    private int anzahlZuBuchenderBusiSitzplaetze;
    private String customerUsername;
    private int id_buchung;
    private Object datei;


    private AppSession(){

    }

    public static AppSession getIntance(){
        if(instance==null){
            instance = new AppSession();
        }

        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId_manager() {
        return id_manager;
    }

    public void setId_manager(int id_manager) {
        this.id_manager = id_manager;
    }

    public String getGesellshaftname() {
        return gesellshaftname;
    }

    public void setGesellshaftname(String gesellshaftname) {
        this.gesellshaftname = gesellshaftname;
    }

    public int getId_gesellschaft() {
        return id_gesellschaft;
    }

    public void setId_gesellschaft(int id_gesellschaft) {
        this.id_gesellschaft = id_gesellschaft;
    }

    public int getId_flug() {
        return id_flug;
    }

    public void setId_flug(int id_flug) {
        this.id_flug = id_flug;
    }

    public void setAnazahlZuBuchenerEcoSitzplaetze(int anazahlZuBuchenerEcoSitzplaetze) {
        this.anazahlZuBuchenerEcoSitzplaetze = anazahlZuBuchenerEcoSitzplaetze;
    }

    public int getAnazahlZuBuchenerEcoSitzplaetze() {
        return anazahlZuBuchenerEcoSitzplaetze;
    }

    public int getAnzahlZuBuchenderBusiSitzplaetze() {
        return anzahlZuBuchenderBusiSitzplaetze;
    }

    public void setAnzahlZuBuchenderBusiSitzplaetze(int anzahlZuBuchenderBusiSitzplaetze) {
        this.anzahlZuBuchenderBusiSitzplaetze = anzahlZuBuchenderBusiSitzplaetze;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public int getId_buchung() {
        return id_buchung;
    }

    public void setId_buchung(int id_buchung) {
        this.id_buchung = id_buchung;
    }

    public Object getDatei() {
        return datei;
    }

    public void setDatei(Object datei) {
        this.datei = datei;
    }
}
