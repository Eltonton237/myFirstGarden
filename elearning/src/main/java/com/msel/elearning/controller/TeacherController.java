package com.msel.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msel.elearning.model.Teacher;
import com.msel.elearning.service.SignupFormService;
import com.msel.elearning.service.TeacherService;

import jakarta.validation.Valid;

@Controller
public class TeacherController {

	@Autowired
	TeacherService teacherService;
	
	//this variable is to know if the submit is correct 
	Boolean isBad = false;
	
/*	
	@GetMapping("/")
	public String home() {
		return "index";
	}
*/
	
	@GetMapping("/createTeacher")
	public void createTeacher(Model model) {
		
		model.addAttribute("isBad", isBad);
		
		//effictive create a new teacher
		model.addAttribute("teacher", new Teacher());
	}
	
	@GetMapping("/updateTeacher/{teacherId}")
	public String updateTeacher(@PathVariable("teacherId") final long teacherId, Model model) {
		model.addAttribute("teacherr", teacherService.getTeacher(teacherId));	
		return "redirect:/";		
	}
	
	@GetMapping("/acceuil_admin_enseignant.html")
	public String deleteTeacher(@RequestParam("dropelement") String dropelement) {
		long teacherId = Long.parseLong(dropelement);
		teacherService.deleteTeacher(teacherId);
		return "redirect:/acceuil_admin_enseignant";		
	}
	
	@PostMapping("/saveTeacher")
	public String saveTeacher(@Valid Teacher teacher, BindingResult bindingResult, Model model) {
		
		
		if (bindingResult.hasErrors()) {
			isBad = true;
			model.addAttribute("isBad", isBad);
			return "pages_admin/acceuil_admin_enseignant";
        }
		
		if (!SignupFormService.isEmailValid(teacher.getEmail()) || teacherService.findTeacherByEmail(teacher.getEmail()).isPresent()) {
			isBad = true;
			model.addAttribute("isBad", isBad);
			bindingResult.rejectValue("email", "error.email");
			return "pages_admin/acceuil_admin_enseignant";
		}
		
		if (!teacherService.isValidContact(teacher.getContact())) {
			isBad = true;
			model.addAttribute("isBad", isBad);
			bindingResult.rejectValue("contact", "error.contact");
			return "pages_admin/acceuil_admin_enseignant";
		}
		
		teacherService.saveTeacher(teacherService.buildTeacher(teacher.getName(), teacher.getEmail(), teacher.getContact()));
		isBad=false;
		model.addAttribute("isBad", isBad);
		return "redirect:/acceuil_admin_enseignant";	
	}
	
}