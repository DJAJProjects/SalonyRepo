package pl.polsl.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Kuba on 3/19/2016.
 */
@Table
public class Car {
    @Id
    @GeneratedValue
    public long Id;

    @Column
    public String name;

}
