package com.cloud.gray.core.annotation;

import com.cloud.gray.GrayAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//@Import(ApolloConfig.class)
@Documented
@Inherited
@Import(GrayAutoConfiguration.class)
public @interface EnableGrayConfig {
}
