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
    private Dictionary idName;
    private Date prodDate;
    private Showroom showroom;
    private Contract contract;
    private Integer cost;

    public Car() {}

    public Car(Dictionary idName, Date prodDate, Showroom showroom, Integer cost) {
        this.idName = idName;
        this.prodDate = prodDate;
        this.showroom = showroom;
        this.cost = cost;
    }

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
    @JoinColumn(name = "id_name")
    public Dictionary getIdName() {
        return idName;
    }

    public void setIdName(Dictionary idName) {
        this.idName = idName;
    }

    @Basic
    @Column(name = "prod_date")
    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
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
    @ManyToOne
    @JoinColumn(name = "id_contract")
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Basic
    @Column(name = "cost")
    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
