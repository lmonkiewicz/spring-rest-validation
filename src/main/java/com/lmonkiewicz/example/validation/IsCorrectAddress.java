package com.lmonkiewicz.example.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by lmonkiewicz on 20.03.2017.
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { IsCorrectAddressValidator.class })
public @interface IsCorrectAddress {
    String message() default "Address is incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
