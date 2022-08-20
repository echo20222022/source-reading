package com.cloud.tencent.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    public BaseController() {
        System.out.println("...");
    }

    @GetMapping("/base/hello")
    public String hello(@RequestParam("key") String key) {
        return "Hello," + key;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }
}
