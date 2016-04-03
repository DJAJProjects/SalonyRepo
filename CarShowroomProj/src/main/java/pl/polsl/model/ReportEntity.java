package pl.polsl.model;

import javax.persistence.*;

/**
 * Created by Kuba on 3/25/2016.
 */
@Entity
@Table(name = "reports", schema = "salonydb", catalog = "")
public class ReportEntity {
    private int id;
    private ShowroomEntity idShowRoom;
    private ContractorEntity idContractor;
    private String content;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   /* @ManyToOne
    @JoinColumn (name = "idShowRoom")
    public ShowroomEntity getIdShowRoom() {
        return idShowRoom;
    }

    public void setIdShowRoom(ShowroomEntity idShowRoom) {
        this.idShowRoom = idShowRoom;
    }*/

    /*@ManyToOne
    @JoinColumn (name = "idContractor")
    public ContractorEntity getIdContractor() {
        return idContractor;
    }

    public void setIdContractor(ContractorEntity idContractor) {
        this.idContractor = idContractor;
    }*/

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
