package com.dubbo.demo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@EnableDubbo
@SpringBootApplication
public class ProviderApp {

    public static void main(String[] args) throws Exception {
        //new EmbeddedZ
        SpringApplication.run(ProviderApp.class);
        System.out.println("dubbo service started.");
        new CountDownLatch(1).await();
    }
}
