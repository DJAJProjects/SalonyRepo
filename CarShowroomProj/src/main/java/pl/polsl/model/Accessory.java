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
    private Integer idName;
    private Integer cost;
    private Showroom showroom;

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
    @JoinColumn(name = "id_contract")
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Basic
    @Column(name = "id_name")
    public Integer getIdName() {
        return idName;
    }

    public void setIdName(Integer idName) {
        this.idName = idName;
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
    @ManyToOne
    @JoinColumn(name = "id_showroom")
    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }
}
