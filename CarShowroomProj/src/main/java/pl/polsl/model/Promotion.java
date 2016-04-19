package pl.polsl.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="promotions", schema = "salonydb")
public class Promotion {
    private int id;
    private int percValue;
    private String name;
    private Date dateStart;
    private Date dateEnd;
//    private Integer idAccessory;
    private Set<Contract>contracts;


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
    @Column(name = "perc_value")
    public int getPercValue() {
        return percValue;
    }

    public void setPercValue(int percValue) {
        this.percValue = percValue;
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
    @Column(name = "date_start")
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date start) {
        this.dateStart = start;
    }

    @Basic
    @Column(name = "date_end")
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date end) {
        this.dateEnd = end;
    }


//    @Basic
//    @Column(name = "id_accessory")
//    public Integer getIdAccessory() {
//        return idAccessory;
//    }
//
//    public void setIdAccessory(Integer idAccessory) {
//        this.idAccessory = idAccessory;
//    }

    @Basic
    @ManyToMany(mappedBy = "promotions")
    public Set<Contract> getContracts() {
        return this.contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

}
