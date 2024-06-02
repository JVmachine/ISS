package com.example.spital.service;

import com.example.spital.model.Medicine;
import com.example.spital.model.Order;
import com.example.spital.repository.IOrderRepository;
import com.example.spital.utils.Observable;
import com.example.spital.utils.Observer;
import com.example.spital.utils.events.ChangeEventType;
import com.example.spital.utils.events.MedicineEvent;

import java.util.ArrayList;
import java.util.List;

public class OrderService implements Observable<MedicineEvent> {
    private IOrderRepository orderRepository;
    private List<Observer<MedicineEvent>> observerList = new ArrayList<>();

    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Medicine> getOrderItems(int idOrder) {
        return orderRepository.getMedicinesOrder(idOrder);
    }

    @Override
    public void addObserver(Observer<MedicineEvent> e) {
        observerList.add(e);
    }

    @Override
    public void removeObserver(Observer<MedicineEvent> e) {
        observerList.remove(e);
    }

    @Override
    public void notifyObservers(MedicineEvent t) {
        observerList.forEach(x->x.update(t));
    }

    public void updateOrder(Order order, Integer id) {
        orderRepository.update(order,id);
        notifyObservers(new MedicineEvent(ChangeEventType.HONOR_ORDER,order));
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order.getId());
        notifyObservers(new MedicineEvent(ChangeEventType.DELETE_ORDER,order));
    }

    public void updateOrderItem(Medicine medicine, Integer id) {
        orderRepository.updateOrderItem(medicine,id);
        notifyObservers(new MedicineEvent(ChangeEventType.UPDATE_ORDER,medicine));
    }
}
