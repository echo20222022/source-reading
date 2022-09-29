package com.cloud.gateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * guava实现的令牌桶限流
 * */
//@Component
public class RateLimitFilter extends ZuulFilter {

    //每秒钟产生2个令牌
    private static final RateLimiter limiter = RateLimiter.create(1);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -10;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        //尝试获取令牌，如果获取失败，则直接返回结果
        if (!limiter.tryAcquire()) {
            System.out.println("限流检测未通过...");
            //不向后传播了
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(429);
            context.setResponseDataStream(new ByteArrayInputStream("rate limit error".getBytes(StandardCharsets.UTF_8)));
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("限流通过...");
        return null;
    }
}
