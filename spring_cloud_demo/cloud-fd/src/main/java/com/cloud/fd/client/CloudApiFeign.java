package com.cloud.fd.client;

import com.cloud.api.CloudApi;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.IPing;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(
        value = "cloud-base",
        contextId = "cloudApiFeign",
        path = CloudApi.PREFIX,
        //,
        //fallback = CloudApi.DefaultFallback.class
        fallbackFactory = CloudApiFeign.DefaultFallbackFactory.class
        //url = "${cloudApi}"
)
public interface CloudApiFeign extends CloudApi {

    @Component
    class DefaultFallbackFactory implements FallbackFactory<CloudApi> {

        @Override
        public CloudApi create(Throwable throwable) {
            //记录异常
            throwable.printStackTrace();
            //RibbonAutoConfiguration
            //EurekaClientAutoConfiguration
            //IPing
            //DynamicServerListLoadBalancer
            /**

             * */
            //返回降级对象
            return new CloudApi.DefaultFallback();
        }
    }

}
