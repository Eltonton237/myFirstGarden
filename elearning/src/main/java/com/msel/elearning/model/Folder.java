package com.msel.elearning.model;

import java.util.ArrayList;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Folder {
	
    private String name;
    private List<File> files;
    private List<Folder> subfolders;
    private LocalDate created;
    private LocalDate modified;

    
    public Folder(@Value("${folder.name}") String name) {
        this.name = name;
        this.files = new ArrayList<>();
        this.subfolders = new ArrayList<>();
        this.created = LocalDate.now();
        this.modified = LocalDate.now();
    }
}