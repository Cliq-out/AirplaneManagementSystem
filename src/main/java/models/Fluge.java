package models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;


//Brenda s Tabelle
@Entity
    @Table(name ="flugebrenda")

    public class
Fluge {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idflug")
    private int idflug;

    @Column(name = "idfluglinie")
    private int idfluglinie;

    @Column(name = "abfahrtdate")
    private Date abfahrtdate;

    @Column(name = "ecoseatact")
    private int ecoseatact;

    @Column(name = "busseatact")
    private int busseatact;

    @Column(name = "operatedate")
    private Calendar instancedate;

    @Column(name = "status")
    private String status;

    @Column(name = "reservierungen")
    private boolean[] reservierungen;

    @Column(name = "gewinn")
    private Double gewinn;

    @Column(name = "ecosold")
    private Integer ecosold;

    @Column(name = "bussold")
    private Integer bussold;



    public int getIdflug() {
        return idflug;
    }

    public void setIdflug(int idflug) {
        this.idflug = idflug;
    }

    public int getIdfluglinie() {
        return idfluglinie;
    }

    public void setIdfluglinie(int idfluglinie) {
        this.idfluglinie = idfluglinie;
    }

    public Date getAbfahrtdate() {
        return abfahrtdate;
    }

    public void setAbfahrtdate(Date abfahrtdate) {
        this.abfahrtdate = abfahrtdate;
    }

    public int getEcoseatact() {
        return ecoseatact;
    }

    public void setEcoseatact(int ecoseatact) {
        this.ecoseatact = ecoseatact;
    }

    public int getBusseatact() {
        return busseatact;
    }

    public void setBusseatact(int busseatact) {
        this.busseatact = busseatact;
    }

    public Calendar getInstancedate() {
        return instancedate;
    }

    public void setInstancedate(Calendar instancedate) {
        this.instancedate = instancedate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean[] getReservierungen() {
        return reservierungen;
    }

    public void setReservierungen(boolean[] reservierungen) {
        this.reservierungen = reservierungen;
    }

    public Double getGewinn() {
        return gewinn;
    }

    public void setGewinn(Double gewinn) {
        this.gewinn = gewinn;
    }

    public Integer getEcosold() {
        return ecosold;
    }

    public void setEcosold(Integer ecosold) {
        this.ecosold = ecosold;
    }

    public Integer getBussold() {
        return bussold;
    }

    public void setBussold(Integer bussold) {
        this.bussold = bussold;
    }
}
