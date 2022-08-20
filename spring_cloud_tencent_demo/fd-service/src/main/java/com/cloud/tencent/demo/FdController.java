package com.cloud.tencent.demo;

import com.cloud.tencent.demo.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FdController {

    @Autowired
    private BaseService baseService;

    @GetMapping("/hello")
    public String hello(@RequestParam("key") String key) {
        return baseService.hello(key);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }
}
