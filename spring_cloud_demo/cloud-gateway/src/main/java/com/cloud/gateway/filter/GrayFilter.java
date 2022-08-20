package com.cloud.gateway.filter;

import com.cloud.gateway.config.GrayRuleConfigProp;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 灰度.
 * */
//@Component
public class GrayFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrayFilter.class);

    @Autowired
    private GrayRuleConfigProp grayRuleConfigProp;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //从请求参数中拿到用户数据
        RequestContext ctx = RequestContext.getCurrentContext();
        String user = ctx.getRequest().getParameter("user");

        //判断当前用户释放在灰度用户范围内
        boolean isGrayUser = grayRuleConfigProp.getUsers().contains(user);
        String grayVersion = isGrayUser ? grayRuleConfigProp.getVersion() : null;
        LOGGER.info("user => {}, isGrayUser => {}, grayVersion => {}", user, isGrayUser, grayVersion);

        if (isGrayUser) {
            //用于zuul本身调用下游服务用，避免Hystrix线程隔离时，变量丢失
            //GrayPropagateInterceptor.initHystrixRequestContext(grayVersion);
            //用于向后传递version
            ctx.addZuulRequestHeader("version", grayVersion);
        }
        return null;
    }
}
