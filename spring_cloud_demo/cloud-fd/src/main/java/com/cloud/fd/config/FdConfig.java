package com.cloud.fd.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.cloud.fd.lb.FdLoadBalanceRule;
import com.netflix.loadbalancer.IRule;
import feign.Logger;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootConfiguration
public class FdConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*@Bean
    public IRule loadBalanceRule() {
        List<Integer> excludePorts = new ArrayList<>();
        //excludePorts.add(8081);
        return new FdLoadBalanceRule(excludePorts);
    }*/

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
