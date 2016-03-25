package pl.polsl.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Kuba on 3/25/2016.
 */
@Entity
@Table(name = "invoices", schema = "salonydb", catalog = "")
public class InvoiceEntity {
    private int id;
    private Date dateCreated;
    private Date dateSold;
    private Date paymentDeadline;
    private String paymentForm;
    private String type;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dateCreated")
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "dateSold")
    public Date getDateSold() {
        return dateSold;
    }

    public void setDateSold(Date dateSold) {
        this.dateSold = dateSold;
    }

    @Basic
    @Column(name = "paymentDeadline")
    public Date getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(Date paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    @Basic
    @Column(name = "paymentForm")
    public String getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(String paymentForm) {
        this.paymentForm = paymentForm;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
