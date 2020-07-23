package com.example.safestorage.services;

import com.example.safestorage.models.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceTestImpl implements UserService {

    private static final Map<String, User> users = new HashMap<>();
    @Override
    public User findUserByName(String username) {
        return (User) users.get( username );
    }

    @Override
    public void saveUser(User user) {
        users.put( user.getUsername(), user );
    }
}
