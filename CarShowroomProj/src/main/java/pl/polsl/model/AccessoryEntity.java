package pl.polsl.model;

import javax.persistence.*;

/**
 * Created by Kuba on 3/25/2016.
 */
@Entity
@Table(name = "accessories", schema = "salonydb", catalog = "")
public class AccessoryEntity {
    private int id;
    private Integer idContract;
    private String name;
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
    @Column(name = "idContract")
    public Integer getIdContract() {
        return idContract;
    }

    public void setIdContract(Integer idContract) {
        this.idContract = idContract;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
