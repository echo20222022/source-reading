package com.cloud.gray.core;

import com.cloud.gray.Gray;
import com.cloud.gray.GrayAutoConfiguration;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在Hystrix环境下向下传播version
 * */
/*@Component
@ConditionalOnBean(GrayAutoConfiguration.Marker.class)*/
public class GrayPropagateInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Gray.class);
    public static final HystrixRequestVariableDefault<String> VERSION = new HystrixRequestVariableDefault<>();

    public GrayPropagateInterceptor() {
        LOGGER.info("initialize GrayPropagateInterceptor complete");
    }

    public static void initHystrixRequestContext(String version) {
        LOGGER.info("hystrix context gray version => {}", version);
        if (!HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.initializeContext();
        }

        if (StringUtils.isNotEmpty(version)) {
            VERSION.set(version);
        } else {
            VERSION.set("");
        }
    }

    public static void shutdownHystrixRequestContext() {
        if (HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.getContextForCurrentThread().shutdown();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        GrayPropagateInterceptor.initHystrixRequestContext(request.getHeader("version"));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //请求结束后将version清除
        GrayPropagateInterceptor.shutdownHystrixRequestContext();
    }
}
