package com.cloud.gray;

import com.cloud.gray.core.FeignRequestInterceptor;
import com.cloud.gray.core.GrayPropagateInterceptor;
import com.cloud.gray.core.HttpRequestInterceptor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class GrayAutoConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(httpRequestInterceptor());
        return restTemplate;
    }

    @Bean
    public FeignRequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }

    @Bean
    public GrayPropagateInterceptor grayPropagateInterceptor() {
        return new GrayPropagateInterceptor();
    }

    @Bean
    public HttpRequestInterceptor httpRequestInterceptor() {
        return new HttpRequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(grayPropagateInterceptor());
    }
}
