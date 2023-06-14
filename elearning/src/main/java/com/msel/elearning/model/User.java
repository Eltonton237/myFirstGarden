package com.msel.elearning.model;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/*
 * class user to represent the table user in our database
 */

@Data
@Entity
@Table(name = "user")
public class User {
	
	/*
	 * user_id is automatically add to the table
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId;
	
	private String name;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(name = "subscription_date", nullable = false)
	private LocalDate subscriptionDate;
	
	//it's for hashing the password
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    public void setPassword(String password) {
        this.password = passwordEncoder().encode(password);
    }

}
