package com.example.printease.apis;

import com.example.printease.modals.DTO.AuthenticationRequestDTO;
import com.example.printease.modals.DTO.RegistrationRequestDTO;
import com.example.printease.modals.DTO.RegistrationResponseDTO;
import com.example.printease.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<RegistrationResponseDTO> register(@RequestBody RegistrationRequestDTO registrationRequestDTO) {
        return new ResponseEntity< RegistrationResponseDTO >(authenticationService.register(registrationRequestDTO), HttpStatus.CREATED);
    }
}
