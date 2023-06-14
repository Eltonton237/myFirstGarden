package com.msel.elearning.model;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 * class user to represent the table user in our database
 */

@Data
@Entity
@Table(name = "teacher")
public class Teacher {
	
	/*
	 * teacher_id is automatically add to the table
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "teacher_id")
	private long teacherId;
	
	@NotBlank
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@NotBlank
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@NotBlank
	@Column(name = "contact", unique = true, nullable = false)
	private String contact;
	//this is the pass hash for database
	private String password;
	
	//this is the password for the view
	@Column(name = "password_view", unique = true, nullable = false)
	private String passwordView;
	
	@Column(name = "subscription_date", nullable = false)
	private LocalDate subscriptionDate;
	
	//auto-generation of password using random
	public String autoPasswordBuild() {

    	//random process
    	int year = LocalDate.now().getYear();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_.";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        int length = 5;
        
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        
        return year + sb.toString();    	
    }
    
	//it's for hashing the password
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    public void setPassword() {
    	this.passwordView = autoPasswordBuild();
        this.password = passwordEncoder().encode(passwordView);
    }

}

