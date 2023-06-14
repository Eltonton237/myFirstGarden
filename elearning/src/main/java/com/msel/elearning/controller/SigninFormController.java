package com.msel.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msel.elearning.model.Admin;
import com.msel.elearning.model.SigninForm;
import com.msel.elearning.model.Teacher;
import com.msel.elearning.model.User;
import com.msel.elearning.service.SigninFormService;
import com.msel.elearning.service.TeacherService;
import com.msel.elearning.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class SigninFormController {
	
	public Cookie cookie;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private TeacherService teacherService;
    
    @GetMapping("/signin")
    public String showSigninForm(Model model) {
        model.addAttribute("signinForm", new SigninForm());
        return "signin";
    }

    @PostMapping("/signin")
    public String submitSignupForm(@Valid SigninForm signinForm, BindingResult bindingResult,
    	@RequestParam String email, @RequestParam String password, HttpServletResponse response) {
        /*
         *  a is for the type of connexion :
         *  0 : when error occurs
         *  1 : when it's a user
         *  2 : when it's a teacher 
         */
    	int a = 0;
    	//only to verify if it's bank
    	if (bindingResult.hasErrors()) {
            return "signin-error";
        }
    	
        //email verification
        if (!SigninFormService.isEmailValid(signinForm.getEmail())) {
        	bindingResult.rejectValue("email", "error.email", "");
            return "signin-error";
		}
        
        if( (userService.findUserByEmail(signinForm.getEmail()).isEmpty())) {
        	a=0;
        }else {
        	a=1;
		}
        
        if(a==0) {
        	if(teacherService.findTeacherByEmail(signinForm.getEmail()).isEmpty()) {
        		a=0;
        	}else {
        		a=2;
        	}
        }
        
        if (a==0) {
			if (signinForm.getEmail().equals(Admin.getEmail())) {
				a=3;
			}
		}
        
        if(a==0) {
			bindingResult.rejectValue("email", "error.email");
            return "signin-error";
		}

      //we must hash the password before verification
        if (a==1 && !(User.passwordEncoder().matches(signinForm.getPassword(), userService.findUserByEmail(signinForm.getEmail()).get().getPassword()))) {
			bindingResult.rejectValue("password", "error.password", "");
			return "signin-error";
		}

		if (a==2 && !(Teacher.passwordEncoder().matches(signinForm.getPassword(), teacherService.findTeacherByEmail(signinForm.getEmail()).get().getPassword()))) {
			bindingResult.rejectValue("password", "error.password", "");
			return "signin-error";
		}

		if (a==3 && !signinForm.getPassword().equals(Admin.getPassword())) {
			bindingResult.rejectValue("password", "error.password", "");
			return "signin-error";
		}

		
		//creation du cookie
		
		if (a!=0) {
	            // Crée un cookie avec l'identifiant de l'utilisateur
				if(a==1) {
					cookie = new Cookie("userId", Long.toString(userService.findUserByEmail(signinForm.getEmail()).get().getUserId()));
				}else if(a==2) {
					cookie = new Cookie("teacherId", Long.toString(teacherService.findTeacherByEmail(signinForm.getEmail()).get().getTeacherId()));
				}else {
					cookie = new Cookie("adminId", Long.toString(Admin.getAdminid()));
				}
	            
	            // Définit la durée de vie du cookie à 30 jours
	            cookie.setMaxAge(24 * 60 * 60);

	            // Ajoute le cookie à la réponse HTTP
	            response.addCookie(cookie);

		}
		
        //choose the page where we go
        switch(a) {
        	case 1 : return "redirect:/acceuil_abonne";
        	case 2 : return "redirect:/acceuil_enseignant";
        	default : return "redirect:/acceuil_admin_enseignant";
		}
        
        
        
    }
}
