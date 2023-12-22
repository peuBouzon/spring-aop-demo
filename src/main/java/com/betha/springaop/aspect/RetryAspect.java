package com.betha.springaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class RetryAspect {
    private final Logger logger = Logger.getLogger(RetryAspect.class.getName());
    private static final int MAX_TRIES = 3;
    private static final int BACK_OFF_TIME = 1000;

    @Pointcut("@annotation(com.betha.springaop.utils.Retry)")
    public void isRetry() {}

    @Around(value = ("isRetry()"), argNames = "joinPoint")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        for (int i=0; i<MAX_TRIES-1; i++) {
            try {
                return joinPoint.proceed();
            } catch (Exception e) {
                logger.severe("Erro ao executar método " + joinPoint.getSignature().getName());
                logger.severe("Tentando novamente em " + BACK_OFF_TIME + " ms ...");
                Thread.sleep(BACK_OFF_TIME);
            }
        }
        return joinPoint.proceed();
    }
}
