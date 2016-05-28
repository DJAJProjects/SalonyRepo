package pl.polsl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="privileges", schema = "salonydb")
public class Privileges {
    private int id;
    private String name;
    private Dictionary module;
    private Boolean readPriv;
    private Boolean insertPriv;
    private Boolean deletePriv;
    private Boolean updatePriv;


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
    @ManyToOne
    @JoinColumn(name="id_module_name")
    public Dictionary getModule() {
        return module;
    }

    public void setModule(Dictionary module) {
        this.module = module;
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
    @Column(name = "read_priv")
    public Boolean getReadPriv() {return readPriv;}

    public void setReadPriv(Boolean readPriv) { this.readPriv = readPriv; }

    @Basic
    @Column(name = "insert_priv")
    public Boolean getInsertPriv() {return insertPriv;}

    public void setInsertPriv(Boolean insertPriv) { this.insertPriv = insertPriv; }

    @Basic
    @Column(name = "delete_priv")
    public Boolean getDeletePriv() {return deletePriv;}

    public void setDeletePriv(Boolean deletePriv) { this.deletePriv = deletePriv; }

    @Basic
    @Column(name = "update_priv")
    public Boolean getUpdatePriv() {return updatePriv;}

    public void setUpdatePriv(Boolean updatePriv) { this.updatePriv = updatePriv; }



}
