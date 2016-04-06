package pl.polsl.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
public class Contracts {
    private int id;
    private Integer idSeller;
    private Integer idInvoice;
    private Integer idContractor;
    private Integer totalCost;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_seller")
    public Integer getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(Integer idSeller) {
        this.idSeller = idSeller;
    }

    @Basic
    @Column(name = "id_invoice")
    public Integer getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(Integer idInvoice) {
        this.idInvoice = idInvoice;
    }

    @Basic
    @Column(name = "id_contractor")
    public Integer getIdContractor() {
        return idContractor;
    }

    public void setIdContractor(Integer idContractor) {
        this.idContractor = idContractor;
    }

    @Basic
    @Column(name = "totalCost")
    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }


}
