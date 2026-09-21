package com.andef.javalabs.lab3.aspect;

import com.andef.javalabs.lab3.model.Storable;
import com.andef.javalabs.lab3.model.WithdrawalResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class WarehouseAspect {

    private static final String WITHDRAW_POINTCUT =
            "execution(* com.andef.javalabs.lab3.service.Warehouse.withdraw(..))";

    @Before(WITHDRAW_POINTCUT)
    public void beforeWithdrawal(JoinPoint joinPoint) {
        System.out.printf(
                "[Before] signature=%s, arguments=%s%n",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs())
        );
    }

    @AfterReturning(pointcut = WITHDRAW_POINTCUT, returning = "result")
    public void afterSuccessfulWithdrawal(JoinPoint joinPoint, WithdrawalResult result) {
        System.out.printf(
                "[AfterReturning] method=%s, requester=%s, issued=%d%n",
                joinPoint.getSignature().getName(),
                result.requestedBy(),
                result.items().size()
        );
    }

    @AfterThrowing(pointcut = WITHDRAW_POINTCUT, throwing = "error")
    public void afterFailedWithdrawal(JoinPoint joinPoint, Throwable error) {
        System.out.printf(
                "[AfterThrowing] method=%s, error=%s: %s%n",
                joinPoint.getSignature().getName(),
                error.getClass().getSimpleName(),
                error.getMessage()
        );
    }

    @After(WITHDRAW_POINTCUT)
    public void afterWithdrawalFinally(JoinPoint joinPoint) {
        System.out.println("[After/finally] completed: " + joinPoint.getSignature().toShortString());
    }

    @Around(WITHDRAW_POINTCUT)
    public Object aroundWithdrawal(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs().clone();
        String requestedBy = (String) arguments[0];
        Class<?> itemType = (Class<?>) arguments[1];
        int quantity = (int) arguments[2];

        System.out.printf(
                "[Around before] requester=%s, itemType=%s, quantity=%d%n",
                requestedBy,
                itemType.getSimpleName(),
                quantity
        );

        if ("guest".equalsIgnoreCase(requestedBy) && quantity > 1) {
            arguments[2] = 1;
            System.out.println("[Around] guest quantity changed to 1");
        }

        try {
            WithdrawalResult original = (WithdrawalResult) joinPoint.proceed(arguments);
            WithdrawalResult changed = new WithdrawalResult(
                    original.requestedBy(),
                    List.copyOf(original.items()),
                    "[Checked by aspect] " + original.message()
            );
            System.out.println("[Around after] result message changed");
            return changed;
        } catch (RuntimeException error) {
            System.out.println("[Around after] error handled: " + error.getMessage());
            return new WithdrawalResult(
                    requestedBy,
                    List.<Storable>of(),
                    "Request rejected: " + error.getMessage()
            );
        }
    }
}
