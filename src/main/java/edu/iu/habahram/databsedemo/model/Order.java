package edu.iu.habahram.databsedemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(schema = "lectures", name="orders")
public class Order {
    @Id
    @GeneratedValue
    private int id;

    private String status;
    private Date statusDate;

    public Order() {
    }
}
