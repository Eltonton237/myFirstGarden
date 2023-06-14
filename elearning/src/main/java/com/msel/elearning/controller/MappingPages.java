package com.msel.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.msel.elearning.model.Admin;
import com.msel.elearning.model.Teacher;
import com.msel.elearning.model.User;
import com.msel.elearning.service.FolderService;
import com.msel.elearning.service.TeacherService;
import com.msel.elearning.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MappingPages {

	@Autowired
	UserService userService;
	
	@Autowired
	TeacherService teacherService;

	@Autowired
	FolderService folderService;
	
	String nomUtilisateur = new String();
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/se_deconnecter")
	public String seDeconnecter() {
		return "se_deconnecter";
	}
	
	@GetMapping("/en_savoir_plus")
	public String enSavoirPlus() {
		return "en_savoir_plus";
	}
	
	//pour les pages abonnes
	
	@GetMapping("/acceuil_abonne")
	public String acceuilAbonne() {
		return "pages_abonnes/acceuil_abonne";
	}
	
	@GetMapping("/parcourirAbonne")
	public String parcourirAbonne(Model model) {
		if (FolderController.ouvrir.equals("")) {
			FolderController.path = FolderController.ouvrir;
		}
		model.addAttribute("folders", folderService.getFolders(FolderController.ouvrir));
		model.addAttribute("PDFs", folderService.getPDFs(FolderController.ouvrir));
		model.addAttribute("videos", folderService.getVideos(FolderController.ouvrir));
		model.addAttribute("path", FolderController.ouvrir);
		FolderController.ouvrir = "";
		return "pages_abonnes/parcourir";
	}
	
	@GetMapping("/profilAbonne")
	public String profilAbonne() {
		return "pages_abonnes/profil";
	}
	
	// pour les pages admin

	@GetMapping("/acceuil_admin_eleve")
	public String acceuilAdminEleve(Model model) {
		Iterable<User> users = userService.getUsers();
		model.addAttribute("users", users);
		return "pages_admin/acceuil_admin_eleve";
	}
	
	@GetMapping("/acceuil_admin_enseignant")
	public String acceuilAdminEnseignant(Model model) {
		(new TeacherController()).createTeacher(model);
		Iterable<Teacher> teachers = teacherService.getTeachers();
		model.addAttribute("teachers", teachers);
		return "/pages_admin/acceuil_admin_enseignant";
	}
	
	@GetMapping("/parcourirAdmin")
	public String parcourirAdmin(Model model) {
		if (FolderController.ouvrir.equals("")) {
			FolderController.path = FolderController.ouvrir;
		}
		model.addAttribute("folders", folderService.getFolders(FolderController.ouvrir));
		model.addAttribute("PDFs", folderService.getPDFs(FolderController.ouvrir));
		model.addAttribute("videos", folderService.getVideos(FolderController.ouvrir));
		model.addAttribute("path", FolderController.ouvrir);
		FolderController.ouvrir = "";
		return "pages_admin/parcourir";
	}
	
	@GetMapping("/profilAdmin")
	public String profilAdmin() {
		return "pages_admin/profil";
	}
	
	// pour les pages enseignants
	@GetMapping("/acceuil_enseignant")
	public String acceuiEnseignant() {
		return "pages_enseignant/acceuil_enseignant";
	}
	
	@GetMapping("/parcourirEns")
	public String parcoursEns() {
		return "pages_enseignant/parcourir";
	}
	
	@GetMapping("/profilEns")
	public String profilEns() {
		return "pages_enseignant/profil";
	}
	
	
	@GetMapping("/")
	public String profil(HttpServletRequest request, Model model) {
		//ici on met tout ce qui doit être opérationnel au lancement de l'application
		
		
	    // Récupère le cookie "userId"
	    Cookie[] cookies = request.getCookies();
	    Long currentUserId = null;
	    
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("userId")) {
	                currentUserId = Long.parseLong(cookie.getValue());
	                break;
	            }
	            
	            if (cookie.getName().equals("teacherId")) {
	            	currentUserId = Long.parseLong(cookie.getValue());
	                break;
	            }
	            
	            if (cookie.getName().equals("adminId")) {
	            	currentUserId = Long.parseLong(cookie.getValue());
	                break;
	            }
	            
	        }
	    }

	    // Vérifie si le cookie "userId" existe
	    if (currentUserId != null) {
	        // Récupère le nom d'utilisateur à partir de l'identifiant
	    	if (userService.getUser(currentUserId).isPresent()) {
	        	nomUtilisateur = userService.getUser(currentUserId).get().getName();
		        // Ajoute le nom d'utilisateur au modèle
		        model.addAttribute("nomUtilisateur", nomUtilisateur);

		        // Affiche la page de profil
		        return "redirect:/acceuil_abonne";
		        
			}else if(teacherService.getTeacher(currentUserId).isPresent()) {
				nomUtilisateur = teacherService.getTeacher(currentUserId).get().getName();
		        model.addAttribute("nomUtilisateur", nomUtilisateur);

		        return "redirect:/acceuil_enseignant";
			
			}else {
				nomUtilisateur = Admin.getName();
		        model.addAttribute("nomUtilisateur", nomUtilisateur);

		        return "redirect:/acceuil_admin_enseignant";
			}

	    } else {
	    	
	    	model.addAttribute("nomUtilisateur", nomUtilisateur);
	        // Redirige l'utilisateur vers la page de connexion
	    	return "redirect:/index";
	    }
	}
	

}
