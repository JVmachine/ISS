package com.example.spital.model;

import java.util.List;

public class Order implements Identifiable<Integer>{
    private Integer id;
    private List<Medicine> medicines;
    private Status status;

    public Order(List<Medicine> medicines, Status status) {
        this.medicines = medicines;
        this.status = status;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", medicines=" + medicines +
                ", status=" + status +
                '}';
    }
}
