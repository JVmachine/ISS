package com.example.spital.repository;

import com.example.spital.model.Medicine;
import com.example.spital.model.Order;
import com.example.spital.model.Status;
import com.example.spital.model.validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OrderRepository implements IOrderRepository{
    private Validator<Order> validator;
    private JdbcUtils jdbcUtils;

    public OrderRepository(Validator<Order> validator, Properties properties) {
        this.validator = validator;
        this.jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public void save(Order elem) {
        validator.validate(elem);
        Connection connection = jdbcUtils.getConnection();
        int idOrder = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into Orders(status) values(?)")){
            preparedStatement.setString(1, elem.getStatus().toString());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                idOrder = resultSet.getInt(1);
            }
            saveOrderItems(idOrder,elem.getMedicines());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void saveOrderItems(int idOrder, List<Medicine> medicines) {
        Connection connection = jdbcUtils.getConnection();
        for(Medicine medicine:medicines){
            try(PreparedStatement preparedStatement = connection.prepareStatement("insert into OrderItems(medicine,quantity,idOrder) values(?,?,?)")){
                preparedStatement.setString(1,medicine.getName());
                preparedStatement.setInt(2,medicine.getQuantity());
                preparedStatement.setInt(3,idOrder);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Order elem, Integer id) {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("update Orders set status = ? where id=?")){
            preparedStatement.setString(1, String.valueOf(elem.getStatus()));
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from Orders where id =?")){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            deleteOrderItems(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteOrderItems(int  id) {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from OrderItems where idOrder =?")){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order findOne(Integer id) {
        return null;
    }

    @Override
    public Iterable<Order> findAll() {
        Connection connection = jdbcUtils.getConnection();
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from Orders")){
            try(ResultSet resultSet = preparedStatement.executeQuery()){

                while(resultSet.next()){
                    int idOrder = resultSet.getInt("id");
                    Status statusOrder = Status.valueOf(resultSet.getString("status"));
                    List<Medicine> orderItems = getMedicinesOrder(idOrder);
                    Order order = new Order(orderItems,statusOrder);
                    order.setId(idOrder);
                    System.out.println(order.getId());
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Medicine> getMedicinesOrder(int idOrder) {
        Connection connection = jdbcUtils.getConnection();
        List<Medicine> medicines = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from OrderItems where idOrder = ?")){
            preparedStatement.setInt(1,idOrder);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    String medicineName =resultSet.getString("medicine");
                    int medicineQuantity = resultSet.getInt("quantity");
                    Medicine medicine = new Medicine(medicineName,medicineQuantity);
                    medicines.add(medicine);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }

    @Override
    public void updateOrderItem(Medicine medicine, int idOrder) {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("update OrderItems set quantity = ? where medicine = ? and idOrder = ?")){
            preparedStatement.setInt(1,medicine.getQuantity());
            preparedStatement.setString(2, medicine.getName());
            preparedStatement.setInt(3,idOrder);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
