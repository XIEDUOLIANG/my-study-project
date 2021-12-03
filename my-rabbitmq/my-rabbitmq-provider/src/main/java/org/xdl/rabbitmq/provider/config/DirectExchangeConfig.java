package org.xdl.rabbitmq.provider.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {

    @Bean
    public Queue queue() {
        return new Queue("defaultQueue",true);
    }
}
