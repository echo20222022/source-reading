package com.cloud.user.ds;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//标记注解
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DS {
    DynamicOperationType value() default DynamicOperationType.READ;
}
