package com.myapp.validation.name;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NameValidator implements ConstraintValidator<ValidName, String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.chars().allMatch(Character::isLetter);

    }
}
