package pl.polsl.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="contractors", schema = "salonydb")
public class Contractor {
    private int id;
    private String name;
    private String surname;
    private String pesel;
    private String nip;
    private String regon;
    private Dictionary city;
    private Dictionary country;
    private String street;
    private Set<Contract> contract;

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
    @Column(name = "pesel")
    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Basic
    @Column(name = "nip")
    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    @Basic
    @Column(name = "regon")
    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_city")
    public Dictionary getCity() {
        return city;
    }

    public void setCity(Dictionary city) {
        this.city = city;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_country")
    public Dictionary getCountry() {
        return country;
    }

    public void setCountry(Dictionary country) {
        this.country = country;
    }

    @Basic
    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @OneToMany(mappedBy="contractor")
    public Set<Contract> getContract() {return contract;}

    public void setContract(Set<Contract> contract){this.contract = contract;};



}
