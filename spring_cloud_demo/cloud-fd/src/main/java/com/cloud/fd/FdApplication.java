package com.cloud.fd;

//import com.cloud.gray.GrayAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.List;

//开启feign client支持
@EnableFeignClients
//开启hystrix熔断器
//@EnableCircuitBreaker
@SpringCloudApplication
//开启hystrix dashboard
@EnableHystrixDashboard
//@Import(GrayAutoConfiguration.class)
public class FdApplication {
    public static void main(String[] args) {
        SpringApplication.run(FdApplication.class);
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    /**

     DESKTOP-V42AHNP.lan:cloud-fd:8080
     DESKTOP-V42AHNP.lan
     8080
     {management.port=8080}

     DESKTOP-V42AHNP.lan:cloud-base:8081
     DESKTOP-V42AHNP.lan
     8081
     {management.port=8081}

     DESKTOP-V42AHNP.lan:cloud-base:8082
     DESKTOP-V42AHNP.lan
     8082
     {management.port=8082}

     * */
    @PostConstruct
    public void init() {
        List<String> services =
                discoveryClient.getServices();

        services.stream().forEach(name -> {
            List<ServiceInstance> instanceList = discoveryClient.getInstances(name);
            for (ServiceInstance serviceInstance : instanceList) {
                System.out.println(serviceInstance.getInstanceId());
                System.out.println(serviceInstance.getHost());
                System.out.println(serviceInstance.getPort());
                System.out.println(serviceInstance.getMetadata());
                System.out.println();
            }
        });
    }
}
