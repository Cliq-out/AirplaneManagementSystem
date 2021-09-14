package models;

import javax.persistence.*;

@Entity
@Table(name ="fluggesellschaft_anlegen")

public class Fluggesellschaft {

    public Fluggesellschaft(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name= "id_fluggesellschaft")
    private int id_fluggesellschaft;
    @Column(name= "name")
    private String name;
    @Column(name= "land")
    private String land;
    @Column(name= "budget")
    private double budget;
    @Column(name= "id_manager")
    private int id_manager;

    //creating connection with fluggesmanager

    public int getId_manager() {
        return id_manager;
    }

    public void setId_manager(int id_manager) {
        this.id_manager = id_manager;
    }

    public int getId_fluggesellschaft() {
        return id_fluggesellschaft;
    }

    public void setId_fluggesellschaft(int id_fluggesellschaft) {
        this.id_fluggesellschaft = id_fluggesellschaft;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }



}
