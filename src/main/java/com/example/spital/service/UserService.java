package com.example.spital.service;

import com.example.spital.model.Type;
import com.example.spital.model.User;
import com.example.spital.repository.IUserRepository;

public class UserService {
    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public String getTypeByUsernamePassword(String username,String password){
        return userRepository.getTypeOfUser(username,password);
    }
    public void saveUser(String firstName,String lastName,String address,String phoneNr,String username,String password,String type){
        User user = new User(firstName,lastName,address,phoneNr,username,password,type);
        userRepository.save(user);

    }
}
