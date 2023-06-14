package com.msel.elearning.model;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Component
@Data
public class SignupForm {

	@NotBlank
	private String name;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
}
