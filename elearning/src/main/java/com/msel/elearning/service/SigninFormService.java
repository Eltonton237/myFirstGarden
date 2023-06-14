package com.msel.elearning.service;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class SigninFormService {

	public static boolean isEmailValid(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }
}
