package models;

import javax.persistence.Column;
import java.util.Date;

public class FlugeView {

    private int idfluge;

    private Date startdate;

    private int id_fluglinie;

    private String startname;

    private String zielname;

    private String model;

    private double ecopreis;

    private double busspreis;

    private int ecoseat;

    private int busseat;

    private double gewinn;

    private int ecosold;

    private int bussold;


    public int getIdfluge() {
        return idfluge;
    }

    public void setIdfluge(int idfluge) {
        this.idfluge = idfluge;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public int getId_fluglinie() {
        return id_fluglinie;
    }

    public void setId_fluglinie(int id_fluglinie) {
        this.id_fluglinie = id_fluglinie;
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

    public int getEcoseat() {
        return ecoseat;
    }

    public void setEcoseat(int ecoseat) {
        this.ecoseat = ecoseat;
    }

    public int getBusseat() {
        return busseat;
    }

    public void setBusseat(int busseat) {
        this.busseat = busseat;
    }

    public double getGewinn() {
        return gewinn;
    }

    public void setGewinn(double gewinn) {
        this.gewinn = gewinn;
    }

    public int getEcosold() {
        return ecosold;
    }

    public void setEcosold(int ecosold) {
        this.ecosold = ecosold;
    }

    public int getBussold() {
        return bussold;
    }

    public void setBussold(int bussold) {
        this.bussold = bussold;
    }


    //von fabian, für multistop suche/buchung
    private String multiStopId;

    private int stopCount;

    public String getMultiStopId() {
        return multiStopId;
    }

    public void setMultiStopId(String multiStopId) {
        this.multiStopId = multiStopId;
    }

    public int getStopCount() {
        return stopCount;
    }

    public void setStopCount(int stopCount) {
        this.stopCount = stopCount;
    }
}
