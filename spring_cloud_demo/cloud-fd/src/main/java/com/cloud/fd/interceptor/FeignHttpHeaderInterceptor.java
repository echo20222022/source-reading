package com.cloud.fd.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignHttpHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header("MyHeader", "OMGod");
    }

}
