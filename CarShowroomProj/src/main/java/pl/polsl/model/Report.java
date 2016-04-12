package pl.polsl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="reports", schema = "salonydb")
public class Report {
    private int id;
    private Showroom idShowroom;
    private Contractor idContractor;
    private String content;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_showroom")
    public Showroom getIdShowroom() {
        return idShowroom;
    }

    public void setIdShowroom(Showroom idShowroom) {
        this.idShowroom = idShowroom;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_contractor")
    public Contractor getIdContractor() {
        return idContractor;
    }

    public void setIdContractor(Contractor idContractor) {
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
