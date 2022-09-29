package com.cloud.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@SpringBootApplication
//@EnableDiscoveryClient
//@SpringCloudApplication
@SpringBootApplication
//@EnableDiscoveryClient
public class BaseApplication01 {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication01.class);
    }
}
