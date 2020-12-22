package com.myapp.validation.age;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class AgeValidator implements ConstraintValidator<ValidAge, String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        int age;
        try {
            age = Integer.parseInt(s);
        } catch (Exception ignored) {
            return false;
        }
        return age >= 18;

    }
}
