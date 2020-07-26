package com.example.safestorage.services;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Primary
@Service
public class UserServiceImpl implements UserService {

    private final MongoTemplate template;

    @Autowired
    public UserServiceImpl(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public User findUserByName(String username) {
        return template.findOne(  Query.query( Criteria.where("username").is(username)), User.class );
    }

    @Override
    public void saveUser(User user) {
        template.save( user );
    }

    @Override
    public void removeUser(String username) {
        template.findAndRemove( Query.query( Criteria.where( "username" ).is( username )), Note.class );
    }
}
