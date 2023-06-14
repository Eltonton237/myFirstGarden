package com.msel.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.msel.elearning.model.User;
import com.msel.elearning.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/createUser")
	public String createUser(Model model) {
		model.addAttribute("user", new User());
		return "redirect:/";
	}
	
	@GetMapping("/updateUser/{userId}")
	public String updateUser(@PathVariable("userId") final long userId, Model model) {
		model.addAttribute("user", userService.getUser(userId));	
		return "redirect:/";		
	}
	
	@GetMapping("/acceuil_admin_eleve.html")
	public String deleteUser(@RequestParam("element") String element) {
		long userId = Long.parseLong(element);
		userService.deleteUser(userId);
		return "redirect:/acceuil_admin_eleve";		
	}
	
	@PostMapping("/saveUser")
	public ModelAndView saveUser(@ModelAttribute User user) {
		userService.saveUser(user);
		return new ModelAndView("redirect:/");	
	}
	
}
