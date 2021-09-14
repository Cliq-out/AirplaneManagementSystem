package models;

import javax.persistence.*;

@Entity
@Table(name = "flughaefen")


public class Flughaefen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name= "id_flughaefen")
    private int int_flughaefen;

    @Column(name= "code")
    private String code;

    @Column(name= "lat")
    private double lat;
    @Column(name= "lon")
    private double lon;
    @Column(name= "name")
    private String name;
    @Column(name= "city")
    private String city;
    @Column(name= "state")
    private String state;
    @Column(name= "country")
    private String country;
    @Column(name= "woeid")
    private long woeid;
    @Column(name= "tz")
    private String tz;
    @Column(name= "phone")
    private String phone;
    @Column(name= "type")
    private String type;
    @Column(name= "email")
    private String email;
    @Column(name= "url")
    private String url;
    @Column(name= "runway_length")
    private int runway_length;
    @Column(name= "elev")
    private int elev;
    @Column(name= "icao")
    private String icao;
    @Column(name= "direct_flights")
    private int direct_flights;
    @Column(name= "carriers")
    private int carriers;


    public int getInt_flughaefen() {
        return int_flughaefen;
    }

    public void setInt_flughaefen(int int_flughaefen) {
        this.int_flughaefen = int_flughaefen;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getWoeid() {
        return woeid;
    }

    public void setWoeid(long woeid) {
        this.woeid = woeid;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRunway_length() {
        return runway_length;
    }

    public void setRunway_length(int runway_length) {
        this.runway_length = runway_length;
    }

    public int getElev() {
        return elev;
    }

    public void setElev(int elev) {
        this.elev = elev;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public int getDirect_flights() {
        return direct_flights;
    }

    public void setDirect_flights(int direct_flights) {
        this.direct_flights = direct_flights;
    }

    public int getCarriers() {
        return carriers;
    }

    public void setCarriers(int carriers) {
        this.carriers = carriers;
    }


    //overwrite the normal toString()
    @Override
    public String toString() {
        return "code="           +code+
                ", lat="            +lat+
                ", lon="            +lon+
                ", name="           +name+
                ", city="           +city+
                ", state="          +state+
                ", country="        +country+
                ", woeid="          +woeid+
                ", tz="             +tz+
                ", phone="          +phone+
                ", type="           +type+
                ", email="          +email+
                ", url="            +url+
                ", runway_length="  +runway_length+
                ", elev="           +elev+
                ", icao="           +icao+
                ", direct_flights=" +direct_flights+
                ", carriers="       +carriers;
    }


}
