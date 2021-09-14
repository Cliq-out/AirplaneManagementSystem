package models;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name ="fluglinie")

public class FluglinieAnlegen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_fluglinie")
    private int id_fluglinie;

    //@Column(name="startflughafen")
    //private Flughaefen startflughafen;

    @Column(name="startname")
    private String startname;

   // @Column(name = "zielflughafen")
    //private Flughaefen zielflughafen;

    @Column(name="zielname")
    private String zielname;

    @Column(name = "startdatum")
    private Date startdatum;

    @Column(name = "enddatum")
    private Date enddatum;

    @Column(name = "interval")
    private String interval;

    @Column (name = "model")
    private String model;


    @Column(name = "ecoseats")
    private int ecoseats;

    @Column(name = "bussseats")
    private int bussseats;

    @Column(name = "ecopreis")
    private double ecopreis;


    @Column(name = "busspreis")
    private double busspreis;

    @Column(name = "distance")
    private int distance;


    @Column(name = "fluggesellschaft")
    private int fluggesellschaft;

    @Column(name = "instance")
    private String instance;

    public FluglinieAnlegen() {
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getStartname() {
        return startname;
    }

    public void setStartname(String startname) {
        this.startname = startname;
    }

    public String getZielname() {
        return zielname;
    }

    public void setZielname(String zielname) {
        this.zielname = zielname;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    public int getId_fluglinie() {
        return id_fluglinie;
    }

    public void setId_fluglinie(int id_fluglinie) {
        this.id_fluglinie = id_fluglinie;
    }



    public Date getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(Date startdatum) {
        this.startdatum = startdatum;
    }

    public Date getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(Date enddatum) {
        this.enddatum = enddatum;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public int getEcoseats() {
        return ecoseats;
    }

    public void setEcoseats(int ecoseats) {
        this.ecoseats = ecoseats;
    }

    public int getBussseats() {
        return bussseats;
    }

    public void setBussseats(int bussseats) {
        this.bussseats = bussseats;
    }

    public double getEcopreis() {
        return ecopreis;
    }

    public void setEcopreis(double ecopreis) {
        this.ecopreis = ecopreis;
    }

    public double getBusspreis() {
        return busspreis;
    }

    public void setBusspreis(double busspreis) {
        this.busspreis = busspreis;
    }

    public int getFluggesellschaft() {
        return fluggesellschaft;
    }

    public void setFluggesellschaft(int fluggesellschaft) {
        this.fluggesellschaft = fluggesellschaft;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    @Override
    public String toString() {
        return "Id_fluglinie="           +id_fluglinie+
                ", Startflughafen="            +startname+
                ", Zielflughafen="            +zielname+
                ", Startdatum="           +startdatum+
                ", Interval="           +interval+
                ", Flugzeug="           +model+
                ", Ecoseats="           +ecoseats+
                ", Bussseats="           +bussseats+
                ", Ecopreis="           +ecopreis+
                ", Buspreis="           +busspreis+
                ", Fluggesellschaft="          +fluggesellschaft;

    }


    public static void main (String[] args){
        FluglinieAnlegen fluglinieAnlegen = new FluglinieAnlegen();
        System.out.println(fluglinieAnlegen);
    }

}