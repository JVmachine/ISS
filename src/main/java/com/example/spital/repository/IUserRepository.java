package com.example.spital.repository;

import com.example.spital.model.Type;
import com.example.spital.model.User;

public interface IUserRepository extends IRepository<User,Integer>{
    String getTypeOfUser(String username,String password);
}
