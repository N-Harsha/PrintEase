package com.example.printease.api;

import com.example.printease.modal.DTO.AuthenticationRequestDTO;
import com.example.printease.modal.DTO.RegistrationRequestDTO;
import com.example.printease.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationApi {
    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequestDTO));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDTO registrationRequestDTO) {
        return ResponseEntity.ok(authenticationService.register(registrationRequestDTO));
    }
}
