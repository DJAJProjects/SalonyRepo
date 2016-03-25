package pl.polsl.model;

import javax.persistence.*;

/**
 * Created by Kuba on 3/25/2016.
 */
@Entity
@Table(name = "reports", schema = "salonydb", catalog = "")
public class ReportEntity {
    private int id;
    private Integer idShowRoom;
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
    @Column(name = "idShowRoom")
    public Integer getIdShowRoom() {
        return idShowRoom;
    }

    public void setIdShowRoom(Integer idShowRoom) {
        this.idShowRoom = idShowRoom;
    }

    @Basic
    @Column(name = "idContractor")
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
