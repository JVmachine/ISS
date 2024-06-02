package com.example.spital.controller;

import com.example.spital.model.Type;
import com.example.spital.service.MedicineService;
import com.example.spital.service.OrderService;
import com.example.spital.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private UserService userService;
    private MedicineService medicineService;
    private OrderService orderService;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameTextField;


    @FXML
    private Label passwordErrorTextField;

    @FXML
    private Label userErrorTextField;

    public LoginController() {
    }
    private void validateFields(String username,String password){
        if(username.equals("") && password.equals("")){
            userErrorTextField.setText("Please enter username");
            passwordErrorTextField.setText("Please enter password");
            usernameTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            passwordTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }
        if(username.equals("")){
            userErrorTextField.setText("Please enter username");
            usernameTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }
        if(password.equals("")){
            passwordErrorTextField.setText("Please enter password");
            passwordTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }
        if(!username.equals("") && !password.equals("")){
            loginMessageLabel.setText("");
            userErrorTextField.setText("");
            passwordErrorTextField.setText("");
            usernameTextField.setStyle("-fx-border-color: none ; -fx-border-width: 1px ;");
            passwordTextField.setStyle("-fx-border-color: none ; -fx-border-width: 1px ;");
        }
    }
    private void verifyUserType(String userType){
        if(Type.valueOf(userType) == Type.ADMIN){
            try {
                loginMessageLabel.setText("");
                userErrorTextField.setText("");
                passwordErrorTextField.setText("");
                FXMLLoader fxmlLoader = new FXMLLoader(AdminController.class.getResource("admin-window-view.fxml"));
                Parent parent = (Parent)fxmlLoader.load();

                AdminController adminController = fxmlLoader.getController();
                adminController.setService(medicineService);
                adminController.init();

                Stage stage = new Stage();
                Scene scene = new Scene(parent,869,426);
                stage.setScene(scene);
                stage.show();
//                ((Node)(event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(Type.valueOf(userType) == Type.MEDICALPERSON){
            try {
                loginMessageLabel.setText("");
                userErrorTextField.setText("");
                passwordErrorTextField.setText("");
                FXMLLoader fxmlLoader = new FXMLLoader(MedicalPersonController.class.getResource("medicalPerson-window-view.fxml"));
                Parent parent = (Parent)fxmlLoader.load();

                MedicalPersonController medicalPersonController = fxmlLoader.getController();
                medicalPersonController.setMedicineService(medicineService);
                medicalPersonController.setOrderService(orderService);
                medicalPersonController.initMedicineTable();
                Stage stage = new Stage();
                Scene scene = new Scene(parent,869,461);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
            e.printStackTrace();
        }

        }
        if(Type.valueOf(userType) == Type.PHARMACIST){
            try{
                loginMessageLabel.setText("");
                userErrorTextField.setText("");
                passwordErrorTextField.setText("");
                FXMLLoader fxmlLoader = new FXMLLoader(PharmacistController.class.getResource("pharmacist-window-view.fxml"));
                Parent parent = (Parent) fxmlLoader.load();

                PharmacistController pharmacistController = fxmlLoader.getController();
                pharmacistController.setOrderService(orderService);
                pharmacistController.initOrderTable();
                Stage stage = new Stage();
                Scene scene = new Scene(parent,666,461);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(userType == null){
            loginMessageLabel.setText("Invalid user");
        }
    }

    @FXML
    void loginButtonOnAction(ActionEvent event){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        validateFields(username,password);
        String userType = userService.getTypeByUsernamePassword(username,password);
        verifyUserType(userType);
    }

    @FXML
    void signUpButtonOnAction(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(SignUpController.class.getResource("signUp-view.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            SignUpController signUpController = fxmlLoader.getController();
            signUpController.setService(userService);
            Stage stage = new Stage();
            Scene scene = new Scene(parent,780,350);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setServices(UserService userService, MedicineService medicineService,OrderService orderService) {
        this.userService = userService;
        this.medicineService = medicineService;
        this.orderService = orderService;
    }
}
