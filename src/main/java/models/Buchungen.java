package models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="buchungen")

public class Buchungen {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id_buchung")
    private int id_buchung;


    @Column(name="idflug")
    private int idflug;


    @Column(name="start")
    private String start;

    @Column(name="ende")
    private String ende;

    @Column(name="id_fluglinie")
    private int id_fluglinie;

    @Column(name="flugzeugtyp")
    private String flugzeugtyp;

   // @ManyToOne
    @Column(name="id_kunde")
    private int id_kunde;

    @Column(name="date")//fecha abfahrt
    private Date date;

    @Column(name="art")
    private String art;

    @Column(name="betrag")
    private double betrag;

    @Column(name="anzahl_eco")
    private int anzahl_eco;

    @Column(name="anzahl_bus")
    private int anzahl_bus;

    @Column(name = "date_buchung")
    private Date date_buchung;

    @Column(name="status")
    private String status;

    @Column(name = "hatsitzereserviert")
    private boolean hatsitzereserviert;

    public boolean getHatsitzereserviert() {
        return hatsitzereserviert;
    }

    public void setHatsitzereserviert(boolean hatsitzereserviert) {
        this.hatsitzereserviert = hatsitzereserviert;
    }

    public int getId_buchung() {
        return id_buchung;
    }

    public void setId_buchung(int id_buchung) {
        this.id_buchung = id_buchung;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnde() {
        return ende;
    }

    public void setEnde(String ende) {
        this.ende = ende;
    }

    public int getId_fluglinie() {
        return id_fluglinie;
    }

    public void setId_fluglinie(int id_fluglinie) {
        this.id_fluglinie = id_fluglinie;
    }

    public String getFlugzeugtyp() {
        return flugzeugtyp;
    }

    public void setFlugzeugtyp(String flugzeugtyp) {
        this.flugzeugtyp = flugzeugtyp;
    }

    public int getId_kunde() {
        return id_kunde;
    }

    public void setId_kunde(int id_kunde) {
        this.id_kunde = id_kunde;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public int getAnzahl_eco() {
        return anzahl_eco;
    }

    public void setAnzahl_eco(int anzahl_eco) {
        this.anzahl_eco = anzahl_eco;
    }

    public int getAnzahl_bus() {
        return anzahl_bus;
    }

    public void setAnzahl_bus(int anzahl_bus) {
        this.anzahl_bus = anzahl_bus;
    }

    public Date getDate_buchung() {
        return date_buchung;
    }

    public void setDate_buchung(Date date_buchung) {
        this.date_buchung = date_buchung;
    }

    public int getIdflug() {
        return idflug;
    }

    public void setIdflug(int idflug) {
        this.idflug = idflug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isHatSitzeReserviert() {
        return hatsitzereserviert;
    }

    public void setHatSitzeReserviert(boolean hatSitzeReserviert) {
        this.hatsitzereserviert = hatSitzeReserviert;
    }

}