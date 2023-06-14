package com.msel.elearning.service;

import java.time.LocalDate;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import com.msel.elearning.model.SignupForm;
import com.msel.elearning.model.User;

@Service
public class SignupFormService {
	
	public static boolean isEmailValid(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }
	
	
}
