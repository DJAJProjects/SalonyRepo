package pl.polsl.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="dictionary", schema = "salonydb")
public class Dictionary {
    private int id;
    private String type;
    private String value;
    private String value2;
    private Set<Showroom> country;
    private Set<Showroom> city;
    private Set<Invoice> invoiceType;
    private Set<Invoice>paymentForm;
    private Set<Car>carName;
    private Set<Accessory>accessory;


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

    @OneToMany (mappedBy="country")
    public Set<Showroom> getCountry() {
        return country;
    }

    public void setCountry(Set<Showroom> country) {
        this.country = country;
    }
    @OneToMany
    public Set<Showroom> getCity() {
        return city;
    }

    public void setCity(Set<Showroom> city) {
        this.city = city;
    }

    @OneToMany (mappedBy="invoiceType",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = false)
    public Set<Invoice> getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Set<Invoice> paymentForm) {
        this.invoiceType = paymentForm;
    }

    @OneToMany (mappedBy="paymentForm",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = false)
    public Set<Invoice> getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(Set<Invoice> paymentForm) {
        this.paymentForm = paymentForm;
    }

    @OneToMany (mappedBy="carName",cascade = CascadeType.REMOVE, orphanRemoval = false)
    public Set<Car> getCarName() {
        return carName;
    }

    public void setCarName(Set<Car> carName) {
        this.carName = carName;
    }

    @OneToMany (mappedBy="accessory",cascade = CascadeType.REMOVE, orphanRemoval = false)
    public Set<Accessory> getAccessory() {
        return accessory;
    }

    public void setAccessory(Set<Accessory> accessory) {
        this.accessory = accessory;
    }
}
