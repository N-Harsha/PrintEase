package com.printease.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
	private static final long serialVersionUID = 1L;
	private String token;
	private String email;
	private String name;
	private String role;
}
