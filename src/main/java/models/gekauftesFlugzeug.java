package models;

import javax.persistence.*;

@Entity
@Table (name = "fabian_flugzeugeKaufen")

public class gekauftesFlugzeug {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name= "id_flugzeug")
    private int id_flugzeug;
    @Column(name= "flugzeugtyp")
    private String flugzeugtyp;
    @Column (name = "id_fluggesellschaft")
    private int id_fluggesellschaft;

    public int getId_flugzeug() {
        return id_flugzeug;
    }

    public void setId_flugzeug(int id_flugzeug) {
        this.id_flugzeug = id_flugzeug;
    }

    public String getFlugzeugtyp() {
        return flugzeugtyp;
    }

    public void setFlugzeugtyp(String flugzeugtyp) {
        this.flugzeugtyp = flugzeugtyp;
    }

    public int getId_fluggesellschaft() {
        return id_fluggesellschaft;
    }

    public void setId_fluggesellschaft(int id_fluggesellschaft) {
        this.id_fluggesellschaft = id_fluggesellschaft;
    }
}
