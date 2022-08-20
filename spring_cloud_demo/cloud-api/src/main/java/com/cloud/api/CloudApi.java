package com.cloud.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.dc.pr.PRError;

//@RequestMapping(CloudApi.PREFIX)
public interface CloudApi {

    String PREFIX = "/cloud/api";
    @GetMapping("/hello")
    String hello();

    @GetMapping("/circuitBreakerOpen")
    String circuitBreakerOpen();

    //默认降级实现
    class DefaultFallback implements CloudApi {
        @Override
        public String hello() {
            return "default fallback";
        }

        @Override
        public String circuitBreakerOpen() {
            return "circuitBreakerOpen fallback";
        }
    }
}
