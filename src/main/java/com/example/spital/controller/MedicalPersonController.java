package com.example.spital.controller;

import com.example.spital.model.Medicine;
import com.example.spital.model.Order;
import com.example.spital.model.Status;
import com.example.spital.model.validators.ValidationException;
import com.example.spital.service.MedicineService;
import com.example.spital.service.OrderService;
import com.example.spital.utils.Observer;
import com.example.spital.utils.events.MedicineEvent;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MedicalPersonController implements Observer<MedicineEvent> {
    @FXML
    private Button addMedicineButton;

    @FXML
    private TableColumn<Medicine, Date> expirationDate;

    @FXML
    private Button logOutButton;

    @FXML
    private TableColumn<Medicine, Date> medicineOrderExpirationDate;

    @FXML
    private TableColumn<Medicine, String> medicineOrderName;

    @FXML
    private TableColumn<Medicine, Integer> medicineOrderQuantity;

    @FXML
    private TableView<Medicine> medicinesTable;

    @FXML
    private Label messageLabel;

    @FXML
    private TableColumn<Medicine, String> name;

    @FXML
    private TableView<Medicine> orderTable;

    @FXML
    private TableColumn<Medicine, Integer> quantity;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Button sendOrderButton;

    @FXML
    private Button showOrdersButton;

    @FXML
    private Label messageOrderLabel;


    private MedicineService medicineService;
    private OrderService orderService;
    ObservableList<Medicine> medicineList = FXCollections.observableArrayList();

    ObservableList<Medicine> medicineOrderList = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        name.setCellValueFactory(new PropertyValueFactory<Medicine,String>("name"));
        expirationDate.setCellValueFactory(new PropertyValueFactory<Medicine,Date>("expirationDate"));
        quantity.setCellValueFactory(new PropertyValueFactory<Medicine,Integer>("quantity"));
        medicinesTable.setItems(medicineList);

        medicineOrderName.setCellValueFactory(new PropertyValueFactory<Medicine,String>("name"));
        medicineOrderExpirationDate.setCellValueFactory(new PropertyValueFactory<Medicine,Date>("expirationDate"));
        medicineOrderQuantity.setCellValueFactory(new PropertyValueFactory<Medicine,Integer>("quantity"));
        orderTable.setItems(medicineOrderList);


    }

    public void initMedicineTable(){
        Iterable<Medicine> medicines = medicineService.getAll();
        List<Medicine> allMedicines = new ArrayList<>();
        medicines.forEach(allMedicines::add);
        medicineList.setAll(allMedicines);
    }

    private boolean validateQuantity(Integer quantity){
        if(quantity < 0){
            messageLabel.setText("Invalid number!");
            return false;
        }
        else{
            messageLabel.setText("");
            return true;
        }
    }
    @FXML
    void addMedicineButtonOnAction(ActionEvent event) {
        Medicine medicine = medicinesTable.getSelectionModel().getSelectedItem();
        if(medicine != null){
            if(quantityTextField.getText() == ""){
                messageLabel.setText("Please insert the quantity");
            }
            else{
                Integer quantity = Integer.parseInt(quantityTextField.getText());
                if(validateQuantity(quantity)){
                    Medicine medicineForOrder = new Medicine(medicine.getName(),medicine.getExpirationDate(),quantity);
                    medicineOrderList.add(medicineForOrder);
                    medicineService.updateQuantity(medicine.getId(),medicine.getQuantity(),quantity);
                }

            }


        }
        else{
            messageLabel.setText("Please select a medicine first!");
        }
    }

    @FXML
    void logOutButtonOnAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    @FXML
    void sendOrderButtonOnAction(ActionEvent event) {
        List<Medicine> medicines = medicineOrderList;
        try{
            Order order = new Order(medicines, Status.PENDING);
            orderService.saveOrder(order);
            messageOrderLabel.setText("Order placed");
        }catch (ValidationException ex){
            messageOrderLabel.setText(ex.getMessage());
        }



    }

    @FXML
    void showOrdersButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SectionOrdersController.class.getResource("sectionOrders-window-view.fxml"));
            Parent parent = (Parent)fxmlLoader.load();

            SectionOrdersController sectionOrdersController = fxmlLoader.getController();
            sectionOrdersController.setOrderService(orderService);
            sectionOrdersController.initOrderTable();
            Stage stage = new Stage();
            Scene scene = new Scene(parent,754,461);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setMedicineService(MedicineService medicineService) {
        this.medicineService = medicineService;
        medicineService.addObserver(this);
    }

    @Override
    public void update(MedicineEvent medicineEvent) {
        initMedicineTable();
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

}


