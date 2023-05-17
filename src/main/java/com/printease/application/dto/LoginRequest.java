package com.printease.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "{login_email_not_empty}")
	private String email;

	@NotEmpty(message = "{login_password_not_empty}")
	private String password;

}
