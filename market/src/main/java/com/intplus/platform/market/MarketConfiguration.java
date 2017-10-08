package com.intplus.platform.market;

import com.intplus.platform.http.JsonElementHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.AsyncRestTemplate;

@Configuration
public class MarketConfiguration {

    @Bean
    public AsyncRestOperations rest(){
        // To-do : Add requestFactory.
        AsyncRestTemplate restTemplate = new AsyncRestTemplate();
        restTemplate.getMessageConverters().add(0,new JsonElementHttpMessageConverter());
        return restTemplate;
    }
}
