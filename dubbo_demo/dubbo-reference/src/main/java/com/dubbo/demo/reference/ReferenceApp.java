package com.dubbo.demo.reference;

import com.dubbo.demo.api.IHelloService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDubbo
@SpringBootApplication
@RestController
public class ReferenceApp {

    @DubboReference(check = false, mock = "true", group="demo", version = "1.0")
    private IHelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        return helloService.sayHello("zhangsan");
    }

    public static void main(String[] args) {
        SpringApplication.run(ReferenceApp.class);
    }
}
