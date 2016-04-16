package pl.polsl.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Aleksandra Chronowska on 04.04.2016.
 */
@Entity
@Table(name="invoices", schema = "salonydb")
public class Invoice {
    private int id;
    private Date dateCreated;
    private Date dateSold;
    private Date paymentDeadline;
    private Dictionary paymentForm;
    private Dictionary invoiceType;
    private Contract contract;

    @GeneratedValue
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
    @ManyToOne
    @JoinColumn(name="id_payment_form")
    public Dictionary getPaymentForm() {
        return invoiceType;
    }

    public void setPaymentForm(Dictionary idPaymentForm) {
        this.paymentForm = idPaymentForm;
    }

    @Basic
    @ManyToOne
    @JoinColumn(name="id_type")
    public Dictionary getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Dictionary type) {
        this.invoiceType = type;
    }

    @OneToOne(mappedBy="invoice")
    public Contract getContract() {return contract;}

    public void setContract(Contract contract){this.contract = contract;};

}
