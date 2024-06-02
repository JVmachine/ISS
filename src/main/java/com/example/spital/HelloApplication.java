package com.example.spital;

import com.example.spital.controller.LoginController;
import com.example.spital.model.validators.MedicineValidator;
import com.example.spital.model.validators.OrderValidator;
import com.example.spital.model.validators.UserValidator;
import com.example.spital.repository.MedicineRepository;
import com.example.spital.repository.OrderRepository;
import com.example.spital.repository.UserRepository;
import com.example.spital.service.MedicineService;
import com.example.spital.service.OrderService;
import com.example.spital.service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {
    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }
    @Override
    public void start(Stage stage) throws IOException {
        initialize();
        FXMLLoader root = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Parent myPane = (Parent) root.load();

        Properties properties = new Properties();
        try{
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config" + e);
        }
        UserRepository userRepository = new UserRepository(sessionFactory,new UserValidator());
        UserService userService = new UserService(userRepository);

        MedicineRepository medicineRepository = new MedicineRepository(sessionFactory,new MedicineValidator());
        MedicineService medicineService = new MedicineService(medicineRepository);

        OrderRepository orderRepository = new OrderRepository(new OrderValidator(),properties);
        OrderService orderService = new OrderService(orderRepository);
        LoginController loginController = root.getController();
        loginController.setServices(userService,medicineService,orderService);


        Scene scene = new Scene(myPane,520,400);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}