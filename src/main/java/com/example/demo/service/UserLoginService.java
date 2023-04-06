package com.example.demo.service;

import com.example.demo.entity.UserLogin;
import com.example.demo.repository.UserLoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserLoginService {
    @Autowired
    private UserLoginRepo loginRepository;

    public void updateLoginTime(UserLogin login) {
        login.setLoginTime(LocalDateTime.now());
        loginRepository.save(login);
    }

    public void updateLogoutTime(UserLogin login) {
        login.setLogoutTime(LocalDateTime.now());
        loginRepository.save(login);
    }
}