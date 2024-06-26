package com.globalpayments.challenge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer", schema = "challenge")
@Getter
@Setter
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "points", nullable = false)
    private String custPoints;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustPoints() {
        return custPoints;
    }

    public void setCustPoints(String custPoints) {
        this.custPoints = custPoints;
    }
}
