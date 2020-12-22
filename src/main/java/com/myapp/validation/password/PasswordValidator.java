package com.myapp.validation.password;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPwd, Object> {

    private String firstPwd;
    private String secondPwd;
    private String message;

    @Override
    public void initialize(ValidPwd constraintAnnotation) {
        this.firstPwd = constraintAnnotation.first();
        this.secondPwd = constraintAnnotation.second();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Object first = new BeanWrapperImpl(value).getPropertyValue(firstPwd);
            Object second = new BeanWrapperImpl(value).getPropertyValue(secondPwd);

            if (!(first != null && first.equals(second))) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(firstPwd)
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
                return false;
            }
        } catch (Exception ignore) {
            return false;
        }
        return true;
    }
}
