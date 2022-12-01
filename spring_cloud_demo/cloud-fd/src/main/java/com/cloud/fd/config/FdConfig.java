package com.cloud.fd.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.cloud.fd.interceptor.FeignHttpHeaderInterceptor;
import com.cloud.fd.interceptor.RestTemplateHttpHeaderInterceptor;
import com.cloud.fd.lb.FdLoadBalanceRule;
import com.netflix.loadbalancer.IRule;
import feign.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootConfiguration
public class FdConfig {

    @Autowired
    private RestTemplateHttpHeaderInterceptor httpHeaderInterceptor;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(httpHeaderInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    /*@Bean
    public IRule loadBalanceRule() {
        List<Integer> excludePorts = new ArrayList<>();
        //excludePorts.add(8081);
        return new FdLoadBalanceRule(excludePorts);
    }*/

    @Bean
    public FeignHttpHeaderInterceptor httpHeaderInterceptor() {
        return new FeignHttpHeaderInterceptor();
    }



    @Bean
    public Logger.Level feignLevel() {
        return Logger.Level.BASIC;
    }

    //配置sentinel aspejctj
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
