package com.printease.application.controller;

import com.printease.application.security.dto.RegistrationRequest;
import com.printease.application.security.dto.RegistrationResponse;
import com.printease.application.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class RegistrationController {

	private final UserService userService;

	@PostMapping("/register")
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER','ROLE_SERVICE_PROVIDER')")
	public ResponseEntity<RegistrationResponse> registrationRequest(@Valid @RequestBody RegistrationRequest registrationRequest) {

		final RegistrationResponse registrationResponse = userService.registration(registrationRequest);

		return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
	}

}
