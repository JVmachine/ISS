package com.example.spital.repository;

import com.example.spital.model.Medicine;
import com.example.spital.model.Order;

import java.util.List;

public interface IOrderRepository extends IRepository<Order,Integer> {
    public List<Medicine> getMedicinesOrder(int idOrder);
    public void updateOrderItem(Medicine medicine,int idOrder);
    public void deleteOrderItems(int id);
}
