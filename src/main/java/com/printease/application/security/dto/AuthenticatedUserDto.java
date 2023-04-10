package com.printease.application.security.dto;

import com.printease.application.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AuthenticatedUserDto {

	private String name;

	private String email;

	private String password;

	private UserRole userRole;



}
