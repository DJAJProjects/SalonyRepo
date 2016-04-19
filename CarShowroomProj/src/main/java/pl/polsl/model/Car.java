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
//    private Date prodDate;
    private int cost;
//    private Showroom showroom;
    private Contract contract;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_name")
    public Dictionary getCarName() {
        return carName;
    }

    public void setCarName(Dictionary carName) {
        this.carName = carName;
    }

//    @Basic
//     @Column(name = "prod_date")
//     public Date getProdDate() {
//        return prodDate;
//    }
//
//    public void setProdDate(Date prodDate) {
//        this.prodDate = prodDate;
//    }

    @Basic
    @Column(name = "prod_date")
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

//    @JsonIgnore
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
}
