package pl.polsl.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Model class for showroom
 * @author Dominika Błasiak
 * @version 1.0
 */
@Entity
@Table(name="showrooms", schema = "salonydb")
public class Showroom {
    /**
     * Showroom id
     */
    private int id;
    /**
     * Showroom name
     */
    private String name;
    /**
     * Showroom street
     */
    private String street;
    /**
     * Showroom city
     */
    private Dictionary city;
    /**
     * Showroom country
     */
    private Dictionary country;
    /**
     * Showroom director
     */
    private Worker director;
    /**
     * Set of workers which is related to showroom
     */
    private Set<Worker> workers;
    /**
     * Set of cars which is related to showroom
     */
    private Set<Car> cars;
    /**
     * Set of reports which is related to showroom
     */
    private Set<Report> reports;

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

    @ManyToOne
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

    @OneToOne
    @JoinColumn(name = "id_director", nullable = false)
    public Worker getDirector() {
        return director;
    }

    public void setDirector(Worker director) {
        this.director = director;
    }

    @OneToMany(mappedBy="showroom")
    public Set<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<Worker> workers) {
        this.workers = workers;
    }

    @OneToMany(mappedBy = "showroom")
    public Set<Car> getCars() { return cars; }

    public void setCars(Set<Car> cars) { this.cars = cars;  }

    @OneToMany(mappedBy = "showroom")
    public Set<Report> getReports() { return reports; }

    public void setReports(Set<Report> reports) { this.reports = reports; }
}
