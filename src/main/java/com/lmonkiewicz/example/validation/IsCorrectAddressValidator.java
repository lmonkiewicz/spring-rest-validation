package com.lmonkiewicz.example.validation;

import com.lmonkiewicz.example.model.Address;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by lmonkiewicz on 20.03.2017.
 */
public class IsCorrectAddressValidator implements ConstraintValidator<IsCorrectAddress, Address> {
    @Override
    public void initialize(IsCorrectAddress constraintAnnotation) {

    }

    @Override
    public boolean isValid(Address value, ConstraintValidatorContext context) {
        if (value == null) return true;

        if (value.getName() == null || value.getName().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Name is required")
                    .addPropertyNode("name").addConstraintViolation();
            return false;
        }

        if (value.isCompany()){
            if (value.getTaxId() == null || value.getTaxId().isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("TaxId is required for company address")
                    .addPropertyNode("taxId").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
