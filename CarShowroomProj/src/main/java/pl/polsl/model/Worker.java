package pl.polsl.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by Dominika Błasiak on 04.04.2016.
 */
@Entity
@Table(name="workers", schema = "salonydb")
public class Worker {
    private int id;
    private String name;
    private String surname;
    private Dictionary position;
    private Showroom showroom;
    private Integer payment;
    private Date dateHired;
    private Set<Contract> contractList;
    private String login;
    private String password;

    public Worker() {
    }

    public Worker(String name, String surname, Dictionary position, Showroom showroom, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.showroom = showroom;
        this.login = login;
        this.password = password;
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_showroom")
    public Showroom getShowroom() {
        return showroom;
    }
    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }

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

    @ManyToOne
    @JoinColumn(name = "id_position")
    public Dictionary getPosition() {
        return position;
    }

    public void setPosition(Dictionary position) {
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
    @Column(name = "date_hired")
    public Date getDateHired() {
        return dateHired;
    }

    public void setDateHired(Date dateHired) {
        this.dateHired = dateHired;
    }

    @OneToMany (mappedBy="worker")
    public Set<Contract> getContractList() {
        return contractList;
    }
    public void setContractList(Set<Contract> contractList) {
        this.contractList = contractList;
    }

    @Basic
    @Column
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
