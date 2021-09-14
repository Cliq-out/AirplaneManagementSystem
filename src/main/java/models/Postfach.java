package models;

import javax.persistence.*;
import java.util.Date;

@Entity
    @Table(name = "postfach")

    public class Postfach {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id_postfach")
        private int id_postfach;
        @Column(name = "id_kunde")
        private int id_kunde;
        @Column(name = "username")
        private String username;
        @Column(name = "message")
        private String message;
        @Column(name = "subject")
        private String subject;
        @Column(name = "dateTime" , columnDefinition= "TIMESTAMP WITH TIME ZONE")
        //private Timestamp dateTime;
        //private OffsetDateTime dateTime;
        private java.util.Date dateTime;


    public Date getDateTime() {
        return dateTime;
    }


    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }


    public int getId_postfach() {
            return id_postfach;
        }

        public void setId_postfach(int id_postfach) {
            this.id_postfach = id_postfach;
        }

        public int getId_kunde() {
            return id_kunde;
        }

        public void setId_kunde(int id_kunde) {
            this.id_kunde = id_kunde;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

   /*     public Timestamp getDateTime() {
        return dateTime;
    }*/

    //public OffsetDateTime getDateTime() {return dateTime;}


}
