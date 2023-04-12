package com.printease.application.controller;

import com.printease.application.security.dto.LoginRequest;
import com.printease.application.security.dto.LoginResponse;
import com.printease.application.security.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {

	private final JwtTokenService jwtTokenService;

	@PostMapping("/login")
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER','ROLE_SERVICE_PROVIDER')")
	public ResponseEntity<LoginResponse> loginRequest(@Valid @RequestBody LoginRequest loginRequest) {

		final LoginResponse loginResponse = jwtTokenService.getLoginResponse(loginRequest);

		return ResponseEntity.ok(loginResponse);
	}

}
