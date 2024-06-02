package com.example.spital.model;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "Medicines")
public class Medicine implements Identifiable<Integer>{
    private Integer id;
    private String name;
    private Date expirationDate;
    private Integer quantity;

    public Medicine(String name, Date expirationDate, Integer quatity) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.quantity = quatity;
    }

    public Medicine() {
    }

    public Medicine(String medicineName, int medicineQuantity) {
        this.name = medicineName;
        this.quantity = medicineQuantity;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "expirationDate")
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                ", name='" + name + '\'' +
                ", expirationDate=" + expirationDate +
                ", quatity=" + quantity +
                '}';
    }

    @Override
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
