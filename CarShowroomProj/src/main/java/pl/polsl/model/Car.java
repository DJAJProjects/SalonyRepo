package pl.polsl.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="cars", schema = "salonydb")
public class Car {
    private int id;
    private int idShowroom;
    private Integer idName;
    private Date prodDate;

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
    public int getIdShowroom() {
        return idShowroom;
    }

    public void setIdShowroom(int idShowroom) {
        this.idShowroom = idShowroom;
    }

    @Basic
    @Column(name = "id_name")
    public Integer getIdName() {
        return idName;
    }

    public void setIdName(Integer idName) {
        this.idName = idName;
    }

    @Basic
    @Column(name = "prod_date")
    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

}
