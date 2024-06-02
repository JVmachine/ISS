package com.example.spital.repository;

import com.example.spital.model.Medicine;
import com.example.spital.model.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class MedicineRepository implements IMedicineRepository {
    private Validator<Medicine> validator;
    private SessionFactory sessionFactory;

    public MedicineRepository(SessionFactory sessionFactory, Validator<Medicine> validator) {
        this.sessionFactory = sessionFactory;
        this.validator = validator;
    }

    @Override
    public void save(Medicine elem) {
        validator.validate(elem);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(elem);
                transaction.commit();
            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void update(Medicine elem, Integer id) {
        validator.validate(elem);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Medicine medicine = session.get(Medicine.class,id);
                medicine.setName(elem.getName());
                medicine.setExpirationDate(elem.getExpirationDate());
                medicine.setQuantity(elem.getQuantity());
                session.save(medicine);
                transaction.commit();
            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Medicine medicine = session.get(Medicine.class, id);
                session.delete(medicine);
                transaction.commit();
            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public Medicine findOne(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Medicine medicine = session.get(Medicine.class, id);
                transaction.commit();
                return medicine;
            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public Iterable<Medicine> findAll() {
            List<Medicine> medicineList = new ArrayList<>();
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    medicineList = session.createQuery("from com.example.spital.model.Medicine", Medicine.class).list();
                    transaction.commit();
                } catch (Exception ex) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                }
            }
            return medicineList;
        }

    @Override
    public Medicine getMedicineByName(String name) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                Query query = session.createQuery("from com.example.spital.model.Medicine where name = ?");
                query.setParameter(0,name);
                List result = query.getResultList();
                transaction.commit();
                if (result.size() == 0) return null;
                Medicine medicine = (Medicine) result.get(0);
                return medicine;
            }
            catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public void updateQuantity(Medicine elem,Integer id) {
        validator.validate(elem);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Medicine medicine = session.get(Medicine.class,id);
                medicine.setQuantity(elem.getQuantity());
                session.save(medicine);
                transaction.commit();
            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
