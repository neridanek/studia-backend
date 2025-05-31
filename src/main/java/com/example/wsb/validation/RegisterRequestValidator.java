package com.example.wsb.validation;
import com.example.wsb.security.auth.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;


@Component
public class RegisterRequestValidator implements ConstraintValidator<CheckRegisterRequest, RegisterRequest> {
    @Override
    public void initialize(CheckRegisterRequest constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterRequest registerRequest, ConstraintValidatorContext constraintValidatorContext) {
//        if (registerRequest.getFirstName() == null || registerRequest.getFirstName().trim().isEmpty()) {
//            constraintValidatorContext.buildConstraintViolationWithTemplate("First Name must not be empty.")
//                    .addPropertyNode("firstName").addConstraintViolation();
//            return false;
//        }
//
//        if (registerRequest.getLastName() == null || registerRequest.getLastName().trim().isEmpty()) {
//            constraintValidatorContext.buildConstraintViolationWithTemplate("Last Name must not be empty.")
//                    .addPropertyNode("lastName").addConstraintViolation();
//            return false;
//        }

        if (registerRequest.getLogin() == null || registerRequest.getLogin().trim().isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Login must not be empty.")
                    .addPropertyNode("login").addConstraintViolation();
            return false;
        }

        if (registerRequest.getPassword() == null || registerRequest.getPassword().trim().isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password must not be empty.")
                    .addPropertyNode("password").addConstraintViolation();
            return false;
        } else if (registerRequest.getPassword().length() < 8) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password must be at least 8 characters long.")
                    .addPropertyNode("password").addConstraintViolation();
            return false;
        }

//        if (registerRequest.getEmail() == null || registerRequest.getEmail().trim().isEmpty()) {
//            constraintValidatorContext.buildConstraintViolationWithTemplate("Email must not be empty.")
//                    .addPropertyNode("email").addConstraintViolation();
//            return false;
//        } else if (!registerRequest.getEmail().matches("[\\w.-]+@[\\w.-]+")) {
//            constraintValidatorContext.buildConstraintViolationWithTemplate("Email must be a well-formed email address.")
//                    .addPropertyNode("email").addConstraintViolation();
//            return false;
//        }

        return true;
    }
}
