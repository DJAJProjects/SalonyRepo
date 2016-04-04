package pl.polsl.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
public class Reports {
    private int id;
    private Integer idShowroom;
    private Integer idContractor;
    private String content;

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
    public Integer getIdShowroom() {
        return idShowroom;
    }

    public void setIdShowroom(Integer idShowroom) {
        this.idShowroom = idShowroom;
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
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
