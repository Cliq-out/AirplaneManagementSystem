package models;

import javax.persistence.*;

@Entity
@Table(name = "flugzeuge")


public class Flugzeuge {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name= "id_flugzeuge")
    private int id_flugzeuge;

    @Column(name= "hersteller")
    private String hersteller;

    @Column(name= "flugzeugtyp")
    private String flugzeugtyp;
    @Column(name= "anzahlsitzplaetze")
    private int anzahl_Sitzplaetze;
    @Column(name= "geschwindigkeit")
    private int  geschwindigkeit;
    @Column(name= "preisinmio")
    private double  preis_in_Mio;
    @Column(name= "reichweite")
    private int reichweite;

    public Flugzeuge(String hersteller, String flugzeugtyp, int anzahl_sitzplaetze, int geschwindigkeit, int preis_in_mio, int reichweite) {
    }

    public Flugzeuge() {
    }

    public String getFlugzeugtyp() {
        return flugzeugtyp;
    }

    public void setFlugzeugtyp(String flugzeugtyp) {
        this.flugzeugtyp = flugzeugtyp;
    }


    public String getHersteller() {
        return hersteller;
    }

    public void setHersteller(String hersteller) {
        this.hersteller = hersteller;
    }

    public int getAnzahl_Sitzplaetze() {
        return anzahl_Sitzplaetze;
    }

    public void setAnzahl_Sitzplaetze(int anzahl_Sitzplaetze) {
        this.anzahl_Sitzplaetze = anzahl_Sitzplaetze;
    }

    public int getGeschwindigkeit() {
        return geschwindigkeit;
    }

    public void setGeschwindigkeit(int geschwindigkeit) {
        this.geschwindigkeit = geschwindigkeit;
    }

    public double getPreis_in_Mio() {
        return preis_in_Mio;
    }

    public void setPreis_in_Mio(double preis_in_Mio) {
        this.preis_in_Mio = preis_in_Mio;
    }

    public int getReichweite() {
        return reichweite;
    }

    public void setReichweite(int reichweite) {
        this.reichweite = reichweite;
    }

    @Override
    public String toString() {
        return "Hersteller="           +hersteller+
                ", Flugzeugtyp="            +flugzeugtyp+
                ", Anzahl_Sitzplaetze="            +anzahl_Sitzplaetze+
                ", Geschwindigkeit="           +geschwindigkeit+
                ", Preis_in_Mio="           +preis_in_Mio+
                ", Reichweite="          +reichweite;

    }

 }