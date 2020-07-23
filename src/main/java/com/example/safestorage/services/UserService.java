package com.example.safestorage.services;

import com.example.safestorage.models.User;

public interface UserService {
    User findUserByName(String username);
    void saveUser(User user);
}
