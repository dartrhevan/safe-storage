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
                new CachingConnectionFactory("192.168.99.100");//extract
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
        return new DirectExchange("safe-storage-exchange");
    }

    @Bean
    public Queue getEncodeQueue() {
        return new Queue("encodeQueue");
    }


    @Bean
    public Binding getEncodeQueueBinding() {
        return BindingBuilder.bind(getEncodeQueue()).to( getDirectExchange()).with( "encode");
    }

    @Bean
    public Queue getDecodeQueue() {
        return new Queue("decodeQueue");
    }


    @Bean
    public Binding getDecodeQueueBinding() {
        return BindingBuilder.bind(getDecodeQueue()).to( getDirectExchange()).with( "decode");
    }

    @Bean
    public Queue getSaveOrUpdateNoteQueue() {
        return new Queue("saveOrUpdateNoteQueue");
    }

    @Bean
    public Binding getSaveOrUpdateNoteQueueBinding() {
        return BindingBuilder.bind(getSaveOrUpdateNoteQueue()).to( getDirectExchange()).with( "saveOrUpdateNote");
    }

    @Bean
    public Queue getUserQueue() {
        return new Queue("userQueue");
    }

    @Bean
    public Binding getUserQueueBinding() {
        return BindingBuilder.bind(getUserQueue()).to( getDirectExchange()).with( "getIdByUsername");
    }

    @Bean
    public Queue getRemoveNoteQueue() {
        return new Queue("removeNoteQueue");
    }

    @Bean
    public Binding getRemoveNoteQueueBinding() {
        return BindingBuilder.bind(getRemoveNoteQueue()).to( getDirectExchange()).with( "removeNote");
    }

    @Bean
    public Queue getNoteQueue() {
        return new Queue("getNoteQueue");
    }

    @Bean
    public Binding getNoteQueueBinding() {
        return BindingBuilder.bind(getNoteQueue()).to( getDirectExchange()).with( "getNote");
    }
}