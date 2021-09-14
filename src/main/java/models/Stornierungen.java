package models;

import javax.persistence.*;
import java.util.Calendar;


//Brenda s Tabelle
@Entity
    @Table(name ="stornierungen")

    public class Stornierungen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idstornierung")
    private int idstornierung;

    @Column(name = "idflug")
    private int idflug;

    @Column(name = "datestorn")
    private Calendar datestorn;

    public int getIdstornierung() {
        return idstornierung;
    }

    public void setIdstornierung(int idstornierung) {
        this.idstornierung = idstornierung;
    }

    public int getIdflug() {
        return idflug;
    }

    public void setIdflug(int idflug) {
        this.idflug = idflug;
    }

    public Calendar getDatestorn() {
        return datestorn;
    }

    public void setDatestorn(Calendar datestorn) {
        this.datestorn = datestorn;
    }
}
