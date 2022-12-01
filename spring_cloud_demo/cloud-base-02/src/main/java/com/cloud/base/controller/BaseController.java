package com.cloud.base.controller;

import com.cloud.api.CloudApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(CloudApi.PREFIX)
public class BaseController implements CloudApi {

    public String hello() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println(request.getHeader("MyHeader"));//RestTemplateHeader
        System.out.println(request.getHeader("RestTemplateHeader"));//RestTemplateHeader
        return "Hello, Cloud-Base-02, cloudToken => " + request.getHeader("cloud_token");
    }

    @Override
    public String circuitBreakerOpen() {
        //throw new RuntimeException("circuitBreakerOpen");
        return "circuitBreakerOpen";
    }
}
