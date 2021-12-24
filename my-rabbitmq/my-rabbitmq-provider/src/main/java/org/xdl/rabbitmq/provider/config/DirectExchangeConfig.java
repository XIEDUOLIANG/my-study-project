package org.xdl.rabbitmq.provider.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {

    public static final String defaultQueueKey = "key_one";

    @Autowired
    private Queue queue;

    @Autowired
    private DirectExchange directExchange;

    @Bean
    public Queue queue() {
        return new Queue("defaultQueue",true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("defaultDirectExchange",true,false);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue).to(directExchange).with(defaultQueueKey);
    }
}
