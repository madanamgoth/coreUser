package com.example.CoreUser.RabbitMq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE = "transaction_queue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true, false, false, Map.of("x-message-ttl", 60000));
    }
}
