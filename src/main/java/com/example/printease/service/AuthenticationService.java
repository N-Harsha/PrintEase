package com.example.printease.service;

import com.example.printease.modal.DTO.AuthenticationRequestDTO;
import com.example.printease.modal.DTO.AuthenticationResponseDTO;
import com.example.printease.modal.DTO.RegistrationRequestDTO;
import com.example.printease.modal.DTO.RegistrationResponseDTO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public AuthenticationResponseDTO login(@NonNull AuthenticationRequestDTO authenticationRequestDTO) {
        return AuthenticationResponseDTO.builder().token("token").build();
    }

    public RegistrationResponseDTO register(@NonNull RegistrationRequestDTO registrationRequestDTO) {

        return RegistrationResponseDTO.builder().message("Registration Successful").build();
    }
}
