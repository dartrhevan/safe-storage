package com.example.safestorage.services;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.User;
import org.bson.types.ObjectId;
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
    public void saveUser(User user) throws Exception {
        var entity = template.findOne( Query.query( Criteria.where( "username" ).is(user.getUsername()) ), User.class );
        if(entity == null)
            template.save( user );
        throw new Exception("Such user already exists");//TODO: replace with result
    }

    @Override
    public void removeUser(String id) {
        template.remove( Query.query( Criteria.where( "id" ).is( new ObjectId(id) )), User.class );//findAndRemove( Query.query( Criteria.where( "username" ).is( username )), Note.class );
    }

    @Override
    public String getIdByUsername(String username) {
        var user = template.findOne( Query.query( Criteria.where( "username" ).is(username) ), User.class );
        if(user == null)
            return null;
        return user.getId();
    }
}
