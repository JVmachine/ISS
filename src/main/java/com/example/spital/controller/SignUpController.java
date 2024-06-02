package com.example.spital.controller;

import com.example.spital.model.validators.ValidationException;
import com.example.spital.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignUpController {
    private UserService userService;
    @FXML
    private TextField addressTextField;

    @FXML
    private Button backButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private PasswordField confirmPasswordTextFields;

    @FXML
    private Button createAccountButton;

    @FXML
    private TextField firstNameTextFields;

    @FXML
    private TextField lastNameTextFields;

    @FXML
    private RadioButton medicalPersonRadioButton;

    @FXML
    private Label messageLabel;

    @FXML
    private PasswordField passwordTextFields;

    @FXML
    private RadioButton pharmacistRadioButton;

    @FXML
    private TextField phoneNrTextField;

    @FXML
    private TextField usernameTextFields;

    @FXML
    private Label addressErrorLabel;

    @FXML
    private Label firstNameErrorLabel;

    @FXML
    private Label lastNameErrorLabel;

    @FXML
    private Label phoneNrErrorLabel;

    @FXML
    private Label usernameErrorLabel;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private Label confirmPasswordErrorLabel;

    @FXML
    private Label typeErrorLabel;


    @FXML
    void backButtonOnAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();


    }

    private boolean validateFields(){
        String firstName = firstNameTextFields.getText();
        String lastName = lastNameTextFields.getText();
        String address = addressTextField.getText();
        String phoneNr = phoneNrTextField.getText();
        String username = usernameTextFields.getText();
        String password = passwordTextFields.getText();
        String confirmPassword = confirmPasswordTextFields.getText();
        if(!firstName.equals("")&& !lastName.equals("") && !address.equals("") && !phoneNr.equals("") && !username.equals("") && !password.equals("") && !confirmPassword.equals("")){
            return true;
        }
        if(firstName.equals("")){
            firstNameErrorLabel.setText("Please enter first name");
            firstNameTextFields.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
        if(lastName.equals("")){
            lastNameErrorLabel.setText("Please enter last name");
            lastNameTextFields.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
        if(firstName.equals("")){
            firstNameErrorLabel.setText("Please enter first name");
            firstNameTextFields.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
        if(address.equals("")){
            addressErrorLabel.setText("Please enter address");
            addressTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
        if(phoneNr.equals("")){
            phoneNrErrorLabel.setText("Please enter phone number");
            phoneNrTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
        if(username.equals("")){
            usernameErrorLabel.setText("Please enter username");
            usernameTextFields.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
        if(password.equals("")){
            passwordErrorLabel.setText("Please enter password");
            passwordTextFields.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
        if(confirmPassword.equals("")){
            confirmPasswordErrorLabel.setText("Please confirm password");
            confirmPasswordTextFields.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
        if(!pharmacistRadioButton.isSelected() || !medicalPersonRadioButton.isSelected()){
            typeErrorLabel.setText("Please select pharmacist or medical person");
        }
        return false;
    }
    @FXML
    void createAccountOnAction(ActionEvent event) {
        String firstName = firstNameTextFields.getText();
        String lastName = lastNameTextFields.getText();
        String address = addressTextField.getText();
        String phoneNr = phoneNrTextField.getText();
        String username = usernameTextFields.getText();
        String password = passwordTextFields.getText();
        String confirmPassword = confirmPasswordTextFields.getText();
        String type = null;
        try{
            if(validateFields()){
                firstNameErrorLabel.setText("");
                firstNameTextFields.setStyle("-fx-border-color: none ; -fx-border-width: 1px ;");
                lastNameErrorLabel.setText("");
                lastNameTextFields.setStyle("-fx-border-color: none; -fx-border-width: 1px ;");
                addressErrorLabel.setText("");
                addressTextField.setStyle("-fx-border-color: none ; -fx-border-width: 1px ;");
                phoneNrErrorLabel.setText("");
                phoneNrTextField.setStyle("-fx-border-color: none ; -fx-border-width: 1px ;");
                usernameErrorLabel.setText("");
                usernameTextFields.setStyle("-fx-border-color: none ; -fx-border-width: 1px ;");
                passwordErrorLabel.setText("");
                passwordTextFields.setStyle("-fx-border-color: none ; -fx-border-width: 1px ;");
                confirmPasswordErrorLabel.setText("");
                confirmPasswordTextFields.setStyle("-fx-border-color: none ; -fx-border-width: 1px ;");
                if(pharmacistRadioButton.isSelected()){
                    type = "PHARMACIST";
                }
                if(medicalPersonRadioButton.isSelected()){
                    type = "MEDICALPERSON";
                }
                if(!confirmPassword.equals(password)){
                    messageLabel.setText("Password doesn't match");
                    return;
                }
                userService.saveUser(firstName,lastName,address,phoneNr,username,password,type);
                messageLabel.setText("Account created");
            }
        }catch (ValidationException ex){
            messageLabel.setText(ex.getMessage());
        }

//
    }

    public void setService(UserService userService) {
        this.userService = userService;
    }
}
