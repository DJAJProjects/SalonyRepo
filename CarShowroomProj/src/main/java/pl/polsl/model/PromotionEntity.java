package pl.polsl.model;

import javax.persistence.*;

/**
 * Created by Kuba on 3/25/2016.
 */
@Entity
@Table(name = "promotions", schema = "salonydb", catalog = "")
public class PromotionEntity {
    private int id;
    private int percValue;
    private String name;
    private Integer idAccessory;
    private Integer idCar;
    private Integer idContract;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "percValue")
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
    @Column(name = "idAccessory")
    public Integer getIdAccessory() {
        return idAccessory;
    }

    public void setIdAccessory(Integer idAccessory) {
        this.idAccessory = idAccessory;
    }

    @Basic
    @Column(name = "idCar")
    public Integer getIdCar() {
        return idCar;
    }

    public void setIdCar(Integer idCar) {
        this.idCar = idCar;
    }

    @Basic
    @Column(name = "idContract")
    public Integer getIdContract() {
        return idContract;
    }

    public void setIdContract(Integer idContract) {
        this.idContract = idContract;
    }


}
