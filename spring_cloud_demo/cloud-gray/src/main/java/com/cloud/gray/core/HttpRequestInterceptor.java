package com.cloud.gray.core;

import com.cloud.gray.Gray;
import com.cloud.gray.GrayAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
//@ConditionalOnBean(GrayAutoConfiguration.Marker.class)
public class HttpRequestInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Gray.class);

    public HttpRequestInterceptor() {
        LOGGER.info("initialize HttpRequestInterceptor complete");
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(httpRequest);

        String grayVersion = GrayPropagateInterceptor.VERSION.get();
        LOGGER.debug("http client gray version => {}",grayVersion);
        requestWrapper.getHeaders().add("version", grayVersion);

        return clientHttpRequestExecution.execute(requestWrapper, bytes);
    }
}
