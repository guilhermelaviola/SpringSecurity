package com.example.demo.security;

// Setting permissions for each type of user
public enum ApplicationUserPermission {
	CUSTOMER_READ("customer:read"),
	CUSTOMER_WRITE("customer:write"),
	COURSE_READ("course:read"),
	COURSE_WRITE("course:write");
	
	private final String permission;

	private ApplicationUserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
	
}
