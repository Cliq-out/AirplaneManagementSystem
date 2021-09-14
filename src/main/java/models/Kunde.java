package models;

import javax.persistence.*;

@Entity
@Table(name = "kunde")

public class Kunde{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_kunde")
    private int id_kunde;
    @Column(name = "vorname")
    private String vorname;
    @Column(name = "nachname")
    private String nachname;
    @Column(name = "adresse")
    private String adresse;
    @Column(name = "postfach")
    private String postfach;
    @Column(name = "id_chat")
    private String id_chat;
    @Column(name = "telefonnummer")
    private String telefonnummer;
    @Column(name = "username")
    private String username;
    private String passwort; //
    @Column(name = "message")
    private String message;
    @Column(name = "subject")
    private String subject;

    public int getId_kunde() {
        return id_kunde;
    }

    public void setId_kunde(int id_kunde) {
        this.id_kunde = id_kunde;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPostfach() {
        return postfach;
    }

    public void setPostfach(String postfach) {
        this.postfach = postfach;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
    public String getId_chat() {
        return id_chat;
    }

    public void setId_chat(String id_chat) {
        this.id_chat = id_chat;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}