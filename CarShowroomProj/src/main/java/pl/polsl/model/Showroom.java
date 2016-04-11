package pl.polsl.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Dominika Błasiak on 04.04.2016.
 */
@Entity
@Table(name="showrooms", schema = "salonydb")
public class Showroom {
    private int id;
    private Dictionary city;
    private String street;
    private Dictionary country;
    private Set<Accessory> listOfAccesories;
//    private Worker director;
//    private Set<Worker> listOfWorkers;
    private Set<Car> listOfCars;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @ManyToOne
    @JoinColumn(name = "id_city")
    public Dictionary getCity() {
        return city;
    }

    public void setCity(Dictionary city) {
        this.city = city;
    }

    @Basic
    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @ManyToOne
    @JoinColumn(name = "id_country")
    public Dictionary getCountry() {
        return country;
    }

    public void setCountry(Dictionary country) {
        this.country = country;
    }

//    public Worker getDirector() {
//        return director;
//    }
//
//    public void setDirector(Worker director) {
//        this.director = director;
//    }
//
//    public Set<Worker> getListOfWorkers() {
//        return listOfWorkers;
//    }
//
//    public void setListOfWorkers(Set<Worker> listOfWorkers) {
//        this.listOfWorkers = listOfWorkers;
//    }

    @OneToMany (mappedBy="showroom")
    public Set<Car> getListOfCars() {
        return listOfCars;
    }

    public void setListOfCars(Set<Car> listOfCars) {
        this.listOfCars = listOfCars;
    }

    @OneToMany (mappedBy="showroom")
    public Set<Accessory> getListOfAccesories() {
        return listOfAccesories;
    }

    public void setListOfAccesories(Set<Accessory> listOfAccesories) {
        this.listOfAccesories = listOfAccesories;
    }
}
