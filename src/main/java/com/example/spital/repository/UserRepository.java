package com.example.spital.repository;

import com.example.spital.model.Type;
import com.example.spital.model.User;
import com.example.spital.model.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class UserRepository implements IUserRepository{
    private JdbcUtils jdbcUtils;
    private Validator<User> validator;
    SessionFactory sessionFactory;


    public UserRepository(SessionFactory sessionFactory,Validator<User> validator) {
        this.sessionFactory = sessionFactory;
        this.validator = validator;
    }

    @Override
    public void save(User elem) {
        validator.validate(elem);
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                session.save(elem);
                transaction.commit();
            }catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

    }

    @Override
    public void update(User elem, Integer id) {
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public User findOne(Integer id) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public String getTypeOfUser(String username, String password) {
        try(Session session = (Session) sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from com.example.spital.model.User where username = ? and password = ?");
            query.setParameter(0,username);
            query.setParameter(1,password);
            List result = query.getResultList();
            session.getTransaction().commit();
            if(result.size() == 0) return null;
            User user = (User) result.get(0);
            return user.getType();

        }

    }
}
