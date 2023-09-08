package com.vietphat.newswave.validation.uniquefield;

import com.vietphat.newswave.service.UniqueFieldService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object> {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private HttpServletRequest request;

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
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        if (value == null) return true;

        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String id = pathVariables.get("id");

        return !this.service.fieldValueExists(value, this.fieldName, id);
    }
}
