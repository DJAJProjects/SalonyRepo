package pl.polsl.model;

import javax.persistence.*;

/**
 * Created by Kuba on 3/25/2016.
 */
@Entity
@Table(name = "showrooms", schema = "salonydb", catalog = "")
public class ShowroomEntity {
    private int id;
    private String city;
    private String street;
    private String country;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
