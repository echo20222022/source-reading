package com.cloud.user.ds;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

//通过切面拦截所有被注解DS标记的方法的调用
@Aspect
@Component
public class DynamicAop {

    @Pointcut("@annotation(com.cloud.user.ds.DS)")
    public void pointCut() { }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            DS ds = method.getAnnotation(DS.class);
            System.out.println("method →" + method.getName() +" ds → " + ds);
            DynamicOperationType operationType = ds.value();
            if (ds == null) {
                operationType = DynamicOperationType.WRITE;
            }
            DynamicOperationHolder.put(operationType);
            return joinPoint.proceed();
        } finally {
            DynamicOperationHolder.clear();
        }
    }
}
