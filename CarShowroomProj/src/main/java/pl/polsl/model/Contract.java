package pl.polsl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Aleksandra Chronowska on 04.04.2016.
 */
@Entity
@Table(name="contracts", schema = "salonydb")
public class Contract {
    private int id;
    private Worker worker;
    private Invoice invoice;
    private Contractor contractor;
    private Integer totalCost;
    private Set<Accessory> accessoryList;
    private Set<Car> carList;
//    private Promotion promotion;

    @GeneratedValue
    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @JsonIgnore
    @Basic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_seller")
    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @OneToOne
    @JoinColumn(name = "id_invoice")
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice idInvoice) {
        this.invoice = idInvoice;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contractor")
    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    @Basic
    @Column(name = "total_cost")
    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    @OneToMany (mappedBy="contract")
    public Set<Accessory> getAccessoryList() {
        return accessoryList;
    }

    public void setAccessoryList(Set<Accessory> accessoryList) {
        this.accessoryList = accessoryList;
    }

    @OneToMany (mappedBy="contract")
    public Set<Car> getCarList() {
        return carList;
    }

    public void setCarList(Set<Car> carList) {
        this.carList = carList;
    }

    @ManyToMany
    @JoinTable(
            name="contracts_promotions",
            joinColumns=@JoinColumn(name="id_contracts", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="id_promotions", referencedColumnName="id"))
    private Set<Promotion> promotionList;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_promotion")
//    public Promotion getPromotion() {
//        return promotion;
//    }
//
//    public void setPromotion(Dictionary idCountry) {
//        this.promotion = promotion;
//    }
}
