package com.example.spital.controller;

import com.example.spital.model.Medicine;
import com.example.spital.model.validators.ValidationException;
import com.example.spital.service.MedicineService;
import com.example.spital.utils.Observer;
import com.example.spital.utils.events.MedicineEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AdminController implements Observer<MedicineEvent> {
    @FXML
    private Button addMedicineButton;

    @FXML
    private Button deleteMedicineButton;

    @FXML
    private TableColumn<Medicine, Date> expirationDate;

    @FXML
    private DatePicker expirationDatePicker;

    @FXML
    private TableView<Medicine> medicinesTable;

    @FXML
    private Label messageLabel;

    @FXML
    private TableColumn<Medicine, String> name;

    @FXML
    private TextField nameTextField;

    @FXML
    private TableColumn<Medicine, Integer> quantity;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Button updateMedicineButton;

    ObservableList<Medicine> list = FXCollections.observableArrayList();

    private MedicineService medService;

    public void init(){
        Iterable<Medicine> medicines = medService.getAll();

        List<Medicine> allMedicines = new ArrayList<>();
        medicines.forEach(allMedicines::add);
        System.out.println(allMedicines.size());
        list.setAll(allMedicines);

    }
    @FXML
    private void initialize(){
        name.setCellValueFactory(new PropertyValueFactory<Medicine,String>("name"));
        expirationDate.setCellValueFactory(new PropertyValueFactory<Medicine,Date>("expirationDate"));
        quantity.setCellValueFactory(new PropertyValueFactory<Medicine,Integer>("quantity"));
        medicinesTable.setItems(list);
    }

    @FXML
    void addMedicineButtonOnAction(ActionEvent event) {
        String medicineName = nameTextField.getText();
        Date medicineExpirationDate = java.sql.Date.valueOf(expirationDatePicker.getValue());
        Integer medicineQuantity = Integer.parseInt(quantityTextField.getText());
        try{
            Medicine foundMedicine = medService.getMedicineByName(medicineName);
            if(foundMedicine == null){
                medService.saveMedicine(medicineName,medicineExpirationDate,medicineQuantity);
                messageLabel.setText("Medicine " + medicineName + " successufully added.");
            }
            else{
                messageLabel.setText("Existing medicine");
            }
        }catch (ValidationException ex){
            messageLabel.setText(ex.getMessage());
        }



    }

    @FXML
    void deleteMedicineButtonOnAction(ActionEvent event) {
        Medicine medicineSelected = medicinesTable.getSelectionModel().getSelectedItem();
        if(medicineSelected != null){
            medService.delete(medicineSelected);
            messageLabel.setText("Medicine removed");
        }else{
            messageLabel.setText("Please select a medicine first");
        }
    }

    @FXML
    void updateMedicineButtonOnAction(ActionEvent event) {
        Medicine medicineSelected = medicinesTable.getSelectionModel().getSelectedItem();
        String medicineName = nameTextField.getText();
        Date medicineExpirationDate = java.sql.Date.valueOf(expirationDatePicker.getValue());
        Integer medicineQuantity = Integer.parseInt(quantityTextField.getText());
        try{
            if(medicineSelected != null){
                medService.update(medicineSelected.getId(),medicineName,medicineExpirationDate,medicineQuantity);
                messageLabel.setText("Medicine successufully updated");
            }
            else{
                messageLabel.setText("Please select a medicine first");
            }
        }catch (ValidationException ex){
            messageLabel.setText(ex.getMessage());
        }

    }
    @FXML
    void logOutButtonOnAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void setService(MedicineService medicineService) {
        this.medService = medicineService;
        medicineService.addObserver(this);
//        init();
    }

    @Override
    public void update(MedicineEvent medicineEvent) {
        init();
    }

}
