package com.example.safestorage.messaging;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


//@EnableRabbit //нужно для активации обработки аннотаций @RabbitListener
@Component
public class Listeners {

    @RabbitListener(queues = "queue4")
    public void processQueue1(String message) {
        System.out.println("From queue4: " + message);
        //logger.info("Received from queue 1: " + message);
    }

    @RabbitListener(queues = "queue2")
    public void processQueue2(String message) {
        System.out.println("From queue2: " + message);
        //logger.info("Received from queue 1: " + message);
    }
/*
    @RabbitListener(queues = "queue3")
    public int worker1(MyMessage message) throws InterruptedException {
        //Thread.sleep(600); //эмулируем полезную работу
        return message.getNumber() + 8;//"received on worker : " + message;
    }*/
}
