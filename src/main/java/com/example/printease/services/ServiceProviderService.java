package com.example.printease.services;

import com.example.printease.enums.UserRoles;
import com.example.printease.modals.DTO.RegistrationRequestDTO;
import com.example.printease.modals.DTO.RegistrationResponseDTO;
import com.example.printease.modals.ServiceProvider;
import com.example.printease.repositories.ServiceProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ServiceProviderService {
    Logger logger = Logger.getLogger(ServiceProviderService.class.getName());
    private final PasswordEncoder passwordEncoder;

    private final ServiceProviderRepository serviceProviderRepository;


    public RegistrationResponseDTO serviceProviderRegistration(RegistrationRequestDTO registrationRequestDTO) {
        ServiceProvider serviceProvider = ServiceProvider.builder()
                .name(registrationRequestDTO.getName())
                .email(registrationRequestDTO.getEmail())
                .phoneNumber(registrationRequestDTO.getPhoneNumber())
                .userRole(UserRoles.ROLE_SERVICE_PROVIDER)
//                .associatedServices(new ArrayList<>())
                .businessName(registrationRequestDTO.getBusinessName())
                .gstIn(registrationRequestDTO.getGstIn())
                .accountCreatedOn(LocalDateTime.now())
                .accountUpdatedOn(LocalDateTime.now())
                .password(passwordEncoder.encode(registrationRequestDTO.getPassword()))
                .address(registrationRequestDTO.getAddress())
//                .ratings(new ArrayList<>())
                .build();

        serviceProviderRepository.save(serviceProvider);

        return RegistrationResponseDTO.builder().message("Registration Successful").build();
    }
}
