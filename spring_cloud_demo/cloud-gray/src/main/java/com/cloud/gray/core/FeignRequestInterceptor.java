package com.cloud.gray.core;

import com.cloud.gray.Gray;
import com.cloud.gray.GrayAutoConfiguration;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/*@Component
@ConditionalOnBean(GrayAutoConfiguration.Marker.class)*/
public class FeignRequestInterceptor implements RequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Gray.class);

    public FeignRequestInterceptor() {
        LOGGER.info("initialize FeignRequestInterceptor complete");
    }

    @Override
    public void apply(RequestTemplate template) {
        String grayVersion = GrayPropagateInterceptor.VERSION.get();
        LOGGER.info("feign gray version => {}", grayVersion);
        template.header("version", grayVersion);
    }
}
