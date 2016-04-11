package pl.polsl.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Dominika Błasiak on 04.04.2016.
 */
@Entity
@Table(name="workers", schema = "salonydb")
public class Worker {
    private int id;
//    private Showroom showroom;
    private String name;
    private String surname;
    private String position;
    private Integer payment;
    private Date dateHired;
    private int showroom;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_showroom")
    public int getShowroom() {
        return showroom;
    }

    public void setShowroom(int showroom) {
        this.showroom = showroom;
    }

    //    @Basic
//    @Column(name = "id_showroom")
//    public Showroom getShowroom() {
//        return showroom;
//    }
//
//    public void setShowroom(Showroom showroom) {
//        this.showroom = showroom;
//    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "payment")
    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    @Basic
    @Column(name = "dateHired")
    public Date getDateHired() {
        return dateHired;
    }

    public void setDateHired(Date dateHired) {
        this.dateHired = dateHired;
    }


}
