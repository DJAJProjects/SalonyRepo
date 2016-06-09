package pl.polsl.model;

import javax.persistence.*;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="accessories", schema = "salonydb")
public class Accessory {
    private int id;
    private Contract contract;
    private Dictionary accessory;
    private Integer cost;
    private Car car;
    private Integer assemblyCost;
//    private Showroom showroom;

    public Accessory() {
    }

    public Accessory(Dictionary accessory, Integer cost) {
        this.accessory = accessory;
        this.cost = cost;
    }

    public Accessory(int id, Dictionary accessory, Integer cost) {
        this.id = id;
        this.accessory = accessory;
        this.cost = cost;
    }

    public Accessory(Dictionary accessory, Integer cost, Integer assemblyCost) {
        this.accessory = accessory;
        this.cost = cost;
        this.assemblyCost = assemblyCost;
    }

    public Accessory(int id, Contract contract, Dictionary accessory, Integer cost) {
        this.id = id;
        this.contract = contract;
        this.accessory = accessory;
        this.cost = cost;
    }

    public Accessory(int id, Contract contract, Dictionary accessory, Integer cost, Integer assemblyCost) {
        this.id = id;
        this.contract = contract;
        this.accessory = accessory;
        this.cost = cost;
        this.assemblyCost = assemblyCost;
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

    @Basic
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contract")
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @ManyToOne()
    @JoinColumn(name = "id_name")
    public Dictionary getAccessory() {
        return accessory;
    }

    public void setAccessory(Dictionary carName) {
        this.accessory = carName;
    }
    @Basic
    @Column(name = "cost")
    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @ManyToOne()
    @JoinColumn(name = "id_car")
    public Car getCar() {
        return this.car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Basic
    @Column(name = "assembly_cost")
    public Integer getAssemblyCost() {
        return assemblyCost;
    }

    public void setAssemblyCost(Integer assemblyCost) {
        this.assemblyCost = assemblyCost;
    }

//    @Basic
//    @ManyToOne
//    @JoinColumn(name = "id_showroom")
//    public Showroom getShowroom() {
//        return showroom;
//    }
//
//    public void setShowroom(Showroom showroom) {
//        this.showroom = showroom;
//    }
}
