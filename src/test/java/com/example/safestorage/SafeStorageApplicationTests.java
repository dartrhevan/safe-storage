package com.example.safestorage;

import com.example.safestorage.models.User;
import com.example.safestorage.services.UserService;
import com.example.safestorage.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SafeStorageApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserService userService;

    @Test
    void mongoSimpleTest() {
        assert userService.getClass().equals( UserServiceImpl.class );
        var name = "dsad";
        var pass = "ewf";
        var user = new User(name, pass);
        userService.saveUser( user );
        var entity = userService.findUserByName( name );
        assert name.equals( entity.getUsername() );
        assert pass.equals( entity.getPasswordHash() );
        // TODO: rewrite or add delete
    }

}
