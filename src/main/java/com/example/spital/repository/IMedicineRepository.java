package com.example.spital.repository;

import com.example.spital.model.Medicine;
import com.example.spital.model.Type;
import com.example.spital.model.User;

public interface IMedicineRepository extends IRepository<Medicine,Integer> {
    public Medicine getMedicineByName(String name);
    public void updateQuantity(Medicine elem,Integer id);
}
