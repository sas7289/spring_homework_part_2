package com.example.my_market.util.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

@Component
@Aspect
public class GenUuidAspect {

    @Around("@annotation(generateUUID)")
    public Object generateUuid(ProceedingJoinPoint p, GenerateUUID generateUUID) throws Throwable {
        Object o = p.proceed();
        Method setId = o.getClass().getMethod("setId", UUID.class);
        setId.invoke(o, UUID.randomUUID());
        return p;
    }
}
