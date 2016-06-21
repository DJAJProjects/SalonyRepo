package pl.polsl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * Model class for dictionary
 * @author Dominika Błasiak
 * @version 1.0
 */
@Entity
@Table(name="dictionary", schema = "salonydb")
public class Dictionary {
    /**
     * Dictionary id
     */
    private int id;
    /**
     * Dictionary type
     */
    private String type;
    /**
     * Dictionary value
     */
    private String value;
    /**
     * Dictionary alternative value
     */
    private String value2;
    /**
     * Dictionary alternative value
     */
    private String value3;

    //region related set to dictionary
    private Set<Showroom> country;
    private Set<Showroom> city;
    private Set<Invoice> invoiceType;
    private Set<Invoice>paymentForm;
    private Set<Car>carName;
    private Set<Accessory>accessory;
    private Set<Service> serviceType;
    private Set<Worker> position;
    //endregion
    public Dictionary(){}

    public Dictionary(int id, String type, String value, String value2) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.value2 = value2;
    }

    public Dictionary(int id, String type, String value, String value2, String value3) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.value2 = value2;
        this.value3 = value3;
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
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "value2")
    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    @Basic
    @Column(name = "value3")
    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    @JsonIgnore
    @OneToMany (mappedBy="country")
    public Set<Showroom> getCountry() {
        return country;
    }

    public void setCountry(Set<Showroom> country) {
        this.country = country;
    }

    @OneToMany(mappedBy="city")
    @JsonIgnore
    public Set<Showroom> getCity() {
        return city;
    }

    public void setCity(Set<Showroom> city) {
        this.city = city;
    }

    @OneToMany (mappedBy="invoiceType",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = false)
    @JsonIgnore
    public Set<Invoice> getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Set<Invoice> invoiceType) {
        this.invoiceType = invoiceType;
    }

    @OneToMany (mappedBy="paymentForm",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = false)
    @JsonIgnore
    public Set<Invoice> getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(Set<Invoice> paymentForm) {
        this.paymentForm = paymentForm;
    }

    @OneToMany (mappedBy="carName")
    @JsonIgnore
    public Set<Car> getCarName() {
        return carName;
    }

    public void setCarName(Set<Car> carName) {
        this.carName = carName;
    }

    @OneToMany (mappedBy="accessory",cascade = CascadeType.REMOVE, orphanRemoval = false)
    @JsonIgnore
    public Set<Accessory> getAccessory() {
        return accessory;
    }

    public void setAccessory(Set<Accessory> accessory) {
        this.accessory = accessory;
    }

    @OneToMany(mappedBy="serviceType")
    @JsonIgnore
    public Set<Service> getServices() {
        return serviceType;
    }

    public void setServices(Set<Service> services) {
        this.serviceType = services;
    }

    @OneToMany(mappedBy = "position")
    @JsonIgnore
    public Set<Worker> getPosition() {
        return position;
    }

    public void setPosition(Set<Worker> position) {
        this.position = position;
    }
}
