package com.example.safestorage.configurations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;

@PropertySource(value="classpath:application.properties",  ignoreResourceNotFound=true)
@Configuration
public class MongoConfiguration {

    private final String mongoUrl;
    private final String mongoDbName;

    @Autowired
    public MongoConfiguration(@Value( "${mongoUrl}" ) String mongoUrl, @Value( "${mongoDbName}" ) String mongoDbName) {
        this.mongoUrl = mongoUrl;
        this.mongoDbName = mongoDbName;
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUrl);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), mongoDbName);
    }
}
