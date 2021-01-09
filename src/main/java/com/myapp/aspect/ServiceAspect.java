package com.myapp.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {
    private final Logger logger = Logger.getLogger(this.getClass());

    @AfterThrowing(pointcut = "execution(* com.myapp.service.EmployeeServiceImpl.*(..))", throwing = "exception")
    public void afterDAOFinally(JoinPoint point, Exception exception) {
        String method = point.getSignature().toShortString();
        logger.debug("Trow new exception in: " + method + "Exception is: " + exception);
    }
}
