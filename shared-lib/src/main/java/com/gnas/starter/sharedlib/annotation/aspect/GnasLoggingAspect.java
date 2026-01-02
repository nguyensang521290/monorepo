package com.gnas.starter.sharedlib.annotation.aspect;

import com.gnas.starter.sharedlib.annotation.GnasLogging;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class GnasLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(GnasLoggingAspect.class);

    @Around("@within(com.gnas.starter.sharedlib.annotation.GnasLogging) || @annotation(com.gnas.starter.sharedlib.annotation.GnasLogging)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        GnasLogging logging = method.getAnnotation(GnasLogging.class);
        if (logging == null) logging = joinPoint.getTarget().getClass().getAnnotation(GnasLogging.class);

        long startTime = System.nanoTime();
        if (logging == null || logging.logArgs()) {
            log.info("→ {}.{} args={}", signature.getDeclaringType().getSimpleName(), signature.getName(), Arrays.toString(signature.getParameterNames()));
        } else {
            log.info("→ {}.{}", signature.getDeclaringType().getSimpleName(), signature.getName());
        }

        try {
            Object result = joinPoint.proceed();
            long tookMs = (System.nanoTime() - startTime) / 1_000_000;
            if (logging != null && logging.logReturn()) {
                log.info("← {}.{} took={}ms result={}", signature.getDeclaringType().getSimpleName(), signature.getName(), tookMs, result);
            } else {
                log.info("← {}.{} took={}ms", signature.getDeclaringType().getSimpleName(), signature.getName(), tookMs);
            }
            return result;
        } catch (Throwable t) {
            long tookMs = (System.nanoTime() - startTime) / 1_000_000;
            log.error("✖ {}.{} failed after {}ms: {}", signature.getDeclaringType().getSimpleName(), signature.getName(), tookMs, t.toString());
            throw t;
        }
    }
}
