package com.vietphat.newswave.validation.passwordmatches;

import com.vietphat.newswave.dto.ResetPasswordDTO;
import com.vietphat.newswave.dto.UserRegistrationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {

        boolean isValid = false;

        if (o instanceof UserRegistrationDTO) {
            UserRegistrationDTO user = (UserRegistrationDTO) o;
            isValid = user.getPassword() != null && user.getPassword().equals(user.getConfirmPassword());
        } else if (o instanceof ResetPasswordDTO) {
            ResetPasswordDTO user = (ResetPasswordDTO) o;
            isValid = user.getPassword() != null && user.getPassword().equals(user.getConfirmPassword());
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Mật khẩu xác nhận không đúng")
                    .addPropertyNode("confirmPassword").addConstraintViolation();
        }

        return isValid;
    }
}
