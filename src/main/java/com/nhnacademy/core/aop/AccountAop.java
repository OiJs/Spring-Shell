package com.nhnacademy.core.aop;

import com.nhnacademy.core.account.service.AuthenticationService;
import com.nhnacademy.core.exception.LoggingException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AccountAop {

    @Pointcut("execution(* com.nhnacademy.core.account.service.AuthenticationService.login(..)) || " +
            "execution(* com.nhnacademy.core.account.service.AuthenticationService.logout(..))")
    public void accountLog() {}


    @Around("accountLog()")
    public Object accountActivity(ProceedingJoinPoint pjp) {
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        try {
            Object result = pjp.proceed();
            log.info("{}({})", methodName, Arrays.toString(args));
            return result;
        } catch (Throwable e) {
            throw new LoggingException(e.getMessage());
        }
    }
}
