package com.cloud.fd.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.cloud.fd.client.CloudApiFeign;
import com.cloud.fd.client.UserApiFeign;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FdController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CloudApiFeign cloudApi;
    @Autowired
    private UserApiFeign userApiFeign;

    //方法级的降级
    //@HystrixCommand(fallbackMethod = "helloFallback")
    @RequestMapping("/hello")
    public String hello(HttpServletRequest request) {
        //return restTemplate.getForEntity("http://cloud-base/base/hello", String.class).getBody();

        //获取zuul中的敏感头
        //String token = request.getHeader("token");   //有
        //String setCookie = request.getHeader("Set-Cookie"); //没有
        //String cookie = request.getHeader("Cookie"); //没有

        //System.out.println("token=" + token);
        //System.out.println("set-cookie=" + setCookie);
        //System.out.println("cookie="+cookie);

        //FeignClient
        System.out.println(request.getHeader("Host"));


        return cloudApi.hello();
    }

    @RequestMapping("/getUserNameById")
    public String getUserNameById(@RequestParam("id") Long id) {
        //return userApiFeign.getUserName(id);
        return null;
    }

    @HystrixCommand(
            threadPoolKey = "circuitBreakerOpenThreadPool",
            commandProperties = {
                    //时间窗大小
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "3000"),
                    //最小请求次数
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                    //失败率
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    //活动窗口大小
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "3000"),
            },
            fallbackMethod = "circuitBreakerFallback"
    )
    @RequestMapping("/circuitBreakerOpen")
    public String circuitBreakerOpen() {
        //return cloudApi.circuitBreakerOpen();
        return restTemplate.getForObject("http://cloud-base//cloud/api/circuitBreakerOpen", String.class);
    }

    public String circuitBreakerFallback() {
        //HystrixCommandProperties
        return "circuitBreakerOpen's method fallback";
    }

//    public String helloFallback() {
//        return "hystrix result";
//    }


    /**
     * sentinal
     * */
    @GetMapping("/helloSentinel")
    public String helloSentinel() {
       //通过异常定义资源
        /*try (Entry entry = SphU.entry("HelloSentinel")) {
            return "Sentinel 大爷你好!" + System.currentTimeMillis();
        }catch (Exception exception) {
            exception.printStackTrace();
            return "系统繁忙";
        }*/

        //true/false
        if (SphO.entry("HelloSentinel2")) {
            try {
                return "Sentinel2 大爷你好!" + System.currentTimeMillis();
            }finally {
                SphO.exit();
            }
        } else {
            return "系统繁忙";
        }
    }

    //这里会生成两个资源：HelloSentinel3和/helloSentinel3
    @SentinelResource(value = "HelloSentinel3", blockHandler = "helloSentinel3BlockHandler")
    @GetMapping("/helloSentinel3")
    public String helloSentinel3() {
        return "Sentinel3 大爷你好!" + System.currentTimeMillis();
    }

    public String helloSentinel3BlockHandler(BlockException ex) {
        ex.printStackTrace();
        return "系统繁忙";
    }

    @GetMapping("/pathValueUrl/{id}")
    public String pathValueUrl(@PathVariable("id") Long id) {
        return "id => " + id;
    }


    //定义限流规则，后面会在控制台配置
    /*@PostConstruct
    public void initFlowRule() {
        List<FlowRule> ruleList = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloSentinel"); //设置资源，和SphU.entry("HelloSentinel"))匹配
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS); //限流类型
        rule.setCount(2);  //qps上限
        ruleList.add(rule);
        FlowRuleManager.loadRules(ruleList);
    }*/
}
