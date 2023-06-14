package com.msel.elearning.model;

import org.springframework.stereotype.Component;

@Component
public class Admin {

	private static final long adminId = 999999999;
	
	private static final String name = "ADMIN";
	
	private static final String email = "admin@msel.com";
	
	private static final String password = "M.Sel";

	public static String getEmail() {
		return email;
	}

	public static String getPassword() {
		return password;
	}

	public static long getAdminid() {
		return adminId;
	}

	public static String getName() {
		return name;
	}
}
