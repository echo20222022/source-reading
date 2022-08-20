package com.cloud.tencent.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "base-service",
        fallback = BaseService.BaseServiceFallback.class)
public interface BaseService {

    @GetMapping("/base/hello")
    String hello(@RequestParam("key") String key);

    class BaseServiceFallback implements BaseService{

        @Override
        public String hello(String key) {
            return "fallback-ok";
        }
    }
}
