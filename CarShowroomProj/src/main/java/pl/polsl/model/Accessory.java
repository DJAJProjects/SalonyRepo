package pl.polsl.model;

import javax.persistence.*;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="accessories", schema = "salonydb")
public class Accessory {
    private int id;
    private Integer idContract;
    private Integer idName;
    private Integer cost;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_contract")
    public Integer getIdContract() {
        return idContract;
    }

    public void setIdContract(Integer idContract) {
        this.idContract = idContract;
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

}
