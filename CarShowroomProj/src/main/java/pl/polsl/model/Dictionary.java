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
    @Id
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
    @OneToMany (mappedBy="city")
    public Set<Showroom> getCity() {
        return city;
    }

    public void setCity(Set<Showroom> city) {
        this.city = city;
    }
}
