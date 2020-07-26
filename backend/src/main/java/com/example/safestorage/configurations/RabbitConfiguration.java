package com.example.safestorage.configurations;

//import org.apache.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableRabbit
@Configuration
//@SuppressWarnings("SpringConfigurationProxyMethods")
public class RabbitConfiguration {
    //Logger logger = Logger.getLogger(RabbitConfiguration.class);

    //настраиваем соединение с RabbitMQ
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("192.168.99.100");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange("exchange-example-4");
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange getDirectExchange(){
        return new DirectExchange("exchange-example-4");
    }

    @Bean
    public Queue getEncodingQueue() {
        return new Queue("encodingQueue");
    }


    @Bean
    public Binding getEncodingQueueBinding() {
        return BindingBuilder.bind(getEncodingQueue()).to( getDirectExchange()).with( "encoding");
    }

    @Bean
    public Queue getNoteQueue() {
        return new Queue("noteQueue");
    }

    @Bean
    public Binding getNoteQueueBinding() {
        return BindingBuilder.bind(getNoteQueue()).to( getDirectExchange()).with( "note");
    }

    @Bean
    public Queue getUserQueue() {
        return new Queue("userQueue");
    }

    @Bean
    public Binding getUserQueueBinding() {
        return BindingBuilder.bind(getUserQueue()).to( getDirectExchange()).with( "getIdByUsername");
    }

}