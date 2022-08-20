package com.cloud.gateway.fallback;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.common.util.concurrent.RateLimiter;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalFallback implements FallbackProvider {

    private static Logger LOGGER = LoggerFactory.getLogger(GlobalFallback.class);


    //指定对哪个服务进行降级
    @Override
    public String getRoute() {
        //对所有服务降级
        return "*";
    }

    //返回一个降级后的响应结果
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        //记录异常日志
        //cause.printStackTrace();
        LOGGER.error(ExceptionUtils.getFullStackTrace(cause));

        //返回null，标识不给出降级结果，即异常后不做任何处理
        //return null;

        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                Map<String, Object> fallbackResult = new HashMap<>();
                fallbackResult.put("code", 2000);
                fallbackResult.put("message", "无数据");
                return new ByteArrayInputStream(fallbackResult.toString().getBytes(StandardCharsets.UTF_8));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return httpHeaders;
            }
        };
    }
}
