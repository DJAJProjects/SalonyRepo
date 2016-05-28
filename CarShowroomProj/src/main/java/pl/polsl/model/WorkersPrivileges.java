package pl.polsl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="workers_privileges", schema = "salonydb")
public class WorkersPrivileges {
    private int id;
    private Privileges privileges;
    private Worker worker;


    public WorkersPrivileges() {}

    public WorkersPrivileges(Privileges privileges, Worker worker) {
        this.privileges = privileges;
        this.worker = worker;
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

    @ManyToOne()
    @JoinColumn(name = "id_privileges")
    public Privileges getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Privileges privileges) {
        this.privileges = privileges;
    }

    @ManyToOne()
    @JoinColumn(name = "id_workers")
    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

}
