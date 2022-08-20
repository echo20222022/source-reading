package com.cloud.base;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

//@SpringBootApplication
//@EnableDiscoveryClient
@SpringCloudApplication
public class BaseApplication02 {

    public static void main(String[] args) {
        //EurekaClientConfigServerAutoConfiguration
        //EurekaDiscoveryClientConfigServiceAutoConfiguration
        //EurekaClientAutoConfiguration
        //EurekaDiscoveryClientConfiguration
        //EurekaDiscoveryClientConfiguration
        //AutoServiceRegistrationConfiguration

        //org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
        /*org.springframework.cloud.netflix.eureka.config.EurekaClientConfigServerAutoConfiguration,\
        org.springframework.cloud.netflix.eureka.config.EurekaDiscoveryClientConfigServiceAutoConfiguration,\
        org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration,\
        org.springframework.cloud.netflix.ribbon.eureka.RibbonEurekaAutoConfiguration,\
        org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration

        org.springframework.cloud.bootstrap.BootstrapConfiguration=\
        org.springframework.cloud.netflix.eureka.config.EurekaDiscoveryClientConfigServiceBootstrapConfiguration
*/


        SpringApplication.run(BaseApplication02.class);
    }
}
