package com.cloud.tencent.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FdApplication {
    public static void main(String[] args) {
        SpringApplication.run(FdApplication.class);
    }
}
