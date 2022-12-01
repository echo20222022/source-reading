package com.cloud.base.controller;

import com.cloud.api.CloudApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(CloudApi.PREFIX)
public class BaseController implements CloudApi {

    @Autowired
    private DiscoveryClient discoveryClient;

    public String hello() {
        //通过request对象拿到 通过RequetInterceptor设置的header
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println(request.getHeader("MyHeader"));
        System.out.println(request.getHeader("RestTemplateHeader"));
        return "Hello, Cloud-Base-01, token => " + request.getHeader("cloud_token");
    }

    @GetMapping("/discovery")
    public String printDiscoveryClient() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(service);
            for (ServiceInstance serviceInstance : serviceInstances) {
                String instanceId = serviceInstance.getInstanceId();
                String host = serviceInstance.getHost();
                int port = serviceInstance.getPort();
                String scheme = serviceInstance.getScheme();
                URI uri = serviceInstance.getUri();
                Map<String, String> metadata = serviceInstance.getMetadata();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("instance id = " + instanceId +" ");
                stringBuilder.append("host = " + host + " ");
                stringBuilder.append("port = " + port + " ");
                stringBuilder.append("scheme = " + scheme + " ");
                stringBuilder.append("uri = " + uri + " ");
                stringBuilder.append("metadata = " + metadata + " ");
                System.out.println(stringBuilder.toString());
            }
        }
        return "ok";
    }

    @Override
    public String circuitBreakerOpen() {
        //throw new RuntimeException("circuitBreakerOpen");
        return "circuitBreakerOpen";

    }
}
