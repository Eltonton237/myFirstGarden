package com.msel.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.msel.elearning.model.SignupForm;
import com.msel.elearning.service.SignupFormService;
import com.msel.elearning.service.UserService;

import jakarta.validation.Valid;

@Controller
public class SignupFormController {

    @Autowired
    private UserService userService;

	
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String submitSignupForm(@Valid SignupForm signupForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup-error";
        }
     
        if (userService.findUserByName(signupForm.getName()).isPresent()) {
        	bindingResult.rejectValue("name", "error.name", "");
            return "signup-error";
		}
        
        if (!SignupFormService.isEmailValid(signupForm.getEmail()) || userService.findUserByEmail(signupForm.getEmail()).isPresent()) {
			bindingResult.rejectValue("email","error.email", "");
			return "signup-error";
		}
      
        userService.saveUser(userService.buildUser(signupForm));
        return "pages_abonnes/acceuil_abonne";
    }
}