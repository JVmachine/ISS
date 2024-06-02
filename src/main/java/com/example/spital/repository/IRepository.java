package com.example.spital.repository;

public interface IRepository<T,Tid>{
    void save(T elem);
    void update(T elem,Tid id);
    void delete(Tid id);
    T findOne(Tid id);
    Iterable<T> findAll();
}
