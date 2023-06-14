package com.msel.elearning.model;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Component
public class SigninForm {

	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
}
