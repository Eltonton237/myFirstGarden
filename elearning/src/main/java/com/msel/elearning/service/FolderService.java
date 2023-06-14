package com.msel.elearning.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.msel.elearning.model.Folder;
import com.msel.elearning.model.MyFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService {

    public static String rootFolderPath;

    // Constructor that takes the root folder path as a parameter
    @Autowired
    public FolderService(@Value("${root.folder}") String rootFolderPath) {
        this.rootFolderPath = rootFolderPath;
    }

    // Returns a list of all folders in the root directory
    public List<Folder> getFolders(String path) {
        List<Folder> folders = new ArrayList<>();
        File rootFolder = new File(rootFolderPath, path);
        if (rootFolder.exists() && rootFolder.isDirectory()) {
            for (File file : rootFolder.listFiles()) {
                if (file.isDirectory()) {
                    folders.add(convertToFileObject(file));
                }
            }
        }
        return folders;
    }

    // Returns the folder with the specified ID
    public Folder getFolder(String path) {
        File folder = new File(rootFolderPath, path);
        if (folder.exists() && folder.isDirectory()) {
            return convertToFileObject(folder);
        }
        return null;
    }

    // Creates a new folder with the specified name
    public void createFolder(String name) throws IOException {
        try {
	    	File folder = new File(rootFolderPath, name);
	        if (!folder.mkdir()) {
	            throw new IOException("Failed to create folder");
	        }
        } catch (IOException e) {
        	
		}
     }

    // Deletes the folder with the specified ID
    public void deleteFolder(String path) throws IOException {
        try {
	    	File folder = new File(rootFolderPath, path);
	        if (folder.isFile()) {
				folder.delete();
			} else if (folder.exists() && folder.isDirectory()) {
	        	FileUtils.deleteDirectory(folder);
	        } else {
	            throw new IOException("Folder not found");
	        }
        }catch (IOException e) {
			// TODO: handle exception
		}
    }
    
    //rename the folder
    public void renameFolder(String path, String newName) {
        try {
	    	File folder = new File(rootFolderPath, path);
	        File newFolder = new File(folder.getParentFile(), newName);
	        boolean renamed = folder.renameTo(newFolder);
	        if (!renamed) {
	            throw new IllegalStateException("Impossible de renommer le dossier : " + path);
	        }
        }catch (IllegalStateException e) {
			// TODO: handle exception
		}
    }

    // Converts a File object to a Folder object
    private Folder convertToFileObject(File file) {
        Folder folder = new Folder(file.getName());
        for (File subFile : file.listFiles()) {
            if (subFile.isFile()) {
                folder.getFiles().add(new File(subFile.getName()));
            } else if (subFile.isDirectory()) {
                folder.getSubfolders().add(convertToFileObject(subFile));
            }
        }
        return folder;
    }
    
    public List<MyFile> getPDFs(String path) {
		File rootFolder = new File(rootFolderPath, path);
		List<MyFile> PDFs = new ArrayList<>();
		
		File []files1 = rootFolder.listFiles();
		if(files1 != null) {	
			for (File file : files1) {
				if (file.isFile() && isPDF(file)) {
					PDFs.add(new MyFile(file));
				}
			}
		}		return PDFs;
    }

    public List<MyFile> getVideos(String path) {
		File rootFolder = new File(rootFolderPath, path);
		List<MyFile> videos = new ArrayList<>();
		
		File []files1 = rootFolder.listFiles();
		if(files1 != null) {	
			for (File file : files1) {
				if (file.isFile() && isVideo(file)) {
					videos.add(new MyFile(file));
				}
			}
		}
		return videos;
    }
    public static boolean isVideo(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".mp4") || name.endsWith(".avi") || name.endsWith(".mkv");
    }
    
    public static boolean isPDF(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".pdf");
    }
    

    public void FileUploader(MultipartFile file, String path) {
		try {
	        Path sourcePath = Paths.get(file.getOriginalFilename());
	        Path targetPath = Paths.get(rootFolderPath + path+ File.separator +file.getOriginalFilename());
	        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    
}
