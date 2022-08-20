package com.cloud.fd.client.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * feign的请求拦截器
 * */
@Component
public class HeaderInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //设置一个请求头
        System.out.println("headers=" + requestTemplate.headers());
        requestTemplate.header("cloud_token", UUID.randomUUID().toString());
    }
}
