package pl.polsl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="reports", schema = "salonydb")
public class Report {
    private int id;
    private Showroom showroom;
    private Contractor contractor;
    private String name;
    private String content;
    private Date dateBeggining;
    private Date dateEnd;

    public Report(){}

    public Report(String name, Showroom showroom, String content, Date dateBeggining, Date dateEnd){
        this.name = name;
        this.showroom = showroom;
        this.content = content;
        this.dateBeggining = dateBeggining;
        this.dateEnd = dateEnd;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_showroom")
    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_contractor")
    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
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
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "date_beggining")
    public Date getDateBeggining() {
        return dateBeggining;
    }

    public void setDateBeggining(Date dateBeggining) {
        this.dateBeggining = dateBeggining;
    }

    @Basic
    @Column(name = "date_end")
    public Date getDateEnd() { return dateEnd; }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
