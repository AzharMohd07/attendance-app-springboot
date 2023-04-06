package com.example.demo.controllers;


import com.example.demo.entity.User;
import com.example.demo.entity.UserLogin;
import com.example.demo.repository.UserLoginRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserLoginService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.time.LocalDateTime;

@Controller
public class UserController {
    @Autowired
    private UserLoginRepo loginRepo;

    @Autowired(required = true)
    private UserService service;

    @Autowired(required = true)
    private UserRepo repo;

    @Autowired
    private UserLoginService loginService;



    @GetMapping("/")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "register";
    }


    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("user") User user) {
        String result = null;
        if (user.getPassword().equals(user.getCpassword())) {
            try {
                service.registerUser(user);
                result = "login";
            } catch (Exception e) {
                result = "error";

            }
        }

        return "login";
    }

    @GetMapping("/siginRequest")
    public String login(Model model) {
        UserLogin user = new UserLogin();
        model.addAttribute("login", user);
        return "signIn";
    }


    @PostMapping("/signIn")
    public String loginUser(@ModelAttribute("user") UserLogin user) {
        Optional<User> userdata = repo.findById(user.getUsername());
        if (userdata.isPresent() && userdata.get().getUsername().equals(user.getUsername()) && (userdata.get().getPassword().equals(user.getPassword()))) {

            loginRepo.save(user);
            return "signIn";
        }
        return "error";
}
    @PostMapping("/signIntime")
    public String loginUser(@ModelAttribute("user") UserLogin user, HttpSession session) {
        Optional<User> userData = repo.findById(user.getUsername());
        if (userData.isPresent() && userData.get().getPassword().equals(user.getPassword())) {
            UserLogin userLogin = new UserLogin();
            userLogin.setUsername(user.getUsername());
            userLogin.setPassword(user.getPassword());
            userLogin.setLoginTime(LocalDateTime.now());
            loginRepo.save(userLogin);
            session.setAttribute("userLogin", userLogin);
            return "signIntime";
        } else {
            return "error";
        }
    }



    @GetMapping("/report")
    public String getUserData(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // user is not logged in, redirect to login page
            return "login";
        }
        UserLogin user = loginRepo.findById(userId).orElse(null);
        if (user == null) {
            // user does not exist in database, show error page
            return "error";
        }
        model.addAttribute("user", user);
        return "report";

    }








    @PostMapping("/signOuttime")
    public String signOut(@ModelAttribute("user") UserLogin user) {
        Optional<User> userdata = repo.findById(user.getUsername());
        if (userdata.isPresent() && userdata.get().getPassword().equals(user.getPassword())) {
            UserLogin loginData = loginRepo.findByUsername(user.getUsername());
            if (loginData != null) {
                loginData.setLogoutTime(LocalDateTime.now());
                loginRepo.save(loginData);
                return "signOuttime";
            }
        }
        return "error";
    }

    @GetMapping("/loginRequest")
    public String loginTime(Model model) {
        UserLogin user = new UserLogin();
        model.addAttribute("login", user);
        return "signInTimestamp";
    }


}