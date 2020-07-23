package com.example.safestorage.configurations;

//import org.apache.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@EnableRabbit
//@Configuration
@SuppressWarnings("SpringConfigurationProxyMethods")
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
    public DirectExchange directExchange(){
        return new DirectExchange("exchange-example-4");
    }

    @Bean
    public Queue myQueue4() {
        return new Queue("queue4");
    }

    @Bean
    public Queue myQueue2() {
        return new Queue("queue2");
    }

    @Bean
    public Queue myQueue3() {
        return new Queue("queue3");
    }

    @Bean
    public Binding Binding3(){
        return BindingBuilder.bind(myQueue3()).to(directExchange()).with("route3");
    }

    @Bean
    public Binding Binding4(){
        return BindingBuilder.bind(myQueue4()).to(directExchange()).with("route4");
    }

    @Bean
    public Binding Binding2(){
        return BindingBuilder.bind(myQueue2()).to(directExchange()).with("route2");
    }
    /*
    //объявляем контейнер, который будет содержать листенер для сообщений
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer1() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames("queue1");
        container.setMessageListener(new MessageListener() {
            //тут ловим сообщения из queue1
            public void onMessage(Message message) {
                System.out.println(new String(message.getBody()));
                //logger.info("received from queue1 : " + new String(message.getBody()));
            }
        });
        return container;
    }*/
}