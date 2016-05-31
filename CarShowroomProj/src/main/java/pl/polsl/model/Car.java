package pl.polsl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="cars", schema = "salonydb")
public class Car {
    private int id;
    private Dictionary carName;
    private Date prodDate;
    private Integer cost;
    private Integer ordered;
    private Showroom showroom;
    private Contract contract;


    public Car() {}

    public Car(Dictionary carName, Date prodDate, Showroom showroom, Integer cost, Integer ordered, Contract contract) {
        this.carName = carName;
        this.prodDate = prodDate;
        this.showroom = showroom;
        this.ordered = ordered;
        this.cost = cost;
        this.contract = contract;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_name")
    public Dictionary getCarName() {
        return carName;
    }

    public void setCarName(Dictionary carName) {
        this.carName = carName;
    }

    @Basic
    @Column(name = "prod_date")
    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    @Basic
    @Column(name = "cost")
    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "ordered")
    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }


    @JsonIgnore
    @Basic
    @ManyToOne
    @JoinColumn(name = "id_showroom")
    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }

    @JsonIgnore
    @Basic
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contract")
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
