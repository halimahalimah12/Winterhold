package com.indocyber.Winterhold.validator;

import com.indocyber.Winterhold.dtos.auth.AuthRegisterDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ComparePasswordValidator implements ConstraintValidator<ComparePassword, AuthRegisterDto> {
    @Override
    public boolean isValid(AuthRegisterDto authRegisterDto, ConstraintValidatorContext constraintValidatorContext) {
        return authRegisterDto.getPassword().equals(authRegisterDto.getConfirmPassword());
    }
}
