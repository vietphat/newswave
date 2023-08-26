package com.vietphat.newswave.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatches {

    public String message() default "Mật khẩu xác nhận không đúng";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
