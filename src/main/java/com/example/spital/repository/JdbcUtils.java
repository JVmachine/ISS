package com.example.spital.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private Properties properties;
    private Connection instance = null;

    public JdbcUtils(Properties properties) {
        this.properties = properties;
    }

    private Connection getNewConnection(){
        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.user");
        String pass = properties.getProperty("jdbc.pass");
        Connection connection = null;
        try{
            if(user != null && pass != null)
                connection = DriverManager.getConnection(url,user,pass);
            else
                connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error getting connection " + e);
        }
        return connection;
    }

    public Connection getConnection(){
        try{
            if(instance == null || instance.isClosed())
                instance = getNewConnection();
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return instance;
    }
}
