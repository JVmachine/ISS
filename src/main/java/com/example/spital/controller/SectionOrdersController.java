package com.example.spital.controller;
import com.example.spital.model.Medicine;
import com.example.spital.model.Order;
import com.example.spital.service.OrderService;
import com.example.spital.utils.Observer;
import com.example.spital.utils.events.MedicineEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.jfr.EventType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SectionOrdersController implements Observer<MedicineEvent> {
    @FXML
    private TableColumn<Medicine,String> medicineOrderName;

    @FXML
    private TableColumn<Medicine, Integer> medicineOrderQuantity;

    @FXML
    private Label messageLabel;

    @FXML
    private TableView<Medicine> orderDetailsTable;

    @FXML
    private TableColumn<Order, Integer> orderNumber;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order,String> status;

    @FXML
    private TextField quantityTextField;

    private OrderService orderService;
    private ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
    private ObservableList<Medicine> medicineOrderList = FXCollections.observableArrayList();
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
        orderService.addObserver(this);
    }

    public void initOrderTable() {
        Iterable<Order> orders = orderService.getAllOrders();
        List<Order> orderList = new ArrayList<>();
        orders.forEach(order -> orderList.add(order));
        orderObservableList.setAll(orderList);
    }
    @FXML
    private void initialize(){
        orderNumber.setCellValueFactory(new PropertyValueFactory<Order,Integer>("id"));
        status.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
        ordersTable.setItems(orderObservableList);

        medicineOrderName.setCellValueFactory(new PropertyValueFactory<Medicine,String>("name"));
        medicineOrderQuantity.setCellValueFactory(new PropertyValueFactory<Medicine,Integer>("quantity"));
        orderDetailsTable.setItems(medicineOrderList);
    }
    @FXML
    void showDetailsButtonOnAction(ActionEvent event) {
        Order order = ordersTable.getSelectionModel().getSelectedItem();
        if(order != null){
            List<Medicine> medicines = orderService.getOrderItems(order.getId());
            medicineOrderList.setAll(medicines);
        }
        else{
            messageLabel.setText("Select an order first!");
        }
    }

    public void updateOrderButtonOnAction(ActionEvent actionEvent) {
        Order order = ordersTable.getSelectionModel().getSelectedItem();
        if(order != null){
            Medicine medicine = orderDetailsTable.getSelectionModel().getSelectedItem();
            Integer quantity = Integer.valueOf(quantityTextField.getText());
            medicine.setQuantity(quantity);
            orderService.updateOrderItem(medicine,order.getId());

        }else{
            messageLabel.setText("You must to select an order first");
        }
    }

    public void deleteOrderButtonOnAction(ActionEvent actionEvent) {
        Order order = ordersTable.getSelectionModel().getSelectedItem();
        if(order != null){
            orderService.deleteOrder(order);
            messageLabel.setText("Order removed");
        }else{
            messageLabel.setText("You must to select an order first");
        }
    }

    @Override
    public void update(MedicineEvent medicineEvent) {
        initOrderTable();

        Order order = ordersTable.getSelectionModel().getSelectedItem();
        if(order != null){
            List<Medicine> medicines = orderService.getOrderItems(order.getId());
            medicineOrderList.setAll(medicines);
        }
    }
}
