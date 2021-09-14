package models;


public class BuchungView {

    private int idkunde;

    private String vorname;

    private String nachname;

    private int buchungnr;

    private int ecoseats;

    private int busseats;

    private double betrag;

    public int getIdkunde() {
        return idkunde;
    }

    public void setIdkunde(int idkunde) {
        this.idkunde = idkunde;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public int getBuchungnr() {
        return buchungnr;
    }

    public void setBuchungnr(int buchungnr) {
        this.buchungnr = buchungnr;
    }

    public int getEcoseats() {
        return ecoseats;
    }

    public void setEcoseats(int ecoseats) {
        this.ecoseats = ecoseats;
    }

    public int getBusseats() {
        return busseats;
    }

    public void setBusseats(int busseats) {
        this.busseats = busseats;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }
}
