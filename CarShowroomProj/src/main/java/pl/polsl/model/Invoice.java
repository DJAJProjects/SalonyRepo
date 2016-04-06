package pl.polsl.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="invoices", schema = "salonydb")
public class Invoice {
    private int id;
    private Date dateCreated;
    private Date dateSold;
    private Date paymentDeadline;
    private Integer idPaymentForm;
    private Integer idType;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date_created")
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "date_sold")
    public Date getDateSold() {
        return dateSold;
    }

    public void setDateSold(Date dateSold) {
        this.dateSold = dateSold;
    }

    @Basic
    @Column(name = "payment_deadline")
    public Date getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(Date paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    @Basic
    @Column(name = "id_payment_form")
    public Integer getIdPaymentForm() {
        return idPaymentForm;
    }

    public void setIdPaymentForm(Integer idPaymentForm) {
        this.idPaymentForm = idPaymentForm;
    }

    @Basic
    @Column(name = "id_type")
    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }
}
