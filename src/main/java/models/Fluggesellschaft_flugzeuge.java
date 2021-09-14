package models;

import javax.persistence.*;

@Entity
@Table (name = "Fluggesellschaft_flugzeuge")
public class Fluggesellschaft_flugzeuge {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name= "id_gesflug")
    private int id_gesflug;

    @Column(name= "fluggesellschaft_id_fluggesellschaft")
    private int fluggesellschaft_id_fluggesellschaft;

    @Column(name= "id_flugzeug")
    private int flugzeuge_id_flugzeug;
    @Column(name= "menge")
    private int menge;
    @Column(name= "betrag")
    private double  betrag;
    @Column(name= "flugzeugtyp")
    private String flugzeugtyp;


    public int getId_gesflug() {
        return id_gesflug;
    }

    public void setId_gesflug(int id_gesflug) {
        this.id_gesflug = id_gesflug;
    }

    public int getFluggesellschaft_id_fluggesellschaft() {
        return fluggesellschaft_id_fluggesellschaft;
    }

    public void setFluggesellschaft_id_fluggesellschaft(int fluggesellschaft_id_fluggesellschaft) {
        this.fluggesellschaft_id_fluggesellschaft = fluggesellschaft_id_fluggesellschaft;
    }

    public int getFlugzeuge_id_flugzeug() {
        return flugzeuge_id_flugzeug;
    }

    public void setFlugzeuge_id_flugzeug(int flugzeuge_id_flugzeug) {
        this.flugzeuge_id_flugzeug = flugzeuge_id_flugzeug;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public String getFlugzeugtyp() {
        return flugzeugtyp;
    }

    public void setFlugzeugtyp(String flugzeugtyp) {
        this.flugzeugtyp = flugzeugtyp;
    }


    public static void main (String[] args){
        Fluggesellschaft_flugzeuge flugges_flugz = new Fluggesellschaft_flugzeuge();
        System.out.println(flugges_flugz);
    }

}
