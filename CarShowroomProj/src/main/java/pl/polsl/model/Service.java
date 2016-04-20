package pl.polsl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by Kuba on 20.04.2016.
 */
@Entity
@Table(name="services", schema = "salonydb")
public class Service {
    private int id;
    private Accessory accessory;
    private Dictionary type;
    private Worker serviceman;
    private Car car;
    private Date dateConducted;
    private DecimalFormat cost;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "dateConducted")
    public Date getDateConducted() {
        return dateConducted;
    }

    public void setDateConducted(Date dateConducted) {
        this.dateConducted = dateConducted;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_accessory")
    public Accessory getIdAccessory() {
        return accessory;
    }

    public void setIdAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_serviceman")
    public Worker getIdServiceman() {
        return serviceman;
    }

    public void setIdServiceman(Worker serviceman) {
        this.serviceman = serviceman;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_car")
    public Car getIdCar() {
        return car;
    }

    public void setIdCar(Car car) {
        this.car = car;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_type")
    public Dictionary getIdType() {
        return type;
    }

    public void setIdType(Dictionary type) {
        this.type = type;
    }

    @Column (name = "cost")
    public DecimalFormat getCost() {
        return cost;
    }

    public void setCost(DecimalFormat cost) {
        this.cost = cost;
    }

}
