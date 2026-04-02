package com.nhnacademy.core.aop;

import com.nhnacademy.core.account.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Aspect
public class PriceAop {
    private final AuthenticationService authenticationService;

    @Pointcut("within(com.nhnacademy.core.price.service.impl.*)")
    public void priceLog() {
    }

    @Around("priceLog()")
    public Object priceActivity(ProceedingJoinPoint pjp) {
        String methodName = pjp.getSignature().getDeclaringTypeName();
        Object[] args = pjp.getArgs();

        log.info("----- {} class {}({}) ----->", authenticationService.getCurrentAccount().getName(), methodName, args);
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            log.info("<----- {} class {}({}) -----", authenticationService.getCurrentAccount().getName(), methodName,
                    args);

        }

    }
}