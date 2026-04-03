package com.nhnacademy.core.aop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.core.exception.LoggingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountAopTest {

    @Mock
    private ProceedingJoinPoint pjp;

    @Mock
    private Signature signature;

    @InjectMocks
    private AccountAop accountAop;

    @BeforeEach
    void setUp() {
        when(pjp.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("testMethod");
        when(pjp.getArgs()).thenReturn(new Object[]{"arg1"});
    }

    @Test
    void accountActivity_success() throws Throwable {
        Object expectedResult = "success";
        when(pjp.proceed()).thenReturn(expectedResult);

        Object result = accountAop.accountActivity(pjp);

        assertEquals(expectedResult, result);
        verify(pjp).proceed();
    }

    @Test
    void accountActivity_throwLoggingException() throws Throwable {
        when(pjp.proceed()).thenThrow(new RuntimeException("error"));

        assertThrows(LoggingException.class, () -> accountAop.accountActivity(pjp));
    }
}