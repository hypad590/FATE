package com.hypad.App.service;

import com.hypad.App.model.UserEntity;
import com.hypad.App.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> getAllUsers(){
        return repository.findAll();
    }
}
