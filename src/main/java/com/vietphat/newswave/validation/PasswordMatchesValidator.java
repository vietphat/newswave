package com.vietphat.newswave.validation;

import com.vietphat.newswave.dto.UserRegistrationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationDTO> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserRegistrationDTO user, ConstraintValidatorContext context) {

        boolean isValid = user.getPassword().equals(user.getConfirmPassword());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Mật khẩu xác nhận không đúng")
                    .addPropertyNode("confirmPassword").addConstraintViolation();
        }

        return isValid;
    }
}
