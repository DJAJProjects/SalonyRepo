package pl.polsl.model;

import javax.persistence.*;

/**
 * Created by Dominika Błasiak on 04.04.2016.
 */
@Entity
@Table(name="showrooms", schema = "salonydb")
public class Showroom {
    private int id;
    private String name;
    private String street;
    private Dictionary city;
    private Dictionary country;
    private Worker director;

    public Showroom(){}
    public Showroom(String name, String street, Dictionary city, Dictionary country, Worker director) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.country = country;
        this.director = director;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne()
    @JoinColumn(name = "id_city",nullable = false)
    public Dictionary getCity() {
        return city;
    }

    public void setCity(Dictionary city) {
        this.city = city;
    }

    @Basic
    @Column(name = "street", nullable = false)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @ManyToOne
    @JoinColumn(name = "id_country", nullable = false)
    public Dictionary getCountry() {
        return country;
    }

    public void setCountry(Dictionary country) {
        this.country = country;
    }

    @ManyToOne
    @JoinColumn(name = "id_director", nullable = false)
    public Worker getDirector() {
        return director;
    }

    public void setDirector(Worker director) {
        this.director = director;
    }
}
