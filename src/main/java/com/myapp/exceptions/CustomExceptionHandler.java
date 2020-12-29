package com.myapp.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@ControllerAdvice
public class CustomExceptionHandler {

    private final Logger logger = Logger.getLogger(getClass().getName());


    @ExceptionHandler
    public String handleNotFoundExceptions(NotFoundException exc) {
        String exception = exc.getMessage().toUpperCase() + " " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
        logger.info(exception);
        return "error-pages/error-page1";

    }
    @ExceptionHandler
    public String handleAllExceptions(Exception exc) {
        String exception = exc.getMessage() + " " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
        logger.info(exception);
        return "error-pages/error-page2";

    }



}
