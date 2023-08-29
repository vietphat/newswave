package com.vietphat.newswave.validation.uniquefield;

import com.vietphat.newswave.service.UniqueFieldService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object> {

    @Autowired
    private ApplicationContext applicationContext;

    private UniqueFieldService service;
    private String fieldName;

    @Override
    public void initialize(UniqueField uniqueField) {

        Class<? extends UniqueFieldService> clazz = uniqueField.service();

        this.fieldName = uniqueField.fieldName();
        String serviceQualifier = uniqueField.serviceQualifier();

        if (!serviceQualifier.equals("")) {
            this.service = this.applicationContext.getBean(serviceQualifier, clazz);
        } else {
            this.service = this.applicationContext.getBean(clazz);
        }
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return !this.service.fieldValueExists(o, this.fieldName);
    }
}
