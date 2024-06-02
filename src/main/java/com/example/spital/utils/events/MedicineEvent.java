package com.example.spital.utils.events;

import com.example.spital.model.Medicine;
import com.example.spital.model.Order;

public class MedicineEvent implements Event{
    private ChangeEventType type;
    private Medicine newMedicine,oldMedicine;
    private Order newOrder,oldOrder;

    public MedicineEvent(ChangeEventType type, Medicine newMedicine) {
        this.type = type;
        this.newMedicine = newMedicine;
    }

    public MedicineEvent(ChangeEventType type, Medicine newMedicine, Medicine oldMedicine) {
        this.type = type;
        this.newMedicine = newMedicine;
        this.oldMedicine = oldMedicine;
    }

    public MedicineEvent(ChangeEventType type, Order newOrder) {
        this.type = type;
        this.newOrder = newOrder;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Medicine getNewMedicine() {
        return newMedicine;
    }

    public Medicine getOldMedicine() {
        return oldMedicine;
    }

    public Order getNewOrder() {
        return newOrder;
    }
    public Order getOldOrder() {
        return oldOrder;
    }
}
