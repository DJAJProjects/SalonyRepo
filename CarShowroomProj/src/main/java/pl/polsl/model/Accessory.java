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
//    private Showroom showroom;

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
