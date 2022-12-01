package com.cloud.fd.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestTemplateHttpHeaderInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        System.out.println("RestTemplateHttpHeaderInterceptor");
        HttpHeaders httpHeaders = httpRequest.getHeaders();
        httpHeaders.add("RestTemplateHeader", "Oh shit");
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
