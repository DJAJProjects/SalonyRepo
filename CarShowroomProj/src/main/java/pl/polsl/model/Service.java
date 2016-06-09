package pl.polsl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.sql.Date;

/**
 * Created by Kuba on 20.04.2016.
 */
@Entity
@Table(name="services", schema = "salonydb")
public class Service {
    private int id;
    private Dictionary serviceType;
    private Worker serviceman;
    private Car car;
    private Accessory accessory;
    private Integer cost;
    private Date dateConducted;
    private Dictionary subserviceType;

    public Service() {
    }

    public Service(int id, Dictionary type, Worker serviceman, Car car, Accessory accessory, int cost, Date dateConducted) {
        this.id = id;
        this.serviceType = type;
        this.serviceman = serviceman;
        this.car = car;
        this.accessory = accessory;
        this.cost = cost;
        this.dateConducted = dateConducted;
    }

    public Service(Dictionary type, Worker serviceman, Car car, Accessory accessory, int cost, Date dateConducted) {
        this.serviceType = type;
        this.serviceman = serviceman;
        this.car = car;
        this.accessory = accessory;
        this.cost = cost;
        this.dateConducted = dateConducted;
    }

    public Service(int id, Dictionary type, Worker serviceman, Car car, Dictionary subservice, int cost, Date dateConducted) {
        this.id = id;
        this.serviceType = type;
        this.serviceman = serviceman;
        this.car = car;
        this.subserviceType = subservice;
        this.cost = cost;
        this.dateConducted = dateConducted;
    }

    public Service(Dictionary type, Worker serviceman, Car car, Dictionary subservice, int cost, Date dateConducted) {
        this.serviceType = type;
        this.serviceman = serviceman;
        this.car = car;
        this.subserviceType = subservice;
        this.cost = cost;
        this.dateConducted = dateConducted;
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

    @Basic
    @Column(name = "date_conducted")
    public Date getDateConducted() {
        return dateConducted;
    }

    public void setDateConducted(Date dateConducted) {
        this.dateConducted = dateConducted;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "id_accessory")
    public Accessory getAccessory() {
        return accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_serviceman")
    public Worker getServiceman() {
        return serviceman;
    }

    public void setServiceman(Worker serviceman) {
        this.serviceman = serviceman;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_car")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_type")
    public Dictionary getServiceType() {
        return serviceType;
    }

    public void setServiceType(Dictionary type) {
        this.serviceType = type;
    }

    @Basic
    @Column (name = "cost")
    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_subtype")
    public Dictionary getSubserviceType() {
        return subserviceType;
    }

    public void setSubserviceType(Dictionary type) {
        this.subserviceType = type;
    }

}
