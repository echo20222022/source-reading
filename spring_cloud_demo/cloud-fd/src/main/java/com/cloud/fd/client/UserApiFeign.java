package com.cloud.fd.client;

import com.cloud.api.CloudApi;
import com.cloud.api.UserApi;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(
        value = "cloud-base",
        contextId = "userApiFeign",
        path = UserApi.PREFIX,
        fallbackFactory = UserApiFeign.DefaultFallbackFactory.class
)
public interface UserApiFeign extends UserApi {

    @Component
    class DefaultFallbackFactory implements FallbackFactory<UserApi> {
        @Override
        public UserApi create(Throwable throwable) {
            throwable.printStackTrace();
            return new UserApi.DefaultFallback();
        }
    }
}
