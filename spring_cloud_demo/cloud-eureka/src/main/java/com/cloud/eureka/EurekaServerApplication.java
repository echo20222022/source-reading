package com.cloud.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.eureka.server.EurekaServerAutoConfiguration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    //EurekaServerAutoConfiguration
    //EurekaClientAutoConfiguration
    //EurekaDiscoveryClientConfiguration
    //EurekaDiscoveryClientConfiguration
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class);
    }
}
