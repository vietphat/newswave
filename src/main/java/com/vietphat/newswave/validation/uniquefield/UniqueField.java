package com.vietphat.newswave.validation.uniquefield;

import com.vietphat.newswave.service.UniqueFieldService;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = UniqueFieldValidator.class)
public @interface UniqueField {

    /*String message() default "{unique.value.violation}";*/
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends UniqueFieldService> service();
    String serviceQualifier() default "";
    String fieldName();

}
