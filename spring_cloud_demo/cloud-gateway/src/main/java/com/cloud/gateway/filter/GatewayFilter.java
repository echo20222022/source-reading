package com.cloud.gateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.w3c.dom.ls.LSResourceResolver;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
public class GatewayFilter extends ZuulFilter {

    //令牌桶
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(2);

    /**
     * pre
     * route
     * post
     * error
     * */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器排序
     * 越小 优先级越高
     *r
     * FilterConstants 定义了系统内置Filter的一些排序值
     * 自定义Filter时可以参考
     * */
    @Override
    public int filterOrder() {
        return 10000;
    }

    /**
     * 指定是否需要执行该过滤器的 run
     * */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        boolean debugRouting = ctx.debugRouting();
        boolean debugRequest = ctx.debugRequest();
        System.out.println(debugRouting);
        System.out.println(debugRequest);
        RequestContext requestContext = RequestContext.getCurrentContext();
        //如超载了
        /*if (!RATE_LIMITER.tryAcquire()) {
            //中断服务路由
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }*/

        //判断服务路由是否已经被中断
        /*if (requestContext.sendZuulResponse()) {

        }*/
        Enumeration<Servlet> servlets = requestContext.getRequest().getServletContext().getServlets();
        HttpServletMapping httpServletMapping = requestContext.getRequest().getHttpServletMapping();
        String servletName = httpServletMapping.getServletName();
        System.out.println(httpServletMapping.getMatchValue());
        System.out.println(httpServletMapping.getPattern());
        System.out.println(httpServletMapping.getMappingMatch());
        System.out.println("servletName -> " + servletName);
        System.out.println("servlet more -> " + servlets.hasMoreElements());
        while (servlets.hasMoreElements()) {
            Servlet servlet = servlets.nextElement();
            String name = servlet.getClass().getName();
            System.out.println("servlet name -> " + name);
        }



        return true;
    }

    /**
     * 过滤器的核心方法
     *
     * 过滤逻辑一般都写在该方法内
     * */
    @Override
    public Object run() throws ZuulException {
       HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        RequestContext.getCurrentContext().getResponse();
        RequestContext.getCurrentContext().getResponseBody();
        //System.out.println("GatewayFilter 过滤通过");
        //request.getServletContext();

        return null;
    }
}
