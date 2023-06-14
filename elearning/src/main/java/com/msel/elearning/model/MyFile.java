package com.msel.elearning.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MyFile {

	private String name;
	private String path;
	private String modified;
	private Long size;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public MyFile(@Value("${folder.name}") File file) {
		this.name = file.getName();
		this.path = file.getPath();
		
		this.modified = (new SimpleDateFormat("yyyy-MM-dd")).format(file.lastModified());
				;
		this.size = file.length()/1024;
	}
}
