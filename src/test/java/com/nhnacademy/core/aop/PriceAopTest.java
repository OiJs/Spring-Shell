package com.nhnacademy.core.aop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.nhnacademy.core.account.dto.Account;
import com.nhnacademy.core.account.service.AuthenticationService;
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
class PriceAopTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private ProceedingJoinPoint pjp;

    @Mock
    private Signature signature;

    @InjectMocks
    private PriceAop priceAop;

    @BeforeEach
    void setUp() {
        Account account = new Account(1L, "pwd", "이준서");
        when(authenticationService.getCurrentAccount()).thenReturn(account);
        when(pjp.getSignature()).thenReturn(signature);
        when(signature.getDeclaringTypeName()).thenReturn("PriceService");
        when(pjp.getArgs()).thenReturn(new Object[]{});
    }

    @Test
    void priceActivity_success() throws Throwable {
        Object expected = "result";
        when(pjp.proceed()).thenReturn(expected);

        Object result = priceAop.priceActivity(pjp);

        assertEquals(expected, result);
    }

    @Test
    void priceActivity_exception() throws Throwable {
        when(pjp.proceed()).thenThrow(new RuntimeException("fail"));

        assertThrows(LoggingException.class, () -> priceAop.priceActivity(pjp));
    }
}