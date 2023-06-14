package com.msel.elearning.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.msel.elearning.service.FolderService;

@Controller
public class FolderController {

	@Autowired
    private FolderService folderService;

	protected static String ouvrir = new String();
	
	protected static String path = new String();
	
	@GetMapping("/parcourirAdminouvrir")
	public String parcourirAdminOuvrir(@RequestParam("ouvrir") String ouvrir, Model model) {
		path = ouvrir;
		
		this.ouvrir = path;

		return "redirect:/parcourirAdmin";
	}
	
	
    @GetMapping("/parcourirAdminsupprimer")
    public String deleteFolder( @RequestParam("dropelement") String dropelement) {
    	try {
			folderService.deleteFolder(path + "/" + dropelement);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.ouvrir = path;
    	return "redirect:/parcourirAdmin";
    }
    
    @GetMapping("/parcourirAdminrename")
    public String renameFolderOrFile(@RequestParam("renamedelement") String renamedelement, @RequestParam("newName") String newName) {
    	folderService.renameFolder(renamedelement, newName);
    	this.ouvrir = path;
    	return "redirect:/parcourirAdmin";
    }

    @GetMapping("/parcourirAdmincreate")
    public String createFolder(@RequestParam("nouveauFichier") String nouveauFichier) {
    	try {
			folderService.createFolder(path + "/" + nouveauFichier);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.ouvrir = path;
    	return "redirect:/parcourirAdmin";
    }
    

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file) {

		folderService.FileUploader(file, path);
    	this.ouvrir = path;
		return "redirect:/parcourirAdmin";
	}
	
    @GetMapping("/openFile")
    public ResponseEntity<Resource> openFile(@RequestParam("filename") String filename, @RequestParam("contentType") String contentType, Model model) throws MalformedURLException {
        Path file = Paths.get(FolderService.rootFolderPath + filename);
        Resource resource = new UrlResource(file.toUri());

        model.addAttribute("filenom", filename);
        if (resource.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filename);
            headers.setContentType(MediaType.parseMediaType(contentType));

            try {
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(resource.contentLength())
                        .body(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.notFound().build();
    }
	
}
