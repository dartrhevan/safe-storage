package com.example.safestorage.controllers;

import com.example.safestorage.models.User;
import com.example.safestorage.models.UserDTO;
import com.example.safestorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @PostMapping("/api/register")
    public void registration(UserDTO userData) {
        System.out.println(userData.getUsername());
        var user = new User(userData.getUsername(), encoder.encode( userData.getPassword() ));
        userService.saveUser( user );
    }

    @GetMapping("/api/get-login")
    public String get_login(Principal user) {
        return user.getName();
    }

    @PostMapping("/api/after-login")
    public String afterLogin(Principal user) {
        return "OK";// user.getName();
    }
}
