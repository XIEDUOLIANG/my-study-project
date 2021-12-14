package org.xdl.crawler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean(value = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
