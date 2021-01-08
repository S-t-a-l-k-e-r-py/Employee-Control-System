package com.myapp.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {
    private final Logger logger = Logger.getLogger(this.getClass());
    @Pointcut("execution(* com.myapp.controllers.employee.*.*(..))")
    private void forEmployeeController() {
    }
    @After("forEmployeeController()")
    public void afterDAOFinally(JoinPoint point) {
        Object[] args = point.getArgs();
        for (Object temp : args) {
            logger.info("JoinPoint arguments: " + temp);
        }
        logger.debug("Executing @After method for EmployeeController class");
    }
}
