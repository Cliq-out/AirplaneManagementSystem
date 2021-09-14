package models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fluggesmanager")

public class PearllyTest {
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name= "DOB")
    private int dob;
    @Column(name= "first_name")
    private String FirstName;
    @Column(name= "last_name")
    private String lastName;

    public int getDob() {
        return dob;
    }

    public void setDob(int dob) {
        this.dob = dob;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
