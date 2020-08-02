package com.example.safestorage.configurations;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:application.properties")
@EnableRabbit
@Configuration
public class RabbitConfiguration {
    @Autowired
    public RabbitConfiguration(@Value( "${rabbitHost}" ) String rabbitHost) {
        this.rabbitHost = rabbitHost;
    }

    //настраиваем соединение с RabbitMQ
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(rabbitHost);
        return connectionFactory;
    }

    private final String rabbitHost;

    private static final String EXCHANGE_NAME = "safe-storage-exchange";

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange(EXCHANGE_NAME);
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange getDirectExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue getEncodeQueue() {
        return new Queue(QueuesNames.ENCODE_QUEUE);
    }


    @Bean
    public Binding getEncodeQueueBinding() {
        return BindingBuilder.bind(getEncodeQueue()).to( getDirectExchange()).with( QueuesRoutes.ENCODE );
    }

    @Bean
    public Queue getDecodeQueue() {
        return new Queue(QueuesNames.DECODE_QUEUE);
    }


    @Bean
    public Binding getDecodeQueueBinding() {
        return BindingBuilder.bind(getDecodeQueue()).to( getDirectExchange()).with( QueuesRoutes.DECODE );
    }

    @Bean
    public Queue getSaveOrUpdateNoteQueue() {
        return new Queue(QueuesNames.SAVE_OR_UPDATE_QUEUE);
    }

    @Bean
    public Binding getSaveOrUpdateNoteQueueBinding() {
        return BindingBuilder.bind(getSaveOrUpdateNoteQueue()).to( getDirectExchange()).with( QueuesRoutes.SAVE_OR_UPDATE );
    }

    @Bean
    public Queue getUserQueue() {
        return new Queue(QueuesNames.GET_ID_BY_USERNAME_QUEUE );
    }

    @Bean
    public Binding getUserQueueBinding() {
        return BindingBuilder.bind(getUserQueue()).to( getDirectExchange()).with( QueuesRoutes.GET_ID_BY_USERNAME );
    }

    @Bean
    public Queue getRemoveNoteQueue() {
        return new Queue(QueuesNames.REMOVE_NOTE_QUEUE);
    }

    @Bean
    public Binding getRemoveNoteQueueBinding() {
        return BindingBuilder.bind(getRemoveNoteQueue()).to( getDirectExchange()).with( QueuesRoutes.REMOVE_NOTE );
    }

    @Bean
    public Queue getNoteQueue() {
        return new Queue(QueuesNames.GET_NOTE_QUEUE);
    }

    @Bean
    public Binding getNoteQueueBinding() {
        return BindingBuilder.bind(getNoteQueue()).to( getDirectExchange()).with( QueuesRoutes.GET_NOTE );
    }
}