package pl.polsl.model;

import javax.persistence.*;
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
    private Integer idAccessory;
    private Integer idCar;
//    private Set<Contract> contractList;

    @Id
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
    @Column(name = "id_accessory")
    public Integer getIdAccessory() {
        return idAccessory;
    }

    public void setIdAccessory(Integer idAccessory) {
        this.idAccessory = idAccessory;
    }

    @Basic
    @Column(name = "id_car")
    public Integer getIdCar() {
        return idCar;
    }

    public void setIdCar(Integer idCar) {
        this.idCar = idCar;
    }

//    @Basic
//    @OneToMany (mappedBy="promotion")
//    public Set<Contract> getContractList() {
//        return contractList;
//    }
//    public void setContractList(Set<Contract> contractList) {
//        this.contractList = contractList;
//    }


}
