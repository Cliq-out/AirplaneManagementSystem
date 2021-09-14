package models;

import javax.persistence.*;

@Entity
@Table(name = "fluggesmanager")

public class FlugGesManager {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name= "id_manager")
    private int id_manager;
    @Column(name= "vorname")
    private String vorname;
    @Column(name= "nachname")
    private String nachname;
    @Column(name= "geschlecht")
    private String geschlecht;
    @Column(name= "username")
    private String username;
    private String passwort;
    //@Column(name= "hash")
    //private String hash;
    //@Column(name= "salt")
    //private String salt;

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }



    public int getId_manager() {
        return id_manager;
    }

    public void setId_manager(int id_manager) {
        this.id_manager = id_manager;
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

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




}
